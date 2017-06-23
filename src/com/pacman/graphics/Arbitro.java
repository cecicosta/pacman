package com.pacman.graphics;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.pacman.elementos.Fantasma;
import com.pacman.elementos.Pacman;
import com.pacman.entrada.ControladorAutomato.Estados;
import com.pacman.entrada.ControladorFantasma;
import com.pacman.entrada.ControladorPacman;

public class Arbitro extends Frame implements ActionListener, WindowListener, Runnable, KeyListener{

	/**Classe que implementa o Árbitro no jogo pacman.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MenuBar menu;
	private Menu ajuda;
	private MenuItem sobre;
	private int altura;
	private int largura;
	private int sinalAtualizar = 0;
	private long duracaoFrame = 30;
	private Thread temporizador;
	private boolean gameInit = false;
	
	RenderizadorLabirinto labirinto;
	
	private Fantasma[] fantasmas = new Fantasma[4];
	private Pacman pacman;
	private Estados pacmanEstado = Estados.PARADO;
	
	/**
	* Construtor do Árbitro. É responsavel por inicializar os elementos 
	* da interface e elementos móveis como o pacman e os fantasmas.
	*/
	public Arbitro(){
		super("PAC MAN");
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		largura = gd.getDisplayMode().getWidth()/2;
		altura = gd.getDisplayMode().getHeight()/2;
		setSize(largura, altura);
		
		addWindowListener(this);
		
		iniGUI();
		sobre.addActionListener(this);
		addKeyListener(this);
		
		labirinto = new RenderizadorLabirinto(this);
		
		for(int i=0; i<4; i++){
			fantasmas[i] = new Fantasma(this,new ControladorFantasma(0,0), Integer.toString(i).charAt(0));
			fantasmas[i].iniciar();
		}
		
		pacman = new Pacman(this, new ControladorPacman(0, 0), 's');
		pacman.iniciar();
		pacmanEstado = Estados.PARADO;
		
		this.setVisible(true);
	}
	
	/**
	* Este método inicializa os elementos da GUI, como menus e botões.
	*/
	void iniGUI()
	{
		menu = new MenuBar();
		ajuda = new Menu("Ajuda");
		sobre = new MenuItem("Sobre");

		ajuda.add(sobre);
		menu.add(ajuda);

		setMenuBar(menu);
		
		addNotify();
	}
	

	/**
	* Este método cria uma thread para interromper as threads dos elementos 
	* móveis antes de reposicionar.
	*/
	void reposicionar(){
		Thread t = new Thread(new Runnable() {
			
			public void run()
			{
				boolean todosInterrompidos = false;
				try{
					for(int i=0; i<4; i++){
						fantasmas[i].getThread().interrupt();
					}
					pacman.getThread().interrupt();
				}catch(Exception e){
					e.printStackTrace();
				}
				while(!todosInterrompidos){
					try { Thread.sleep(10); } 
					catch (InterruptedException e) {
						return;
					}
					todosInterrompidos = true;
					for(int i=0; i<4; i++){
						if(fantasmas[i].getThread().isAlive()){
							todosInterrompidos = false;
							break;
						}
					}
					todosInterrompidos = pacman.getThread().isAlive()? false: todosInterrompidos;
				}
				for(int i=0; i<4; i++){
					fantasmas[i].reiniciar();
					fantasmas[i].iniciar();
				}
				pacman.reiniciar();
				pacman.iniciar();
				pacmanEstado = Estados.PARADO;
			}
		});
		t.start();
	}
	
	/**
	* Este método reinicia os elementos móveis e o estado do laborinto 
	* para o estado inicial.
	*/
	void reiniciar(){
		
	}
	
	/**
	* Este método dispara a thread do árbitro
	*/
	void iniciarLoop(){
		temporizador = new Thread(this);
		temporizador.start();
	}
	
	/**
	* Método invocado pelo Frame para atualizar a tela.
	* @param g Objeto do tipo Graphic, utilizado para renderizar as imagens
	* no Frame.
	*/
	public void paint(Graphics g){
		
		if (gameInit == false){	
			setSize(largura, altura);
	
			setResizable(false);
			gameInit = true;
			iniciarLoop();
		}
		g.setColor(Color.black);
		g.fillRect(0,0, largura, altura);	
	}
	
	/**
	* Chama as rotinas específicas para atualizar os elementos móveis
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens
	* no Frame.
	*/
	public void update(Graphics g)
	{
		if (sinalAtualizar > 0){

			int posX = largura/2 - labirinto.getLargura()*labirinto.getDimensao()/2;
			int posY = altura/2 - labirinto.getAltura()*labirinto.getDimensao()/2;
			labirinto.renderizar(g, posX, posY);
			
			pacmanAtualizacao(g, posX, posY);
			
			fantasmasAtualizacao(g, posX, posY);
			
			sinalAtualizar = 0;
		}
	}
	
	/**
	* Este método chama as rotinas especificas do pacman para atualizar seu estado
	* e animar e renderizar os sprites na tela.
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens 
	* no Frame.
	* @param posX Posição X global na tela onde os objetos estão sendo renderizados.
	* @param posY Posição Y global na tela onde os objetos estão sendo renderizados.
	*/
	void pacmanAtualizacao(Graphics g, int posX, int posY){
		//Passos para atualizar o controlador esperam até pacman atingir posicao de destino
		pacman.atualizarControlador(pacmanEstado, pacman.movendo());
		pacman.move(g, posX, posY);
		
	}
	
	void fantasmasAtualizacao(Graphics g, int posX, int posY){
		int[] coord = pacman.getControlador().getCoordenadas();
		
		for(int i=0; i<4; i++){
			fantasmas[i].move(g, posX, posY, coord[0], coord[1]);
			int[] pos = fantasmas[i].getControlador().getCoordenadas();
			//Checa a condição de morte do pacman "encostar em um fantasma"
			if(pos[0] == coord[0] && pos[1] == coord[1]){
				pacman.getControlador().setEntrada('m');
				reposicionar();
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) { 
		for(int i=0; i<4; i++){
			fantasmas[i].getThread().interrupt();
		}
		pacman.getThread().interrupt();
		temporizador.interrupt();
		dispose(); 
	}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {}
	
	public void run() {
		while (true) {	
			try { Thread.sleep(duracaoFrame); } 
			catch (InterruptedException e) {
				return;
			}
	
			sinalAtualizar++;
			update(this.getGraphics());
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {		
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			pacmanEstado = Estados.DIREITA;
			break;
		case KeyEvent.VK_LEFT:
			pacmanEstado = Estados.ESQUERDA;
			break;
		case KeyEvent.VK_UP:
			pacmanEstado = Estados.CIMA;
			break;
		case KeyEvent.VK_DOWN:
			pacmanEstado = Estados.BAIXO;
			break;
		default:
		}
		
		arg0.consume();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}

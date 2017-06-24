package com.pacman.graphics;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
import com.pacman.entrada.ControladorFantasma;
import com.pacman.entrada.ControladorPacman;
import com.pacman.entrada.Labirinto;

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
	private Thread temporizador = new Thread(this);;
	private boolean gameInit = false;
	
	private boolean exibirTelaInicial = true;
	private int contadorVidas = 3;
	private Animador vida;
	private Animador telaInicial;
	private RenderizadorLabirinto labirinto;
	
	private Fantasma[] fantasmas = new Fantasma[4];
	private Pacman pacman;
	private char entrada = 'p';
	private boolean reiniciando;
	private Button button;
	private Image imgNome;
	private Image imgPontos;
	
	/**
	* Construtor do Árbitro. É responsavel por inicializar os elementos 
	* da interface e elementos móveis como o pacman e os fantasmas.
	*/
	public Arbitro(){
		super("PAC MAN");
		setLayout(null);
		largura = 640;
		altura = 480;
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
		
		vida = new Animador(this);
		vida.carregarFrames("pacman-large.png", 7, 5, 1, 1, 16, 0.25f);
		vida.Play(Animador.Frames.NONE);
		
		telaInicial = new Animador(this);
		telaInicial.carregarFrames("capa.png", 0, 0, 1, 1, largura, 0.5f);
		telaInicial.Play(Animador.Frames.NONE);
		
	    //Cria botão para iniciar o jogo
	    button = new Button("Iniciar");
	    button.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				setFocusable(true);
				requestFocusInWindow();
				button.setVisible(false);
				exibirTelaInicial = false;
			}
		});
	    add(button);
	    button.setBounds(270, 250, 100, 30);
	    revalidate();
		addNotify();
	}
	

	/**
	* Este método cria uma thread para interromper as threads dos elementos móveis e do Árbitro
	* antes de reposicionar.
	*/
	void reposicionar(){
	
		if(reiniciando)
			return;
		contadorVidas--;
		reiniciando = true;
		Thread t = new Thread(new Runnable() {
			
			public void run()
			{
				//Pequeno intervalo antes que as posições sejam reiniciadas
				try { Thread.sleep(2000); } 
				catch (InterruptedException e) {
					return;
				}
				entrada = 'p';
				boolean todosInterrompidos = false;
				//Garante que primeiramente o thread do árbitro é interrompida
				try{ temporizador.interrupt();} catch(Exception e){}
				while(temporizador.isAlive());
				
				try{
					temporizador.interrupt();
					for(int i=0; i<4; i++){
						fantasmas[i].getThread().interrupt();
					}
					pacman.getThread().interrupt();
				}catch(Exception e){
					e.printStackTrace();
				}
				//Espera até que todas as threads sejam interrompidas
				while(!todosInterrompidos){
					todosInterrompidos = true;
					for(int i=0; i<4; i++){
						if(fantasmas[i].getThread().isAlive()){
							todosInterrompidos = false;
							break;
						}
					}
					todosInterrompidos = pacman.getThread().isAlive()? false: todosInterrompidos;
				}
				//Reinicia todas as threads
				for(int i=0; i<4; i++){
					fantasmas[i].reiniciar();
					fantasmas[i].iniciar();
				}
				pacman.reiniciar();
				pacman.iniciar();
				
				iniciarLoop();
				reiniciando = false;
			}
		});
		t.start();
	}
	
	/**
	* Este método reinicia os elementos móveis e o estado do laborinto 
	* para o estado inicial.
	*/
	void reiniciar(){
		Labirinto.reiniciar();
		contadorVidas = 3;
		pacman.getControlador().setContadotorPontos(0);
		reposicionar();
		labirinto.desenhar();
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
	
			
			imgNome = createImage(250,30);
			Graphics imgNomeG = imgNome.getGraphics();
			imgNomeG.setColor(Color.black);
			imgNomeG.fillRect(0, 0, 250, 30);
			imgNomeG.setColor(Color.green);
			imgNomeG.setFont(new Font("Helvetica", Font.BOLD, 12));
			imgNomeG.drawString("Desenvolvido Por: Luis Felipe Nogueira", 0, 20);
			
			imgPontos = createImage(250,30);
			
			setResizable(false);
			gameInit = true;
			iniciarLoop();
		}
	

	}
	
	/**
	* Chama as rotinas específicas para atualizar os elementos móveis
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens
	* no Frame.
	*/
	public void update(Graphics g)
	{
		//if (sinalAtualizar > 0)
		{
			
			g.setColor(Color.black);
			g.fillRect(0,0, largura, altura);
			
			int posX = largura/2 - labirinto.getLargura()*labirinto.getDimensao()/2;
			int posY = altura/2 - labirinto.getAltura()*labirinto.getDimensao()/2;
			labirinto.renderizar(g, posX, posY);
			
			fantasmasAtualizacao(g, posX, posY);
			pacmanAtualizacao(g, posX, posY);
			
			for(int i=0; i<contadorVidas; i++){
				vida.Animar(g, posX + (labirinto.getLargura()-i - 2)*labirinto.getDimensao(), 
								   posY + labirinto.getAltura()*labirinto.getDimensao());
			}
			
			//Atualiza os pontos na tela - Quantidade de bolinhas comidas
			Graphics imgPontosG = imgPontos.getGraphics();
			imgPontosG.setColor(Color.black);
			imgPontosG.fillRect(0, 0, 250, 30);
			imgPontosG.setColor(Color.green);
			imgPontosG.setFont(new Font("Helvetica", Font.BOLD, 12));
			imgPontosG.drawString("Pontos: " + pacman.getControlador().getContadotorPontos(), 0, 20);
			g.drawImage(imgPontos, 
					50, 420, this);
			sinalAtualizar = 0;
			
			if(exibirTelaInicial){
				g.setColor(Color.black);
				g.fillRect(0,0, largura, altura);
				button.setVisible(true);
				telaInicial.Animar(g, 0, 0);
				g.drawImage(imgNome, 
						200, 300, this);
			}
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
		pacman.atualizarControlador(entrada, pacman.movendo());
		pacman.move(g, posX, posY);
		if(Labirinto.vazio()) //Chaca condição de vitória do pacman, "nenhum '.' no labirinto
			reiniciar();
	}
	
	/**
	* Este método chama as rotinas especificas dos fantasmas para atualizar seus estados
	* e, animar e renderizar os sprites na tela.
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens 
	* no Frame.
	* @param posX Posição X global na tela onde os objetos estão sendo renderizados.
	* @param posY Posição Y global na tela onde os objetos estão sendo renderizados.
	*/
	void fantasmasAtualizacao(Graphics g, int posX, int posY){
		//Obtém coordenadas do pacma, para que os fantasmas o localizem
		int[] coord = pacman.getControlador().getCoordenadas();
		
		for(int i=0; i<4; i++){
			fantasmas[i].move(g, posX, posY, coord[0], coord[1]);
			int[] pos = fantasmas[i].getControlador().getCoordenadas();
			//Checa a condição de morte do pacman "encostar em um fantasma"
			if(pos[0] == coord[0] && pos[1] == coord[1]){
				entrada = 'm';
				if(contadorVidas > 0)
					reposicionar(); //reinicia os elementos móveis
				else
					reiniciar();
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
			entrada = 'd';
			break;
		case KeyEvent.VK_LEFT:
			entrada = 'e';
			break;
		case KeyEvent.VK_UP:
			entrada = 'c';
			break;
		case KeyEvent.VK_DOWN:
			entrada = 'b';
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

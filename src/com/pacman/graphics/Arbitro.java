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

import com.pacman.entrada.ControladorAutomato;
import com.pacman.entrada.ControladorAutomato.Estados;
import com.pacman.entrada.ControladorFantasma;
import com.pacman.entrada.ControladorPacman;
import com.pacman.entrada.Labirinto;
import com.pacman.entrada.MaquinaDeEstados;
import com.pacman.graphics.AnimacaoPacman.Frames;

public class Arbitro extends Frame implements ActionListener, WindowListener, Runnable, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MenuBar menu;
	private Menu ajuda;
	private MenuItem sobre;
	private boolean gameInit = false;
	
	AnimacaoFantasmas[] fantasmas = new AnimacaoFantasmas[4];
	AnimacaoPacman pacman;
	DesenharLabirinto labirinto;
	private int sinalAtualizar = 0;
	private long duracaoFrame = 30;
	private Thread temporizador;
	
	private int altura;
	private int largura;

	private Thread threadPcman;
	private ControladorFantasma[] controlFantasmas = new ControladorFantasma[4];
	private ControladorPacman controlePcman;
	private Thread[] threadFantasmas = new Thread[4];
	private int[] ultimaPosPcman = new int[2];
	private boolean posicaoAtualizada = false;
	private float velocidade = 0;
	private float speed = 0.25f;
	private Estados novoEstado = Estados.PARADO;
	private MaquinaDeEstados maquinaEstPcman;
	private int estadoPipeline;
	private float[] velocidadeF = new float[]{0,0,0,0};
	private int[] estadoPipelineF = new int[]{0,0,0,0};
	private MaquinaDeEstados[] inFantasma = new MaquinaDeEstados[4];
	private boolean[] posicaoAtualizadaF = new boolean[]{false,false,false,false};
	private int[] ultimaPosFant = new int[8];
	private float speedF = 0.25f;
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
		
		labirinto = new DesenharLabirinto(this);
		
		for(int i=0; i<4; i++){
			fantasmas[i] = new AnimacaoFantasmas(this);
			fantasmas[i].carregarFrames("pacman-large.png", 12+i, 0, 1, 8, 16);
			fantasmas[i].Play(AnimacaoFantasmas.Frames.CIMA);
			
			int[] pos = Labirinto.coordenadaCelula(Integer.toString(i).charAt(0));
			ultimaPosFant[i*2] = pos[0]*labirinto.getDimensao();
			ultimaPosFant[i*2+1] = pos[1]*labirinto.getDimensao();
			controlFantasmas[i] = new ControladorFantasma(pos[0], pos[1]);
			inFantasma[i] = new MaquinaDeEstados(controlFantasmas[i]);
			threadFantasmas[i]= new Thread(inFantasma[i]);
			threadFantasmas[i].start();
		}
		
		pacman = new AnimacaoPacman(this);
		pacman.Play(AnimacaoPacman.Frames.DIREITA);
		
		//Cria e inicia thread do controlador do pacman
		int[] coord = Labirinto.coordenadaInicial();
		ultimaPosPcman = new int[]{coord[0]*labirinto.getDimensao(),
				  				  coord[1]*labirinto.getDimensao()};
		controlePcman = new ControladorPacman(coord[0], coord[1]);
		maquinaEstPcman = new MaquinaDeEstados(controlePcman);
		threadPcman = new Thread(maquinaEstPcman);
		threadPcman.start();
		
		this.setVisible(true);
		
	}
	
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
	
	void iniciarLoop(){
		temporizador = new Thread(this);
		temporizador.start();
	}
	
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
	
	//Atualiza entidades baseadas no estado e posição
	public void update(Graphics g)
	{
		if (sinalAtualizar > 0){

			int posX = largura/2 - labirinto.getLargura()*labirinto.getDimensao()/2;
			int posY = altura/2 - labirinto.getAltura()*labirinto.getDimensao()/2;
			labirinto.renderizar(g, posX, posY);
			
			pacmanAtualizacao(g, posX, posY);
			
			int[] coord = controlePcman.getCoord();
			int dim = labirinto.getDimensao();
			
			
			for(int i=0; i<4; i++){
				
				if(estadoPipelineF[i] == 0){
					controlFantasmas[i].controlador(coord[0], coord[1]);
					estadoPipelineF[i] = 1;
				}else if(estadoPipelineF[i] == 1){
					estadoPipelineF[i] = inFantasma[i].lendoPalavra()? 1: 2;
				}else if(estadoPipelineF[i] == 2){
					if(controlFantasmas[i].coordAtualizada()){
						estadoPipelineF[i] = 3;
					}else{
						estadoPipelineF[i] = 0;
					}
				}else if(estadoPipelineF[i] == 3){
					estadoPipelineF[i] = posicaoAtualizadaF[i]? 0: 3;
				}
				
				velocidadeF[i] += speedF ;
				int[] pos = controlFantasmas[i].getCoord();
				
				int dx = (int) ((pos[0]*dim - ultimaPosFant[i*2])*velocidadeF[i]);
				int dy = (int) ((pos[1]*dim - ultimaPosFant[i*2+1])*velocidadeF[i]);
				
				fantasmas[i].Animar(g, posX + ultimaPosFant[i*2+1] + dy - dim/2, 
						   			   posY + ultimaPosFant[i*2] + dx - dim/2);
				
				if(pos[0]*dim == ultimaPosFant [i*2] + dx && pos[1]*dim == ultimaPosFant[i*2+1] + dy){
					ultimaPosFant[i*2] = pos[0]*dim;
					ultimaPosFant[i*2+1] = pos[1]*dim;
					velocidadeF[i] = 0;
					posicaoAtualizadaF[i] = true;
				}else{
					posicaoAtualizadaF[i] = false;
				}
				
				//Checa a condição de morte do pacman "encostar em um fantasma"
				if(pos[0] == coord[0] && pos[1] == coord[1]){
					controlePcman.controlador('m');
				}
			}
			sinalAtualizar = 0;
		}
	}
	
	void pacmanAtualizacao(Graphics g, int posX, int posY){
		if(estadoPipeline == 0){
			switch(novoEstado){
			case PARADO:
				pacman.Play(Frames.PARADO); break;
			case DIREITA:
				controlePcman.controlador('d');
				estadoPipeline = 1;
				pacman.Play(Frames.DIREITA); break;
			case ESQUERDA:
				controlePcman.controlador('e');
				estadoPipeline = 1;
				pacman.Play(Frames.ESQUERDA); break;
			case CIMA:
				controlePcman.controlador('c');
				estadoPipeline = 1;
				pacman.Play(Frames.CIMA); break;
			case BAIXO:
				controlePcman.controlador('b');
				estadoPipeline = 1;
				pacman.Play(Frames.BAIXO); break;
			case MORTO:
				pacman.Play(Frames.MORTE); break;
			}
			
		}else if(estadoPipeline == 1){
			estadoPipeline = maquinaEstPcman.lendoPalavra()? 1: 2;	
		}else if(estadoPipeline == 2){
			if(controlePcman.coordAtualizada()){
				estadoPipeline = 3;
			}else{
				estadoPipeline = 0;
				novoEstado = novoEstado == Estados.MORTO? Estados.MORTO: Estados.PARADO;
			}
		}else if(estadoPipeline == 3){
			estadoPipeline = posicaoAtualizada? 0: 3;
		}
		
		int dim = labirinto.getDimensao();
		int[] coord = controlePcman.getCoord();
		
		velocidade += speed;
		int dx = (int) ((coord[0]*dim - ultimaPosPcman[0])*velocidade);
		int dy = (int) ((coord[1]*dim - ultimaPosPcman[1])*velocidade);
		
		pacman.Animar(g, posX + ultimaPosPcman[1] + dy - dim/2, 
						 posY + ultimaPosPcman[0] + dx - dim/2, 1);
		
		if(coord[0]*dim == ultimaPosPcman[0] + dx && coord[1]*dim == ultimaPosPcman[1] + dy){
			ultimaPosPcman[0] = coord[0]*dim;
			ultimaPosPcman[1] = coord[1]*dim;
			velocidade = 0;
			posicaoAtualizada = true;
		}else{
			posicaoAtualizada = false;
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) { 
		for(int i=0; i<4; i++){
			threadFantasmas[i].interrupt();
		}
		threadPcman.interrupt();
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
	
	public void run()
	{
		while (true)
		{	
			try { Thread.sleep(duracaoFrame); } 
			catch (InterruptedException e)
			{
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
			novoEstado = Estados.DIREITA;
			break;
		case KeyEvent.VK_LEFT:
			novoEstado = Estados.ESQUERDA;
			break;
		case KeyEvent.VK_UP:
			novoEstado = Estados.CIMA;
			break;
		case KeyEvent.VK_DOWN:
			novoEstado = Estados.BAIXO;
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

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
	private long duracaoFrame = 60;
	private Thread temporizador;
	private String rastroPacman = "";
	
	private ControladorPacman controlPacman;
	
	private int altura;
	private int largura;

	private Thread threadPacman;
	private ControladorFantasma[] controlFantasmas = new ControladorFantasma[4];
	private Thread[] threadFantasmas = new Thread[4];
	public Arbitro(){
		super("PAC MAN");
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		largura = gd.getDisplayMode().getWidth()/2;
		altura = gd.getDisplayMode().getHeight()/2;
		
		addWindowListener(this);
		
		iniGUI();
		sobre.addActionListener(this);
		addKeyListener(this);
		
		for(int i=0; i<4; i++){
			fantasmas[i] = new AnimacaoFantasmas(this);
			fantasmas[i].carregarFrames("pacman-large.png", 12+i, 0, 1, 8, 16);
			fantasmas[i].Play(AnimacaoFantasmas.Frames.CIMA);
			
			int[] pos = Labirinto.coordenadaCelula(Integer.toString(i).charAt(0));
			controlFantasmas[i] = new ControladorFantasma(pos[0], pos[1]);
			MaquinaDeEstados inFantasma = new MaquinaDeEstados(controlFantasmas[i]);
			threadFantasmas[i]= new Thread(inFantasma);
			threadFantasmas[i].start();
		}
		
		pacman = new AnimacaoPacman(this);
		pacman.Play(AnimacaoPacman.Frames.DIREITA);
		labirinto = new DesenharLabirinto(this);
		
		//Cria e inicia thread do controlador do pacman
		int[] posicao = labirinto.coordenadaInicial();
		controlPacman = new ControladorPacman(posicao[0], posicao[1]);
		MaquinaDeEstados inPacman = new MaquinaDeEstados(controlPacman);
		threadPacman = new Thread(inPacman);
		threadPacman.start();
		
		setSize(largura, altura);
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
		if (sinalAtualizar > 0)
		{
			sinalAtualizar = 0;
			int posX = largura/2 - labirinto.getLargura()/2;
			int posY = altura/2 - labirinto.getAltura()/2;
			labirinto.Renderizar(g, posX, posY);
			
			int d = labirinto.getDimensao();
			int[] posicao = controlPacman.getPosicao();
			
			switch(controlPacman.getEstado()){
			case DIREITA:
				pacman.Play(Frames.DIREITA); break;
			case ESQUERDA:
				pacman.Play(Frames.ESQUERDA); break;
			case CIMA:
				pacman.Play(Frames.CIMA); break;
			case BAIXO:
				pacman.Play(Frames.BAIXO); break;
			case MORTO:
				pacman.Play(Frames.MORTE); break;
			}
			
			pacman.Animar(g, posX + posicao[1]*d - d/2, 
							 posY + posicao[0]*d - d/2);
			for(int i=0; i<4; i++){
				int[] pos = controlFantasmas[i].getPosicao();
				fantasmas[i].Animar(g, posX + pos[1]*d - d/2, 
									   posY + pos[0]*d - d/2);
				if(pos[0] == posicao[0] && pos[1] == posicao[1]){
					controlPacman.controlador('m');
				}
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
			threadFantasmas[i].interrupt();
		}
		threadPacman.interrupt();
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

			int[] posicao = controlPacman.getPosicao();
			for(int i=0; i<4; i++){
				controlFantasmas[i].controlador(posicao[0], posicao[1]);
			}
			sinalAtualizar++;
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			rastroPacman += "d";
			controlPacman.controlador('d');
			break;
		case KeyEvent.VK_LEFT:
			rastroPacman += "e";
			controlPacman.controlador('e');
			break;
		case KeyEvent.VK_UP:
			rastroPacman += "c";
			controlPacman.controlador('c');
			break;
		case KeyEvent.VK_DOWN:
			rastroPacman += "b";
			controlPacman.controlador('b');
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

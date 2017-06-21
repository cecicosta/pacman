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

import com.pacman.entrada.ControladorCallback;
import com.pacman.entrada.ControladorPacman;
import com.pacman.entrada.Entrada;
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
	public Arbitro(){
		super("PAC MAN");
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		largura = gd.getDisplayMode().getWidth()/2;
		altura = gd.getDisplayMode().getHeight()/2;
		
		addWindowListener(this);
		
		iniGUI();
		sobre.addActionListener(this);
		addKeyListener(this);
		
		pacman = new AnimacaoPacman(this);
		pacman.Play(AnimacaoPacman.Frames.DIREITA);
		labirinto = new DesenharLabirinto(this);
		
		//Cria e inicia thread do controlador do pacman
		int[] posicao = labirinto.coordenadaInicial();
		controlPacman = new ControladorPacman(posicao[0], posicao[1]);
		Entrada inPacman = new Entrada(controlPacman);
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
			}
			
			pacman.Animar(g, posX + posicao[1]*d - d/2, 
							 posY + posicao[0]*d - d/2);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) { 
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

			sinalAtualizar++;
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println("Comando");
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			System.out.println("Direita");
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

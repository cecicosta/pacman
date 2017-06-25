package com.pacman.jogo;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import com.pacman.graphics.Arbitro;

class Jogo extends Frame implements WindowListener, ActionListener, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int largura;
	private int altura;
	private MenuBar menu;
	private Menu ajuda;
	private MenuItem sobre;
	private Arbitro arbitro;
	private long duracaoFrame = 45;
	private Thread thread;
	
	public Jogo(){
		super("PAC MAN");
		largura = 640;
		altura = 480;
		setSize(largura, altura);
		
		addWindowListener(this);
		addNotify();
		
		menu = new MenuBar();
		ajuda = new Menu("Ajuda");
		sobre = new MenuItem("Sobre");
		sobre.addActionListener(this);

		ajuda.add(sobre);
		menu.add(ajuda);

		setMenuBar(menu);
		sobre.addActionListener(this);
		
		addWindowListener(this);
		arbitro = new Arbitro(this, largura, altura);
		add(arbitro);
		arbitro.setVisible(true);
		setResizable(false);
	}
	
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void update(Graphics g){
		arbitro.updateGame(g);
	}
	
	public void run() {
		while (true) {	
			try { Thread.sleep(duracaoFrame); } 
			catch (InterruptedException e) {
				return;
			}
	
			update(this.getGraphics());
			repaint();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {	
		thread.interrupt();
		arbitro.finalizar();
		dispose(); 
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

public class JogoPacman {
	public static void main (String[] args) 
	{
		Jogo jogo = new Jogo();
		jogo.setVisible(true);
		jogo.start();
	}
}

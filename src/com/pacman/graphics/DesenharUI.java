package com.pacman.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DesenharUI extends Frame implements ActionListener, WindowListener, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MenuBar menu;
	private Menu ajuda;
	private MenuItem sobre;
	private Image imgScore;
	private Graphics imgScoreG;
	private Image imgHiScore;
	private Image spriteSheet;
	private Graphics imgHiScoreG;
	private boolean gameInit = false;
	private int changeHiScore;
	private int changeScore;
	
	AnimacaoPacman pacman;
	DesenharLabirinto labirinto;
	private int sinalAtualizar = 0;
	private long duracaoFrame = 60;
	private Thread temporizador;

	public DesenharUI(){
		super("PAC MAN");
		addWindowListener(this);
		
		iniGUI();
		sobre.addActionListener(this);
		
		setSize(640, 480);
		this.setVisible(true);
		
        String path = "pacman-large.png";
        try {
        	InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        	spriteSheet = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
        int resW = spriteSheet.getWidth(this)/4;
        int resH = spriteSheet.getHeight(this)/4;
        BufferedImage scaledBI = new BufferedImage(resW, resH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
        g.drawImage(spriteSheet, 0, 0, resW, resH, null); 
        g.dispose();
        
		pacman = new AnimacaoPacman(scaledBI, this);
		pacman.Play(AnimacaoPacman.Frames.MORTE);
		labirinto = new DesenharLabirinto(this);
	}
	
	void iniGUI()
	{
		menu = new MenuBar();
		ajuda = new Menu("Ajuda");
		sobre = new MenuItem("Sobre");

		ajuda.add(sobre);
		menu.add(ajuda);

		setMenuBar(menu);
		
		

		addNotify();  // for updated inset information
	}
	
	void iniciarLoop(){
		temporizador = new Thread(this);
		temporizador.start();
	}
	
	public void paint(Graphics g){
		
		if (gameInit == false){
			// initialize the score images
			imgScore = createImage(150,16);
			imgScoreG = imgScore.getGraphics();
			imgHiScore = createImage(150,16);
			imgHiScoreG = imgHiScore.getGraphics();
	
			imgHiScoreG.setColor(Color.black);
			imgHiScoreG.fillRect(0,0,150,16);
			imgHiScoreG.setColor(Color.red);
			imgHiScoreG.setFont(new Font("Helvetica", Font.BOLD, 12));
			imgHiScoreG.drawString("HI SCORE", 0, 14);
	
			imgScoreG.setColor(Color.black);
			imgScoreG.fillRect(0,0,150,16);
			imgScoreG.setColor(Color.green);
			imgScoreG.setFont(new Font("Helvetica", Font.BOLD, 12));
			imgScoreG.drawString("SCORE", 0, 14);

	
			setSize(640,480);
	
			setResizable(false);
			gameInit = true;
			iniciarLoop();
		}
		g.setColor(Color.black);
		g.fillRect(0,0,640,480);
		
		changeScore=1;
		changeHiScore=1;
		
		// display extra information
		if (changeHiScore==1)
		{
			imgHiScoreG.setColor(Color.black);
			imgHiScoreG.fillRect(60,14,10,14);
			imgHiScoreG.setColor(Color.red);
			imgHiScoreG.drawString(Integer.toString(10), 60,14);
			g.drawImage(imgHiScore, 
					0, 90, this);

			changeHiScore=0;
		}

		if (changeScore==1)
		{
			//imgScoreG.setColor(Color.black);
			//imgScoreG.fillRect(0,0,80,50);
			//imgScoreG.setColor(Color.green);
			//imgScoreG.drawString(Integer.toString(20000), 0,0);
			g.drawImage(imgScore, 
					80, 90, this);

			changeScore=0;
		}
		
	}
	
	
	public void update(Graphics g)
	{
		if (sinalAtualizar > 0)
		{
			sinalAtualizar = 0;
			pacman.Animar(g);
			labirinto.Renderizar(g);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) { dispose(); }
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
}
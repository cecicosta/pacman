package com.pacman.graphics;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

interface AnimFrame{
	public int inicio();
	public int fim();
}

public class Animador{
	
	private int ultimoFrame = 0;
	private AnimFrame atual = Frames.NONE;
	public enum Frames implements AnimFrame{
		NONE(0,0);
		Frames(int inicio, int fim){this.inicio = inicio; this.fim = fim;}
    	public int inicio;
    	public int fim;
		@Override
		public int inicio() {
			return inicio;
		}
		@Override
		public int fim() {
			return fim;
		}
	}
	private int contador;

	private Image spriteSheet;
	private BufferedImage frames[] = new BufferedImage[100];
	private Frame frame;
	private int delay = 0;
	public Animador(Frame f){
		frame = f;        
	}
	
	//Carrega sprites da animação
	public void carregarFrames(String name, int x, int y, int dx, int dy, int dim){
        try {
        	InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        	spriteSheet = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
        //Redimenciona sprites 
        int resW = spriteSheet.getWidth(frame)/4;
        int resH = spriteSheet.getHeight(frame)/4;
        BufferedImage escalado = new BufferedImage(resW, resH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = escalado.createGraphics();
        g.drawImage(spriteSheet, 0, 0, resW, resH, null); 
        g.dispose();
		
        BufferedImage tileset = new BufferedImage(spriteSheet.getWidth(frame), 
        		spriteSheet.getHeight(frame), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = tileset.createGraphics();
        
        //Carregar os frames de movimento do fantasma
        for(int j=0; j < dx; j++){
        	for(int i=0; i < dy; i++){
	        	g2d.drawImage(escalado, - (x+j)*dim, -(y+i)*dim, frame);
		        Raster temp = tileset.getData( new Rectangle( 0, 0, dim, dim) );
		        frames[ultimoFrame + j*dy + i] = new BufferedImage( dim, dim, BufferedImage.TYPE_INT_RGB );
		        frames[ultimoFrame + j*dy + i].setData( temp );	
        	}
        }
        ultimoFrame += dx*dy; //Evita que blocos de frames sejam subsescritos por multiplas chamadas
	}
	
	public AnimFrame getFrameAtual(){
		return atual;
	}
	public void setFrameAtual(AnimFrame f){
		atual =  f;
	}
	
	//Determina qual sequencia de frames será tocada
	public void Play(AnimFrame frames){
		if(frames == getFrameAtual())
			return;
		setFrameAtual(frames);
		contador = getFrameAtual().inicio();
	}
	
	public void Animar(Graphics g, int x, int y){
		g.drawImage( frames[contador++], x, y, frame );
		if(contador > getFrameAtual().fim()) contador = getFrameAtual().inicio();
	}
	
	public void Animar(Graphics g, int x, int y, int delay, boolean loop){
		g.drawImage( frames[contador], x, y, frame );
		this.delay = this.delay == 0? delay: this.delay  - 1;
		contador = this.delay == 0? contador + 1: contador;
		if(contador >  getFrameAtual().fim() && loop) contador = getFrameAtual().inicio();
	}
}

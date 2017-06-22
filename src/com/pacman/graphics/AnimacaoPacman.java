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

public class AnimacaoPacman {
	private int d = 16;
	private Frames atual;
	public enum Frames{
		PARADO (0,0),
		CIMA (3,5),
		BAIXO(9,11),
		DIREITA(6,8),
		ESQUERDA(0,2),
		MORTE (12,19);
		
    	public int inicio;
    	public int fim;
    	Frames(int inicio, int fim){
    		this.inicio = inicio;
    		this.fim = fim;
    	}
	}
	private int contador;

	private Image spriteSheet;
	private BufferedImage frames[] = new BufferedImage[20];
	private Frame frame;
	private int delay = 0;
	public AnimacaoPacman(Frame f){
		frame = f;        
		
		//Carrega sprites da animação
        String path = "pacman-large.png";
        try {
        	InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
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
		
        BufferedImage tileset = new BufferedImage(spriteSheet.getWidth(f), 
        		spriteSheet.getHeight(f), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = tileset.createGraphics();
        
        //Carregar os frames de movimento do pacman
        for(int j=0; j < 2; j++){
        	for(int i=0; i < 6; i++){
	        	g2d.drawImage(escalado, - (9+j)*d, -i*d, f);
		        Raster temp = tileset.getData( new Rectangle( 0, 0, d, d) );
		        frames[j*6 + i] = new BufferedImage( d, d, BufferedImage.TYPE_INT_RGB );
		        frames[j*6 + i].setData( temp );	
        	}
        }
        
        //Carregar os frames de morte do pacman
        for(int i=0; i < 8; i++){
        	g2d.drawImage(escalado, - 8*d, -i*d, f);
	        Raster temp = tileset.getData( new Rectangle( 0, 0, d, d) );
	        frames[12 + i] = new BufferedImage( d, d, BufferedImage.TYPE_INT_RGB );
	        frames[12 + i].setData( temp );
        }
	}
	
	//Determina qual sequencia de frames será tocada
	public void Play(Frames frames){
		if(frames == atual)
			return;
		atual = frames;
		contador = atual.inicio;
	}
	
	public void Animar(Graphics g, int x, int y, int delay){
		g.drawImage( frames[contador], x, y, frame );
		this.delay = this.delay == 0? delay: this.delay - 1;
		contador = this.delay == 0? contador + 1: contador;
		if(contador >  atual.fim) contador = atual.inicio;
	}
}

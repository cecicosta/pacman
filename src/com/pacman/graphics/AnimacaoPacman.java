package com.pacman.graphics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class AnimacaoPacman {
	private int d = 16;
	private Frames atual;
	public enum Frames{
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
	
	private BufferedImage frames[] = new BufferedImage[20];
	private Frame frame;
	public AnimacaoPacman(Image spriteSheet, Frame f){
		frame = f;        
        BufferedImage tileset = new BufferedImage(spriteSheet.getWidth(f), 
        		spriteSheet.getHeight(f), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = tileset.createGraphics();
        
        //Carregar os frames de movimento do pacman
        for(int j=0; j < 2; j++){
        	for(int i=0; i < 6; i++){
	        	g2d.drawImage(spriteSheet, - (9+j)*d, -i*d, f);
		        Raster temp = tileset.getData( new Rectangle( 0, 0, d, d) );
		        frames[j*6 + i] = new BufferedImage( d, d, BufferedImage.TYPE_INT_RGB );
		        frames[j*6 + i].setData( temp );	
        	}
        }
        
        //Carregar os frames de morte do pacman
        for(int i=0; i < 8; i++){
        	g2d.drawImage(spriteSheet, - 8*d, -i*d, f);
	        Raster temp = tileset.getData( new Rectangle( 0, 0, d, d) );
	        frames[12 + i] = new BufferedImage( d, d, BufferedImage.TYPE_INT_RGB );
	        frames[12 + i].setData( temp );
        }
	}
	
	public void Play(Frames frames){
		atual = frames;
		contador = atual.inicio;
	}
	
	public void Animar(Graphics g){
		g.drawImage( frames[contador++], 100, 100, frame );
		if(contador > atual.fim) contador = atual.inicio;
	}
}

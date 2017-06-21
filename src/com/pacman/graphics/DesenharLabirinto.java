package com.pacman.graphics;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import com.pacman.entrada.Labirinto;

public class DesenharLabirinto {
	private int altura = 31;
	private int largura = 46;
	
	static final int HEIGHT=16;
	static final int WIDTH=21;
	static final int iHeight=20;
	static final int iWidth=20;
	
	private int d = 12;	
			
	private Image imageMaze;
	private Frame frame;
	
	public DesenharLabirinto(Frame frame){
		this.frame = frame;
		imageMaze = frame.createImage(640, 640);
		Graphics gmaze = imageMaze.getGraphics();

		// background
		gmaze.setColor(Color.BLACK);
		gmaze.fillRect(0,0,640,640);
		desenhar(gmaze);
	}
	
	public void desenhar(Graphics g){
		g.setColor(Color.BLUE);
	
		for(int i=0; i<altura; i++)
			for(int j=0; j<largura; j++){
				
				if( casa(i, j) == 'x'){
					if(i == 0 || i == altura - 1){
						g.drawLine(j*d,i*d,j*d+d,i*d);				
						
					}else if(j == 0 || j == largura - 1){
						g.drawLine(j*d,i*d,j*d,i*d+d);
						
					}else if( casa(i + 1, j) == 'x' && casa(i, j + 1) == 'x'){
						g.drawLine(j*d,i*d,j*d+d,i*d);
						g.drawLine(j*d,i*d,j*d,i*d+d);
						
					}else if( casa(i + 1, j) == 'x' && casa(i, j - 1) == 'x'){
						g.drawLine(j*d,i*d,j*d-d,i*d);
						g.drawLine(j*d,i*d,j*d,i*d+d);
						
					}else if( casa(i - 1, j) == 'x' && casa(i, j + 1) == 'x'){
						g.drawLine(j*d,i*d,j*d+d,i*d);
						g.drawLine(j*d,i*d,j*d,i*d-d);
						
					}else if( casa(i - 1, j) == 'x' && casa(i, j - 1) == 'x'){
						g.drawLine(j*d,i*d,j*d-d,i*d);
						g.drawLine(j*d,i*d,j*d,i*d-d);
						
					}else if( casa(i - 1, j) == 'x' || casa(i + 1, j) == 'x'){
						g.drawLine(j*d,i*d,j*d,i*d+d);
						
					}else if( casa(i, j - 1) == 'x' || casa(i, j + 1) == 'x'){
						g.drawLine(j*d,i*d,j*d+d,i*d);
					
					}
			}else if(casa(i, j) == '.'){
				g.drawRoundRect(j*d, i*d, 2, 2, 5,5);
			}else if(casa(i, j) == 'o'){
				g.drawRoundRect(j*d, i*d, 6, 6, 5,5);
			}
		}
	}
	private char casa(int i, int j){
		return Labirinto.labirinto.charAt(j + i*largura);
	}
	
	public int[] coordenadaInicial(){
		int posicao = Labirinto.labirinto.indexOf("s");
		return new int[]{posicao/largura, posicao%largura} ;
	}
	
	public int[] posicaoInicial(){
		int posicao = Labirinto.labirinto.indexOf("s");
		return new int[]{posicao%largura*d - d/2, posicao/largura*d - d/2} ;
	}

	
	public void Renderizar(Graphics g, int x, int y){
		g.drawImage( imageMaze, x, y, frame );
	}
	
	public int getAltura(){
		return altura*d;
	}
	public int getLargura(){
		return largura*d;
	}
	
	public int getDimensao(){
		return d;
	}
}

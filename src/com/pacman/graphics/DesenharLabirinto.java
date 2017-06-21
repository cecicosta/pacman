package com.pacman.graphics;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

public class DesenharLabirinto {
	private int altura = 31;
	private int largura = 46;
	
	static final int HEIGHT=16;
	static final int WIDTH=21;
	static final int iHeight=20;
	static final int iWidth=20;
	
	private int d = 12;
	
	String labirinto = ""
			+ "                                              "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "
			+ " x.........................................ox "
			+ " x.xxxxxxx.xxx.xxx.xxxxx.xxx.xxx.xxxxxx.xxx.x "
			+ " x.x     x.x x.x x.x   x.x x.x x.x    x.x x.x "
			+ " x.xxxxxxx.x x.xxx.xxx x.xxx.xxx.xxxx x.xxx.x "
			+ " x........ox x......ox x............x x.....x "
			+ " x.xxx.xxxxx x.xxx.xxx x.xxxxxxxxxxxx x.xxx.x "
			+ " x.x x.x     x.x x.x   x.x            x.x x.x "
			+ " x.xxx.xxxxxxx.xxx.xxxxx.xxxxxxxxxxxxxx.xxx.x "
			+ " x..........................................x "
			+ " x.xxxxxx.xxx.xxxxxxx xxxxxxx.xxxx.xxxxxxxxxx "
			+ " x.x    x.x x.x     x x     x.x  x.x          "
			+ " x.xxxx x.x x.x xxxxx xxxxx x.x  x.x          "
			+ " x....x x.xxx.x x         x x.x  x.x          "
			+ " x.xxxx x.....x x         x x.xxxx.x          "
			+ " x.x    x.xxx.x x         x x......x          "
			+ " x.x    x.x x.x xxxxxxxxxxx x.xxxx.x          "
			+ " x.x    x.x x.x             x.x  x.x          "
			+ " x.xxxxxx.xxx.xxxxxxxxxxxxxxx.xxxx.xxxxxxxxxx "
			+ " x..........................................x "
			+ " x.xxx.xxxxxxxxxxxxxx.xxxxx.xxx.xxxxxxx.xxx.x " 	
			+ " x.x x.x            x.x   x.x x.x     x.x x.x " 			
			+ " x.xxx.x xxxxxxxxxxxx.x xxx.xxx.x xxxxx.xxx.x " 			
			+ " x.....x x............x xo......x xo........x " 			
			+ " x.xxx.x xxxx.xxx.xxx.x xxx.xxx.x x.xxxxxxx.x " 			
			+ " x.x x.x    x.x x.x x.x   x.x x.x x.x     x.x " 			
			+ " x.xxx.xxxxxx.xxx.xxx.xxxxx.xxx.xxx.xxxxxxx.x " 
			+ " xo.........................................x "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "

			+ "                                              ";
	
			
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
		return labirinto.charAt(j + i*largura);
	}
	
	public void Renderizar(Graphics g){
		g.drawImage( imageMaze, 50, 50, frame );
	}
}

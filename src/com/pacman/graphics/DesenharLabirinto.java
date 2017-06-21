package com.pacman.graphics;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

public class DesenharLabirinto {
	private int altura = 22;
	private int largura = 52;
	
	static final int HEIGHT=16;
	static final int WIDTH=21;
	static final int iHeight=20;
	static final int iWidth=20;
	
	private int d = 12;
	
	String labirinto = ""
			+ "                                                    "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "
			+ " x................................................x "
			+ " x....xxxxxxxxx.xxx...............................x "
			+ " x............x.x.x...............................x "
			+ " x....xxxxxxx.x.x.x...............................x "
			+ " x..........x.x.x.xx..............................x "
			+ " x..........x.....x...............................x "
			+ " x..........xxxxx.x...............................x "
			+ " x..............x.x...............................x "
			+ " x..............x.x...............................x "
			+ " x..............x.x...............................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " x................................................x "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "
			+ "                                                    ";
	private Image imageMaze;
	private Frame frame;
	
	public DesenharLabirinto(Frame frame){
		this.frame = frame;
		imageMaze = frame.createImage(360, 480);
		Graphics gmaze = imageMaze.getGraphics();

		// background
		gmaze.setColor(Color.BLACK);
		gmaze.fillRect(0,0,360,480);
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
					
				}else{
					g.drawOval(i*d+3, j*d+3, 2, 2);
				}
			}
		}
	}
	private char casa(int i, int j){
		return labirinto.charAt(j + i*largura);
	}
	
	public void Renderizar(Graphics g){
		g.drawImage( imageMaze, 150, 150, frame );
	}
}

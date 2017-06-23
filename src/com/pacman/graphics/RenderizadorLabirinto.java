package com.pacman.graphics;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

import com.pacman.entrada.Labirinto;

public class RenderizadorLabirinto {
	
	private Image imageMaze;
	Graphics gmaze;
	private Frame frame;
	
	public RenderizadorLabirinto(Frame frame){
		this.frame = frame;
		imageMaze = frame.createImage(frame.getWidth(), frame.getHeight());
		gmaze = imageMaze.getGraphics();

		// background
		gmaze.setColor(Color.BLACK);
		gmaze.fillRect(0,0,frame.getWidth(), frame.getHeight());
		desenhar();
	}
	
	public void desenhar(){
		
		for(int i=0; i<getAltura(); i++)
			for(int j=0; j<getLargura(); j++){
				desenharCelula(gmaze, i, j);
		}
	}
	
	private void desenharCelula(Graphics g, int i, int j){
		int dim = Labirinto.getDimensao();
		gmaze.setColor(Color.BLUE);
		if( celula(i, j) == 'x'){
			if(i == 0 || i == getAltura() - 1){
				g.drawLine(j*dim,i*dim,j*dim+dim,i*dim);				
				
			}else if(j == 0 || j == getLargura() - 1){
				g.drawLine(j*dim,i*dim,j*dim,i*dim+dim);
				
			}else if( celula(i + 1, j) == 'x' && celula(i, j + 1) == 'x'){
				g.drawLine(j*dim,i*dim,j*dim+dim,i*dim);
				g.drawLine(j*dim,i*dim,j*dim,i*dim+dim);
				
			}else if( celula(i + 1, j) == 'x' && celula(i, j - 1) == 'x'){
				g.drawLine(j*dim,i*dim,j*dim-dim,i*dim);
				g.drawLine(j*dim,i*dim,j*dim,i*dim+dim);
				
			}else if( celula(i - 1, j) == 'x' && celula(i, j + 1) == 'x'){
				g.drawLine(j*dim,i*dim,j*dim+dim,i*dim);
				g.drawLine(j*dim,i*dim,j*dim,i*dim-dim);
				
			}else if( celula(i - 1, j) == 'x' && celula(i, j - 1) == 'x'){
				g.drawLine(j*dim,i*dim,j*dim-dim,i*dim);
				g.drawLine(j*dim,i*dim,j*dim,i*dim-dim);
				
			}else if( celula(i - 1, j) == 'x' || celula(i + 1, j) == 'x'){
				g.drawLine(j*dim,i*dim,j*dim,i*dim+dim);
				
			}else if( celula(i, j - 1) == 'x' || celula(i, j + 1) == 'x'){
				g.drawLine(j*dim,i*dim,j*dim+dim,i*dim);
			
			}
		}else if(celula(i, j) == '.'){
			g.drawRoundRect(j*dim, i*dim, 2, 2, 5,5);
		}else if(celula(i, j) == 'o'){
			g.drawRoundRect(j*dim, i*dim, 6, 6, 5,5);
		}else if(celula(i, j) == 'e'){
			gmaze.setColor(Color.BLACK);
			g.fillRect(j*dim -dim/2, i*dim - dim/2, dim, dim);
		}else if(celula(i, j) == ' '){
			g.fillRect(j*dim -dim/2, i*dim - dim/2, dim, dim);
		}
	}
	
	public void renderizar(Graphics g, int x, int y){
		int[] posicao;
		
		while((posicao = Labirinto.coordenadaCelula('n')) != null){
			setCelula(posicao[0], posicao[1], 'e');
		}
		g.drawImage( imageMaze, x, y, frame );
	}
	public char celula(int i, int j){
		return Labirinto.getCelula(i, j);
	}
	public int getAltura(){
		return Labirinto.getAltura();
	}
	public int getLargura(){
		return Labirinto.getLargura();
	}
	
	public void setCelula(int i, int j, char c){
		Labirinto.setCelula(i, j, c);
		desenharCelula(gmaze, i, j);
	}
	
	public int getDimensao(){
		return Labirinto.getDimensao();
	}
}

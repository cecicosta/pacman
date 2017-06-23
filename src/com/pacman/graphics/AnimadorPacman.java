package com.pacman.graphics;

import java.awt.Frame;

public class AnimadorPacman extends Animador{
	public AnimadorPacman(Frame f) {
		super(f);
	}
	
	public enum Frames implements AnimFrame{
		PARADO (0,0),
		CIMA (3,5),
		BAIXO(9,11),
		DIREITA(6,8),
		ESQUERDA(0,2),
		MORTE (12,23);
		Frames(int inicio, int fim){ this.inicio = inicio; this.fim = fim; }
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
	protected Frames atual;
	
	@Override
	public Frames getFrameAtual(){
		return atual;
	}
	@Override
	public void setFrameAtual(AnimFrame f){
		atual = (Frames) f;
	}
	
}

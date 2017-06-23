package com.pacman.graphics;

import java.awt.Frame;

public class AnimadorFantasmas extends Animador{
	public AnimadorFantasmas(Frame f) {
		super(f);
		// TODO Auto-generated constructor stub
	}
	public enum Frames implements AnimFrame{
		CIMA (6,7),
		BAIXO(2,3),
		DIREITA(0,1),
		ESQUERDA(4,5);
		
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

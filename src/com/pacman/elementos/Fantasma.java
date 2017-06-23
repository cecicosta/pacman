package com.pacman.elementos;

import java.awt.Frame;
import java.awt.Graphics;

import com.pacman.entrada.ControladorFantasma;
import com.pacman.entrada.Labirinto;
import com.pacman.graphics.AnimadorFantasmas;

public class Fantasma extends ElementoMovel{

	private AnimadorFantasmas animador;
	private static int id;
	public Fantasma(Frame frame, ControladorFantasma controlador, char inicio){
		super(controlador, inicio);
		
		animador = new AnimadorFantasmas(frame);
		animador.carregarFrames("pacman-large.png", 12+id++, 0, 1, 8, 16);
		animador.Play(AnimadorFantasmas.Frames.CIMA);
	}
	
	public void move(Graphics g, int posX, int posY, int moveI, int moveJ){
		
		((ControladorFantasma)controlador).
			atualizarControlador(moveI, moveJ, !posicaoFinal);
		
		velocidade += speed ;
		int[] pos = controlador.getCoordenadas();
		int dim = Labirinto.getDimensao();
		
		int dx = (int) ((pos[0]*dim - ultimaPosicao[0])*velocidade);
		int dy = (int) ((pos[1]*dim - ultimaPosicao[1])*velocidade);
		
		animador.Animar(g, posX + ultimaPosicao[1] + dy - dim/2, 
				   		   posY + ultimaPosicao[0] + dx - dim/2);
		
		if(pos[0]*dim == ultimaPosicao[0] + dx && pos[1]*dim == ultimaPosicao[1] + dy){
			ultimaPosicao[0] = pos[0]*dim;
			ultimaPosicao[1] = pos[1]*dim;
			velocidade = 0;
			posicaoFinal = true;
		}else{
			posicaoFinal = false;
		}
	}

}

package com.pacman.elementos;

import java.awt.Frame;
import java.awt.Graphics;

import com.pacman.entrada.ControladorPacman;
import com.pacman.entrada.Labirinto;
import com.pacman.entrada.ControladorAutomato.Estados;
import com.pacman.graphics.AnimadorPacman;

public class Pacman extends ElementoMovel{

	private AnimadorPacman animador;
	public Pacman(Frame frame, ControladorPacman controlador, char inicio) {
		super(controlador, inicio);
		
		animador = new AnimadorPacman(frame);
		animador.carregarFrames("pacman-large.png", 9, 0, 2, 6, 16, 0.25f); //frames de movimento
		animador.carregarFrames("pacman-large.png", 8, 0, 1, 8, 16, 0.25f); //frames de morte - inicio
		animador.carregarFrames("pacman-large.png", 7, 0, 1, 4, 16, 0.25f); //frames de morte - fim
		animador.Play(AnimadorPacman.Frames.PARADO);
	}
	
	@Override
	public ControladorPacman getControlador(){
		return (ControladorPacman) controlador;
	}
	
	public void atualizarControlador(char entrada, boolean esperar){
		getControlador().atualizarControlador(entrada, animador, esperar);
	}
	
	public boolean movendo(){
		return !posicaoFinal;
	}
	
	public void move(Graphics g, int posX, int posY){
		
		velocidade += speed ;
		int[] pos = controlador.getCoordenadas();
		int dim = Labirinto.getDimensao();
		
		int dx = (int) ((pos[0]*dim - ultimaPosicao[0])*velocidade);
		int dy = (int) ((pos[1]*dim - ultimaPosicao[1])*velocidade);
		
		animador.Animar(g, posX + ultimaPosicao[1] + dy - dim/2, 
				   		   posY + ultimaPosicao[0] + dx - dim/2, 1, 
				   		   controlador.getEstado() == Estados.MORTO? false: true);
		
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

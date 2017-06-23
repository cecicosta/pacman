package com.pacman.entrada;

import com.pacman.graphics.AnimadorPacman;
import com.pacman.graphics.AnimadorPacman.Frames;

public class ControladorPacman extends ControladorAutomato {

	private int passoAtuaizacao = 0;

	public ControladorPacman(int i, int j) {
		super(i, j);
	}	
	public void setEntrada(char c){
		if(leituraCompleta())
			setLeituraPendente();
		palavra += c;
	}
	@Override
	public void estadoAtualizado(Estados anterior, Estados novo){
		if(Labirinto.getCelula(posicao[0], posicao[1]) == '.'){
			Labirinto.setCelula(posicao[0], posicao[1], 'n');
		}
	}
	
	/**
	 * 	   [Novo Estado] ----> [Entrada do alfabeto] 
	 * 			^					    |
	 * 			|					    v
	 * 		    |--------------- <Entrada valida?> 
	 * 			|			   n	    | s
	 * 			|					    v
	 * 			|		[Atualiza coordenada no labirinto]
	 * 			|					    |
	 * 			|		   s		    v			 	 n
	 * 			 -----------<Posição na tela atualizada?>--> (Fica em espera)
	 */
	
	//Passos para atualização da entrada, animações e coordenadas no labirinto. 
	//entra em espera até que o árbitro termine de atualizar a posição na tela 
	public void atualizarControlador(Estados novoEstado, AnimadorPacman pacman , boolean espera){
		if(passoAtuaizacao == 0){
			switch(novoEstado){
			case PARADO:
				pacman.Play(Frames.PARADO); break;
			case DIREITA:
				setEntrada('d');
				passoAtuaizacao = 1;
				pacman.Play(Frames.DIREITA); break;
			case ESQUERDA:
				setEntrada('e');
				passoAtuaizacao = 1;
				pacman.Play(Frames.ESQUERDA); break;
			case CIMA:
				setEntrada('c');
				passoAtuaizacao = 1;
				pacman.Play(Frames.CIMA); break;
			case BAIXO:
				setEntrada('b');
				passoAtuaizacao = 1;
				pacman.Play(Frames.BAIXO); break;
			case MORTO:
				pacman.Play(Frames.MORTE); break;
			}
			
		}else if(passoAtuaizacao == 1){
			passoAtuaizacao = !leituraCompleta()? 1: 2;	
		}else if(passoAtuaizacao == 2){
			if(coordAtualizada()){
				passoAtuaizacao = 3;
			}else{
				passoAtuaizacao = 0;
				novoEstado = novoEstado == Estados.MORTO? Estados.MORTO: Estados.PARADO;
			}
		}else if(passoAtuaizacao == 3){ 
			passoAtuaizacao = espera? 3: 0;
		}
	}
}

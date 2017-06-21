package com.pacman.entrada;

public class ControladorPacman extends ControladorAutomato {

	public ControladorPacman(int i, int j) {
		super(i, j);
	}	
	public void controlador(char c){
		palavra += c;
	}
	@Override
	public void estadoAtualizado(Estados anterior, Estados novo){
		if(Labirinto.getCelula(posicao[0], posicao[1]) == '.'){
			Labirinto.setCelula(posicao[0], posicao[1], 'n');
		}
	}
}

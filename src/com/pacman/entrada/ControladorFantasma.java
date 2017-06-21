package com.pacman.entrada;

public class ControladorFantasma extends ControladorCallback{
	
	public ControladorFantasma(int i, int j) {
		super(i, j);
	}

	//Controlador baseado na posição relativa do pacman
	public void controlador(int i, int j){
		if(i > posicao[0])
			palavra += 'c';
		else if(i < posicao[0])
			palavra += 'b';
		else if(j > posicao[1])
			palavra += 'd';
		else if(j < posicao[1])
			palavra += 'e';
	}
}

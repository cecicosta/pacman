package com.pacman.entrada;

import java.util.Random;

public class ControladorFantasma extends ControladorAutomato{
	
	Random decisao = new Random();
	public ControladorFantasma(int i, int j) {
		super(i, j);
	}

	//Controlador baseado na posição relativa do pacman
	public void controlador(int i, int j){
		//Adiciona um modificador na escolha de caminho dos fantasmas para que se espalhem
		int escolha = decisao.nextInt()%2;
		
		if(i > posicao[0] && Labirinto.getCelula(posicao[0] + 1, posicao[1]) != 'x' && escolha == 1)
			palavra += 'b';
		else if(i < posicao[0] && Labirinto.getCelula(posicao[0] - 1, posicao[1]) != 'x' && escolha == 1)
			palavra += 'c';
		else if(j > posicao[1] && Labirinto.getCelula(posicao[0], posicao[1] + 1) != 'x' && escolha == 0)
			palavra += 'd';
		else if(j < posicao[1] && Labirinto.getCelula(posicao[0], posicao[1] - 1) != 'x'&& escolha == 0)
			palavra += 'e';
	}
}

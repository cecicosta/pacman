package com.pacman.entrada;

import java.util.Random;

public class ControladorFantasma extends ControladorAutomato{
	
	Random decisao = new Random();
	private int estadoPipeline;
	public ControladorFantasma(int i, int j) {
		super(i, j);
	}

	//Controlador baseado na posição relativa do pacman
	public void setEntrada(int i, int j){
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
		else if(i > posicao[0] && Labirinto.getCelula(posicao[0] + 1, posicao[1]) != 'x' && escolha == 0)
			palavra += 'b';
		else if(i < posicao[0] && Labirinto.getCelula(posicao[0] - 1, posicao[1]) != 'x' && escolha == 0)
			palavra += 'c';
		else if(j > posicao[1] && Labirinto.getCelula(posicao[0], posicao[1] + 1) != 'x' && escolha == 1)
			palavra += 'd';
		else if(j < posicao[1] && Labirinto.getCelula(posicao[0], posicao[1] - 1) != 'x'&& escolha == 1)
			palavra += 'e';
	}
	
	//Passos para atualização da entrada e coordenadas no labirinto, entra em espera até que o arbitro solicite 
	public void atualizarControlador(int i, int j, boolean espera){
		if(	estadoPipeline == 0){
			setEntrada(i, j);
				estadoPipeline = 1;
		}else if(	estadoPipeline == 1){
				estadoPipeline = !leituraCompleta()? 1: 2;
		}else if(	estadoPipeline == 2){
			if(coordAtualizada()){
					estadoPipeline = 3;
			}else{
					estadoPipeline = 0;
			}
		}else if(	estadoPipeline == 3){
				estadoPipeline = espera? 3: 0;
		}
	}
}

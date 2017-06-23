package com.pacman.entrada;

import com.pacman.graphics.AnimadorPacman;
import com.pacman.graphics.AnimadorPacman.Frames;

public class ControladorPacman extends ControladorAutomato {

	private int passoAtuaizacao = 0;

	public ControladorPacman(int i, int j) {
		super(i, j);
	}	
	
	/**
	* Recebe uma nova entrada da palavra que será processada pelo autômato
	* @param c Charcater representando a entrada da palavra
	*/
	public void setEntrada(char c){
		if(leituraCompleta()) //Projetado para garantir a consistência da checagem "leituraCompleta()" com a MaquinaDeEstados
			setLeituraPendente();
		palavra += c;
	}
	
	/**
	* Método de callback chamado pela MaquinaDeEstados quando o estado é atualizado.
	* @param anterior Estado antes da atualização
	* @param novo Estado após a atualização
	*/
	@Override
	public void estadoAtualizado(Estados anterior, Estados novo){
		if(Labirinto.getCelula(posicao[0], posicao[1]) == '.'){
			Labirinto.setCelula(posicao[0], posicao[1], 'n');
		}
	}

	/**
	* Este método executa os passos necessários para atualização da entrada e 
	* coordenadas no labirinto, além de definir os frames atuais da animação.
	* @param entrada Nova entrada da palavra que o autômato do pacman irá processar.
	* @param pacman Animador do pacman. Animação definida de acordo com a entrada estado
	* @param espera se verdadeira indica que o método deve esperar após a atualização. 
	* Utilizado para que o Árbitro atualize a posição da animação de acordo com a 
	* nova coordenada.
	*/
	//Passos para atualização da entrada, animações e coordenadas no labirinto. 
	//entra em espera até que o árbitro termine de atualizar a posição na tela 
	public void atualizarControlador(char entrada, AnimadorPacman pacman , boolean espera){

		if(passoAtuaizacao == 0){
			switch(entrada){
			case 'p':
				setEntrada(entrada);
				passoAtuaizacao = 1;
				pacman.Play(Frames.PARADO); break;
			case 'd':
				setEntrada(entrada);
				passoAtuaizacao = 1;
				pacman.Play(Frames.DIREITA); break;
			case 'e':
				setEntrada(entrada);
				passoAtuaizacao = 1;
				pacman.Play(Frames.ESQUERDA); break;
			case 'c':
				setEntrada(entrada);
				passoAtuaizacao = 1;
				pacman.Play(Frames.CIMA); break;
			case 'b':
				setEntrada(entrada);
				passoAtuaizacao = 1;
				pacman.Play(Frames.BAIXO); break;
			case 'm':
				setEntrada(entrada);
				passoAtuaizacao = 1;
				pacman.Play(Frames.MORTE); break;
			}
			
		}else if(passoAtuaizacao == 1){
			passoAtuaizacao = !leituraCompleta()? 1: 2;	
		}else if(passoAtuaizacao == 2){
			if(coordAtualizada()){
				passoAtuaizacao = 3;
			}else{
				//checa se a entrada significa 'm' morte ou 'p' parado e volta para o passo 0
				entrada = entrada == 'm'? 'm': 'p'; 
				passoAtuaizacao = 0;
			}
		}else if(passoAtuaizacao == 3){ 
			passoAtuaizacao = espera? 3: 0;
		}
	}
}

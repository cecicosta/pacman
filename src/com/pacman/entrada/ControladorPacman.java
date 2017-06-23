package com.pacman.entrada;

import com.pacman.graphics.AnimadorPacman;
import com.pacman.graphics.AnimadorPacman.Frames;

public class ControladorPacman extends ControladorAutomato {

	private int passoAtuaizacao = 0;

	public ControladorPacman(int i, int j) {
		super(i, j);
	}	
	
	/**
	* Recebe uma nova entrada da palavra que ser� processada pelo aut�mato
	* @param c Charcater representando a entrada da palavra
	*/
	public void setEntrada(char c){
		if(leituraCompleta()) //Projetado para garantir a consist�ncia da checagem "leituraCompleta()" com a MaquinaDeEstados
			setLeituraPendente();
		palavra += c;
	}
	
	/**
	* M�todo de callback chamado pela MaquinaDeEstados quando o estado � atualizado.
	* @param anterior Estado antes da atualiza��o
	* @param novo Estado ap�s a atualiza��o
	*/
	@Override
	public void estadoAtualizado(Estados anterior, Estados novo){
		if(Labirinto.getCelula(posicao[0], posicao[1]) == '.'){
			Labirinto.setCelula(posicao[0], posicao[1], 'n');
		}
	}

	/**
	* Este m�todo executa os passos necess�rios para atualiza��o da entrada e 
	* coordenadas no labirinto, al�m de definir os frames atuais da anima��o.
	* @param entrada Nova entrada da palavra que o aut�mato do pacman ir� processar.
	* @param pacman Animador do pacman. Anima��o definida de acordo com a entrada estado
	* @param espera se verdadeira indica que o m�todo deve esperar ap�s a atualiza��o. 
	* Utilizado para que o �rbitro atualize a posi��o da anima��o de acordo com a 
	* nova coordenada.
	*/
	//Passos para atualiza��o da entrada, anima��es e coordenadas no labirinto. 
	//entra em espera at� que o �rbitro termine de atualizar a posi��o na tela 
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

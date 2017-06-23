package com.pacman.entrada;

import java.util.Random;

public class ControladorFantasma extends ControladorAutomato{
	
	/**
	 * A Intelig�ncia dos fantasmas � baseada na posi��o relativa do pacman � cada um.
	 * Eles ir�o se mover sempre para alguma c�lula do labirinto que o coloque mais pr�ximo do pacman.
	 * E.x.: Caso o pacman se localize � esquerda e acima de um fantasma, ele poder� escolher se mover
	 * para a esquerda ou para cima, de acordo com um fator aleat�rio inserido.
	 * O fator aleat�rio foi inserido para que os fantasmas se espalhem pelo labirinto, possibilitando
	 * cercar o pacman.
	 */
	
	Random decisao = new Random();
	private int estadoPipeline;
	public ControladorFantasma(int i, int j) {
		super(i, j);
	}

	/**
	* Este m�todo implementa a Intelig�ncia dos fantasmas baseada na posi��o relativa do 
	* pacman � cada um. A escolha ir� definir a nova entrada da palavra que ser� processada 
	* pelo aut�mato que controla os fantasmas.
	* @param i Coordenada da linha do alvo no labirinto
	* no Frame.
	* @param j Coordanada da coluna do alvo no labirinto.
	*/
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
	

	/**
	* Este m�todo executa os passos necess�rios para atualiza��o da entrada e 
	* coordenadas no labirinto.
	* @param i Coordenada da linha do alvo no labirinto
	* no Frame.
	* @param j Coordanada da coluna do alvo no labirinto.
	* @param espera se verdadeira indica que o m�todo deve esperar ap�s a atualiza��o. 
	* Utilizado para que o �rbitro atualize a posi��o da anima��o de acordo com a 
	* nova coordenada.
	*/
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

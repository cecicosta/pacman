package com.pacman.entrada;

public class ControladorAutomato {

	/**
	 * ControladorAutomato : Implementa as funcionalidades básicas do controlador
	 * dos elementos móveis. Assim como o autômato finito deterministico (AFD) que controla a
	 * movimentação desses elementos no labirinto. 
	 * O alfabeto aceito pelo autômato é o seguinte: [c*b*e*d*]*
	 * O autômato garante que dado uma palavra que pertença ao alfabeto gerado pela linguagem,
	 * é possivel traçar a exata posição, em qualquer estado, de qualquer dos elementos móveis 
	 * controlados pelo autômato.
	 */
	
	String palavra = "";
	Estados estado = Estados.PARADO;
	int[] posicao = {0,0};
	private boolean coordAtualizada = true;
	private boolean leituraCompleta = true;
	public ControladorAutomato(int i, int j){
		posicao[0] = i;
		posicao[1] = j;
	}
	public ControladorAutomato(){
		posicao[0] = 0;
		posicao[1] = 0;
	}
	
	/**
	 * 
	 * @return a palavra gerada até então
	 */
	public String getPalavra() {
		return palavra;
	}

	/**
	 * Recebe o novo estado "final", a cada leitura de um character da palavra
	 * @param estado
	 */
	public void setEstado(Estado estado) {
		int i = 0;
		int j = 0;
		Estados anterior = this.estado;
		this.estado = (Estados) estado;
		switch(this.estado){
		case DIREITA:
			j++;
			break;
		case ESQUERDA:
			j--;
			break;
		case CIMA:
			i--;
			break;
		case BAIXO:
			i++;
			break;
		default:
			break;
		}
		//Impede que a entidade invada uma posição invalida no labirinto
		if(Labirinto.getCelula(posicao[0] + i, posicao[1] + j) != 'x'){
			posicao[0] += i;
			posicao[1] += j;
			coordAtualizada = true;
		}else{
			coordAtualizada = false;
		}
			
		estadoAtualizado(anterior, this.estado);
	}
	
	/**
	 * Informa se a coordenada foi atualizada, considerando a ultima entrada ou se 
	 * falhou (ex: colidisão com parede).
	 * @return true, se a ultima entrada causou uma mudança de coordenada, 
	 * false caso contrario.
	 */
	public boolean coordAtualizada(){
		return coordAtualizada;
	}

	public Estados getEstado() {
		return estado;
	}
	
	public int[] getCoordenadas(){
		return posicao;
	}
	public void setCoordenadas(int i, int j){
		posicao[0] = i;
		posicao[1] = j;
	}
	
	public void resetarControlador(){
		estado = Estados.PARADO;
		palavra = "";
		posicao[0] = 0;
		posicao[1] = 0;
	}
	
	/**
	 * informa se a leitura da palavra, considerando a ultima entrada, foi completa
	 * @return true, se a palavra foi completamente lida, false, caso contrário.
	 */
	public boolean leituraCompleta(){
		return leituraCompleta;
	}
	
	public void setLeituraCompleta(){
		leituraCompleta = true;
	}
	
	public void setLeituraPendente(){
		leituraCompleta = false;
	}
	
	/**
	 * Método callback para quando o estado é atualizado
	 * @param anterior estado anterior
	 * @param novo novo estado
	 */
	public void estadoAtualizado(Estados anterior, Estados novo){}
	
	/**Representação do autômato para controle de movimentação dos elementos móveis
	 * O autômato pode ser representado por um grafo completamete conectado exceto 
	 * pelo estado final MORTO. O automato tem comportamento indefinido para entradas 
	 * fora do alfabeto [a*b*c*d*]*, exceto para o estado MORTO.
	 */
	//
	public enum Estados implements Estado {
		PARADO {
	        @Override
	        public Estado proximo(char word) {
	            switch(word) {
                case 'c': return CIMA;
                case 'b': return BAIXO;
                case 'e': return ESQUERDA;
                case 'd': return DIREITA;
                case 'm': return MORTO;
                case 'p': return PARADO;
	            default: return null;
	            }
	        }
	    },
		CIMA {
	        @Override
	        public Estado proximo(char word) {
	            switch(word) {
                case 'c': return CIMA;
                case 'b': return BAIXO;
                case 'e': return ESQUERDA;
                case 'd': return DIREITA;
                case 'm': return MORTO;
                case 'p': return PARADO;
                default: return null;
	            }
	        }
	    },
	    BAIXO {
	        @Override
	        public Estado proximo(char word) {
	            switch(word) {
                case 'c': return CIMA;
                case 'b': return BAIXO;
                case 'e': return ESQUERDA;
                case 'd': return DIREITA;
                case 'm': return MORTO;
                case 'p': return PARADO;
	            default: return null;
	            }
	        }
	    },
		ESQUERDA {
	        @Override
	        public Estado proximo(char word) {
	            switch(word) {
                case 'c': return CIMA;
                case 'b': return BAIXO;
                case 'e': return ESQUERDA;
                case 'd': return DIREITA;
                case 'm': return MORTO;
                case 'p': return PARADO;
	            default: return null;
	            }
	        }
	    },
		DIREITA {
	        @Override
	        public Estado proximo(char word) {
	            switch(word) {
                case 'c': return CIMA;
                case 'b': return BAIXO;
                case 'e': return ESQUERDA;
                case 'd': return DIREITA;
                case 'm': return MORTO;
                case 'p': return PARADO;
	            default: return null;
	            }
	        }
	    },
		MORTO {
	    	@Override
	        public Estado proximo(char word) {
		           return MORTO;
            }
        },
	    
	}	
}

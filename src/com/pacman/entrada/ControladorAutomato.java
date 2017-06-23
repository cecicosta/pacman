package com.pacman.entrada;

public class ControladorAutomato {

	/**
	 * ControladorAutomato : Implementa as funcionalidades b�sicas do controlador
	 * dos elementos m�veis. Assim como o aut�mato finito deterministico (AFD) que controla a
	 * movimenta��o desses elementos no labirinto. 
	 * O alfabeto aceito pelo aut�mato � o seguinte: [c*b*e*d*]*
	 * O aut�mato garante que dado uma palavra que perten�a ao alfabeto gerado pela linguagem,
	 * � possivel tra�ar a exata posi��o, em qualquer estado, de qualquer dos elementos m�veis 
	 * controlados pelo aut�mato.
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
	 * @return a palavra gerada at� ent�o
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
		//Impede que a entidade invada uma posi��o invalida no labirinto
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
	 * falhou (ex: colidis�o com parede).
	 * @return true, se a ultima entrada causou uma mudan�a de coordenada, 
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
	 * @return true, se a palavra foi completamente lida, false, caso contr�rio.
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
	 * M�todo callback para quando o estado � atualizado
	 * @param anterior estado anterior
	 * @param novo novo estado
	 */
	public void estadoAtualizado(Estados anterior, Estados novo){}
	
	/**Representa��o do aut�mato para controle de movimenta��o dos elementos m�veis
	 * O aut�mato pode ser representado por um grafo completamete conectado exceto 
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

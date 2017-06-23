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
	Estados estado = Estados.DIREITA;
	int[] posicao = {0,0};
	private boolean corrdAtualizada = true;
	private boolean leituraCompleta = true;
	public ControladorAutomato(int i, int j){
		posicao[0] = i;
		posicao[1] = j;
	}
	public ControladorAutomato(){
		posicao[0] = 0;
		posicao[1] = 0;
	}
	
	public String getPalavra() {
		return palavra;
	}

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
			corrdAtualizada = true;
		}else{
			corrdAtualizada = false;
		}
			
		estadoAtualizado(anterior, this.estado);
	}
	
	public boolean coordAtualizada(){
		return corrdAtualizada;
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
		palavra = "";
		posicao[0] = 0;
		posicao[1] = 0;
	}
	
	public boolean leituraCompleta(){
		return leituraCompleta;
	}
	
	public void setLeituraCompleta(){
		leituraCompleta = true;
	}
	
	public void setLeituraPendente(){
		leituraCompleta = false;
	}
	
	public void estadoAtualizado(Estados anterior, Estados novo){}
	
	//Representa��o do automato para controle de movimenta��o
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
	            default: return null;
	            }
	        }
	    },
		MORTO {
	        public Estado proximo(char word) {
		           return MORTO;
            }
        },
	    
	}	
}

package com.pacman.entrada;

public class ControladorCallback {

	String palavra = "";
	Estados estado = Estados.DIREITA;
	int[] posicao = {0,0};
	public ControladorCallback(int i, int j){
		posicao[0] = i;
		posicao[1] = j;
	}
	public String getPalavra() {
		return palavra;
	}

	public void setEstado(Estado estado) {
		int i = 0;
		int j = 0;
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
		}
		if(Labirinto.celula(posicao[0] + i, posicao[1] + j) != 'x'){
			posicao[0] += i;
			posicao[1] += j;
		}
	}

	public Estados getEstado() {
		return estado;
	}
	
	public int[] getPosicao(){
		return posicao;
	}
	public void setPosicao(int i, int j){
		posicao[0] = i;
		posicao[1] = j;
	}
	
	public void resetarControlador(){
		palavra = "";
		posicao[0] = 0;
		posicao[1] = 0;
	}
	
	//Representação do automato para controle de movimentação
	public enum Estados implements Estado {
		CIMA {
	        @Override
	        public Estado proximo(char word) {
	            switch(word) {
                case 'c': return CIMA;
                case 'b': return BAIXO;
                case 'e': return ESQUERDA;
                case 'd': return DIREITA;
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
	            default: return null;
	            }
	        }
	    },
	}	
}

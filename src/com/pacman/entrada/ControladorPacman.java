package com.pacman.entrada;

public class ControladorPacman {

	public enum Estatos implements Estado {
		CIMA {
	        @Override
	        public Estado proximo(Entrada word) {
	            switch(word.leia()) {
                case 'a': return CIMA;
                case 'b': return BAIXO;
                case 'c': return ESQUERDA;
                case 'd': return DIREITA;
	            default: return null;
	            }
	        }
	    },
	    BAIXO {
	        @Override
	        public Estado proximo(Entrada word) {
	            switch(word.leia()) {
                case 'a': return CIMA;
                case 'b': return BAIXO;
                case 'c': return ESQUERDA;
                case 'd': return DIREITA;
	            default: return null;
	            }
	        }
	    },
		ESQUERDA {
	        @Override
	        public Estado proximo(Entrada word) {
	            switch(word.leia()) {
                case 'a': return CIMA;
                case 'b': return BAIXO;
                case 'c': return ESQUERDA;
                case 'd': return DIREITA;
	            default: return null;
	            }
	        }
	    },
		DIREITA {
	        @Override
	        public Estado proximo(Entrada word) {
	            switch(word.leia()) {
                case 'a': return CIMA;
                case 'b': return BAIXO;
                case 'c': return ESQUERDA;
                case 'd': return DIREITA;
	            default: return null;
	            }
	        }
	    },
	}	
}

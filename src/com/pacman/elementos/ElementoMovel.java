package com.pacman.elementos;

import com.pacman.entrada.ControladorAutomato;
import com.pacman.entrada.Labirinto;
import com.pacman.entrada.MaquinaDeEstados;

public class ElementoMovel {

	protected int[] ultimaPosicao = new int[2];
	protected ControladorAutomato controlador;
	protected MaquinaDeEstados maquinaDeEstados;
	protected Thread thread;
	
	protected boolean posicaoFinal = false;
	protected float speed = 0.25f;
	protected float velocidade = 0;
	char inicio;
	public ElementoMovel(ControladorAutomato controlador, char inicio){
		this.inicio = inicio;
		
		int[] pos = Labirinto.coordenadaCelula(inicio);
		ultimaPosicao[0] = pos[0]*Labirinto.getDimensao();
		ultimaPosicao[1] = pos[1]*Labirinto.getDimensao();
		this.controlador = controlador;
		this.controlador.setCoordenadas(pos[0], pos[1]);
		maquinaDeEstados = new MaquinaDeEstados(controlador);
	}
	
	public void reiniciar(){
		maquinaDeEstados.resetar();
		int[] pos = Labirinto.coordenadaCelula(inicio);
		ultimaPosicao[0] = pos[0]*Labirinto.getDimensao();
		ultimaPosicao[0] = pos[1]*Labirinto.getDimensao();
		controlador.setCoordenadas(pos[0], pos[1]);
	}
	
	public Thread iniciar(){
		thread = new Thread(maquinaDeEstados);
		thread.start();	
		return thread;
	}
	
	public Thread getThread(){
		return thread;
	}

	public ControladorAutomato getControlador() {
		return controlador;
	}
}

package com.pacman.entrada;

public class ControladorPacman extends ControladorAutomato {

	public ControladorPacman(int i, int j) {
		super(i, j);
	}	
	public void controlador(char c){
		palavra += c;
	}
}

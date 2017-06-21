package com.pacman.entrada;

interface Estado {
    public Estado proximo(char in);
}

public class MaquinaDeEstados implements Runnable{	
	private ControladorAutomato c;
    private int atual = 0;
    public MaquinaDeEstados(ControladorAutomato c) {
    	this.c = c;
    }
	@Override
	public void run() {
		while(true){
			try { Thread.sleep(30); } 
			catch (InterruptedException e)
			{
				return;
			}
			if(atual < c.getPalavra().length()){
				Estado e = c.getEstado();
				System.out.flush();
				c.setEstado(e.proximo(c.getPalavra().charAt(atual++)));	
			}
		}
	}
}
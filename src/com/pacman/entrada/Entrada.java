package com.pacman.entrada;

interface Estado {
    public Estado proximo(char in);
}

public class Entrada implements Runnable{	
	private ControladorCallback c;
    private int atual = 0;
    public Entrada(ControladorCallback c) {
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
			//System.out.println(atual);
			if(atual < c.getPalavra().length()){
				System.out.println(atual);
				Estado e = c.getEstado();
				System.out.println("State machine");
				System.out.flush();
				c.setEstado(e.proximo(c.getPalavra().charAt(atual++)));	
			}
		}
	}
}
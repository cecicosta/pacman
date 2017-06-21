package com.pacman.entrada;

public class Labirinto {
	private static int altura = 31;
	private static int largura = 46;
	public static String labirinto = ""
			+ "                                              "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "
			+ " x.........................................ox "
			+ " x.xxxxxxx.xxx.xxx.xxxxx.xxx.xxx.xxxxxx.xxx.x "
			+ " x.x     x.x x.x x.x   x.x x.x x.x    x.x x.x "
			+ " x.xxxxxxx.x x.xxx.xxx x.xxx.xxx.xxxx x.xxx.x "
			+ " x........ox x......ox x............x x.....x "
			+ " x.xxx.xxxxx x.xxx.xxx x.xxxxxxxxxxxx x.xxx.x "
			+ " x.x x.x     x.x x.x   x.x            x.x x.x "
			+ " x.xxx.xxxxxxx.xxx.xxxxx.xxxxxxxxxxxxxx.xxx.x "
			+ " x..........................................x "
			+ " x.xxxxxx.xxx.xxxxxxx xxxxxxx.xxxx.xxxxxxxxxx "
			+ " x.x    x.x x.x     x x     x.x  x.x          "
			+ " x.xxxx x.x x.x xxxxx xxxxx x.x  x.x          "
			+ " x....x x.xxx.x x         x x.x  x.x          "
			+ " x.xxxx x.....xxx         xxx.xxxx.x          "
			+ " x.x    x.xxx.   0  1 2  3   ......x          "
			+ " x.x    x.x x.xxxxxxxxxxxxxxx.xxxx.x          "
			+ " x.x    x.x x.x             x.x  x.x          "
			+ " x.xxxxxx.xxx.xxxxxxxxxxxxxxx.xxxx.xxxxxxxxxx "
			+ " x...................s......................x "
			+ " x.xxx.xxxxxxxxxxxxxx.xxxxx.xxx.xxxxxxx.xxx.x " 	
			+ " x.x x.x            x.x   x.x x.x     x.x x.x " 			
			+ " x.xxx.x xxxxxxxxxxxx.x xxx.xxx.x xxxxx.xxx.x " 			
			+ " x.....x x............x xo......x xo........x " 			
			+ " x.xxx.x xxxx.xxx.xxx.x xxx.xxx.x x.xxxxxxx.x " 			
			+ " x.x x.x    x.x x.x x.x   x.x x.x x.x     x.x " 			
			+ " x.xxx.xxxxxx.xxx.xxx.xxxxx.xxx.xxx.xxxxxxx.x " 
			+ " xo.........................................x "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "

			+ "                                              ";
	
	public static char celula(int i, int j){
		return labirinto.charAt(j + i*largura);
	}
	
	public static int[] coordenadaInicial(){
		int posicao = Labirinto.labirinto.indexOf("s");
		return new int[]{posicao/largura, posicao%largura} ;
	}
	
	public static int[] coordenadaCelula(char c){
		int posicao = Labirinto.labirinto.indexOf(c);
		return new int[]{posicao/largura, posicao%largura} ;
	}
	
}

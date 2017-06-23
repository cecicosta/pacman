package com.pacman.entrada;

public class Labirinto {
	private static int altura = 31;
	private static int largura = 46;
	private static int dim = 12;
	public static String labirinto = ""
			+ "                                              "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "
			+ " x..........................................x "
			+ " x.xxxxxxx.xxx.xxx.xxxxx.xxx.xxx.xxxxxx.xxx.x "
			+ " x.x     x.x x.x x.x   x.x x.x x.x    x.x x.x "
			+ " x.xxxxxxx.x x.xxx.xxx x.xxx.xxx.xxxx x.xxx.x "
			+ " x.........x x.......x x............x x.....x "
			+ " x.xxx.xxxxx x.xxx.xxx x.xxxxxxxxxxxx x.xxx.x "
			+ " x.x x.x     x.x x.x   x.x            x.x x.x "
			+ " x.xxx.xxxxxxx.xxx.xxxxx.xxxxxxxxxxxxxx.xxx.x "
			+ " x..........................................x "
			+ " x.xxxxxx.xxx.xxxxxxxexxxxxxx.xxxx.xxxxxxxxxx "
			+ " x.x    x.x x.x     xex     x.x  x.x          "
			+ " x.xxxx x.x x.x xxxxxexxxxx x.x  x.x          "
			+ " x....x x.xxx.x xeeeeeeeeex x.x  x.x          "
			+ " x.xxxx x.....xxxeeeeeeeeexxx.xxxx.x          "
			+ " x.x    x.xxx.eee0ee1e2ee3eee......x          "
			+ " x.x    x.x x.xxxxxxxxxxxxxxx.xxxx.x          "
			+ " x.x    x.x x.x             x.x  x.x          "
			+ " x.xxxxxx.xxx.xxxxxxxxxxxxxxx.xxxx.xxxxxxxxxx "
			+ " x...................s......................x "
			+ " x.xxx.xxxxxxxxxxxxxx.xxxxx.xxx.xxxxxxx.xxx.x " 	
			+ " x.x x.x            x.x   x.x x.x     x.x x.x " 			
			+ " x.xxx.x xxxxxxxxxxxx.x xxx.xxx.x xxxxx.xxx.x " 			
			+ " x.....x x............x x.......x x.........x " 			
			+ " x.xxx.x xxxx.xxx.xxx.x xxx.xxx.x x.xxxxxxx.x " 			
			+ " x.x x.x    x.x x.x x.x   x.x x.x x.x     x.x " 			
			+ " x.xxx.xxxxxx.xxx.xxx.xxxxx.xxx.xxx.xxxxxxx.x " 
			+ " x..........................................x "
			+ " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "

			+ "                                              ";
	
	public static char getCelula(int i, int j){
		return labirinto.charAt(j + i*getLargura());
	}
	
	public static void setCelula(int i, int j, char c){
		char[] array = labirinto.toCharArray();
		array[j + i*getLargura()] = c;
		labirinto = new String(array);
	}
	
	/**
	 * Encontra a coordenada de um character especifico
	 * @param c character a ser procurado
	 * @return array com duas posições, representando as coordenadas i,j (linha, coluna) do character.
	 */
	public static int[] coordenadaCelula(char c){
		int posicao = Labirinto.labirinto.indexOf(c);
		if(posicao == -1)
			return null;
		
		return new int[]{posicao/getLargura(), posicao%getLargura()} ;
	}

	public static int getAltura() {
		return altura;
	}

	public static void setAltura(int altura) {
		Labirinto.altura = altura;
	}

	public static int getLargura() {
		return largura;
	}

	public static void setLargura(int largura) {
		Labirinto.largura = largura;
	}

	public static int getDimensao() {
		return dim;
	}

	public static void setDim(int dim) {
		Labirinto.dim = dim;
	}
	
}

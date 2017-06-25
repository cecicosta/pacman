package com.pacman.graphics;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.pacman.elementos.Fantasma;
import com.pacman.elementos.Pacman;
import com.pacman.entrada.ControladorFantasma;
import com.pacman.entrada.ControladorPacman;
import com.pacman.entrada.Labirinto;

public class Arbitro extends JPanel implements ActionListener, KeyListener{

	/**Classe que implementa o Árbitro no jogo pacman.
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int altura;
	private int largura;
	
	private boolean exibirTelaInicial = true;
	private int contadorVidas = 5;
	private Animador vida;
	private Animador telaInicial;
	private RenderizadorLabirinto labirinto;
	
	private Fantasma[] fantasmas = new Fantasma[4];
	private Pacman pacman;
	private char entrada = 'p';
	private boolean reposicionando;
	private Button button;
	private Image imgNome;
	private Image imgPontos;
	private Frame frame;

	private boolean suspenderJogo;

	private String mensagem;

	private boolean exibirMensagem;

	private boolean reiniciando;
	
	/**
	* Construtor do Árbitro. É responsavel por inicializar os elementos 
	* da interface e elementos móveis como o pacman e os fantasmas.
	*/
	public Arbitro(Frame frame, int largura, int altura){
		this.frame = frame;
		setLayout(null);
		this.largura = largura;
		this.altura = altura;
		//setSize(largura, altura);
		
		iniGUI();
		
		addKeyListener(this);
		
		labirinto = new RenderizadorLabirinto(frame);
		
		for(int i=0; i<4; i++){
			fantasmas[i] = new Fantasma(frame,new ControladorFantasma(0,0), Integer.toString(i).charAt(0));
			fantasmas[i].iniciar();
		}
		
		pacman = new Pacman(frame, new ControladorPacman(0, 0), 's');
		pacman.iniciar();
		
	}
	
	/**
	* Este método inicializa os elementos da GUI, como menus e botões.
	*/
	void iniGUI()
	{
		vida = new Animador(frame);
		vida.carregarFrames("pacman-large.png", 7, 5, 1, 1, 16, 0.25f);
		vida.Play(Animador.Frames.NONE);
		
		telaInicial = new Animador(frame);
		telaInicial.carregarFrames("capa.png", 0, 0, 1, 1, largura, 0.5f);
		telaInicial.Play(Animador.Frames.NONE);
		
	    //Cria botão para iniciar o jogo
	    button = new Button("Iniciar");
	    button.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				
				setFocusable(true);
				requestFocusInWindow();
				button.setVisible(false);
				exibirTelaInicial = false;
				exibirMensagem = false;
				suspenderJogo = false;
				contadorVidas = 10;
			}
		});
	    add(button);
	    button.setBounds(260, 210, 100, 30);
	    revalidate();
	}
	

	/**
	* Este método cria uma thread para interromper as threads dos elementos móveis e do Árbitro
	* antes de reposicionar.
	*/
	void reposicionar(){
	
		if(reposicionando)
			return;
		contadorVidas--;
		reposicionando = true;
		Thread t = new Thread(new Runnable() {
			
			public void run()
			{
				//Pequeno intervalo antes que as posições sejam reiniciadas
				try { Thread.sleep(2000); } 
				catch (InterruptedException e) {
					return;
				}

				entrada = 'p';
				boolean todosInterrompidos = false;
				
				try{
					for(int i=0; i<4; i++){
						fantasmas[i].getThread().interrupt();
					}
					pacman.getThread().interrupt();
				}catch(Exception e){
					e.printStackTrace();
				}
				//Espera até que todas as threads sejam interrompidas
				while(!todosInterrompidos){
					todosInterrompidos = true;
					for(int i=0; i<4; i++){
						if(fantasmas[i].getThread().isAlive()){
							todosInterrompidos = false;
							break;
						}
					}
					todosInterrompidos = pacman.getThread().isAlive()? false: todosInterrompidos;
				}
				//Reinicia todas as threads
				for(int i=0; i<4; i++){
					fantasmas[i].reiniciar();
					fantasmas[i].iniciar();
				}
				pacman.reiniciar();
				pacman.iniciar();
				reposicionando = false;

			}
		});
		t.start();
	}
	
	/**
	* Este método reinicia os elementos móveis e o estado do laborinto 
	* para o estado inicial.
	*/
	void reiniciar(){
		if(reiniciando || reposicionando)
			return;
		reiniciando = true;
		contadorVidas--;
		Thread t = new Thread(new Runnable() {
			
			public void run()
			{
				exibirMensagem = true;
				
				try { Thread.sleep(4000); } 
				catch (InterruptedException e) {
					return;
				}
				suspenderJogo = true;
				exibirTelaInicial = true;
				Labirinto.reiniciar();
				pacman.getControlador().setContadotorPontos(0);
				reposicionar();
				labirinto.desenhar();
				reiniciando = false;
			}
		});
		t.start();
	}
	
	/**
	* Método invocado pelo Frame para atualizar a tela.
	* @param g Objeto do tipo Graphic, utilizado para renderizar as imagens
	* no Frame.
	*/
	public void telaInicial(Graphics g){
		
		if(exibirTelaInicial){
			imgNome = createImage(250,30);
			Graphics imgNomeG = imgNome.getGraphics();
			imgNomeG.setColor(Color.black);
			imgNomeG.fillRect(0, 0, 250, 30);
			imgNomeG.setColor(Color.green);
			imgNomeG.setFont(new Font("Helvetica", Font.BOLD, 12));
			imgNomeG.drawString("Desenvolvido Por: Luis Felipe Nogueira", 0, 20);
			
			g.setColor(Color.black);
			g.fillRect(0,0, largura, altura);
			button.setVisible(true);
			telaInicial.Animar(g, 0, 0);
			g.drawImage(imgNome, 
					200, 300, this);
		}
	}
	
	/**
	* Chama as rotinas específicas para atualizar os elementos móveis
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens
	* no Frame.
	*/
	public void updateGame(Graphics g)
	{
		Image temp = createImage(largura, altura);
		Graphics tempG = temp.getGraphics();
		if(!suspenderJogo){
			
			tempG.setColor(Color.black);
			tempG.fillRect(0,0, largura, altura);
			
			int posX = largura/2 - labirinto.getLargura()*labirinto.getDimensao()/2;
			int posY = altura/2 - labirinto.getAltura()*labirinto.getDimensao()/2;
			labirinto.renderizar(tempG, posX, posY);
			
			fantasmasAtualizacao(tempG, posX, posY);
			pacmanAtualizacao(tempG, posX, posY);
			
			for(int i=0; i<contadorVidas; i++){
				vida.Animar(tempG, posX + (labirinto.getLargura()-i - 2)*labirinto.getDimensao(), 
								   posY + labirinto.getAltura()*labirinto.getDimensao());
			}
			
			//Atualiza os pontos na tela - Quantidade de bolinhas comidas
			escreverBackground(tempG, 50, 420, "Pontos: " + pacman.getControlador().getContadotorPontos());
		}

		if(exibirMensagem){
			popup(tempG, mensagem);
		}
		telaInicial(tempG);
		g.drawImage(temp, 0, 0, this);
	}

	private void escreverBackground(Graphics g, int x, int y, String frase){
		imgPontos = createImage(250,30);
		Graphics imgPontosG = imgPontos.getGraphics();
		imgPontosG.setColor(Color.black);
		imgPontosG.fillRect(0, 0, 250, 30);
		imgPontosG.setColor(Color.green);
		imgPontosG.setFont(new Font("Helvetica", Font.BOLD, 12));
		imgPontosG.drawString(frase, 0, 20);
		g.drawImage(imgPontos, 
				x, y, this);
	}
	
	private void popup(Graphics g, String mensagem ){
		g.setColor(Color.orange);
		g.fillRect(largura/2 - 105, altura/2 - 55, 210, 110);
		g.setColor(Color.cyan);
		g.fillRect(largura/2 - 100, altura/2 - 50, 200, 100);

		Image imgTemp = createImage(100, 30);
		Graphics gtemp = imgTemp.getGraphics();
		gtemp.setColor(Color.cyan);
		gtemp.fillRect(0, 0, 100, 30);
		gtemp.setColor(Color.black);
		gtemp.setFont(new Font("Helvetica", Font.BOLD, 12));
		gtemp.drawString(mensagem, 0, 20);
		g.drawImage(imgTemp, 
						largura/2 - 40, altura/2 - 15, this);
	}
	
	/**
	* Este método chama as rotinas especificas do pacman para atualizar seu estado
	* e animar e renderizar os sprites na tela.
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens 
	* no Frame.
	* @param posX Posição X global na tela onde os objetos estão sendo renderizados.
	* @param posY Posição Y global na tela onde os objetos estão sendo renderizados.
	*/
	void pacmanAtualizacao(Graphics g, int posX, int posY){
		//Passos para atualizar o controlador esperam até pacman atingir posicao de destino
		pacman.atualizarControlador(entrada, pacman.movendo());
		pacman.move(g, posX, posY);
		if(Labirinto.vazio()){ //Chaca condição de vitória do pacman, "nenhum '.' no labirinto
			mensagem = "Você ganhou!";
			reiniciar();
		}
	}
	
	/**
	* Este método chama as rotinas especificas dos fantasmas para atualizar seus estados
	* e, animar e renderizar os sprites na tela.
	* @param g Objeto do tipo Graphics, utilizado para renderizar as imagens 
	* no Frame.
	* @param posX Posição X global na tela onde os objetos estão sendo renderizados.
	* @param posY Posição Y global na tela onde os objetos estão sendo renderizados.
	*/
	void fantasmasAtualizacao(Graphics g, int posX, int posY){
		//Obtém coordenadas do pacma, para que os fantasmas o localizem
		int[] coord = pacman.getControlador().getCoordenadas();
		
		for(int i=0; i<4; i++){
			fantasmas[i].move(g, posX, posY, coord[0], coord[1]);
			int[] pos = fantasmas[i].getControlador().getCoordenadas();
			//Checa a condição de morte do pacman "encostar em um fantasma"
			if(pos[0] == coord[0] && pos[1] == coord[1] && !reposicionando && !reiniciando){
				entrada = 'm';
				if(contadorVidas > 1)
					reposicionar(); //reinicia os elementos móveis
				else{
					mensagem = "Você perdeu!";
					reiniciar();
				}
				break;
			}
		}
	}

	public void finalizar() { 
		for(int i=0; i<4; i++){
			fantasmas[i].getThread().interrupt();
		}
		pacman.getThread().interrupt();
	}

	@Override
	public void actionPerformed(ActionEvent e) {}	
	

	@Override
	public void keyPressed(KeyEvent arg0) {		
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			entrada = 'd';
			break;
		case KeyEvent.VK_LEFT:
			entrada = 'e';
			break;
		case KeyEvent.VK_UP:
			entrada = 'c';
			break;
		case KeyEvent.VK_DOWN:
			entrada = 'b';
			break;
		default:
		}
		
		arg0.consume();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}

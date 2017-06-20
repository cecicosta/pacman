package com.pacman.graphics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
public class AnimacaoPacman {
	
	private Image spriteSheet;
	private BufferedImage frames[] = new BufferedImage[20];
	private Frame frame;
	int count = 0;
	public AnimacaoPacman(Graphics g, Frame f){
		frame = f;
        String path = "pacman-large.png";
        try {
        	InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        	spriteSheet = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        BufferedImage tileset = new BufferedImage(spriteSheet.getWidth(f), 
        		spriteSheet.getHeight(f), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = tileset.createGraphics();
        
        
        for(int i=0; i < 6; i++){
        	g2d.drawImage(spriteSheet, - i*64, -5*64, f);
	        Raster temp = tileset.getData( new Rectangle( 0, 0, 64, 64) );
	        frames[i] = new BufferedImage( 64, 64, BufferedImage.TYPE_INT_RGB );
	        frames[i].setData( temp );
        }
        
        for(int i=0; i < 10; i++){
	        Raster temp = tileset.getData( new Rectangle( i*15, 108, 15, 15 ) );
	        frames[i+9] = new BufferedImage( 15, 15, BufferedImage.TYPE_INT_RGB );
	        frames[i+9].setData( temp );
        }
	}
	
	public void Play(Graphics g){
		g.fillRect(150, 150, 64, 64);
		g.drawImage( frames[count++], 150, 150, frame );

		if(count >7) count = 0;
		//g.drawImage( offScreenImage, 0, 0, this );
	}
}

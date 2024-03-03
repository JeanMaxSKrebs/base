package entities.itens.frutas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Uva extends Fruta {

    public static final double regen = 2;
    public static final int tickRegen = 5;
    public static final double curaTotal = 10;
    public static final String nome = "Uva";
	
	private BufferedImage[] spritesUva;

	
	public Uva(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite,  nome, regen, tickRegen, curaTotal);
		
		spritesUva = new BufferedImage[3];

		for(int i=0; i<qtdDirecoes; i++) {
			spritesUva[i] = Game.spritesheet.getSprite(224 + (i*32), 32, 32, 32);
		}
		
	}
	public void tick() {
		
		girar();
		verificaGiro();

	}
	

	public void render(Graphics g) {

		g.drawImage(spritesUva[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

}

package entities.itens.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Maca extends Fruta {

	public static final double regen = 2;
	public static final int tickRegen = 10;
	public static final double curaTotal = 20;
	public static final String nome = "Maca";


	private BufferedImage[] spritesMaca;

	public Maca(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite,  nome, regen, tickRegen, curaTotal);

		spritesMaca = new BufferedImage[qtdDirecoes];

		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMaca[i] = Game.spritesheet.getSprite(224 + (i * 32), 64, 32, 32);

		}

	}

	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

		g.drawImage(spritesMaca[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
	
	
}

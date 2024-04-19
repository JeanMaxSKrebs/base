package entities.itens.comidas.frutas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Player;
import entities.itens.Item;
import world.Camera;

@SuppressWarnings("unused")
public class Uva extends Fruta {

	public double regen = 3;
	public int tickRegen = 5;
	public double curaTotal = 20;
	public static final String nome = "Uva";

	private BufferedImage[] spritesUva;

	public Uva(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);

		spritesUva = new BufferedImage[3];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesUva[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 2, 64, 64);
		}

	}

	public Uva(Uva outraUva) {
		super(outraUva);
		
		spritesUva = new BufferedImage[4];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesUva[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 2, 64, 64);
		}
	}
	
	@Override
	public void comer(Item item) {
	    if (item instanceof Uva) {
	        Uva uva = (Uva) item;

	         
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
	
	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new Uva(this);
	}

}

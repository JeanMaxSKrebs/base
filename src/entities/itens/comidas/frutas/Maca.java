package entities.itens.comidas.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Player;
import entities.itens.Item;
import entities.itens.utensilios.Fogueira;
import world.Camera;
import world.World;

public class Maca extends Fruta {

	public double regen = 5;
	public int tickRegen = 3;
	public double curaTotal = 15;
	protected static final String nome = "Maçã";

	private BufferedImage[] spritesMaca;

	public Maca(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
		spritesMaca = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMaca[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64, 64, 64);

		}

	}

	public Maca(Maca outraMaca) {
		super(outraMaca);
		
		spritesMaca = new BufferedImage[4];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesMaca[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 1, 64, 64);
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

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub

	}

	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new Maca(this);
	}

	@Override
	public void comer(Item item) {
		// TODO Auto-generated method stub
		
	}
}

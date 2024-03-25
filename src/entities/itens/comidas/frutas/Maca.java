package entities.itens.comidas.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Player;
import world.Camera;

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

	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

			g.drawImage(spritesMaca[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	public void coletar(Player player) {
		incrementQuantity(); // Incrementa a quantidade do item
		player.obtainItem(this); // Adiciona o item à lista de itens do jogador
	}

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub

	}
}

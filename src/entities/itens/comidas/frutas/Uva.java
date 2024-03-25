package entities.itens.comidas.frutas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Player;
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

	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

		g.drawImage(spritesUva[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

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

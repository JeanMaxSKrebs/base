package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Morangueiro extends Arvore {

	protected static final String nome = "Morangueiro";

	private BufferedImage[] spritesMorangueiro;

	public Morangueiro(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesMorangueiro = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMorangueiro[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase*3, tamanhoBase, tamanhoBase);

		}
	}

	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

		g.drawImage(spritesMorangueiro[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void metodoAbstrato() {
		// TODO Auto-generated method stub
		
	}
}

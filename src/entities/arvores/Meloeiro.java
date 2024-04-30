package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Meloeiro extends Arvore {
	
	protected static final String nome = "Meloeiro";
	
	private BufferedImage[] spritesMeloeiro;

	public Meloeiro(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesMeloeiro = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMeloeiro[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase*5, tamanhoBase, tamanhoBase);

		}
	}

	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

		g.drawImage(spritesMeloeiro[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void metodoAbstrato() {
		// TODO Auto-generated method stub
		
	}
}

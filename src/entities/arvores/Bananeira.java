package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Bananeira extends Arvore {
	
	protected static final String nome = "Bananeira";

	private BufferedImage[] spritesBananeira;
	
	public Bananeira(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesBananeira = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesBananeira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase*7, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

			g.drawImage(spritesBananeira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void metodoAbstrato() {
		// TODO Auto-generated method stub
		
	}
}

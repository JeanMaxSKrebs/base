package entities.arvores;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Tomateiro extends Arvore {

    public boolean colher = false;
    public int tickColher = 0;
    public int tempoAmadurecimento = 5; // Tempo de amadurecimento em minutos (5 minutos na vida real)

	protected static final String nome = "Tomateiro";

	private BufferedImage[] spritesTomateiro;
	
	public Tomateiro(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesTomateiro = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesTomateiro[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase, tamanhoBase, tamanhoBase);

		}
	}

	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

		g.drawImage(spritesTomateiro[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void metodoAbstrato() {
		// TODO Auto-generated method stub
		
	}
}
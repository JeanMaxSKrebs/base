package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Boss extends Enemy {
	
	public static BufferedImage[] BOSS;
	
	public Boss(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		BOSS = new BufferedImage[1];
		
		BOSS[0] = Game.spritesheet.getSprite(0, 224, 32, 32);
	}
	
	public void tick() {
		moved = false;
		iamAlive();
		
		verificaMovimento();
	}
	public void render(Graphics g) {
		g.drawImage(BOSS[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void reloading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void preparadoAtacar() {
		// TODO Auto-generated method stub
		
	}

}

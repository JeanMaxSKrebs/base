package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;

public class Npc extends Entity {

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
//		g.setColor(Color.red);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}

}

package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Power extends Entity {
	
	private int dx, dy;
	private double speed = 3.5;
	private int dano = 50;
	private int life = 100, curLife = 0;

	public Power(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;

	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		curLife++;
		if(curLife == life) {
			Game.powers.remove(this);
			return;
		}
		colidi();
		}
	
		public void colidi() {
			for (int i = 0; i < Game.enemies.size(); i++) {
				Enemy e = Game.enemies.get(i);
				
				if(isCollidding(this, e)) {
					e.life -= this.dano;
					return;
				}
			}
		}

	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}

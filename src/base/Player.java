package base;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public boolean up, down;
	
	public int x, y;

	public int width, height;
	
	public int speed;

	public static int pontos;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 2;
		this.height = 32;
		this.speed = 3;
	}
	
	public static void pontuou() {
		pontos++;
	}
	
	public void tick() {
		if(up) {			
			y-=speed;
		} else if(down) {
			y+=speed;
		}
		if(y+height>Game.HEIGHT) {
			y = Game.HEIGHT - height;
		}
		if(y<0) {
			y = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
	}
}
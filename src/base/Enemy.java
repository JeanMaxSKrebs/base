package base;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

	public boolean up, down;
	
	public double x, y;

	public int width, height;
	
	public int speed;

	public static int pontos;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 2;
		this.height = 32;
		this.speed = 2;
		pontos = 0;
	}
	
	public static void pontuou() {
		pontos++;
	}
	
	public void tick() {
		double centro = y+height/2;
		if(Game.ball.dx>0) {
			if(centro>Game.ball.y)
				y-=speed;
			else
				y+=speed;
		} else if(y!=Game.HEIGHT/2-height/2) {
			if(y>Game.HEIGHT/2-height/2)
				y--;				
			else
				y++;
			
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
		g.fillRect((int)x, (int)y, width, height);
	}
}
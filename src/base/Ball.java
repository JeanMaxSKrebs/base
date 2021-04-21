package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
public boolean up, down;
	
	public double x, y;
	public double dx, dy;

	public int width, height;
	
	public double speed;
	
	public int angle;


	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 6;
		this.height = 6;
		this.speed = 5;
		
		angle = new Random().nextInt(45);
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() {


		if(y+height+(dy*speed) >= Game.HEIGHT) {
			dy*= -1;
		} else if(y+(dy*speed)<0) {
			dy*= -1;
		}
		if(x<0) {
			Enemy.pontuou();
			x = Game.WIDTH/2-3;
			y = Game.HEIGHT/2-3;
			angle = new Random().nextInt(45);


			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));

		} else if(x+width>=Game.WIDTH) {
			Player.pontuou();
			x = Game.WIDTH/2-3;
			y = Game.HEIGHT/2-3;
			angle = new Random().nextInt(45);

			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);
		
		if(bounds.intersects(boundsPlayer)) {
			angle = new Random().nextInt(45);
			System.out.println(angle);

			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dx<0)
				dx*= -1;
		} else if(bounds.intersects(boundsEnemy)) {
			dx*= -1;
			angle = new Random().nextInt(45);
			System.out.println(angle);

			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dx>0)
				dx*= -1;
		}	
		x+=dx*speed;
		y+=dy*speed;
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int)x, (int)y, width, height);
	}
}
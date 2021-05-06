package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Gosma extends Entity {
	
	private String direcao;
	private int dano = 7;

	private double speed = 2;
	
	private int life = 500, curLife = 0;

	public Gosma(int x, int y, int width, int height, BufferedImage sprite, String direcao) {
		super(x, y, width, height, sprite);
		
		this.direcao = direcao;

	}
	
	public void tick() {
		if(direcao == "x") {
			x = x - speed;
		} else 	if(direcao == "y") {
			y = y - speed;			
		}

		curLife++;
		if(curLife == life) {
			Game.gosmas.remove(this);
			return;
		}
		if(colidi()) {
			Game.gosmas.remove(this);
			Game.player.beingAttacked(dano);
		}
	}
	public boolean colidi() {		
		if(isCollidding(this, Game.player)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void render(Graphics g) {
//		g.setColor(Color.red);
//		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
//		
		g.setColor(Color.black);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
}
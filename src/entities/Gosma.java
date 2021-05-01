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
		g.setColor(Color.black);
		g.fillOval(this.getX() - Camera.x + 13, this.getY() - Camera.y + 13, width, height);
	}
}
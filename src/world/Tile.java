package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;
import entities.itens.Item;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet_Walls.getSprite(112, 0, 112, 112);
	public static BufferedImage TILE_WALL = Game.spritesheet_Walls.getSprite(0, 0, 112, 112);
	public static BufferedImage TILE_WALL_DESERT = Game.spritesheet_Walls.getSprite(224, 0, 112, 112);
	public static BufferedImage TILE_DOOR = Game.spritesheet_Doors.getSprite(0, 0, 112, 112);

	protected BufferedImage sprite;
	protected int x, y;
	protected int width, height;


	public Tile(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);

//		g.setColor(Color.green);
//		g.fillRect(this.x - Camera.x, this.y - Camera.y, Game.getWIDTH(), Game.getHEIGHT());
	}

	public int getX() {
		return (int) x;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	public void tick() {
		
	}

}

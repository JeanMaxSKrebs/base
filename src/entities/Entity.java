package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import base.Game;
import world.Camera;

public abstract class Entity {

	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(0, 224, 32, 32);

	public static BufferedImage KEY_EN = Game.spritesheet_Doors.getSprite(112, 0, 112, 112);
	public static BufferedImage SPECIALKEY_EN = Game.spritesheet_Doors.getSprite(336, 0, 112, 112);

	public static BufferedImage HPBAG_EN = Game.spritesheet.getSprite(128, 32, 32, 32);
	public static BufferedImage STAMINEBAG_EN = Game.spritesheet.getSprite(160, 32, 32, 32);

	public static BufferedImage ITEM_EN = Game.spritesheet_Items.getSprite(0, 0, 112, 112);

	public static BufferedImage FOGUEIRA_IT = Game.spritesheet_Items.getSprite(0, 112, 112, 112);

	public static BufferedImage COMIDA_IT = Game.spritesheet_Foods.getSprite(0, 0, 112, 112);

	public static BufferedImage FRUTA_CO = Game.spritesheet_Fruits.getSprite(0, 0, 64, 64);

	protected double x;
	protected double y;
	protected int width;
	protected int height;

	protected BufferedImage sprite;

	protected int maskx, masky, mwidth, mheight;

	public Entity() {
		// Lógica de inicialização, se necessário //fruta
	}

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}

	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
//		System.out.println("e1Mask");
//		System.out.println(e1Mask);
//		System.out.println(e2Mask);
//		System.out.println("e2Mask");

		return e1Mask.intersects(e2Mask);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);

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

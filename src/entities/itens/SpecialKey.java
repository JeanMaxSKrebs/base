package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import world.Camera;

@SuppressWarnings("unused")
public class SpecialKey extends Item {
	public static final String nome = "SpecialKey";

	public SpecialKey(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
	}
	
	public SpecialKey(SpecialKey outraSpecialKey) {
		super(outraSpecialKey);
		this.sprite = outraSpecialKey.sprite;
	}
	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new SpecialKey(this);
	}

	public void render(Graphics g) {

		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.yellow);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}
}
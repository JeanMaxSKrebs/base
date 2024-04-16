package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Maca;
import world.Camera;

public class Key extends Item {
	public static final String nome = "Key";

	public Key(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
	}
	
	public Key(Key outraKey) {
		super(outraKey);
		this.sprite = outraKey.sprite;
	}
	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new Key(this);
	}
	
	public void render(Graphics g) {

		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
//		g.setColor(Color.gray);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}

}

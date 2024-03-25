package entities.itens.utensilios;

import java.awt.image.BufferedImage;

import entities.itens.Item;

public abstract class Utensilio extends Item {

	public Utensilio(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite, nome);
	}

	public Utensilio(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub

	}
}

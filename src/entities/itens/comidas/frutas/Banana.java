package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import entities.itens.Item;

public class Banana extends Fruta {

    public static final double regen = 2;
    public static final int tickRegen = 5;
    public static final double curaTotal = 10;
    public static final String nome = "Banana";

    public Banana(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome, regen, tickRegen, curaTotal);
    }

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return null;
	}
}

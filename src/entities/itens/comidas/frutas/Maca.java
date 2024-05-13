package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Maca extends Fruta {

    public static final double regen = 2.0; // Exemplo: regeneração de 2.0
    public static final int tickRegen = 5; // Exemplo: a cada 5 ticks
    public static final double curaTotal = 10; // Exemplo: cura total de 10
    public static final String nome = "Maçã";

	private BufferedImage[] spritesMaca;

	public Maca(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
		spritesMaca = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMaca[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 *5, 64, 64);

		}

	}

	public Maca(Maca outraMaca) {
		super(outraMaca);
		
		spritesMaca = new BufferedImage[4];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesMaca[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 5, 64, 64);
		}
	}
	
	public void tick() {

		girar();
		verificaGiro();

	}

    @Override
    public void coletarEspecifico() {
        // Implementação específica
    }

    @Override
    public Item clone() {
        // Implementação específica
        return null;
    }

    @Override
    public void comer(Item item) {
        // Implementação específica
    }
}

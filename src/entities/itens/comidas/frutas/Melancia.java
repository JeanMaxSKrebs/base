package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Melancia extends Fruta {

    public static final double regen = 2.5;
    public static final int tickRegen = 7;
    public static final double curaTotal = 12;
    public static final String nome = "Melancia";

    private BufferedImage[] spritesMelancia;

    public Melancia(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesMelancia = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelancia[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 9, 64, 64);
        }
    }

    public Melancia(Melancia outraMelancia) {
        super(outraMelancia);

        spritesMelancia = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelancia[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 5, 64, 64);
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

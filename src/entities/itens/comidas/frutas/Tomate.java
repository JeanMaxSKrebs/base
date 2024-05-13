package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Tomate extends Fruta {

    public static final double regen = 1.5;
    public static final int tickRegen = 6;
    public static final double curaTotal = 8;
    public static final String nome = "Tomate";

    private BufferedImage[] spritesTomate;

    public Tomate(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesTomate = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesTomate[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 2, 64, 64);
        }
    }

    public Tomate(Tomate outroTomate) {
        super(outroTomate);

        spritesTomate = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesTomate[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 2, 64, 64);
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

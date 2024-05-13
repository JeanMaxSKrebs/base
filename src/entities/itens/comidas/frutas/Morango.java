package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Morango extends Fruta {

    public static final double regen = 1.8;
    public static final int tickRegen = 5;
    public static final double curaTotal = 9;
    public static final String nome = "Morango";

    private BufferedImage[] spritesMorango;

    public Morango(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesMorango = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMorango[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 4, 64, 64);
        }
    }

    public Morango(Morango outroMorango) {
        super(outroMorango);

        spritesMorango = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMorango[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 4, 64, 64);
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

package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Melao extends Fruta {

    public static final double regen = 2.2;
    public static final int tickRegen = 6;
    public static final double curaTotal = 11;
    public static final String nome = "Melão";

    private BufferedImage[] spritesMelao;

    public Melao(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesMelao = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelao[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 6, 64, 64);
        }
    }

    public Melao(Melao outroMelao) {
        super(outroMelao);

        spritesMelao = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelao[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 6, 64, 64);
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

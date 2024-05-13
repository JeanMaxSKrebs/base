package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Noz extends Fruta {

    public static final double regen = 1.8;
    public static final int tickRegen = 5;
    public static final double curaTotal = 9;
    public static final String nome = "Noz";

    private BufferedImage[] spritesNoz;

    public Noz(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesNoz = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesNoz[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 8, 64, 64);
        }
    }

    public Noz(Noz outraNoz) {
        super(outraNoz);

        spritesNoz = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesNoz[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 8, 64, 64);
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

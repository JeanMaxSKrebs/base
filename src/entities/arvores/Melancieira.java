package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Melancia;
import world.Camera;

public class Melancieira extends Arvore {

    protected static final String nome = "Melancieira";
    protected int qtdFrutos = 1;

    private BufferedImage[] spritesMelancieira;

    public Melancieira(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        spritesMelancieira = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelancieira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 8, tamanhoBase, tamanhoBase);
        }
    }

    public void tick() {
        girar();
        verificaGiro();
    }

    public void render(Graphics g) {
        g.drawImage(spritesMelancieira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    @Override
    protected void gerarFrutos() {
        for (int i = 0; i < qtdFrutos; i++) {
            int melanciaX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
            int melanciaY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
            Melancia melancia = new Melancia(melanciaX, melanciaY, 16, 16, null);
            Game.frutas.add(melancia);
        }
    }

    @Override
    public void metodoAbstrato() {
        // Implementação específica
    }

    @Override
    public void adicionarFrutosAleatoriamente() {
        gerarFrutos();
    }
}

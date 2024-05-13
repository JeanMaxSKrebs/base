package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Noz;
import world.Camera;

public class Nogueira extends Arvore {

    protected static final String nome = "Nogueira";
	protected int qtdFrutos = 5; 

    private BufferedImage[] spritesNogueira;

    public Nogueira(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width*2, height*2, sprite);
        spritesNogueira = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesNogueira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 9, tamanhoBase, tamanhoBase);
        }
    }

    public void tick() {
        girar();
        verificaGiro();
    }

    public void render(Graphics g) {
        g.drawImage(spritesNogueira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    @Override
    protected void gerarFrutos() {
    	   for (int i = 0; i < qtdFrutos; i++) {
               int nozX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
               int nozY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
               Noz noz = new Noz(nozX, nozY, 16, 16, null);
               Game.frutas.add(noz);
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

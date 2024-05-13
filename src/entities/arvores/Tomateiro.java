package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Tomate;
import world.Camera;

public class Tomateiro extends Arvore {
	
	protected static final String nome = "Tomateiro";
	protected int qtdFrutos = 8; // Exemplo: 8 tomates por tomateiro

	private BufferedImage[] spritesTomateiro;
	
	public Tomateiro(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesTomateiro = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesTomateiro[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 1, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {
		girar();
		verificaGiro();
	}

	public void render(Graphics g) {
		g.drawImage(spritesTomateiro[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		
	}

	@Override
	protected void gerarFrutos() {
        for (int i = 0; i < qtdFrutos; i++) {
            int tomateX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
            int tomateY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
            Tomate tomate = new Tomate(tomateX, tomateY, 16, 16, null);
            Game.frutas.add(tomate);
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

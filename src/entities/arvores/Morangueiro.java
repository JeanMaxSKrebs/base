package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Morango;
import world.Camera;

public class Morangueiro extends Arvore {
	
	protected static final String nome = "Morangueiro";
	protected int qtdFrutos = 15; // Exemplo: 15 morangos por morangueiro

	private BufferedImage[] spritesMorangueiro;
	
	public Morangueiro(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesMorangueiro = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMorangueiro[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 3, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {
		girar();
		verificaGiro();
	}

	public void render(Graphics g) {
		g.drawImage(spritesMorangueiro[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		
	}

	@Override
	protected void gerarFrutos() {
	      for (int i = 0; i < qtdFrutos; i++) {
	            int morangoX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
	            int morangoY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
	            Morango morango = new Morango(morangoX, morangoY, 16, 16, null);
	            Game.frutas.add(morango);
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

package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Uva;
import world.Camera;

public class Parreira extends Arvore {
	
	protected static final String nome = "Parreira";
	protected int qtdFrutos = 1; // Exemplo: 12 uvas por parreira

	private BufferedImage[] spritesParreira;
	
	public Parreira(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesParreira = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesParreira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 2, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {
		girar();
		verificaGiro();
	}

	public void render(Graphics g) {
		g.drawImage(spritesParreira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		
	}

	@Override
	protected void gerarFrutos() {
		   for (int i = 0; i < qtdFrutos; i++) {
	            int uvaX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
	            int uvaY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
	            Uva uva = new Uva(uvaX, uvaY, 16, 16, null);
	            Game.frutas.add(uva);
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

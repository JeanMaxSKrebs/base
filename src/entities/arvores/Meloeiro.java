package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Melao;
import world.Camera;

public class Meloeiro extends Arvore {
	
	protected static final String nome = "Meloeiro";
	protected int qtdFrutos = 2;
	
	private BufferedImage[] spritesMeloeiro;
	
	public Meloeiro(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesMeloeiro = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMeloeiro[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 5, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {
		girar();
		verificaGiro();
	}

	public void render(Graphics g) {
		g.drawImage(spritesMeloeiro[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		
	}

	@Override
	protected void gerarFrutos() {
        for (int i = 0; i < qtdFrutos; i++) {
            int melaoX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
            int melaoY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
            Melao melao = new Melao(melaoX, melaoY, 16, 16, null);
            Game.frutas.add(melao);
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

package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Batata;
import world.Camera;

public class Batateira extends Arvore {
	
	protected static final String nome = "Batateira";
	protected int qtdFrutos = 1;

	private BufferedImage[] spritesBatateira;
	
	public Batateira(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesBatateira = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesBatateira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 6, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {
		girar();
		verificaGiro();
	}

	public void render(Graphics g) {
		g.drawImage(spritesBatateira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		
	}

	protected void gerarFrutos() {
		spritesBatateira = new BufferedImage[qtdDirecoes];

	    // Atualizar sprites
	    for (int i = 0; i < qtdDirecoes; i++) {
	        spritesBatateira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * 3 + tamanhoBase * i, tamanhoBase * 6, tamanhoBase, tamanhoBase);
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

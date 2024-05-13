package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Maca;
import world.Camera;

public class Macieira extends Arvore {
	
	protected static final String nome = "Macieira";
	protected int qtdFrutos = 3;

	private BufferedImage[] spritesMacieira;
	
	public Macieira(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesMacieira = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMacieira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase * 4, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {
		girar();
		verificaGiro();
	}

	public void render(Graphics g) {
		g.drawImage(spritesMacieira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		
	}

	@Override
	protected void gerarFrutos() {
        for (int i = 0; i < qtdFrutos; i++) {
            int macaX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
            int macaY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
            Maca maca = new Maca(macaX, macaY, 16, 16, null);
            Game.frutas.add(maca);
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

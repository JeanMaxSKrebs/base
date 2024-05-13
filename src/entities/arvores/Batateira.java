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

	@Override
	protected void gerarFrutos() {
	      for (int i = 0; i < qtdFrutos; i++) {
	            int batataX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
	            int batataY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
	            Batata batata = new Batata(batataX, batataY, 16, 16, null);
	            Game.frutas.add(batata);
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

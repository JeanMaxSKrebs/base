package entities.arvores;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;
import world.Camera;

public abstract class Arvore extends Entity {
	// A IMAGEM PADRÃO FICA NO SUPERIOR // ENTITY
	public static BufferedImage TOMATEIRO_AR = Game.spritesheet_Trees.getSprite(0, 64 * 1, 64, 64);
	public static BufferedImage PARREIRA_AR = Game.spritesheet_Trees.getSprite(0, 64 * 2, 64, 64);
	public static BufferedImage MORANGUEIRO_AR = Game.spritesheet_Trees.getSprite(0, 64 * 3, 64, 64);
	public static BufferedImage MACIEIRA_AR = Game.spritesheet_Trees.getSprite(0, 64 * 4, 64, 64);
	public static BufferedImage MELOEIRO_AR = Game.spritesheet_Trees.getSprite(0, 64 * 5, 64, 64);
	public static BufferedImage BATATEIRA_AR = Game.spritesheet_Trees.getSprite(0, 64 * 6, 64, 64);
	public static BufferedImage BANANEIRA_AR = Game.spritesheet_Trees.getSprite(0, 64 * 7, 64, 64);
	public static BufferedImage MELANCIEIRA_AR = Game.spritesheet_Trees.getSprite(0, 64 * 8, 64, 64);
	public static BufferedImage NOGUEIRA_AR = Game.spritesheet_Trees.getSprite(0, 64 * 8, 64, 64);
	protected int tamanhoBase = 112;

	// Nomes das frutas
	private static final String[] NOMES_ARVORES = { "TOMATEIRO", "PARREIRA", "MORANGUEIRO", "MACIEIRA", "MELOEIRO",
			"BATATEIRA", "BANANEIRA", "MELANCIEIRA", "NOGUEIRA" };

	protected static final String nome = "ÁRVORE";
	protected int qtdFrutos; // Exemplo: 10 frutos por árvore

	public Arvore(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		gerarFrutos();
	}
    // Gerar frutos aleatoriamente à bananeira
	protected abstract void gerarFrutos();
    // Adiciona frutos aleatoriamente à bananeira
    public abstract void adicionarFrutosAleatoriamente();

	// Método abstrato que deve ser implementado nas subclasses
	public abstract void metodoAbstrato();

	public Arvore getRandomSubclass(int x, int y, int width, int height, BufferedImage sprite) {
		// Aqui você pode adicionar suas subclasses e escolher uma aleatoriamente
		switch (Game.random(NOMES_ARVORES.length - 1)) { // Suponha que existam 3 subclasses diferentes
		case 0:
			return new Tomateiro(x, y, width, height, sprite);
		case 1:
			return new Parreira(x, y, width, height, sprite);
		case 2:
			return new Morangueiro(x, y, width, height, sprite);
		case 3:
			return new Macieira(x, y, width, height, sprite);
		case 4:
			return new Meloeiro(x, y, width, height, sprite);
		case 5:
			return new Batateira(x, y, width, height, sprite);
		case 6:
			return new Bananeira(x, y, width, height, sprite);
		case 7:
			return new Melancieira(x, y, width, height, sprite);
		case 8:
			return new Nogueira(x, y, width, height, sprite);
		default:
			return null;

		}
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);

		g.setColor(Color.red);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
}

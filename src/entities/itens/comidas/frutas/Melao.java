package entities.itens.comidas.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;
import world.Camera;

public class Melao extends Fruta {

	public double regen = 4;
	public int tickRegen = 3;
	public double curaTotal = 12;
	public static String nome = "Melão";

	private BufferedImage[] spritesMelao;

	public Melao(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
		spritesMelao = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {
			spritesMelao[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 5, 64, 64);
		}
        this.sprite = spritesMelao[0];

	}

	public Melao(Melao outroMelao) {
		super(outroMelao);

		spritesMelao = new BufferedImage[qtdDirecoes];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesMelao[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 5, 64, 64);
		}
        this.sprite = spritesMelao[0];

	}

	public void tick() {
		girar();
		verificaGiro();
	}

	@Override
	public void coletarEspecifico() {
		// Implementação específica
	}

	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new Melao(this);
	}

	@Override
	public void comer(Item item) {
		// Implementação específica
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(spritesMelao[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

	public double getRegen() {
		return regen;
	}

	public int getTickregen() {
		return tickRegen;
	}

	public double getCuratotal() {
		return curaTotal;
	}

	public String getNome() {
		return nome;
	}

	public int getTickRegen() {
		return tickRegen;
	}

	public void setTickRegen(int tickRegen) {
		this.tickRegen = tickRegen;
	}

	public double getCuraTotal() {
		return curaTotal;
	}

	public void setCuraTotal(double curaTotal) {
		this.curaTotal = curaTotal;
	}

	public void setRegen(double regen) {
		this.regen = regen;
	}

	public void setNome(String nome) {
		Melao.nome = nome;
	}
}

package entities.itens.frutas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;
import entities.itens.Item;
import world.Camera;
import world.World;

public class Fruta extends Item implements Comparable<Fruta> {
	// A IMAGEM PADRÃO FICA NO SUPERIOR // ENTITY
	public static BufferedImage UVA_FR = Game.spritesheet.getSprite(256, 32, 32, 32);
	public static BufferedImage MACA_FR = Game.spritesheet.getSprite(256, 64, 32, 32);
	public static BufferedImage MORANGO_FR = Game.spritesheet.getSprite(256, 96, 32, 32);
	public static BufferedImage MELANCIA_FR = Game.spritesheet.getSprite(256, 128, 32, 32);
	public static BufferedImage MELÃO_FR = Game.spritesheet.getSprite(256, 160, 32, 32);
	
    // Nomes das frutas
    private static final String[] NOMES_FRUTAS = { "UVA", "MACA", "MORANGO", "MELANCIA", "MELAO" };
    
	// Array para armazenar as imagens das frutas
	public static BufferedImage[] FRUTAS_SPRITES  = new BufferedImage[getNomesFrutas().length];

	static {
		// Carregar automaticamente as imagens das frutas
		for (int i = 0; i < getNomesFrutas().length; i++) {
			FRUTAS_SPRITES[i] = Game.spritesheet.getSprite(224, (i+1)*32, 32, 32);
		}
	}
    
	private double regen;
	private int tickRegen;
	private double curaTotal;
	private BufferedImage sprite;

	protected int frames = 0, maxFrames = 60;
	protected int index = 1;
	protected int qtdDirecoes = 3;

	protected boolean girando;

	public void girar() {
		girando = true;
	}

	public void verificaGiro() {
		if (girando) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index = Game.random(qtdDirecoes);
			}
		}
	}

	public Fruta(int x, int y, int width, int height, BufferedImage sprite, String nome, double regen, int tickRegen,
			double curaTotal) {
		super(x, y, width, height, sprite, nome);
		this.nome = nome;
		this.regen = regen;
		this.tickRegen = tickRegen;
		this.curaTotal = curaTotal;
		this.setSprite(sprite);

	}

	public Fruta(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		// TODO Auto-generated constructor stub
		super(x, y, width, height, sprite, nome);

	}

	public Fruta(int x, int y, int width, int height,BufferedImage sprite) {
		// TODO Auto-generated constructor stub
		super(x, y, width, height, sprite);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);

//		g.setColor(Color.yellow);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getRegen() {
		return regen;
	}

	public void setRegen(double regen) {
		this.regen = regen;
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


	// Incrementa a quantidade quando uma fruta é coletada
	public void coletar() {
		quantidade++;
	}

	// Decrementa a quantidade quando uma fruta é comida
	public void comer() {
		if (quantidade > 0) {
			quantidade--;
		}

		if (quantidade < 0) {
			quantidade = 0; // Garante que a quantidade não seja negativa
		}
	}

	@Override
	public int compareTo(Fruta outraFruta) {
		return this.nome.compareTo(outraFruta.getNome());
	}

	@Override
	public String toString() {
		return "Fruta{" + "nome='" + nome + '\'' + ", quantidade=" + quantidade + ", regen=" + regen + ", tickRegen="
				+ tickRegen + ", curaTotal=" + curaTotal + '}';
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public static String[] getNomesFrutas() {
		return NOMES_FRUTAS;
	}

}

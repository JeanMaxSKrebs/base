package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.Comida;

public class Fruta extends Comida implements Comparable<Fruta> {
	// A IMAGEM PADRÃO FICA NO SUPERIOR // ENTITY
	public static BufferedImage TOMATE_FR = Game.spritesheet_Fruits.getSprite(0, 64*1, 64, 64);
	public static BufferedImage UVA_FR = Game.spritesheet_Fruits.getSprite(0, 64*2, 64, 64);
	public static BufferedImage MORANGO_FR = Game.spritesheet_Fruits.getSprite(0, 64*3, 64, 64);
	public static BufferedImage MACA_FR = Game.spritesheet_Fruits.getSprite(0, 64*4, 64, 64);
	public static BufferedImage MELÃO_FR = Game.spritesheet_Fruits.getSprite(0, 64*5, 64, 64);
	public static BufferedImage BATATA_FR = Game.spritesheet_Fruits.getSprite(0, 64*6, 64, 64);
	public static BufferedImage BANANA_FR = Game.spritesheet_Fruits.getSprite(0, 64*7, 64, 64);
//	public static BufferedImage MELANCIA_FR = Game.spritesheet_Fruits.getSprite(0, 64*8, 64, 64);

    // Nomes das frutas
    private static final String[] NOMES_FRUTAS = { "TOMATE", "UVA", "MORANGO", "MACA", "MELAO", "BATATA", "BANANA", "MELANCIA" };
    
	// Array para armazenar as imagens das frutas
	public static BufferedImage[] FRUTAS_SPRITES  = new BufferedImage[getNomesFrutas().length];

    public static String nome = "Fruta";

	@SuppressWarnings("unused")
	private BufferedImage sprite;

	protected int qtdDirecoes = 3;




	public Fruta(int x, int y, int width, int height, BufferedImage sprite, String nome, double regen, int tickRegen,
			double curaTotal) {
		super(x, y, width, height, sprite, nome, regen, tickRegen, curaTotal);

	}

	public Fruta(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite, nome, regen, tickRegen, curaTotal);

	}

	public Fruta(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome, regen, tickRegen, curaTotal);

	}
	
	  // Construtor que aceita uma Fruta como argumento para copiar seus atributos
    public Fruta(Fruta outraFruta) {
        super(outraFruta); // Chama o construtor da superclasse para copiar atributos de Item
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
		return Fruta.nome.compareTo(outraFruta.getNome());
	}

	@Override
	public String toString() {
		return "Fruta{" + "nome='" + nome + '\'' + ", quantidade=" + quantidade + ", regen=" + regen + ", tickRegen="
				+ tickRegen + ", curaTotal=" + curaTotal + '}';
	}

	public static String[] getNomesFrutas() {
		return NOMES_FRUTAS;
	}

}

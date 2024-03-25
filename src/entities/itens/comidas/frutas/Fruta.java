package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.Player;
import entities.itens.comidas.Comida;

public abstract class Fruta extends Comida implements Comparable<Fruta> {
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


	@SuppressWarnings("unused")
	protected BufferedImage sprite;

	protected int qtdDirecoes = 3;

	public String nome = "Fruta";
	public double regen = 2; // Amount of health regenerated
	public int tickRegen = 5; // Ticks between regeneration events
	public double curaTotal = 10;



	public Fruta(int x, int y, int width, int height, BufferedImage sprite, String nome, double regen, int tickRegen,
			double curaTotal) {
		super(x, y, width, height, sprite, nome, regen, tickRegen, curaTotal);

	}

	public Fruta(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite, nome);

	}

	public Fruta(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

	}
	
	  // Construtor que aceita uma Fruta como argumento para copiar seus atributos
    public Fruta(Fruta outraFruta) {
        super(outraFruta); // Chama o construtor da superclasse para copiar atributos de Item
    }

	// Incrementa a quantidade quando uma fruta é coletada
    public void coletar(Player player) {
        incrementQuantity(); // Incrementa a quantidade do item
        player.obtainItem(this); // Adiciona o item à lista de itens do jogador
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

	public int compareTo(Fruta outraFruta) {
		return this.nome.compareTo(outraFruta.getNome());
	}

	public String toString() {
		return "Fruta{" + "nome='" + nome + '\'' + ", quantidade=" + quantidade + ", regen=" + regen + ", tickRegen="
				+ tickRegen + ", curaTotal=" + curaTotal + '}';
	}

	public static String[] getNomesFrutas() {
		return NOMES_FRUTAS;
	}

}

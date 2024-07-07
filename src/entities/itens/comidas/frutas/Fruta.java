package entities.itens.comidas.frutas;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import base.Game;
import entities.Player;
import entities.itens.Item;
import entities.itens.comidas.Comida;
import entities.itens.utensilios.Fogueira;

public abstract class Fruta extends Comida implements Comparable<Fruta> {
	private static final long serialVersionUID = 1L;
	// A IMAGEM PADRÃO FICA NO SUPERIOR // ENTITY
	public static transient BufferedImage TOMATE_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 1, 64, 64);
	public static transient BufferedImage UVA_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 2, 64, 64);
	public static transient BufferedImage MORANGO_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 3, 64, 64);
	public static transient BufferedImage MACA_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 4, 64, 64);
	public static transient BufferedImage MELÃO_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 5, 64, 64);
	public static transient BufferedImage BATATA_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 6, 64, 64);
	public static transient BufferedImage BANANA_FR = Game.spritesheet_Fruits.getSprite(0, 64 * 7, 64, 64);
	public static transient BufferedImage MELANCIA_FR = Game.spritesheet_Fruits.getSprite(0, 64*8, 64, 64);
	public static transient BufferedImage NOZ_FR = Game.spritesheet_Fruits.getSprite(0, 64*9, 64, 64);

	// Nomes das frutas
	private static final transient String[] NOMES_FRUTAS = { "TOMATE", "UVA", "MORANGO", "MACA", "MELAO", "BATATA", "BANANA",
			"MELANCIA", "NOZ" };

	// Array para armazenar as imagens das frutas
	public static transient BufferedImage[] FRUTAS_SPRITES = new BufferedImage[getNomesFrutas().length];

	@SuppressWarnings("unused")
	protected transient BufferedImage sprite;

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
	
	public abstract void comer(Item item);


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

    public abstract Item clone();

    public abstract String getNome();

	public abstract void setNome(String nome);

	public abstract int getTickRegen();

	public abstract void setTickRegen(int tickRegen);

	public abstract double getCuraTotal();

	public abstract void setCuraTotal(double curaTotal);
	
	public abstract double getRegen();

	public abstract void setRegen(double regen);
	
	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serializa os campos não-transientes
        
        if (sprite != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(sprite, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            oos.writeInt(imageBytes.length);
            oos.write(imageBytes);
        } else {
            oos.writeInt(0); // Sem imagem
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Desserializa os campos não-transientes
        
        int length = ois.readInt();
        if (length > 0) {
            byte[] imageBytes = new byte[length];
            ois.readFully(imageBytes);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            sprite = ImageIO.read(bais);
        } else {
            sprite = null; // Sem imagem
        }
    }
}

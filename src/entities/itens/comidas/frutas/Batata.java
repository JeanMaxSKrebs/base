package entities.itens.comidas.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import base.Game;
import entities.itens.Item;
import world.Camera;

public class Batata extends Fruta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double regen = 1.5;
	public int tickRegen = 10;
	public double curaTotal = 15;
	public static String nome = "Batata";

	private transient BufferedImage[] spritesBatata;

	public Batata(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
		spritesBatata = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {
			spritesBatata[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 6, 64, 64);
		}
        this.sprite = spritesBatata[0];
	}

	public Batata(Batata outraBatata) {
		super(outraBatata);

		spritesBatata = new BufferedImage[qtdDirecoes];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesBatata[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 6, 64, 64);
		}
        this.sprite = spritesBatata[0];
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
		return new Batata(this);
	}

	@Override
	public void comer(Item item) {
		// Implementação específica
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(spritesBatata[index], this.getX() - Camera.x, this.getY() - Camera.y, 32, 32, null);
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
		Batata.nome = nome;
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
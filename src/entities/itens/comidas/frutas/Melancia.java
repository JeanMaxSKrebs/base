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

public class Melancia extends Fruta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double regen = 5;
    public int tickRegen = 5;
    public double curaTotal = 25;
    public static String nome = "Melancia";

    private transient BufferedImage[] spritesMelancia;

    public Melancia(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesMelancia = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelancia[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 8, 64, 64);
        }
        this.sprite = spritesMelancia[0];
    }

    public Melancia(Melancia outraMelancia) {
        super(outraMelancia);

        spritesMelancia = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesMelancia[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 8, 64, 64);
        }
        this.sprite = spritesMelancia[0];

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
		return new Melancia(this);
	}

    @Override
    public void comer(Item item) {
        // Implementação específica
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(spritesMelancia[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
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
		Melancia.nome = nome;
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

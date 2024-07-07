package entities.itens.comidas;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import entities.Player;
import entities.itens.Item;

public abstract class Comida extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String nome = "Comida";
	public double regen = 4; // Amount of health regenerated
	public int tickRegen = 5; // Ticks between regeneration events
	public double curaTotal = 20; // Maximum health restored
	public boolean isCooked; // Flag indicating if the food is cooked

	public Comida(int x, int y, int width, int height, BufferedImage sprite, String nome, double regen, int tickRegen,
			double curaTotal) {
		super(x, y, width, height, sprite, nome);
		this.regen = regen;
		this.tickRegen = tickRegen;
		this.curaTotal = curaTotal;

		this.isCooked = false; // Initially not cooked
	}

	public Comida(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite, nome);
		this.isCooked = false; // Initially not cooked
	}

	public Comida(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.isCooked = false; // Initially not cooked
	}

	public Comida(Comida outraComida) {
		super(outraComida); // Chama o construtor da superclasse para copiar atributos de Item
	}

	public void use(Player player) {
		if (isCooked()) {
			for (int i = 0; i < tickRegen; i++) {
				player.heal(regen / tickRegen); // Cura fracionada em ticks
				// Simulate delay between ticks (optional)
				// ...
			}
		} else {
			System.out.println("This food needs to be cooked first!");
		}
	}
	
    public abstract void comer(Item item);


	public void cook() { // Simulates cooking process (replace with actual logic)
		isCooked = true;
		System.out.println("The food is cooked and ready to eat!");
	}
	
	public boolean isCooked() {
		return isCooked;
	}

	public void setCooked(boolean cooked) {
		isCooked = cooked;
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

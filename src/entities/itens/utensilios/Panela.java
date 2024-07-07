package entities.itens.utensilios;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import base.Game;
import entities.itens.Item;
import entities.itens.comidas.Comida;
import entities.itens.comidas.frutas.Maca;
import entities.itens.comidas.frutas.Uva;

public class Panela extends Item {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Panela(int x, int y, int width, int height, BufferedImage sprite, String nome) {
        super(x, y, width, height, sprite, nome);
    }

    public boolean cookFood(Comida comida) {  // Method to comida food using Panela
        if (!comida.isCooked()) {
        	comida.cook();
            return true;
        } else {
            System.out.println("A comida já está pronta!");
            return false;
        }
    }

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}

	public Panela(Panela outraPanela) {
		super(outraPanela);
	}
	
	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new Panela(this);
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

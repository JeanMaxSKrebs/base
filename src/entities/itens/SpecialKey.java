package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import entities.Entity;
import world.Camera;

public class SpecialKey extends Item {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String nome = "SpecialKey";

	public SpecialKey(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
	}

	public SpecialKey(SpecialKey outraSpecialKey) {
		super(outraSpecialKey);
		this.sprite = outraSpecialKey.sprite;
	}

	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new SpecialKey(this);
	}

	public void render(Graphics g) {

		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.yellow);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub

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
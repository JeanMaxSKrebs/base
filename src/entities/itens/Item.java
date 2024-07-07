package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;

import javax.imageio.ImageIO;

import base.Game;
import entities.Entity;
import entities.Player;
import entities.itens.utensilios.Fogueira;
import graficos.ItemAnimation;
import world.Camera;
import world.World;

public abstract class Item extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String nome = "Item";
	protected int quantidade = 0;

	public static transient BufferedImage SPECIALKEY_EN = Game.spritesheet_Doors.getSprite(112, 0, 112, 112);
	public static transient BufferedImage KEY_EN = Game.spritesheet_Doors.getSprite(336, 0, 112, 112);

	protected int qtdDirecoes = 3;

	//
	public Item(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite);
		this.nome = nome;
	}

	public Item(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public Item(Item outroItem) {
		super();
		this.nome = outroItem.nome;
		this.sprite = outroItem.sprite;
		// Copie outros atributos, se houver
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

//	public void mudar(String itemName) {
//		try {
//		    Class<?> itemClass = Class.forName(itemName);
//		    Constructor<?> constructor = itemClass.getConstructor(int.class, int.class, int.class, int.class, Sprite.class);
//		    
//		    // Aqui você pode passar os argumentos necessários para o construtor, neste caso estou assumindo que os parâmetros são inteiros
//		    Object itemInstance = constructor.newInstance(Game.player.getX(), Game.player.getY(), World.TILE_SIZE, World.TILE_SIZE, this.sprite);
//		    
//		    // Adicione a instância à lista de itens
//		    Player.getItens().add((Item) itemInstance);
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
//	}

	public void serDropado(Player player) {
		player.dropar(this);
	};

	public void serUsado(Player player) {
		player.use(this);
	};

	// Método abstrato para fornecer uma implementação específica nas subclasses, se
	// necessário
	public abstract void coletarEspecifico();

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		// TODO Auto-generated method stub
		return quantidade;
	}

	public void setQuantidade(int i) {
		// TODO Auto-generated method stub

	}

	public void incrementQuantity() {
		quantidade++;
	}

	public void decrementQuantity() {
		quantidade--;
	}

	public abstract Item clone();

	public void tick() {

	}

	@Override
	public String toString() {
		return "Item{" + "nome='" + nome + '\'' + ", quantidade=" + quantidade + '}';
	}
}

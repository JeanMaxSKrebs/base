package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import base.Game;
import entities.Entity;
import entities.Player;
import entities.itens.utensilios.Fogueira;
import world.Camera;
import world.World;

@SuppressWarnings("unused")
public abstract class Item extends Entity {
	protected String nome = "Item";
	protected int quantidade = 0;

	public static BufferedImage SPECIALKEY_EN = Game.spritesheet_Doors.getSprite(112, 0, 112, 112);
	public static BufferedImage KEY_EN = Game.spritesheet_Doors.getSprite(336, 0, 112, 112);

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

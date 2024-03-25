package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;
import entities.Player;
import world.Camera;

@SuppressWarnings("unused")
public abstract class Item extends Entity {
	protected String nome = "Item";
	public int quantidade = 1;

	public static BufferedImage KEY_EN = Game.spritesheet_Doors.getSprite(112, 0, 112, 112);
	public static BufferedImage SPECIALKEY_EN = Game.spritesheet_Doors.getSprite(336, 0, 112, 112);

	protected boolean girando;

	protected int frames = 0, maxFrames = 60;
	protected int index = 1;
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
		this.quantidade = outroItem.quantidade;
		// Copie outros atributos, se houver
	}

    public void coletar(Player player) {
        incrementQuantity(); // Incrementa a quantidade do item
        player.obtainItem(this); // Adiciona o item à lista de itens do jogador
    }
    

	// Método abstrato para fornecer uma implementação específica nas subclasses, se
	// necessário
	public abstract void coletarEspecifico();

	public void girar() {
		girando = true;
	}

	public void verificaGiro() {
		if (girando) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index = Game.random(qtdDirecoes);
			}
		}
	}
	
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

	public void tick() {

	}

	@Override
	public String toString() {
		return "Item{" + "nome='" + nome + '\'' + ", quantidade=" + quantidade + '}';
	}

}

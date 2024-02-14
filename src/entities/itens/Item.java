package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;

public class Item extends Entity {
	protected String nome = "Without";
	protected int quantidade = 0;

	public Item(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite);
		this.nome = nome;
	}

	public Item(int x, int y, int width, int height, BufferedImage sprite) {
		// TODO Auto-generated constructor stub
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
}

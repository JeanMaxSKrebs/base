package entities.itens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;
import world.Camera;

public class Item extends Entity {
	protected String nome = "Item";
	public int quantidade = 1;
	
	public static BufferedImage KEY_EN = Game.spritesheet_Doors.getSprite(112, 0, 112, 112);
	public static BufferedImage SPECIALKEY_EN = Game.spritesheet_Doors.getSprite(336, 0, 112, 112);
	
	protected boolean girando;
	
	protected int frames = 0, maxFrames = 60;
	protected int index = 1;
	protected int qtdDirecoes = 3;

	public Item(int x, int y, int width, int height, BufferedImage sprite, String nome) {
		super(x, y, width, height, sprite);
		this.nome = nome;
	}

	public Item(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}


//	public Item(int x, int y, BufferedImage sprite) {
//		super(x, y, width, height, sprite);
//	}
	
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
	public void tick() {
		
	}

}

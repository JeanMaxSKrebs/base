package entities.itens.comidas.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;
import world.Camera;

public class Tomate extends Fruta {

    public double regen = 1.5;
    public int tickRegen = 8;
    public double curaTotal = 12;
    public static String nome = "Tomate";

    private BufferedImage[] spritesTomate;

    public Tomate(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite, nome);
        spritesTomate = new BufferedImage[qtdDirecoes];
        for (int i = 0; i < qtdDirecoes; i++) {
            spritesTomate[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 1, 64, 64);
        }
        this.sprite = spritesTomate[0];

    }

    public Tomate(Tomate outroTomate) {
        super(outroTomate);

        spritesTomate = new BufferedImage[qtdDirecoes];

        for (int i = 0; i < qtdDirecoes; i++) {
            spritesTomate[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 1, 64, 64);
        }
        this.sprite = spritesTomate[0];

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
		return new Tomate(this);
	}

    @Override
    public void comer(Item item) {
        // Implementação específica
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(spritesTomate[index], this.getX() - Camera.x, this.getY() - Camera.y, 32, 32, null);
    }
	public double getRegen() {
		return regen;
	}

	public  int getTickregen() {
		return tickRegen;
	}

	public  double getCuratotal() {
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
		Tomate.nome = nome;
	}
}

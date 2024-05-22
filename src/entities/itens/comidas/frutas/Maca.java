package entities.itens.comidas.frutas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;
import world.Camera;

public class Maca extends Fruta {

    public double regen = 2.0; // Exemplo: regeneração de 2.0
    public int tickRegen = 5; // Exemplo: a cada 5 ticks
    public double curaTotal = 10; // Exemplo: cura total de 10
    public static String nome = "Maçã";

	private BufferedImage[] spritesMaca;

	public Maca(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
		spritesMaca = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesMaca[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 *4, 64, 64);

		}
        this.sprite = spritesMaca[0];

	}

	public Maca(Maca outraMaca) {
		super(outraMaca);
		
		spritesMaca = new BufferedImage[4];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesMaca[i] = Game.spritesheet_Fruits.getSprite(64 * i, 64 * 4, 64, 64);
		}
        this.sprite = spritesMaca[0];
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
		return new Maca(this);
	}
    @Override
    public void comer(Item item) {
        // Implementação específica
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(spritesMaca[index], this.getX() - Camera.x, this.getY() - Camera.y, 32, 32, null);
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
		Maca.nome = nome;
	}
}

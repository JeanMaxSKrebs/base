package entities.itens.comidas;

import java.awt.image.BufferedImage;

import entities.Player;
import entities.itens.Item;

public abstract class Comida extends Item {

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
    public void coletar(Player player) {
        incrementQuantity(); // Incrementa a quantidade do item
        player.obtainItem(this); // Adiciona o item à lista de itens do jogador
    }
	

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
}

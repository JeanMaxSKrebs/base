package entities.itens.comidas;

import java.awt.image.BufferedImage;

import entities.Player;
import entities.itens.Item;

public class Comida extends Item {

	public static String nome = "Comida";
	protected static double regen = 2; // Amount of health regenerated
	protected static int tickRegen = 5; // Ticks between regeneration events
	protected static double curaTotal = 10; // Maximum health restored
	protected boolean isCooked; // Flag indicating if the food is cooked

	public Comida(int x, int y, int width, int height, BufferedImage sprite, String nome, double regen, int tickRegen,
			double curaTotal) {
		super(x, y, width, height, sprite, nome);
		Comida.regen = regen;
		Comida.tickRegen = tickRegen;
		Comida.curaTotal = curaTotal;
		this.isCooked = false; // Initially not cooked
	}

	public Comida(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
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

	public void cook() { // Simulates cooking process (replace with actual logic)
		isCooked = true;
		System.out.println("The food is cooked and ready to eat!");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		Comida.nome = nome;
	}

	public static double getRegen() {
		return regen;
	}

	public static void setRegen(double regen) {
		Comida.regen = regen;
	}

	public int getTickRegen() {
		return tickRegen;
	}

	public void setTickRegen(int tickRegen) {
		Comida.tickRegen = tickRegen;
	}

	public double getCuraTotal() {
		return curaTotal;
	}

	public void setCuraTotal(double curaTotal) {
		Comida.curaTotal = curaTotal;
	}

	public boolean isCooked() {
		return isCooked;
	}

	public void setCooked(boolean cooked) {
		isCooked = cooked;
	}
}

package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import entities.Player;
import entities.itens.Item;
import entities.itens.frutas.Fruta;
import world.World;

public class Inventory {

	public String[] options = { "usar item", "dropar item", "voltar" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;

	public int rows = 8; // Quantidade de linhas do inventário
	public int cols = 8; // Quantidade de colunas do inventário
	public String[] inventory = new String[rows * cols]; // Inventário com 5 linhas e 10 colunas

	public static boolean entrouInventario = false;

	public int currentOptionInventory = 0;
	public int maxOptionInventory = inventory.length - 1;

	public boolean right, left;

	public static boolean pause = false;

	public static boolean saveExists = false;

	public void tick() {
		if (entrouInventario) {
			if (up) {
				up = false;
				int currentOptionInventoryTemp = currentOptionInventory;
				currentOptionInventory = currentOptionInventory - cols;
				if (currentOptionInventory < 0) {

					currentOptionInventory = currentOptionInventoryTemp + (cols * rows) - cols;
				}
			}
			if (down) {
				down = false;
				int currentOptionInventoryTemp = currentOptionInventory;
				currentOptionInventory = currentOptionInventory + cols;
				if (currentOptionInventory > maxOptionInventory) {
					currentOptionInventory = currentOptionInventoryTemp - (rows * cols) + cols;

				}
			}
			if (left) {
				left = false;
				currentOptionInventory--;
				if (currentOptionInventory < 0) {
					currentOptionInventory = maxOptionInventory;
				}
			}
			if (right) {
				right = false;
				currentOptionInventory++;
				if (currentOptionInventory > maxOptionInventory) {
					currentOptionInventory = 0;
				}
			}
			if (enter) {
				enter = false;
				// Lógica para realizar a ação no item selecionado do inventário
				String selectedItem = inventory[currentOptionInventory];
				// Verifica se o item selecionado não é nulo e executa a ação adequada
				if (selectedItem != null) {
					// Aqui você pode adicionar lógica para usar ou dropar o item selecionado
				}
			}
		} else {
			if (up) {
				up = false;
				currentOption--;
				if (currentOption < 0) {
					currentOption = maxOption;
				}
			}
			if (down) {
				down = false;
				currentOption++;
				if (currentOption > maxOption) {
					currentOption = 0;
				}
			}
			if (enter) {
				enter = false;
				if (options[currentOption] == "usar item") {
					entrouInventario = true;

				} else if (options[currentOption] == "dropar item") {
					entrouInventario = true;

				} else if (options[currentOption] == "voltar") {
					Game.gameState = "NORMAL";
					pause = false;
				}
			}
		}

	}

	public void render(Graphics g) {

		// Renderização do inventário
		int slotSize = 60; // Tamanho do slot
		int inventoryX = 100; // Posição X inicial do inventário
		int inventoryY = 200; // Posição Y inicial do inventário

		g.setColor(new Color(139, 69, 19)); // Marrom
		g.fillRect(0, 0, Game.getWIDTH() * Game.getSCALE(), Game.getHEIGHT() * Game.getSCALE());

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Inventário", inventoryX, inventoryY - 20);

		// Render each item in the inventory
		int index = 0;
		for (Item item : Player.getItens()) {
			int row = index / cols; // Calculate the row for the current item
			int col = index % cols; // Calculate the column for the current item

			int x = inventoryX + col * slotSize;
			int y = inventoryY + row * slotSize;

			g.drawRect(x, y, slotSize, slotSize); // Desenha o contorno do slot

			// Render item name and details
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawImage(item.getSprite(), x + 5, y + 10, slotSize-5, slotSize-10, null);

//	        g.drawString(item.getNome(), x + 5, y + 20); // Render item name
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString("QTD: " + item.getQuantidade(), x, y + 10); // Render item quantity

			// Increment the index for the next item
			index++;
		}

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				int x = inventoryX + col * slotSize;
				int y = inventoryY + row * slotSize;
				g.drawRect(x, y, slotSize, slotSize); // Desenha o contorno do slot

				// Render a different color around the selected inventory item
				if (row * cols + col == currentOptionInventory) {
					g.setColor(Color.YELLOW); // Change the color to yellow (or any color you prefer)
					g.drawRect(x - 2, y - 2, slotSize + 4, slotSize + 4); // Draw a larger rectangle around the slot
					g.setColor(Color.WHITE); // Restore the color to white for other elements
				}

//				System.out.println("itens");
//				System.out.println(Player.getItens());
				// Se houver um item no inventário, desenha o nome do item no slot
				if (inventory[row * cols + col] != null) {
					g.drawString(inventory[row * cols + col], x + 5, y + 20);

					// Verifica se é uma fruta e renderiza o sprite
					String itemName = inventory[row * cols + col];
					for (int i = 0; i < Fruta.getNomesFrutas().length; i++) {
						if (itemName.equals(Fruta.getNomesFrutas()[i])) {
							BufferedImage frutaSprite = Fruta.FRUTAS_SPRITES[i];
							if (frutaSprite != null) {
								g.drawImage(frutaSprite, x, y, null); // Desenha o sprite da fruta no slot atual do
							}
							break;
						}
					}
				}
			}
		}

		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("Usar item", ((Game.getWIDTH() * Game.getSCALE() / 2)),
				((Game.getHEIGHT() * Game.getSCALE() / 2) + 100));

		g.drawString("Dropar item", ((Game.getWIDTH() * Game.getSCALE() / 2)),
				((Game.getHEIGHT() * Game.getSCALE() / 2) + 200));

		g.drawString("Voltar", ((Game.getWIDTH() * Game.getSCALE() - 250)),
				((Game.getHEIGHT() * Game.getSCALE()) - 50));

		if (options[currentOption] == "usar item") {
			g.drawString(" > ", (((Game.getWIDTH() * Game.getSCALE() / 2) - 50)),
					((Game.getHEIGHT() * Game.getSCALE() / 2) + 100));
		} else if (options[currentOption] == "dropar item") {
			g.drawString(" > ", (((Game.getWIDTH() * Game.getSCALE() / 2) - 50)),
					((Game.getHEIGHT() * Game.getSCALE() / 2) + 200));
		} else if (options[currentOption] == "voltar") {
			g.drawString(" > ", (((Game.getWIDTH() * Game.getSCALE() - 300))),
					((Game.getHEIGHT() * Game.getSCALE()) - 50));
		}
	}
}

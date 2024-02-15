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
	public Item[] inventoryItens = new Item[rows * cols];

	public static boolean entrouInventario = false;
	public static boolean showItemDetails = false;

	public int currentOptionInventory = 0;
	public int maxOptionInventory = inventory.length - 1;

	public boolean right, left;

	private boolean usarItem = false;

	private boolean droparItem = false;

	public static boolean pause = false;

	public static boolean saveExists = false;

	public void tick() {

		// Atualiza o inventário com os itens do jogador
		int index = 0;
		for (Item item : Player.getItens()) {
			// Calcula a linha e a coluna para o item atual
			int row = index / cols;
			int col = index % cols;

			// Atualiza o inventário com o nome do item
			inventoryItens[row * cols + col] = item;

			// Incrementa o índice para o próximo item
			index++;
		}
		if (entrouInventario) {

			if (up) {
				up = false;

				int currentOptionInventoryTemp = currentOptionInventory;
				currentOptionInventory = currentOptionInventory - cols;
				if (currentOptionInventory < 0) {
					showItemDetails = false;
					currentOptionInventory = currentOptionInventoryTemp + (cols * rows) - cols;
				}
			}
			if (down) {
				down = false;
				showItemDetails = false;

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
					showItemDetails = false;

					currentOptionInventory = maxOptionInventory;
				}
			}
			if (right) {
				right = false;

				currentOptionInventory++;
				System.out.println("currentOptionInventory");
				System.out.println(currentOptionInventory);
				System.out.println("(Player.getItens().size()))");
				System.out.println((Player.getItens().size()));
				if (currentOptionInventory >= (Player.getItens().size())) {
					showItemDetails = false;
				}
				
				if (currentOptionInventory > maxOptionInventory) {
					currentOptionInventory = 0;
				}
			}

			if (enter) {
				enter = false;

				if (currentOptionInventory >= 0 && currentOptionInventory < Player.getItens().size()) {
					System.out.println("currentOptionInventory");
					System.out.println(currentOptionInventory);
					System.out.println("inventoryItens[currentOptionInventory]");
					System.out.println(inventoryItens[currentOptionInventory]);

					Item selectedItem = inventoryItens[currentOptionInventory];
					System.out.println("selectedItem");
					System.out.println(selectedItem);
					if (selectedItem != null) {
						showItemDetails = true;
					}
					// Verifica se o item selecionado não é nulo e executa a ação adequada
					if (selectedItem != null) {
						// Aqui você pode adicionar lógica para usar ou dropar o item selecionado
					}
				}
				
				if (usarItem) {

				}

				if (droparItem) {

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
					usarItem = true;

				} else if (options[currentOption] == "dropar item") {
					entrouInventario = true;
					droparItem = true;

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

		// Preencher o fundo do inventário com a cor marromfraco
		g.setColor(new Color(255, 218, 185));
		g.fillRect(inventoryX - 10, inventoryY - 10, cols * slotSize + 20, rows * slotSize + 20);

		g.setColor(Color.black);
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
			g.drawImage(item.getSprite(), x + 5, y + 10, slotSize - 5, slotSize - 10, null);

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
					g.setColor(Color.BLACK);

					for (int i = 2; i <= 10; i++) {
						if (i % 2 != 0) {
							g.setColor(Color.YELLOW); // Change the color to yellow (or any color you prefer)
						} else {
							g.setColor(Color.black); // Change the color to white for even iterations
						}
						g.drawRect(x - i, y - i, slotSize + i * 2, slotSize + i * 2);
					}
					g.setColor(Color.BLACK);

					if (usarItem)
						g.setColor(Color.green); // Change the color to yellow (or any color you prefer)

					if (droparItem)
						g.setColor(Color.red);

					for (int i = 1; i <= 5; i++) {

						g.drawRect(x - i, y - i, slotSize + i * 2, slotSize + i * 2);
					}
					g.setColor(Color.BLACK);

				}

//				System.out.println("itens");
//				System.out.println(Player.getItens());
				// Se houver um item no inventário, desenha o item no slot

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

		// Renderize o detalhamento do item selecionado se a exibição estiver ativada
		if (showItemDetails) {
//			System.out.println("showItemDetails");
//			System.out.println(showItemDetails);
			// Defina as coordenadas e o tamanho do quadro de detalhamento do item
			int detailFrameX = inventoryX + cols * slotSize + 20; // Posição X do quadro de detalhamento
			int detailFrameY = inventoryY; // Posição Y do quadro de detalhamento
			int detailFrameWidth = 200; // Largura do quadro de detalhamento
			int detailFrameHeight = Game.getHEIGHT() - 50; // Altura do quadro de detalhamento

			// Desenhe o quadro de detalhamento
			g.setColor(Color.GRAY);
			g.fillRect(detailFrameX, detailFrameY, detailFrameWidth, detailFrameHeight);

			// Obtenha o item selecionado
			Item selectedItem = Player.getItens().get(currentOptionInventory);
			System.out.println("selectedItem");
			System.out.println(selectedItem);
			// Renderize as informações detalhadas do item
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("Detalhes do Item:", detailFrameX + 10, detailFrameY + 20);
			g.drawString("Nome: " + selectedItem.getNome(), detailFrameX + 10, detailFrameY + 50);
			g.drawString("Quantidade: " + selectedItem.getQuantidade(), detailFrameX + 10, detailFrameY + 80);

			if (selectedItem instanceof Fruta) {
				// Faz um cast para Fruta
				Fruta frutaSelecionada = (Fruta) selectedItem;

				// Acessa os atributos específicos de Fruta
				double regen = frutaSelecionada.getRegen();
				int tickRegen = frutaSelecionada.getTickRegen();
				double curaTotal = frutaSelecionada.getCuraTotal();

//		        // Imprime os valores
//		        System.out.println("Regen: " + regen);
//		        System.out.println("Tick Regen: " + tickRegen);
//		        System.out.println("Cura Total: " + curaTotal);
				g.drawString("Regen: " + regen, detailFrameX + 10, detailFrameY + 110);
				g.drawString("Tick Regen: " + tickRegen, detailFrameX + 10, detailFrameY + 140);
				g.drawString("Cura Total: " + curaTotal, detailFrameX + 10, detailFrameY + 170);
			}
			// Renderize outras informações do item, conforme necessário
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

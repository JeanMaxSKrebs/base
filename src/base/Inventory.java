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
import entities.itens.comidas.frutas.Fruta;
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

	// Renderização do inventário
	public static int slotSize = 60; // Tamanho do slot
	public static int inventoryX = 45; // Posição X inicial do inventário
	public static int inventoryY = 45; // Posição Y inicial do inventário

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

					Item selectedItem = inventoryItens[currentOptionInventory];

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

		// Preencher o fundo da tela de inventário com a cor Marrom
		g.setColor(new Color(139, 69, 19)); // Marrom
		g.fillRect(0, 0, Game.getWIDTH() * Game.getSCALE(), Game.getHEIGHT() * Game.getSCALE());

		// Preencher o fundo do inventário com a cor marromfraco
		g.setColor(new Color(255, 218, 185));
		g.fillRect(inventoryX - 10, inventoryY - 10, cols * slotSize + 20, rows * slotSize + 20);

		// Escrever Inventário no fundo a cor preta
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Inventário", inventoryX, inventoryY - 20);

		renderEachItem(g, slotSize, inventoryX, inventoryY);

		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Usar item", ((Game.getWIDTH() * Game.getSCALE() / 2 + (Game.getWIDTH() / 5))),
				((Game.getHEIGHT() * Game.getSCALE() / 2)));

		g.drawString("Dropar item", ((Game.getWIDTH() * Game.getSCALE() / 2 + (Game.getWIDTH() / 5))),
				((Game.getHEIGHT() * Game.getSCALE() / 2) + 50));

		g.drawString("Voltar", ((Game.getWIDTH() * Game.getSCALE() - 250)),
				((Game.getHEIGHT() * Game.getSCALE()) - 100));

		if (options[currentOption] == "usar item") {
			g.drawString(" > ", (((Game.getWIDTH() * Game.getSCALE() / 2 + (Game.getWIDTH() / 5)) - 50)),
					((Game.getHEIGHT() * Game.getSCALE() / 2)));
		} else if (options[currentOption] == "dropar item") {
			g.drawString(" > ", (((Game.getWIDTH() * Game.getSCALE() / 2 + (Game.getWIDTH() / 5)) - 50)),
					((Game.getHEIGHT() * Game.getSCALE() / 2) + 50));
		} else if (options[currentOption] == "voltar") {
			g.drawString(" > ", (((Game.getWIDTH() * Game.getSCALE() - 300))),
					((Game.getHEIGHT() * Game.getSCALE()) - 100));
		}
	}

	private void renderEachItem(Graphics g, int slotSize, int inventoryX, int inventoryY) {
		// TODO Auto-generated method stub
		// Render each item in the inventory
		int index = 0;
		for (Item item : Player.getItens()) {
			int row = index / cols; // Calculate the row for the current item
			int col = index % cols; // Calculate the column for the current item

			int x = inventoryX + col * slotSize;
			int y = inventoryY + row * slotSize;

			// Desenha o contorno do slot
			g.drawRect(x, y, slotSize, slotSize);

			// Desenha o contorno da quantidade
			g.setColor(Color.GRAY);
			g.fillRect(x, y + slotSize - (slotSize / 4), slotSize * 2 / 3, slotSize / 3);
			g.setColor(Color.black);

			// Render item name and details
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawImage(item.getSprite(), x + 5, y + 10, slotSize - 5, slotSize - 10, null);

			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString(item.getNome(), x + 2, y + 10); // Render item name

			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString("QTD: " + item.getQuantidade(), x + 3, y + slotSize - 4); // Render item quantity

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

//						System.out.println("itens");
//						System.out.println(Player.getItens());

			}
		}

		// Renderize o detalhamento do item selecionado se a exibição estiver ativada
		if (showItemDetails) {
//					System.out.println("showItemDetails");
//					System.out.println(showItemDetails);
			// Defina as coordenadas e o tamanho do quadro de detalhamento do item
			int detailFrameX = inventoryX + cols * slotSize + 20; // Posição X do quadro de detalhamento
			int detailFrameY = inventoryY; // Posição Y do quadro de detalhamento
			int detailFrameWidth = 200; // Largura do quadro de detalhamento
			int detailFrameHeight = Game.getHEIGHT() / 2; // Altura do quadro de detalhamento

			// Desenhe o quadro de detalhamento
			g.setColor(Color.GRAY);
			g.fillRect(detailFrameX, detailFrameY, detailFrameWidth, detailFrameHeight);

			// Obtenha o item selecionado
			Item selectedItem = Player.getItens().get(currentOptionInventory);
//					System.out.println("selectedItem");
//					System.out.println(selectedItem);
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

//				        // Imprime os valores
//				        System.out.println("Regen: " + regen);
//				        System.out.println("Tick Regen: " + tickRegen);
//				        System.out.println("Cura Total: " + curaTotal);
				g.drawString("Regen: " + regen, detailFrameX + 10, detailFrameY + 110);
				g.drawString("Tick Regen: " + tickRegen, detailFrameX + 10, detailFrameY + 140);
				g.drawString("Cura Total: " + curaTotal, detailFrameX + 10, detailFrameY + 170);
			}
			// Renderize outras informações do item, conforme necessário
		}

	}
}

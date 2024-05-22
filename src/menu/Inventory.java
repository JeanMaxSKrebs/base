package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import base.Game;
import entities.Player;
import entities.itens.Item;
import entities.itens.comidas.frutas.Fruta;
import entities.itens.comidas.frutas.Maca;
import entities.itens.comidas.frutas.Uva;

public class Inventory {

	public String[] options = { "usar item", "dropar item", "voltar" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;

	public static int maximoRows = 8; // Quantidade de linhas do inventário
	public static int maximoCols = 8; // Quantidade de colunas do inventário

	public String[] inventory = new String[maximoRows * maximoCols]; // Inventário com 5 linhas e 10 colunas
	public static Item[] inventoryItens = new Item[maximoRows * maximoCols];

	// Renderização do inventário
	public static int slotSize = (maximoRows * maximoCols) - maximoCols / 2; // Tamanho do slot
	public static int inventoryX = 45; // Posição X inicial do inventário
	public static int inventoryY = 45; // Posição Y inicial do inventário
	public static int inventoryWidth = slotSize * maximoCols; // width do inventário
	public static int inventoryHeight = slotSize * maximoRows; // height do inventário

	public static boolean entrouInventario = true;
	public static boolean showItemDetails = false;

	public static int currentOptionInventory = 0;
	public int maxOptionInventory = inventory.length - 1;

	public boolean right, left;

	public static boolean pause = false;

	public static boolean saveExists = false;

	public int index = 0;
	public int maxIndex = Player.inventario;
	
	public Item selectedItem;


	// Declare variables for timing and visibility
	private int blinkCounter = 0;
	private boolean isSymbolVisible = true;
	private final int BLINK_INTERVAL = 30; // Adjust this value to change blink speed

	// In your update method (called every frame)
	public void updateBlink() {
		blinkCounter++;
		if (blinkCounter >= BLINK_INTERVAL) {
			isSymbolVisible = !isSymbolVisible;
			blinkCounter = 0;
		}
	}

	public void tick() {

		// Atualiza o inventário com os itens do jogador
		int index = 0;
		for (Item item : Player.getItensColetados()) {
			// Calcula a linha e a coluna para o item atual
			int row = index / maximoCols;
			int col = index % maximoCols;

			// Atualiza o inventário com o nome do item
			inventoryItens[row * maximoCols + col] = item;

			// Incrementa o índice para o próximo item
			index++;
		}
		for (Fruta fruta : Player.getFrutasColetadas()) {
			// Calcula a linha e a coluna para o item atual
			int row = index / maximoCols;
			int col = index % maximoCols;

			// Atualiza o inventário com o nome da fruta
			inventoryItens[row * maximoCols + col] = fruta;

			// Incrementa o índice para a próxima fruta
			index++;
		}

		if (entrouInventario) {

			if (up) {
				up = false;

				int currentOptionInventoryTemp = currentOptionInventory;
				currentOptionInventory = currentOptionInventory - maximoCols;
				if (currentOptionInventory < 0) {
					currentOptionInventory = currentOptionInventoryTemp + (maximoCols * maximoRows) - maximoCols;
				}
			}
			if (down) {
				down = false;

				int currentOptionInventoryTemp = currentOptionInventory;
				currentOptionInventory = currentOptionInventory + maximoCols;
				if (currentOptionInventory > maxOptionInventory) {

					currentOptionInventory = currentOptionInventoryTemp - (maximoRows * maximoCols) + maximoCols;

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
//				System.out.println("currentOptionInventory");
//				System.out.println(currentOptionInventory);
				if (currentOptionInventory >= 0 && currentOptionInventory < Player.getItensColetados().size()
						+ Player.getFrutasColetadas().size()) {
					
					 selectedItem = inventoryItens[currentOptionInventory];

					if (selectedItem != null) {
						entrouInventario = false;
						currentOption = 0;
					}

				}
			}

			if (currentOptionInventory >= 0 && currentOptionInventory < Player.getItensColetados().size()
					+ Player.getFrutasColetadas().size()) {
				showItemDetails = true;
			} else {
				showItemDetails = false;
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
			if (left) {
				left = false;
			}
			if (right) {
				right = false;
			}
			if (enter) {
				enter = false;
				if (options[currentOption] == "usar item") {
					if(selectedItem.getQuantidade() == 1) {						
						entrouInventario = true;
					}
					Game.player.use(selectedItem);

				} else if (options[currentOption] == "dropar item") {
					if(selectedItem.getQuantidade() == 1) {						
						entrouInventario = true;
					}
					selectedItem.serDropado(Game.player);



				} else if (options[currentOption] == "voltar") {
					entrouInventario = true;
				}
			}
			updateBlink();
		}

	}

	public void render(Graphics g) {

		// Preencher o fundo da tela de inventário com a cor Marrom
		g.setColor(new Color(139, 69, 19)); // Marrom
		g.fillRect(0, 0, Game.getWIDTH() * Game.getSCALE(), Game.getHEIGHT() * Game.getSCALE());

		// Preencher o fundo do inventário com a cor marromfraco
		g.setColor(new Color(255, 218, 185));
		g.fillRect(inventoryX - 10, inventoryY - 10, inventoryWidth + 20, inventoryHeight + 20);

		// Escrever Inventário no fundo a cor preta
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Inventário", inventoryX, inventoryY - 20);

		List<Item> playerItems = Player.getItensColetados();
		if (playerItems != null && !playerItems.isEmpty()) {
			renderEachItem(g, slotSize, inventoryX, inventoryY, inventoryWidth, inventoryHeight);
		}
		List<Fruta> playerFruits = Player.getFrutasColetadas();
		if (playerFruits != null && !playerFruits.isEmpty()) {
			renderEachFruit(g, slotSize, inventoryX, inventoryY, inventoryWidth, inventoryHeight);
		}

		if (showItemDetails) {
			renderShowItensDetails(g);
		}
		if (!entrouInventario) {
			renderUseDetails(g);
		}
		index = 0;

		renderGrid(g);
	}

	private void renderUseDetails(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Usar item", ((Game.getWIDTH() * Game.getSCALE() / 2 + (Game.getWIDTH() / 5))),
				((Game.getHEIGHT() * Game.getSCALE() / 2)));

		g.drawString("Dropar item", ((Game.getWIDTH() * Game.getSCALE() / 2 + (Game.getWIDTH() / 5))),
				((Game.getHEIGHT() * Game.getSCALE() / 2) + 50));

		g.drawString("Voltar", ((Game.getWIDTH() * Game.getSCALE() - 250)),
				((Game.getHEIGHT() * Game.getSCALE()) - 100));

		if (isSymbolVisible) {

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
	}

	private void renderShowItensDetails(Graphics g) {
//					System.out.println("showItemDetails");
//					System.out.println(showItemDetails);
		// Defina as coordenadas e o tamanho do quadro de detalhamento do item
		int detailFrameX = inventoryX + maximoCols * slotSize + 20; // Posição X do quadro de detalhamento
		int detailFrameY = inventoryY; // Posição Y do quadro de detalhamento
		int detailFrameWidth = 200; // Largura do quadro de detalhamento
		int detailFrameHeight = Game.getHEIGHT() / 2; // Altura do quadro de detalhamento

		// Desenhe o quadro de detalhamento
		g.setColor(Color.GRAY);
		g.fillRect(detailFrameX, detailFrameY, detailFrameWidth, detailFrameHeight);
		g.setColor(Color.BLACK);

		List<Item> playerItems = Player.getItensColetados();
		int playerItemsSize = playerItems.size();
		List<Fruta> playerFrutas = Player.getFrutasColetadas();
		int playerFrutasSize = playerFrutas.size();

		if (currentOptionInventory >= 0 && currentOptionInventory <= index) {

			if (currentOptionInventory < playerItemsSize) {
				// Obtenha o item selecionado
				Item selectedItem = playerItems.get(currentOptionInventory);
//						System.out.println("selectedItem");
//						System.out.println(selectedItem);
				// Renderize as informações detalhadas do item
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString("Detalhes do Item:", detailFrameX + 10, detailFrameY + 20);
				g.drawString("Nome: " + selectedItem.getNome(), detailFrameX + 10, detailFrameY + 50);
				g.drawString("Quantidade: " + selectedItem.getQuantidade(), detailFrameX + 10, detailFrameY + 80);
			} else if (currentOptionInventory < playerItemsSize + playerFrutasSize) {
				// Obtenha a fruta selecionada
				Fruta selectedFruta = playerFrutas.get(currentOptionInventory - playerItemsSize);

				// Renderize as informações detalhadas da fruta
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString("Detalhes da Fruta:", detailFrameX + 10, detailFrameY + 20);
				g.drawString("Nome: " + selectedFruta.getNome(), detailFrameX + 10, detailFrameY + 50);
				g.drawString("Quantidade: " + selectedFruta.getQuantidade(), detailFrameX + 10, detailFrameY + 80);
				g.drawString("Regen: " + selectedFruta.getRegen(), detailFrameX + 10, detailFrameY + 110);
				g.drawString("Tick Regen: " + selectedFruta.getTickRegen(), detailFrameX + 10, detailFrameY + 140);
				g.drawString("Cura Total: " + selectedFruta.getCuraTotal(), detailFrameX + 10, detailFrameY + 170);

			}
		}

	}

	private void renderGrid(Graphics g) {
		for (int row = 0; row < maximoRows; row++) {
			for (int col = 0; col < maximoCols; col++) {
				int x = inventoryX + col * slotSize;
				int y = inventoryY + row * slotSize;
				g.drawRect(x, y, slotSize, slotSize); // Desenha o contorno do slot

				// Render a different color around the selected inventory item
				if (row * maximoCols + col == currentOptionInventory) {

					g.setColor(Color.BLACK);

					for (int i = 1; i <= 5; i++) {
						g.drawRect(x - i, y - i, slotSize + i * 2, slotSize + i * 2);
					}
					g.setColor(Color.BLACK);

				}

//				System.out.println("itens");
//				System.out.println(Player.getItens());

			}
		}
	}

	private void renderEachItem(Graphics g, int slotSize, int inventoryX, int inventoryY, int inventoryWidth,
			int inventoryHeight) {

		for (Item item : Player.getItensColetados()) {
			int row = index / maximoRows; // Calculate the row for the current item
			int col = index % maximoCols; // Calculate the column for the current item

			int x = inventoryX + col * slotSize;
			int y = inventoryY + row * slotSize;

			// Desenha o contorno do slot
			g.drawRect(x, y, slotSize, slotSize);
			// Render item name and details
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawImage(item.getSprite(), x + 5, y + 10, slotSize - 5, slotSize - 10, null);

			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString(item.getNome(), x + 2, y + 10); // Render item name

			// Desenha o contorno da quantidade
			g.setColor(Color.white);
			g.fillOval(x, y + slotSize - (slotSize / 4), slotSize / 4, slotSize / 4);
			g.setColor(Color.black);

			if (item.getQuantidade() >= 100) {
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.valueOf(item.getQuantidade()), x, y + slotSize - 4); // Render item quantity
			} else if (item.getQuantidade() >= 10) {
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.valueOf(item.getQuantidade()), x + 2, y + slotSize - 4); // Render item quantity
			} else {
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.valueOf(item.getQuantidade()), x + 5, y + slotSize - 4); // Render item quantity
			}
			// index da lista
			index++;
		}
	}

	public void renderEachFruit(Graphics g, int slotSize, int inventoryX, int inventoryY, int inventoryWidth,
			int inventoryHeight) {

		for (Fruta fruta : Player.getFrutasColetadas()) {
			int row = index / maximoRows; // Calculate the row for the current fruta
			int col = index % maximoCols; // Calculate the column for the current fruta

			int x = inventoryX + col * slotSize;
			int y = inventoryY + row * slotSize;

			// Desenha o contorno do slot
			g.drawRect(x, y, slotSize, slotSize);

			// Render fruta name and details
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawImage(fruta.getSprite(), x + 5, y + 10, slotSize - 5, slotSize - 10, null);

			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString(fruta.getNome(), x + 2, y + 10); // Render fruta name

			// Desenha o contorno da quantidade
			g.setColor(Color.white);
			g.fillOval(x, y + slotSize - (slotSize / 4), slotSize / 4, slotSize / 4);
			g.setColor(Color.black);

			if (fruta.getQuantidade() >= 100) {
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.valueOf(fruta.getQuantidade()), x, y + slotSize - 4); // Render fruta quantity
			} else if (fruta.getQuantidade() >= 10) {
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.valueOf(fruta.getQuantidade()), x + 2, y + slotSize - 4); // Render fruta quantity
			} else {
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.valueOf(fruta.getQuantidade()), x + 5, y + slotSize - 4); // Render fruta quantity
			}
			// index da lista
			index++;
		}
	}

}

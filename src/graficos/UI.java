package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import base.Game;
import entities.Player;
import entities.itens.Item;
import entities.itens.utensilios.BagPacks.BagPack;
import tempo.FaseDaLua;
import tempo.Tempo;
import world.Camera;

public class UI {

	public static boolean showColetar = false;
	int frame;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Time format (hours:minutes)
	public boolean mensagem;
	public boolean renderBars = true;
	public static boolean usarKey = false;
	public static String tipoKey = "Vazio";
	
	public static boolean verFPS = true;

	public static boolean animacaoColeta = false;
	public static int itemX, itemY;
	public static int targetX, targetY;
	public static Item itemSendoColetado;

	public int widthBase = Game.getWIDTH() * Game.getSCALE();
	public int heightBase = Game.getHEIGHT() * Game.getSCALE();
	
	public void render(Graphics g) {

		if(verFPS) {
			int x = 0; // Center horizontally based on string width
			int y = heightBase  - 10;
			
			String FPS = "FPS";
			
			String combinedString = FPS + ":" + Game.FPS;
			
			g.setColor(new Color(155, 155, 155));
			g.fillRect(x, y-25, 100, 100); // Desenhar a barra
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString(combinedString, x+5, y);
		}
		
		quadroEsquerdo(g);
		quadroDireito(g);

		renderHealthBar(g);
		if (renderBars) {
			renderStaminehBar(g);
			renderHungerhBar(g);
			renderThirsthBar(g);
			renderShowBar(g);
		} else {
			renderShowBar(g);
		}

		if(Game.player.hasBagpack) {			
		// Render the backpack
		renderMochila(g);
		}
		renderTime(g);

//		System.out.println("FPS: " + Game.FPS);

		if (showColetar) {
			showColetar = false;

			int rectWidth = (Game.getWIDTH() / 5 * Game.getSCALE()); // Largura do retângulo
			int rectHeight = (Game.getHEIGHT() / 2 * Game.getSCALE()) / 6; // Altura do retângulo
			int rectX = (Game.getWIDTH() * Game.getSCALE() - rectWidth) / 2; // Posição X centralizada
			int rectY = (Game.getHEIGHT() * Game.getSCALE() - rectHeight - 10); // Posição Y centralizada

			g.setColor(new Color(0, 0, 0)); // black
			int borderWidth = 5; // Espessura da borda
			for (int i = 0; i < borderWidth; i++) {
				g.drawRect(rectX - i, rectY - i, rectWidth + (2 * i), rectHeight + (2 * i));
			}
			g.setColor(new Color(155, 255, 165)); // verde
			g.fillRect(rectX, rectY, rectWidth, rectHeight); // Desenhar o retângulo
			if (Game.player.coletando) {
				int progressoBarraWidth = (rectWidth * Game.player.tempoColeta) / Game.player.tempoColetaMax;
				g.setColor(new Color(0, 0, 0)); // preto
				g.drawRect(rectX, rectY, rectWidth, rectHeight); // Desenhar a borda
				g.setColor(new Color(0, 155, 0));
				g.fillRect(rectX, rectY, progressoBarraWidth, rectHeight); // Desenhar a barra
			}
		}

		if (!Game.player.possoColetar) {
			int rectWidth = (Game.getWIDTH() / 5 * Game.getSCALE()); // Largura do retângulo
			int rectHeight = (Game.getHEIGHT() / 2 * Game.getSCALE()) / 6; // Altura do retângulo
			int rectX = (Game.getWIDTH() * Game.getSCALE() - rectWidth) / 2; // Posição X centralizada
			int rectY = (Game.getHEIGHT() * Game.getSCALE() - rectHeight - 10); // Posição Y centralizada

			g.setColor(new Color(0, 0, 0)); // black
			int borderWidth = 5; // Espessura da borda
			for (int i = 0; i < borderWidth; i++) {
				g.drawRect(rectX - i, rectY - i, rectWidth + (2 * i), rectHeight + (2 * i));
			}
			g.setColor(new Color(155, 155, 155));
			g.fillRect(rectX, rectY, rectWidth, rectHeight); // Desenhar o retângulo

			int progressoBarraWidth = (rectWidth * Game.player.tempoEspera) / Game.player.tempoEsperaMax;

			g.setColor(new Color(0, 0, 0)); // preto
			g.drawRect(rectX, rectY, rectWidth, rectHeight); // Desenhar a borda
			g.setColor(new Color(155, 255, 165)); // verde
			g.fillRect(rectX, rectY, progressoBarraWidth, rectHeight); // Desenhar a barra
		}

		int widthBase = Game.getWIDTH() * Game.getSCALE();
		int heightBase = Game.getHEIGHT() * Game.getSCALE();

		if (mensagem) {
			long startTime = System.currentTimeMillis(); // Momento inicial
			long lastSpriteUpdateTime = System.currentTimeMillis(); // Momento da última atualização do sprite

			if (startTime - Game.messageDisplayStartTime < Game.MESSAGE_DISPLAY_DURATION) {
				long elapsedTime = lastSpriteUpdateTime - Game.messageDisplayStartTime; // Tempo decorrido

				int spriteIndex = 0; // Índice do sprite atual
				int maximoFrameCounter = Tempo.FASES_DA_LUA[Tempo.restoLua].getQtdSprites(); // Contador de frames

				g.setColor(new Color(155, 155, 165)); // Cinza
				int rectWidth = (Game.getWIDTH() * Game.getSCALE()); // Largura do retângulo
				int rectHeight = (Game.getHEIGHT() * Game.getSCALE()) / 5; // Altura do retângulo
				int rectX = (Game.getWIDTH() * Game.getSCALE() - rectWidth) / 2; // Posição X centralizada
				int rectY = (Game.getHEIGHT() * Game.getSCALE() - rectHeight) / 2; // Posição Y centralizada
				g.fillRect(rectX, rectY, rectWidth, rectHeight); // Desenhar o retângulo
				g.setColor(Color.white);
				g.setFont(new Font("roboto", Font.BOLD, 10));
				String faseDaLuaString = "Lua do Diabo";

				if (Game.linguagem == "Inglês") {
					faseDaLuaString = Tempo.FASES_DA_LUA[Tempo.restoLua].getNomeIngles();
				} else if (Game.linguagem == "Português") {
					faseDaLuaString = Tempo.FASES_DA_LUA[Tempo.restoLua].getNomePortugues();
				}

				// Medir tamanho da string
				FontMetrics metrics = g.getFontMetrics();
				int ascent = metrics.getAscent(); // Distância acima da linha base
				int descent = metrics.getDescent(); // Distância abaixo da linha base
				int leading = metrics.getLeading(); // Espaço adicional entre as linhas

				int stringHeight = ascent + descent + leading; // Altura total do texto
				int stringWidth = metrics.stringWidth(faseDaLuaString);

				// Calcular coordenadas para centralização
				int x = ((widthBase / 2) - stringWidth * 3); // Centralizar horizontalmente
				int y = (heightBase + stringHeight) / 2 + ascent;
				g.setColor(new Color(0, 0, 0)); // Preta
				g.setFont(new Font("calibri", Font.BOLD, 72));
				g.drawString(faseDaLuaString, x, y);

				// Cálculo do índice do sprite
				spriteIndex = (int) (elapsedTime / 500 % Tempo.FASES_DA_LUA[Tempo.restoLua].getQtdSprites());

//				System.out.println("tempo passado: " + (elapsedTime));

				spriteIndex++;
				if (spriteIndex >= maximoFrameCounter) {
					spriteIndex = 0; // Resetar o spriteIndex se ultrapassar o máximo
				}

				x = (int) ((widthBase / 10));
				y = (int) ((heightBase / 2) - (stringHeight * 4.7));

				// Exibir o sprite
				g.drawImage(Tempo.FASES_DA_LUA[Tempo.restoLua].getSpritesheet()[spriteIndex], x, y, null);

				x = (int) ((widthBase) - (widthBase / 5));
				y = (int) ((heightBase / 2) - (stringHeight * 4.7));
				g.drawImage(Tempo.FASES_DA_LUA[Tempo.restoLua].getSpritesheet()[spriteIndex], x, y, null);

			}
		}

		if (Game.openInventory) {
			if (!Game.player.hasBagpack) {
				if (System.currentTimeMillis() - Game.messageDisplayStartTime < Game.MESSAGE_DISPLAY_DURATION) {
					// Desenhar o quadro
					g.setColor(new Color(139, 69, 19)); // Marrom
					int rectWidth = (Game.getWIDTH() * Game.getSCALE()); // Largura do retângulo
					int rectHeight = (Game.getHEIGHT() * Game.getSCALE()) / 4; // Altura do retângulo
					int rectX = (Game.getWIDTH() * Game.getSCALE() - rectWidth) / 2; // Posição X centralizada
					int rectY = (Game.getHEIGHT() * Game.getSCALE() - rectHeight) / 2; // Posição Y centralizada
					g.fillRect(rectX, rectY, rectWidth, rectHeight); // Desenhar o retângulo

					// Desenhar o texto
					g.setColor(Color.black);
					g.setFont(new Font("calibri", Font.BOLD, 48));
					String message = "Você não tem mochila!";
					
					if (Game.linguagem == "Inglês") {
						message = "You need a backpack first!";
					} else if (Game.linguagem == "Português") {
						message = "Você precisa de uma mochila primeiro!";
					}
					int textWidth = g.getFontMetrics().stringWidth(message); // Largura do texto
					int textX = rectX + (rectWidth - textWidth) / 2; // Posição X centralizada
					int textY = rectY + rectHeight / 2 + g.getFontMetrics().getHeight() / 4; // Posição Y centralizada
					g.drawString(message, textX, textY); // Desenhar o texto
//					g.setColor(Color.black);
//					g.setFont(new Font("calibri", Font.BOLD, 48));
//					g.drawString("Você precisa de uma mochila primeiro!", ((WIDTH * getSCALE() / 5)),
//							((HEIGHT * getSCALE() / 2)));
				} else {
					Game.openInventory = false; // Se passaram 3 segundos, a mensagem não é mais exibida
				}
			}
		}

		if (usarKey) {
			int rectWidth = (Game.getWIDTH() / 5 * Game.getSCALE()); // Largura do retângulo
			int rectHeight = (Game.getHEIGHT() / 2 * Game.getSCALE()) / 6; // Altura do retângulo
			int rectX = (Game.getWIDTH() * Game.getSCALE() - rectWidth) / 2; // Posição X centralizada
			int rectY = (Game.getHEIGHT() * Game.getSCALE() - rectHeight - 10); // Posição Y centralizada

			g.setColor(new Color(0, 0, 0)); // black
			int borderWidth = 5; // Espessura da borda
			for (int i = 0; i < borderWidth; i++) {
				g.drawRect(rectX - i, rectY - i, rectWidth + (2 * i), rectHeight + (2 * i));
			}
			g.setColor(new Color(155, 155, 155));
			g.fillRect(rectX, rectY, rectWidth, rectHeight); // Desenhar o retângulo

			g.setColor(new Color(0, 0, 0)); // black
			g.setFont(new Font("roboto", Font.BOLD, 28));

			int stringWidth = g.getFontMetrics().stringWidth("Usar " + tipoKey);

			if (tipoKey != "Vazio") {
				g.setFont(new Font("roboto", Font.BOLD, 23));
				stringWidth -= 40;
			}
			int xPos = (Game.getWIDTH() - stringWidth) / 2;

			g.drawString("Usar " + tipoKey, xPos, Game.getHEIGHT() - 25);
		}

	}

	private void renderTime(Graphics g) {
		// Formatação do tempo
		String formattedTime = String.format("%02d:%02d", Tempo.hours, Tempo.minutes);

		// Exibição do tempo na tela
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 40));
		g.drawString(formattedTime, Game.getWIDTH() / 2 - 50, 40);

	}

	private void quadroEsquerdo(Graphics g) {
		// Set up the background rectangle and text color
		g.setColor(Color.gray);
		g.fillRect(5, Game.getHEIGHT() / 2 - 60, 145, 70); // Adjusted height and width
		g.setColor(Color.white);

		// Set the font
		g.setFont(new Font("Roboto", Font.PLAIN, 12));
		FontMetrics fm = g.getFontMetrics();

		// Text to be displayed
		String[] labels = { "Inventário:", "Pause:" };
		String[] values = {	"Pressione I", "Pressione P"};

		// Calculate the maximum width of the labels
		int maxWidth = 0;
		for (String label : labels) {
			int width = fm.stringWidth(label);
			if (width > maxWidth) {
				maxWidth = width;
			}
		}

		// Display the labels and values
		int startX = 15; // Adjusted X position for padding
		int startY = Game.getHEIGHT() / 2 - 45;
		int lineHeight = 15;

		for (int i = 0; i < labels.length; i++) {
			g.drawString(labels[i], startX, startY + (i * lineHeight));
			g.drawString(values[i], startX + maxWidth + 5, startY + (i * lineHeight)); // Add some space between label
																						// and value
		}
	}

	private void quadroDireito(Graphics g) {
		// Set up the background rectangle and text color
		g.setColor(Color.gray);
		g.fillRect(Game.getWIDTH() - 125, Game.getHEIGHT() / 2 - 60, 120, 60);
		g.setColor(Color.white);

		// Set the font
		g.setFont(new Font("Roboto", Font.PLAIN, 12));
		FontMetrics fm = g.getFontMetrics();

		// Create a DecimalFormat instance for formatting the numbers
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		symbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("00.0", symbols);

		// Text to be displayed
		String[] labels = { "ARMADURA:", "ESQUIVA:", "VELOCIDADE:" };
		double[] values = { Player.getArmor(), Player.getDodgeChance(), Player.getSpeed() };

		// Calculate the maximum width of the labels
		int maxWidth = 0;
		for (String label : labels) {
			int width = fm.stringWidth(label);
			if (width > maxWidth) {
				maxWidth = width;
			}
		}

		// Display the labels and values
		int startX = Game.getWIDTH() - 120;
		int startY = Game.getHEIGHT() / 2 - 40;
		int lineHeight = 15;

		for (int i = 0; i < labels.length; i++) {
			g.drawString(labels[i], startX, startY + (i * lineHeight));
			g.drawString(df.format(values[i]), startX + maxWidth + 5, startY + (i * lineHeight)); // Add some space
																									// between label and
																									// value
		}

	}

	private void renderBarrinha(Graphics g, int xBar, int yBar, int widthBar, int heightBar) {
		g.setColor(Color.yellow);
		g.fillRect(xBar, yBar, (int) ((Game.player.stamine / Player.maxStamine) * widthBar), heightBar / 3);
		g.setColor(new Color(204, 86, 22));
		g.fillRect(xBar, yBar + heightBar / 3, (int) ((Game.player.hunger / Player.maxHunger) * widthBar),
				heightBar / 3);
		g.setColor(Color.blue);
		g.fillRect(xBar, yBar + heightBar / 3 * 2, (int) ((Game.player.thirsth / Player.maxThirsth) * widthBar),
				heightBar / 3);
	}

	private void renderShowBar(Graphics g) {
		// Desenhar a barrinha

		String stringBar = "vazio";
		int xBar = 33;
		int yBar = 140;
		int widthBar = 80 * 2;
		int heightBar = 20;
		g.setColor(Color.black);
		if (renderBars) {
			yBar = 140;
			stringBar = "↑";
			g.fillRect(xBar - 3, yBar - 3, (widthBar + 6), heightBar + 6);
			g.setColor(Color.GRAY);
			g.fillRect(xBar, yBar, widthBar, heightBar);
		} else {
			yBar = 50;
			stringBar = "";
			g.fillRect(xBar - 3, yBar - 3, (widthBar + 6), heightBar + 5);
			renderBarrinha(g, xBar, yBar, widthBar, heightBar);
		}

		g.setColor(Color.black); // Black
		int tamFont = 16;
		g.setFont(new Font("Calibri", Font.BOLD, tamFont));

		g.drawString(stringBar, xBar + widthBar / 2 - 5, yBar + heightBar - 5);
	}

	private void renderThirsthBar(Graphics g) {
		int height = (Game.getHEIGHT() * Game.getSCALE());
		int width = (Game.getWIDTH() * Game.getSCALE());

		// Health bar
		int xThirsthBar = 33;
		int yThirsthBar = 111;
		int widthThirsthBar = 80 * 2;
		int heightThirsthBar = 20;
		g.setColor(Color.black);
		g.fillRect(xThirsthBar - 3, yThirsthBar - 3, (widthThirsthBar + 6), heightThirsthBar + 6);
		g.setColor(Color.blue);
		g.fillRect(xThirsthBar, yThirsthBar, (int) ((Game.player.thirsth / Player.maxThirsth) * widthThirsthBar),
				heightThirsthBar);
		g.setColor(new Color(0, 0, 128)); // Black-Blue
		int tamFont = 16;
		g.setFont(new Font("roboto", Font.BOLD, tamFont));
		String stringThirsthBar = (int) (Game.player.thirsth) + " / " + (int) (Player.maxThirsth);
		int tamString = stringThirsthBar.length();
		g.drawString((int) (Game.player.thirsth) + " / " + (int) (Player.maxThirsth),
				xThirsthBar + widthThirsthBar / 3 - tamString / 2, yThirsthBar + heightThirsthBar - tamString / 2);

	}

	private void renderHungerhBar(Graphics g) {
		int height = (Game.getHEIGHT() * Game.getSCALE());
		int width = (Game.getWIDTH() * Game.getSCALE());

		// Hunger bar
		int xHungerhBar = 33;
		int yHungerhBar = 83;
		int widthHungerhBar = 80 * 2;
		int heightHungerhBar = 20;
		g.setColor(Color.black);
		g.fillRect(xHungerhBar - 3, yHungerhBar - 3, (widthHungerhBar + 6), heightHungerhBar + 6);
		g.setColor(new Color(204, 86, 22));
		g.fillRect(xHungerhBar, yHungerhBar, (int) ((Game.player.hunger / Player.maxHunger) * widthHungerhBar),
				heightHungerhBar);
		g.setColor(new Color(201, 154, 66));
		int tamFont = 16;
		g.setFont(new Font("roboto", Font.BOLD, tamFont));
		String stringHungerBar = (int) (Game.player.hunger) + " / " + (int) (Player.maxHunger);
		int tamString = stringHungerBar.length();
		g.drawString((int) (Game.player.hunger) + " / " + (int) (Player.maxHunger),
				xHungerhBar + widthHungerhBar / 3 - tamString / 2, yHungerhBar + heightHungerhBar - tamString / 2);

	}

	private void renderStaminehBar(Graphics g) {
		int height = (Game.getHEIGHT() * Game.getSCALE());
		int width = (Game.getWIDTH() * Game.getSCALE());

		// Stamine bar
		int xStaminehBar = 33;
		int yStaminehBar = 55;
		int widthStaminehBar = 80 * 2;
		int heightStaminehBar = 20;
		g.setColor(Color.black);
		g.fillRect(xStaminehBar - 3, yStaminehBar - 3, (widthStaminehBar + 6), heightStaminehBar + 6);
		g.setColor(Color.yellow);
		g.fillRect(xStaminehBar, yStaminehBar, (int) ((Game.player.stamine / Player.maxStamine) * widthStaminehBar),
				heightStaminehBar);
		g.setColor(new Color(168, 153, 12)); // Black-Yellow
		int tamFont = 16;
		g.setFont(new Font("roboto", Font.BOLD, tamFont));
		String stringStamineBar = (int) (Game.player.stamine) + " / " + (int) (Player.maxStamine);
		int tamString = stringStamineBar.length();
		g.drawString((int) (Game.player.stamine) + " / " + (int) (Player.maxStamine),
				xStaminehBar + widthStaminehBar / 3 - tamString / 2, yStaminehBar + heightStaminehBar - tamString / 2);

	}

	private void renderHealthBar(Graphics g) {
		int height = (Game.getHEIGHT() * Game.getSCALE());
		int width = (Game.getWIDTH() * Game.getSCALE());

		// Health bar
		int xHealthBar = 15;
		int yHealthBar = 15;
		int widthHealthBar = 100 * 2;
		int heightHealthBar = 30;
		g.setColor(Color.black);
		g.fillRect(xHealthBar - 5, yHealthBar - 5, (widthHealthBar + 10), heightHealthBar + 10);
		g.setColor(Color.green);
		g.fillRect(xHealthBar, yHealthBar, (int) ((Game.player.life / Player.maxLife) * widthHealthBar),
				heightHealthBar);
		g.setColor(new Color(0, 128, 0)); // Black-Green
		int tamFont = 24;
		g.setFont(new Font("roboto", Font.BOLD, tamFont));
		String stringHealthBar = (int) (Game.player.life) + " / " + (int) (Player.maxLife);
		int tamString = stringHealthBar.length();
		g.drawString((int) (Game.player.life) + " / " + (int) (Player.maxLife), widthHealthBar / 2 - tamString * 4,
				heightHealthBar + tamFont / 3);

	}

	// Método para lidar com os eventos de mouse
	public void mouseClicked(MouseEvent e) {
		// Verificar se o clique ocorreu dentro da área da barrinha
		int mouseX = e.getX();
		int mouseY = e.getY();
		System.out.println(mouseX);
		System.out.println(mouseY);
		if (renderBars) {
			if (mouseX >= 10 && mouseX <= 200 && mouseY >= 10 && mouseY <= 200) {
				// Se sim, alterar renderBars para true
				renderBars = !renderBars;
			}
		} else {
			if (mouseX >= 10 && mouseX <= 200 && mouseY >= 10 && mouseY <= 80) {
				// Se sim, alterar renderBars para true
				renderBars = !renderBars;
			}
		}
	}

	public void renderMochila(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;

		int mochilaX = Game.getWIDTH() * Game.getSCALE() - 112 - 5; // Adjust the size accordingly
		int mochilaY = 5; // Adjust the size accordingly
//		int mochilaY = Game.getHEIGHT() * Game.getSCALE() - 112; // Adjust the size accordingly
	    int mochilaWidth = 100;
	    int mochilaHeight = 100;
	    
	
	    // Create a gradient background
	    Color color1 = new Color(222, 184, 135); // Light brown
	    Color color2 = new Color(34, 139, 34);   // Green
	    
	    // Draw the gradient background
	    g2d.setPaint(new GradientPaint(mochilaX, mochilaY, color1, mochilaX + mochilaWidth, mochilaY + mochilaHeight, color2));
	    g2d.fillOval(mochilaX - 12, mochilaY, mochilaWidth + 24, mochilaHeight + 24); // Extend background for padding

	    // Draw the backpack image
	    g.drawImage(BagPack.BAGPACK_EN, mochilaX, mochilaY + 10, mochilaWidth, mochilaHeight, null);
	}

}

package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import base.Game;
import entities.Player;
import tempo.FaseDaLua;
import tempo.Tempo;
import world.Camera;

public class UI {

	public static boolean showColetar = false;
	int frame;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Time format (hours:minutes)
	public boolean mensagem;

	public void render(Graphics g) {
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
		g.setColor(Color.white);
		int tamFont = 24;
		g.setFont(new Font("roboto", Font.BOLD, tamFont));
		String stringHealthBar = (int) (Game.player.life) + " / " + (int) (Player.maxLife);
		int tamString = stringHealthBar.length();
		g.drawString((int) (Game.player.life) + " / " + (int) (Player.maxLife), widthHealthBar / 2 - tamString * 3,
				heightHealthBar + tamFont / 3);

		// Formatação do tempo
		String formattedTime = String.format("%02d:%02d", Tempo.hours, Tempo.minutes);

		// Exibição do tempo na tela
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 40));
		g.drawString(formattedTime, Game.getWIDTH() / 2 - 50, 40);

//		System.out.println("Game.hours");
//		System.out.println("Game.minutes");
//		System.out.println(Game.hours);
//		System.out.println(Game.minutes);

		if (Game.player.stamine == Player.getMaxStamine()) {
			if (frame >= 10) {
				g.setColor(Color.yellow);
				g.fillRect(84, 8, 16, 32);
				g.setColor(Color.black);
				g.fillRect(88, 12, 8, (int) ((Game.player.stamine / Player.maxStamine) * 24));
				frame = 0;
			}
			frame++;
		}

		g.setColor(Color.gray);
		g.fillRect(0, Game.getHEIGHT() - 96, 96, 96);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 10));

//		quadroEsquerdo();
//		quadroDireito();

//		System.out.println("FPS: " + Game.FPS);

		// frutas da UI
		g.setColor(Color.black);
		g.drawString("UVA:  " + Game.player.countFrutaEspecifica("UVA"), 0, Game.getHEIGHT() - 65);
		g.drawString("MAÇÃS:  " + Game.player.countFrutaEspecifica("MACA"), 0, Game.getHEIGHT() - 55);
		g.drawString("Frutas:  " + Player.getFrutasColetadas().size(), 0, Game.getHEIGHT() - 45);
		g.drawString("Comidas:  " + Player.getComidasColetadas().size(), 0, Game.getHEIGHT() - 35);
		g.drawString("Itens:  " + Player.getItens().size(), 0, Game.getHEIGHT() - 25);
		g.drawString("Inventário:  Press I", 0, Game.getHEIGHT() - 15);
		g.drawString("Pause:  Press P", 0, Game.getHEIGHT() - 5);

		g.setColor(Color.gray);
		g.fillRect(Game.getWIDTH() - 80, Game.getHEIGHT() - 32, 80, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 9));
		g.drawString("ARMADURA: " + Player.getArmor(), Game.getWIDTH() - 72, Game.getHEIGHT() - 25);
		g.drawString("ESQUIVA: " + Player.getDodgeChance(), Game.getWIDTH() - 72, Game.getHEIGHT() - 15);
		g.drawString("VELOCIDADE: " + Player.getSpeed(), Game.getWIDTH() - 72, Game.getHEIGHT() - 5);

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
			
			int progressoBarraWidth = Game.player.tempoEspera;
			
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
		} else {
			Game.gameState = "NORMAL";
			Game.openInventory = false; // Se passaram 3 segundos, a mensagem não é mais exibida
		}

		if (mensagem) {

		}

	}

}

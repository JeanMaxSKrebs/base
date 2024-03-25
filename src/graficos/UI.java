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

	int frame;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Time format (hours:minutes)
	public boolean mensagem;

	public void render(Graphics g) {
		// Health bar
		g.setColor(Color.black);
		g.fillRect(16, 8, 66, 32);
		g.setColor(Color.green);
		g.fillRect(24, 16, (int) ((Game.player.life / Player.maxLife) * 50), 16);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 11));
		g.drawString((int) (Game.player.life) + " / " + (int) (Player.maxLife), 26, 28);

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

	}

}

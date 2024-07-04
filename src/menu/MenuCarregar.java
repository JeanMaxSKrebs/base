package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;

import base.Game;
import base.save.GameSaveManager;
import base.save.Load;

public class MenuCarregar extends Menu {

	public static final Option[] options = { new Option("Usuário", "User"), new Option("Automático", "Auto Save"),
			new Option("Ultima Cama", "Last Bed"), new Option("Voltar", "Back") };

	public MenuCarregar() {
		super(options);
	}

	public void tick() {
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

			if (currentOption < 3) {
				Game.gameState = "NORMAL";
				Load.loadPlayerFromSave(currentOption);
				MenuPause.currentOptionto0();
				currentOption = 0;

			} else if (options[currentOption].getNomePortugues().equals("Voltar")) {
				currentOption = 0;
				Game.gameState = Game.previousGameState;
			}
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
		int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0, larguraDesejada, alturaDesejada);
		g.setColor(Color.WHITE);
		g.drawString("Carregar Jogo", larguraDesejada / 3 - 30, alturaDesejada / 5);

		// menu
		g.setFont(new Font("Arial", Font.BOLD, 40));

		int alturaDesejadaUMTERCO = alturaDesejada / 2 - 50;
		int larguraDesejadaUMTERCO = larguraDesejada / 5;

		String text = "ERRO";
		String lastAccessText = " (Vazio)";
		String combinedText = "ERRO (Vazio)";

		int spacingRows = 100;
		switch (Game.linguagem) {
		case "Inglês":
			text = "Save Selected: " + GameSaveManager.saveNames[GameSaveManager.slot - 1];

			g.drawString(text, larguraDesejada / 3 - 30, alturaDesejada / 3);
			g.setFont(new Font("Arial", Font.BOLD, 30));

			for (int i = 0; i < options.length - 1; i++) {
				text = options[i].getNomeIngles();
				lastAccessText = "Last Access: ";
				if (i < 3 && GameSaveManager.saveLoads[i] != null) {
					lastAccessText += GameSaveManager.saveLoads[i];

				}

				combinedText = text + " - " + lastAccessText;

				g.drawString(combinedText, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);

				lastAccessText = "Game Time: ";

				if (i < 3 && GameSaveManager.saveLoadsTempo[i] != null) {
					lastAccessText += GameSaveManager.saveLoadsTempo[i];

				}
				g.drawString(lastAccessText, larguraDesejadaUMTERCO,
						alturaDesejadaUMTERCO + spacingRows * i + 50);

			}
			g.drawString(options[options.length - 1].getNomeIngles(), larguraDesejada - 150, alturaDesejada - 30);
			break;

		case "Português":
			text = "Save Selecionado: " + GameSaveManager.saveNames[GameSaveManager.slot - 1];

			g.drawString(text, larguraDesejada / 3 - 40, alturaDesejada / 3);
			g.setFont(new Font("Arial", Font.BOLD, 30));

			for (int i = 0; i < options.length - 1; i++) {
				text = options[i].getNomePortugues();
				lastAccessText = "Último Acesso: ";
				if (i < 3 && GameSaveManager.saveLoads[i] != null) {
					lastAccessText += GameSaveManager.saveLoads[i];

				}

				combinedText = text + " - " + lastAccessText;

				g.drawString(combinedText, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);

				lastAccessText = "Tempo de jogo: ";

				if (i < 3 && GameSaveManager.saveLoadsTempo[i] != null) {
					lastAccessText += GameSaveManager.saveLoadsTempo[i];

				}
				g.drawString(lastAccessText, larguraDesejadaUMTERCO,
						alturaDesejadaUMTERCO + spacingRows * i + 50);

			}
			g.drawString(options[options.length - 1].getNomePortugues(), larguraDesejada - 150, alturaDesejada - 30);
			break;

		default:
			break;
		}

		int spacingWidth = 50;

		if (currentOption >= options.length - 1) {
		    drawOptionMarker(g, larguraDesejada - 150 - spacingWidth, alturaDesejada - 55);
		} else {
		    drawOptionMarker(g, larguraDesejadaUMTERCO - spacingWidth,
		            alturaDesejadaUMTERCO + spacingRows * currentOption);
		}

	}

}

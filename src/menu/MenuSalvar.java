package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;

import javax.swing.JOptionPane;

import base.Game;
import base.GameSaveManager;

public class MenuSalvar extends Menu {

	public static final Option[] options = { new Option("Slot 1", "Slot 1"), new Option("Slot 2", "Slot 2"),
			new Option("Slot 3", "Slot 3"), new Option("Voltar", "Back") };

	public MenuSalvar() {
		super(options);
		GameSaveManager.checkAndInitializeSaveNames();
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
				GameSaveManager.slot = currentOption + 1;

				if (GameSaveManager.saveNames[currentOption] == null) {
					String saveName = JOptionPane.showInputDialog("Digite o nome do arquivo de salvamento:");
					if (saveName != null && !saveName.trim().isEmpty()) {
						saveName = saveName.trim();
						GameSaveManager.initializeSavesForSlot(GameSaveManager.slot);
						GameSaveManager.addSaveName(saveName);
						GameSaveManager.saveNames[currentOption] = saveName;
						for (int i = 1; i <= 3; i++) {
							if (i == GameSaveManager.slot) {
								GameSaveManager.saveExists[currentOption * i] = true;
								GameSaveManager.saveExists[currentOption * i + 1] = true;
								GameSaveManager.saveExists[currentOption * i + 2] = true;
							}
						}

					}
					Game.gameState = "NORMAL";

				} else {
					Game.previousGameState = Game.gameState;
					Game.gameState = "CARREGAR";
				}

			} else if (options[currentOption].getNomePortugues().equals("Voltar")) {
				currentOption = 0;
				Game.gameState = "MENUPRINCIPAL";
				
			}
		}
	}

	public void render(Graphics g) {
		int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
		int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0, larguraDesejada, alturaDesejada);
		g.setColor(Color.WHITE);
		g.drawString("Novo Jogo", larguraDesejada / 3, alturaDesejada / 5);
		// menu
		g.setFont(new Font("Arial", Font.BOLD, 48));

		int alturaDesejadaUMTERCO = alturaDesejada / 2 - 30;
		int larguraDesejadaUMTERCO = larguraDesejada / 3;

		int spacingRows = 90;
		switch (Game.linguagem) {
		case "Inglês":
			for (int i = 0; i < options.length - 1; i++) {
				String text = options[i].getNomeIngles();
				if (i < 3 && GameSaveManager.saveNames[i] != null) {
					text += " (" + GameSaveManager.saveNames[i] + ")";
				}
				g.drawString(text, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
			}
			g.drawString(options[options.length - 1].getNomeIngles(), larguraDesejada - 325, alturaDesejada - 30);
			break;
		case "Português":
			for (int i = 0; i < options.length - 1; i++) {
				String text = options[i].getNomePortugues();
				if (i < 3 && GameSaveManager.saveNames[i] != null) {
					text += " (" + GameSaveManager.saveNames[i] + ")";
				}
				g.drawString(text, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
			}
			g.drawString(options[options.length - 1].getNomePortugues(), larguraDesejada - 325, alturaDesejada - 30);
			break;
		default:
			break;
		}
		int spacingWidth = 60;

		if (currentOption >= options.length - 1) {
			g.drawString(" > ", larguraDesejada - 325 - spacingWidth, alturaDesejada - 30);
		} else {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth,
					alturaDesejadaUMTERCO + spacingRows * currentOption);
		}

	}
}

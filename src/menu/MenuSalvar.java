package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import base.Game;
import base.save.GameSaveManager;
import base.save.Save;

public class MenuSalvar extends Menu {

	public static final Option[] options = { new Option("Slot 1", "Slot 1"), new Option("Slot 2", "Slot 2"),
			new Option("Slot 3", "Slot 3"), new Option("Voltar", "Back") };

	public MenuSalvar() {
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
				GameSaveManager.setSlot(currentOption + 1);
				GameSaveManager.checkAndInitializeSaveLoads();
				MenuSalvarFunctions.OptionPaneLanguage();
				
				if (GameSaveManager.saveNames[currentOption] == null) {
					MenuSalvarFunctions.showInputDialogNewSave(currentOption);
					Game.gameState = "NORMAL";

				} else {
					if (!Game.gameState2.equals("CARREGAR")) {
						MenuSalvarFunctions.showInputDialogNewSaveOverwritten(currentOption);
						
		
					} else {
						Game.previousGameState = Game.gameState;
						Game.gameState = "CARREGAR";
					}
				}
			} else if (options[currentOption].getNomePortugues().equals("Voltar")) {
				currentOption = 0;
				Game.gameState = "MENUPRINCIPAL";
				Game.gameState2 = "MENUPRINCIPAL";
			}
		}
	}

	public void render(Graphics g) {
		int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
		int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0, larguraDesejada, alturaDesejada);
		g.setColor(Color.WHITE);

		// gambiarra pra reutilizar o código
		if (Game.gameState2 == "CARREGAR") {
			g.drawString("Carregar", larguraDesejada / 3, alturaDesejada / 5);
		} else {
			g.drawString("Novo Jogo", larguraDesejada / 3, alturaDesejada / 5);
		}
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
			drawOptionMarker(g, larguraDesejada - 325 - spacingWidth, alturaDesejada - 60);
		} else {
			drawOptionMarker(g, larguraDesejadaUMTERCO - spacingWidth,
					alturaDesejadaUMTERCO + spacingRows * currentOption - 30);
		}

	}
}

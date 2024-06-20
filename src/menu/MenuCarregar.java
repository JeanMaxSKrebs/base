package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import base.Game;
import base.GameSaveManager;

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
				System.out.println("Teste 1");
				Game.gameState = "NORMAL";
				// Aqui você pode chamar um método para carregar o jogo com o save selecionado
//					GameSaveManager.loadPlayerFromSave();
//				}
			} else if (options[currentOption].getNomePortugues().equals("Voltar")) {
				currentOption = 0;
				if (Game.gameState != Game.previousGameState) {
					Game.gameState = Game.previousGameState;
					Game.previousGameState = "CARREGAR";
				}
			}
		}
	}

	public void render(Graphics g) {
		int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
		int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0, larguraDesejada, alturaDesejada);
		g.setColor(Color.WHITE);
		g.drawString("Carregar Jogo", larguraDesejada / 3 - 30, alturaDesejada / 5);

		// menu
		g.setFont(new Font("Arial", Font.BOLD, 40));

		int alturaDesejadaUMTERCO = alturaDesejada / 2;
		int larguraDesejadaUMTERCO = larguraDesejada / 7 ;

		String text = "ERRO";
		String lastAccessText = "ultimoacesso";

		int spacingRows = 80;
		switch (Game.linguagem) {
		case "Inglês":
			text = "Save Selected: " + GameSaveManager.saveNames[GameSaveManager.slot-1];
			g.drawString(text, larguraDesejada / 3 - 30, alturaDesejada / 3);

			for (int i = 0; i < options.length - 1; i++) {
				text = options[i].getNomeIngles();
				lastAccessText = "Last Access: " + GameSaveManager.tempoDesdeUltimoAcesso();
				switch (i) {
				case 0:
					text += " - " + lastAccessText;
					break;
				case 1:
					text += " - " + lastAccessText;
					break;
				case 2:
					text += " - " + lastAccessText;
					break;
				default:
					text += " (Vazio)";
					break;
				}

				g.drawString(text, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
			}
			g.drawString(options[options.length - 1].getNomeIngles(), larguraDesejada - 325, alturaDesejada - 30);
			break;

		case "Português":
			text = "Save Selecionado: " + GameSaveManager.saveNames[GameSaveManager.slot-1];
			g.drawString(text, larguraDesejada / 3 - 40, alturaDesejada / 3);

			for (int i = 0; i < options.length - 1; i++) {
				text = options[i].getNomePortugues();
				lastAccessText = "Último acesso: " + GameSaveManager.tempoDesdeUltimoAcesso();
				switch (i) {
				case 0:
					text += " - " + lastAccessText;
					break;
				case 1:
					text += " - " + lastAccessText;
					break;
				case 2:
					text += " - " + lastAccessText;
					break;
				default:
					text += " (Vazio)";
					break;
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

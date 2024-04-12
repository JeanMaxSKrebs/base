package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import base.Game;
import entities.Player;
import world.World;

public class MenuPause extends Menu {

	public MenuPause() {
		super(new String[] { "Continuar", "Carregar", "Opcoes", "Status", "Voltar" });
	}

	public static boolean saveExists = false;

	public void tick() {
		File file = new File("save.txt");
		if (file.exists())
			saveExists = true;
		else
			saveExists = false;

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
			if (options[currentOption] == "Continuar") {
				Game.gameState = "NORMAL";
			} else if (options[currentOption] == "Carregar") {
				file = new File("save.txt");
				if (file.exists()) {
					String saver = loadGame(0);
					applySave(saver);
				}
			} else if (options[currentOption] == "Opcoes") {
				Game.gameState = "OPTIONS";

			} else if (options[currentOption] == "Status") {
				Game.gameState = "STATUS";

			} else if (options[currentOption] == "Voltar") {
				Game.gameState = "NORMAL";
			}
		}
	}

	public void render(Graphics g) {

		int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
		int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0, larguraDesejada, alturaDesejada);
		g.setColor(Color.WHITE);
		g.drawString("Sobrevivência Jogo", ((larguraDesejada / 4)), (alturaDesejada / 5));

		// menu
		g.setFont(new Font("Arial", Font.BOLD, 40));

		int alturaDesejadaUMTERCO = alturaDesejada / 3;
		int larguraDesejadaUMTERCO = larguraDesejada / 3;

		int spacingRows = 60;

			g.drawString("Continuar", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 1);
			g.drawString("Carregar", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 2);
			g.drawString("Opções", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 3);
			g.drawString("Status do Jogo", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 4);
			g.drawString("Voltar", larguraDesejada - 150, ((alturaDesejada) - 50));

		if (options[currentOption] == "Continuar") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 100, alturaDesejadaUMTERCO + spacingRows * 1);
		} else if (options[currentOption] == "Carregar") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 100, alturaDesejadaUMTERCO + spacingRows * 2);
		} else if (options[currentOption] == "Opcoes") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 100, alturaDesejadaUMTERCO + spacingRows * 3);
		} else if (options[currentOption] == "Status") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 100, alturaDesejadaUMTERCO + spacingRows * 4);
		} else if (options[currentOption] == "Voltar") {
			g.drawString(" > ", larguraDesejada - 150 - 100, alturaDesejada - 50);
		}

	}
}

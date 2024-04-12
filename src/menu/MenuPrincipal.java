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

public class MenuPrincipal extends Menu {

	public MenuPrincipal() {
		super(new String[] { "Novo Jogo", "Carregar", "Opcoes", "Sair" });
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
			if (options[currentOption] == "Novo Jogo") {
				Game.gameState = "NORMAL";
				file = new File("save.txt");
				file.delete();

			} else if (options[currentOption] == "Carregar") {
				file = new File("save.txt");
				if (file.exists()) {
					String saver = loadGame(0);
					applySave(saver);
				}

			} else if (options[currentOption] == "Opcoes") {
				Game.gameState = "OPTIONS";

			} else if (options[currentOption] == "Sair") {
				System.exit(1);

			}
		}
	}

	public void render(Graphics g) {

		int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
		int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0, larguraDesejada, alturaDesejada);
		g.setColor(Color.WHITE);
		g.drawString("Sobrevivência Jogo", larguraDesejada / 4, alturaDesejada / 5);

		// menu
		g.setFont(new Font("Arial", Font.BOLD, 40));

		int alturaDesejadaUMTERCO = alturaDesejada / 3;
		int larguraDesejadaUMTERCO = larguraDesejada / 3;

		int spacingRows = 60;

		g.drawString("Novo Jogo", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 1);
		g.drawString("Carregar Jogo", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 2);
		g.drawString("Opções", larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * 3);
		g.drawString("Sair do Jogo", larguraDesejada - 300, alturaDesejada - 30);

		int spacingWidth = 60;
		
		if (options[currentOption] == "Novo Jogo") {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth, alturaDesejadaUMTERCO + spacingRows * 1);
		} else if (options[currentOption] == "Carregar") {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth, alturaDesejadaUMTERCO + spacingRows * 2);
		} else if (options[currentOption] == "Opcoes") {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth, alturaDesejadaUMTERCO + spacingRows * 3);
		} else if (options[currentOption] == "Sair") {
			g.drawString(" > ", larguraDesejada - 300 - spacingWidth, alturaDesejada - 30);
		}

	}
}

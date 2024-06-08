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

	public static final Option[] options = { new Option("Novo Jogo", "New Game"), new Option("Carregar", "Load Game"),
			new Option("Opções", "Options"), new Option("Sair do Jogo", "Exit Game") };

	public MenuPrincipal() {
		super(options);
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
			if (options[currentOption].getNomePortugues() == "Novo Jogo") {
				Game.gameState = "NORMAL";
				file = new File("save.txt");
				file.delete();

			} else if (options[currentOption].getNomePortugues() == "Carregar") {
				file = new File("save.txt");
				if (file.exists()) {
					String saver = loadGame(0);
					applySave(saver);
				}

			} else if (options[currentOption].getNomePortugues() == "Opções") {
				Game.gameState = "OPTIONS";
				Game.previousGameState = "MENUPRINCIPAL";

			} else if (options[currentOption].getNomePortugues() == "Sair") {
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
		g.setFont(new Font("Arial", Font.BOLD, 48));

		int alturaDesejadaUMTERCO = alturaDesejada / 2 - 30;
		int larguraDesejadaUMTERCO = larguraDesejada / 3;

		int spacingRows = 90;
		switch (Game.linguagem) {

		case "Inglês":
			for (int i = 0; i < options.length - 1; i++) {
				g.drawString(options[i].getNomeIngles(), larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
			}
			
			g.drawString(options[options.length-1].getNomeIngles(), larguraDesejada - 275, alturaDesejada - 30);
			break;

		case "Português":
			for (int i = 0; i < options.length - 1; i++) {
				g.drawString(options[i].getNomePortugues(), larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
			}
			
			g.drawString(options[options.length-1].getNomePortugues(), larguraDesejada - 325, alturaDesejada - 30);
			break;
		default:
			break;
		}
		int spacingWidth = 60;

		if (options[currentOption].getNomePortugues() == "Novo Jogo") {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth, alturaDesejadaUMTERCO + spacingRows * 0);
		} else if (options[currentOption].getNomePortugues() == "Carregar") {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth, alturaDesejadaUMTERCO + spacingRows * 1);
		} else if (options[currentOption].getNomePortugues() == "Opções") {
			g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth, alturaDesejadaUMTERCO + spacingRows * 2);
		} else if (options[currentOption].getNomePortugues() == "Sair do Jogo") {
			g.drawString(" > ", larguraDesejada - 325 - spacingWidth, alturaDesejada - 30);
		}

	}
}

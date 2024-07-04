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
import base.save.GameSaveManager;
import entities.Player;
import world.World;

public class MenuPause extends Menu {
//	new Option("Salvar", "Save Game"),
	public static final Option[] options = { new Option("Continuar", "Continue"), new Option("Carregar", "Load Game"),
		 new Option("Opções", "Options"), new Option("Status", "Status"),
			new Option("Voltar ao Menu Principal", "Go Back to Main Menu") };

	public MenuPause() {
		super(options);
	}

	public static int currentOption = 0;

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

			if (options[currentOption].getNomePortugues() == "Continuar") {
				Game.previousGameState = Game.gameState;
				Game.gameState = "NORMAL";
			} else if (options[currentOption].getNomePortugues() == "Carregar") {
				Game.previousGameState = Game.gameState;
				Game.gameState = "CARREGAR";
				GameSaveManager.checkAndInitializeSaveLoads();
				
			} else if (options[currentOption].getNomePortugues() == "Opções") {
				Game.previousGameState = Game.gameState;
				Game.gameState = "OPTIONS";

			} else if (options[currentOption].getNomePortugues() == "Status") {
				Game.previousGameState = Game.gameState;
				Game.gameState = "STATUS";

			} else if (options[currentOption].getNomePortugues() == "Voltar ao Menu Principal") {
				currentOption = 0;
				Game.previousGameState = "MENUPRINCIPAL";
				Game.gameState = "MENUPRINCIPAL";
			}
		}
	}
	public static void currentOptionto0() {
		currentOption = 0;
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
		g.drawString("Voltar ao Menu Principal", larguraDesejada/2+60, ((alturaDesejada) - 30));

		if (options[currentOption].getNomePortugues() == "Continuar") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 50, alturaDesejadaUMTERCO + spacingRows * 1);
		} else if (options[currentOption].getNomePortugues() == "Carregar") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 50, alturaDesejadaUMTERCO + spacingRows * 2);
		} else if (options[currentOption].getNomePortugues() == "Opções") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 50, alturaDesejadaUMTERCO + spacingRows * 3);
		} else if (options[currentOption].getNomePortugues() == "Status") {
			g.drawString(" > ", larguraDesejadaUMTERCO - 50, alturaDesejadaUMTERCO + spacingRows * 4);
		} else if (options[currentOption].getNomePortugues() == "Voltar ao Menu Principal") {
			g.drawString(" > ", larguraDesejada/2+60-50, ((alturaDesejada) - 30));
		}

	}
}

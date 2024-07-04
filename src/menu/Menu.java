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
import java.time.LocalDateTime;

import base.Game;
import base.save.GameSaveManager;
import entities.Player;
import world.World;

public abstract class Menu {

	protected Option[] options;
	protected int currentOption = 0;
	protected int maxOption;

	public boolean up;
	public boolean down;
	public boolean enter;

	public Menu(Option[] options) {
		this.options = options;
		this.currentOption = 0;
		this.maxOption = options.length - 1;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	protected static String capitalizeFirstLetter(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
	
	// Método para desenhar o marcador de opção (símbolo '>' com retângulo atrás)
	protected void drawOptionMarker(Graphics g, int x, int y) {
	    // Desenha o retângulo atrás do símbolo '>'
	    int rectX = x ;
	    int rectY = y ;
	    int rectWidth = 30;
	    int rectHeight = 30;
	    g.setColor(new Color(220, 220, 220)); // Cor do retângulo
	    g.fillRect(rectX, rectY, rectWidth, rectHeight);

	}
}

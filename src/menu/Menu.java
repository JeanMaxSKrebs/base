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
import base.GameSaveManager;
import entities.Player;
import world.World;

public abstract class Menu {

	protected Option[] options;
	protected int currentOption;
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
}

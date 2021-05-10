package base;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import world.World;

public abstract class Menu {
	
	public String[] options = {"novo jogo", "carregar", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;

	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;

	public abstract void tick();
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
				case "fase": {
					World.restartGame("fase"+spl2[1]+".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
				}
				default:
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("Save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						 String[] trans = singleLine.split(":");
						 char[] val = trans[1].toCharArray();
						 trans[1] = "";
						 for (int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						 line += trans[0];
						 line += ":";
						 line += trans[1];
						 line += "/";
					}
				} catch (IOException e) {}
			} catch (FileNotFoundException e) {}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		System.out.println("salvou");
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for (int j = 0; j < value.length; j++) {
				value[j] += encode;
				current += value[j];
			}
			try {
				write.write(current);
				if(i < val1.length - 1)
					write.newLine();
				
			} catch (IOException e) {}
		}
		try {
			write.flush();
			write.close();
		} catch (IOException e) {}
	}
	
	public abstract void render(Graphics g);
}

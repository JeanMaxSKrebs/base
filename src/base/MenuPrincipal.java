package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

public class MenuPrincipal extends Menu {
	
	public String[] options = {"novo jogo", "carregar", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;

	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;

	public void tick() {
		File file = new File("save.txt");
		if(file.exists())
			saveExists = true;
		else
			saveExists = false; 
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "novo jogo") {
				Game.gameState = "MENU_PERSONAGEM";
				file = new File("save.txt");
				file.delete();
			} else if(options[currentOption] == "carregar") {
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}
			} else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		
		int valorwidth = 3;
		int valorheigth = 3;
		int width = Game.WIDTH * Game.SCALE / valorwidth;
		int height = Game.HEIGHT * Game.SCALE / valorheigth;
		int multi = Game.HEIGHT*Game.SCALE/10;

		g.setFont(new Font("Arial", Font.BOLD, 48));
		
		g.fillRect(0, 0,Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.WHITE);
		g.drawString("Nome do Jogo",  (width-multi), (height-multi));

		g.setFont(new Font("Arial", Font.BOLD, 36));
		
		if(pause == false)
			g.drawString("Novo Jogo",  width, (height+multi));
		else 
			g.drawString("Continuar",  width, (height+multi));

		g.drawString("Carregar Jogo",  width, (height+multi*2));
		
		g.drawString("Sair",  width, (height+multi*3));
		
		if(options[currentOption] == "novo jogo") {
			g.drawString(" > ",  (width-50), (height+multi));
		} else if(options[currentOption] == "carregar") {
			g.drawString(" > ",  (width-50), (height+multi*2));
		} else if(options[currentOption] == "sair") {
			g.drawString(" > ",  (width-50), (height+multi*3));
		}

	}
}
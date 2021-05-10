package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

public class MenuPersonagem extends Menu {
	
	public String[] options = {"usar", "criar", "voltar"};
	
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
			if(options[currentOption] == "usar") {
				Game.gameState = "MENU_ESCOLHA";
			} else if(options[currentOption] == "criar") {
				Game.gameState = "MENU_CRIACAO";
			} else if(options[currentOption] == "voltar") {
				Game.gameState = "MENU_PRINCIPAL";
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
		g.drawString("Personagem", (width-multi), (height-multi));

		g.setFont(new Font("Arial", Font.BOLD, 32));
		if(pause == false)
			g.drawString("Carregar", width, (height+multi));
		else 
			g.drawString("Carregar Personagem",  width, (height+multi*2));
		
		if(pause == false)
			g.drawString("Criar",  width, (height+multi*2));
		else 
			g.drawString("Continuar",  width, (height+multi));
		
		g.drawString("Voltar",  width, (height+multi*3));

		if(options[currentOption] == "usar") {
			g.drawString(" > ",  (width-50), (height+multi));
		} else if(options[currentOption] == "criar") {
			g.drawString(" > ",  (width-50), (height+multi*2));
		} else if(options[currentOption] == "voltar") {
			g.drawString(" > ",  (width-50), (height+multi*3));
		}
	}
}

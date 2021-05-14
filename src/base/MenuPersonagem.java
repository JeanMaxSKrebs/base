package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuPersonagem extends Menu {
	
	public String[] options = {"usar", "criar", "voltar"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
		
	public void tick() {
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
//				Game.gameState = "MENU_ESCOLHA";
			} else if(options[currentOption] == "criar") {
				Game.cutsceneState = "nascimento";
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
		
		g.drawString("Carregar", width, (height+multi));
		
		g.drawString("Criar",  width, (height+multi*2));
		
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

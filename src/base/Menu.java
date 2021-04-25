package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String[] options = {"novo jogo", "carregar", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;

	public boolean pause = false;
	
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
			if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar" ) {
				Game.gameState = "NORMAL";
				pause = false;
			} else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.fillRect(0, 0,Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.WHITE);
		g.drawString("O PATO",  ((Game.WIDTH*Game.SCALE/3)), (Game.HEIGHT*Game.SCALE/5));

		
		// menu 
		g.setFont(new Font("Arial", Font.BOLD, 36));
		
		if(pause == false)
			g.drawString("Novo Jogo",  ((Game.WIDTH*Game.SCALE/3)), ((Game.HEIGHT*Game.SCALE/3)+50));
		else 
			g.drawString("Continuar",  ((Game.WIDTH*Game.SCALE/3)), ((Game.HEIGHT*Game.SCALE/3)+50));

		g.drawString("Carregar Jogo",  ((Game.WIDTH*Game.SCALE/3)), ((Game.HEIGHT*Game.SCALE/3)+100));
		g.drawString("Sair",  ((Game.WIDTH*Game.SCALE/3)), ((Game.HEIGHT*Game.SCALE/3)+150));
		
		if(options[currentOption] == "novo jogo") {
			g.drawString(" > ",  (((Game.WIDTH*Game.SCALE/3)-50)), ((Game.HEIGHT*Game.SCALE/3)+50));
		} else if(options[currentOption] == "carregar") {
			g.drawString(" > ",  (((Game.WIDTH*Game.SCALE/3)-50)), ((Game.HEIGHT*Game.SCALE/3)+100));
		} else if(options[currentOption] == "sair") {
			g.drawString(" > ",  (((Game.WIDTH*Game.SCALE/3)-50)), ((Game.HEIGHT*Game.SCALE/3)+150));
		}

	}
}

package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import base.Game;
import tempo.DiaDaSemana;
import tempo.Tempo;

public class Options extends Menu {
	public static final Option[] options = { new Option("Configuração do FPS", "FPS Setting"),
			new Option("Outras Configurações", "Other Settings"), new Option("Outros", "Others"),
			new Option("Voltar", "go back") };

	public Options() {
		super(options);
	}

	private boolean verHorario = false;
	private boolean verFPS = false;

	public void tick() {

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
			if (options[currentOption].getNomePortugues() == "Configuração do FPS") {
				verFPS = true;
			} else if (options[currentOption].getNomePortugues() == "Outras Configurações") {
			} else if (options[currentOption].getNomePortugues() == "Outros") {
			} else if (options[currentOption].getNomePortugues() == "Voltar") {
				currentOption = 0;
				String inverte = Game.gameState;

				Game.gameState = Game.previousGameState;
				// Y / Y 
				Game.previousGameState = inverte;
			}
		}

	}

	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 64));
		int widthBase = Game.getWIDTH() * Game.getSCALE();
		int heightBase = Game.getHEIGHT() * Game.getSCALE();

		g.fillRect(0, 0, widthBase, heightBase);
		g.setColor(Color.WHITE);
		
//		desenhar quadro cinza e >
		
		if (options[currentOption].getNomePortugues() != "Voltar") {
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString(" > ", (((widthBase / 10) - 50)), ((heightBase / 4) + (currentOption*100)+50));

			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.setColor(Color.gray);
			g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);
			g.setColor(Color.WHITE);

		} else {
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString(" > ", (((widthBase - 225))), ((heightBase) - 50));
		}

		switch (Game.linguagem) {
		case "Inglês":
			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.drawString("Options", ((widthBase / 12)), (heightBase / 6));

			g.setFont(new Font("Arial", Font.BOLD, 40));
			for (int i = 0; i < options.length-1; i++) {
				g.drawString(options[i].getNomeIngles(), ((widthBase / 10)), ((heightBase / 4) + ((i*100)+50)));//0 50 //1 100
			}
			g.drawString(options[options.length-1].getNomeIngles(), ((widthBase - 175)), ((heightBase) - 50));
			
			if (options[currentOption].getNomePortugues() == "Configuração do FPS") {
				int x = (widthBase / 2 + 100); // Center horizontally based on string width
				int y = (heightBase / 3);
				
				String FPS = "FPS";
				
				String combinedString = FPS + ":" + Game.FPS;
				
				g.drawString(combinedString, x, y);

			}  else if (options[currentOption].getNomePortugues() == "Outras Configurações") {

			} else if (options[currentOption].getNomePortugues() == "Outros") {
			} else if (options[currentOption].getNomePortugues() == "Voltar") {

			}
			break;
		case "Português":
			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.drawString("Opções", ((widthBase / 12)), (heightBase / 6));

			g.setFont(new Font("Arial", Font.BOLD, 40));
			for (int i = 0; i < options.length-1; i++) {
				g.drawString(options[i].getNomePortugues(), ((widthBase / 10)), ((heightBase / 4) + ((i*100)+50)));//0 50 //1 100
			}
			g.drawString(options[options.length-1].getNomePortugues(), ((widthBase - 175)), ((heightBase) - 50));

			if (options[currentOption].getNomePortugues() == "Configuração do FPS") {
				int x = (widthBase / 2 + 25); // Center horizontally based on string width
				int y = (heightBase / 3);
				
				String FPS = "FPS";
				
				String combinedString = FPS + ":" + Game.FPS;
				
				g.drawString(combinedString, x, y);

			}  else if (options[currentOption].getNomePortugues() == "Outras Configurações") {

			} else if (options[currentOption].getNomePortugues() == "Outros") {
			} else if (options[currentOption].getNomePortugues() == "Voltar") {

			}
			break;
		default:
			break;
		}

		

	}

}

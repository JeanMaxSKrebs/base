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
		super(new String[] { "Novo Jogo", "Carregar", "Opcoes", "Sair" });
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
				Game.gameState = "MENUPAUSE";
			}
		}

	}

	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 64));
		int widthBase = Game.getWIDTH() * Game.getSCALE();
		int heightBase = Game.getHEIGHT() * Game.getSCALE();

		g.fillRect(0, 0, widthBase, heightBase);
		g.setColor(Color.WHITE);
		Option option = new Option();
		g.drawString(option.getNomePortugues(), ((widthBase / 8)), (heightBase / 6));

		// menu
		g.setFont(new Font("Arial", Font.BOLD, 48));

		g.drawString("Ver Horário", ((widthBase / 6)), ((heightBase / 4) + 50));
		g.drawString("Outros Status", ((widthBase / 6)), ((heightBase / 4) + 150));
		g.drawString("Outros", ((widthBase / 6)), ((heightBase / 4) + 250));
		g.drawString("Outros", ((widthBase / 6)), ((heightBase / 4) + 250));

		g.drawString("Voltar", ((widthBase - 175)), ((heightBase) - 50));
//		desenhar >

		if (options[currentOption].getNomePortugues() != "voltar") {
			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.setColor(Color.gray);
			g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);
			g.setColor(Color.WHITE);

		}

		if (options[currentOption].getNomePortugues() == "ver horario") {
			g.drawString(" > ", (((widthBase / 6) - 100)), ((heightBase / 4) + 50));
		} else if (options[currentOption].getNomePortugues() == "outros status") {
			g.drawString(" > ", (((widthBase / 6) - 100)), ((heightBase / 4) + 150));
		} else if (options[currentOption].getNomePortugues() == "outros") {
			g.drawString(" > ", (((widthBase / 6) - 100)), ((heightBase / 4) + 250));
		} else if (options[currentOption].getNomePortugues() == "voltar") {
			g.drawString(" > ", (((widthBase - 225))), ((heightBase) - 50));
		}
		if (options[currentOption].getNomePortugues() == "ver horario") {
			// Format time with leading zeros
			String formattedTime = String.format("%02d:%02d", Tempo.hours, Tempo.minutes);

			// Format date in a user-friendly way (modify format as desired)
			String formattedDate = String.format("%02d/%02d/%02d", Tempo.days, Tempo.months, Tempo.years); // Adjust
																											// format
			// (e.g., MMM yyyy
			// Combine formatted time and date
			String combinedString = formattedTime + "  " + formattedDate; // Newline for separation

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 48)); // Adjust font size for combined string
//			g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);

			int x = (widthBase / 2 + 100); // Center horizontally based on string width
			int y = (heightBase / 3);
			// vertically
			// based on font
			// metrics
			g.drawString(combinedString, x, y);
			String diaDaSemana = "Dia de Deus";

			if (Game.linguagem == "Inglês") {
				diaDaSemana = Tempo.DIAS_DA_SEMANA[Tempo.restoDia].getNomePortugues();
			} else if (Game.linguagem == "Português") {
				diaDaSemana = Tempo.DIAS_DA_SEMANA[Tempo.restoDia].getNomePortugues();
			}
			g.drawString(diaDaSemana, x, y + 100);

			String faseDaLua = "Lua do Diabo";

			if (Game.linguagem == "Inglês") {
				faseDaLua = Tempo.FASES_DA_LUA[Tempo.restoLua].getNomeIngles();

			} else if (Game.linguagem == "Português") {
				faseDaLua = Tempo.FASES_DA_LUA[Tempo.restoLua].getNomePortugues();
			}

			g.drawString(faseDaLua, x, y + 200);

		} else if (options[currentOption].getNomePortugues() == "outros status") {

		} else if (options[currentOption].getNomePortugues() == "voltar") {

		}

	}

}

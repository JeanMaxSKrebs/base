package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import tempo.Tempo;

public class Status {
	public String[] options = { "ver horario", "outros status", "outros", "voltar" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;

	private boolean verHorario = false;

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
			if (options[currentOption] == "ver horario") {
				verHorario = true;
			} else if (options[currentOption] == "voltar") {
				Game.gameState = "MENU";
				;
			}
		}
	}

	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 64));
		int widthBase = Game.getWIDTH() * Game.getSCALE();
		int heightBase = Game.getHEIGHT() * Game.getSCALE();
		g.fillRect(0, 0, widthBase, heightBase);
		g.setColor(Color.WHITE);
		g.drawString("Status", ((widthBase / 8)), (heightBase / 6));

		// menu
		g.setFont(new Font("Arial", Font.BOLD, 48));

		g.drawString("Ver Horário", ((widthBase / 6)), ((heightBase / 4) + 50));
		g.drawString("Outros Status", ((widthBase / 6)), ((heightBase / 4) + 150));
		g.drawString("Outros", ((widthBase / 6)), ((heightBase / 4) + 250));
		g.drawString("Outros", ((widthBase / 6)), ((heightBase / 4) + 250));

		g.drawString("Voltar", ((widthBase - 175)), ((heightBase) - 50));
//		desenhar >

		if (options[currentOption] != "voltar") {
			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.setColor(Color.gray);
			g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);
			g.setColor(Color.WHITE);

		}

		if (options[currentOption] == "ver horario") {
			g.drawString(" > ", (((widthBase / 6) - 100)), ((heightBase / 4) + 50));
		} else if (options[currentOption] == "outros status") {
			g.drawString(" > ", (((widthBase / 6) - 100)), ((heightBase / 4) + 150));
		} else if (options[currentOption] == "outros") {
			g.drawString(" > ", (((widthBase / 6) - 100)), ((heightBase / 4) + 250));
		} else if (options[currentOption] == "voltar") {
			g.drawString(" > ", (((widthBase - 225))), ((heightBase) - 50));
		}
		if (options[currentOption] == "ver horario") {
			// Get current date and time
			Date now = new Date();

			// Format time with leading zeros
			String formattedTime = String.format("%02d:%02d", Tempo.hours, Tempo.minutes);

			// Format date in a user-friendly way (modify format as desired)
			String formattedDate = String.format("%02d/%02d/%02d", Tempo.days, Tempo.months, Tempo.years); // Adjust format
			// (e.g., MMM yyyy
			// Combine formatted time and date
			String combinedString = formattedTime + "  " + formattedDate; // Newline for separation

			// rectangle
			// positioning
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 48)); // Adjust font size for combined string
//			g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);

			// Center the combined string within the rectangle
			int stringWidth = g.getFontMetrics().stringWidth(combinedString);
			int x = (widthBase / 2 + 100); // Center horizontally based on string width
			int y = (heightBase / 3);
			// vertically
			// based on font
			// metrics
			g.drawString(combinedString, x, y);
			String diaDaSemana = "Dia de Deus";

			int resto = Tempo.days % 7;
			if (Game.linguagem == "Inglês") {
				diaDaSemana = Tempo.DIAS_DA_SEMANA[resto].getNomePortugues();
			} else if (Game.linguagem == "Português") {
				diaDaSemana = Tempo.DIAS_DA_SEMANA[resto].getNomePortugues();
			}
			g.drawString(diaDaSemana, x, y + 100);

		} else if (options[currentOption] == "outros status") {

		} else if (options[currentOption] == "voltar") {

		}

	}

}

package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.Game;
import tempo.Tempo;

public class Status extends Menu {

	public static final Option[] options = { new Option("Ver Horário", "See Schedule"),
			new Option("Outros Status", "Other Status"), new Option("Outros", "Others"),
			new Option("Voltar", "Go Back") };

	public Status() {
		super(options);
	}

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
			if (options[currentOption].getNomePortugues() == "Ver Horário") {
				verHorario = true;
				Tempo.addHoras();
			} else if (options[currentOption].getNomePortugues() == "Outros Status") {
			} else if (options[currentOption].getNomePortugues() == "Outros") {
			} else if (options[currentOption].getNomePortugues() == "Voltar") {
				currentOption = 0;

				Game.previousGameState = Game.gameState;
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

		switch (Game.linguagem)

		{
		case "Inglês":
			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.drawString("Status", ((widthBase / 8)), (heightBase / 6));

			g.setFont(new Font("Arial", Font.BOLD, 40));
			for (int i = 0; i < options.length - 1; i++) {
				g.drawString(options[i].getNomeIngles(), ((widthBase / 10)), ((heightBase / 4) + ((i * 100) + 50)));// 0
																													// 50
																													// //1
																													// 100
			}
			g.drawString(options[options.length - 1].getNomeIngles(), ((widthBase - 175)), ((heightBase) - 50));

//			desenhar >

			if (options[currentOption].getNomePortugues() != "Voltar") {
				g.setFont(new Font("Arial", Font.BOLD, 40));
				g.drawString(" > ", (((widthBase / 6) - 50)), ((heightBase / 4) + (currentOption * 100) + 50));

				g.setFont(new Font("Arial", Font.BOLD, 64));
				g.setColor(Color.gray);
				g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);
				g.setColor(Color.WHITE);
			} else {
				g.setFont(new Font("Arial", Font.BOLD, 40));
				g.drawString(" > ", (((widthBase - 225))), ((heightBase) - 50));
			}

			if (options[currentOption].getNomePortugues() == "Ver Horário") {
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
//				g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);

				int x = (widthBase / 2 + 100); // Center horizontally based on string width
				int y = (heightBase / 3);
				// vertically
				// based on font
				// metrics
				g.drawString(combinedString, x, y);
				String diaDaSemana = "God's Day";

				diaDaSemana = Tempo.DIAS_DA_SEMANA[Tempo.restoDia].getNomeIngles();

				g.drawString(diaDaSemana, x, y + 100);

				String faseDaLua = "Devil's Moon";

				faseDaLua = Tempo.FASES_DA_LUA[Tempo.restoLua].getNomeIngles();

				g.drawString(faseDaLua, x, y + 200);

			} else if (options[currentOption].getNomePortugues() == "Outros Status") {

			} else if (options[currentOption].getNomePortugues() == "Voltar") {

			}

			break;
		case "Português":
			g.setFont(new Font("Arial", Font.BOLD, 64));
			g.drawString("Status", ((widthBase / 8)), (heightBase / 6));

			g.setFont(new Font("Arial", Font.BOLD, 40));
			for (int i = 0; i < options.length - 1; i++) {
				g.drawString(options[i].getNomePortugues(), ((widthBase / 10)), ((heightBase / 4) + ((i * 100) + 50)));
			}
			g.drawString(options[options.length - 1].getNomePortugues(), ((widthBase - 175)), ((heightBase) - 50));

//			desenhar >

			if (options[currentOption].getNomePortugues() != "Voltar") {
				g.setFont(new Font("Arial", Font.BOLD, 40));
				g.drawString(" > ", (((widthBase / 10) - 50)), ((heightBase / 4) + (currentOption * 100) + 50));

				g.setFont(new Font("Arial", Font.BOLD, 64));
				g.setColor(Color.gray);
				g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);
				g.setColor(Color.WHITE);
			} else {
				g.setFont(new Font("Arial", Font.BOLD, 40));
				g.drawString(" > ", (((widthBase - 225))), ((heightBase) - 50));
			}

			if (options[currentOption].getNomePortugues() == "Ver Horário") {
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
//				g.fillRect((widthBase / 2), ((heightBase) / 4), widthBase / 2 - 30, heightBase / 2 + 30);

				int x = (widthBase / 2 + 100);
				int y = (heightBase / 3);

				g.drawString(combinedString, x, y);
				String diaDaSemana = "Dia de Deus";

				diaDaSemana = Tempo.DIAS_DA_SEMANA[Tempo.restoDia].getNomePortugues();

				g.drawString(diaDaSemana, x, y + 100);

				String faseDaLua = "Lua do Diabo";

				faseDaLua = Tempo.FASES_DA_LUA[Tempo.restoLua].getNomePortugues();

				g.drawString(faseDaLua, x, y + 200);

			} else if (options[currentOption].getNomePortugues() == "Outros Status") {

			} else if (options[currentOption].getNomePortugues() == "Voltar") {

			}

			break;
		default:
			break;
		}
	}
}

package tempo;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.Game;

public class Tempo {

	// mudar iluminação
	public static int offset = 1; // Ajusta o valor inicial de dias, meses e anos para 1
	public static long timeStart; // Total time in seconds since game start
	public static long timeElapsedSeconds; // Total time in seconds since game start
	private int secondsInGameMinute = 1 * 60 * 24; // Adjust this to control in-game time progression
	public static long minutes;
	public static int hours;
	public static int days = 1;
	public static int months = 1;
	public static int years = 1;

	int resto = days % 7;
	DiaDaSemana diaDaSemana = DIAS_DA_SEMANA[resto];

	public static final DiaDaSemana[] DIAS_DA_SEMANA = { new DiaDaSemana("Segunda-feira", "Monday"),
			new DiaDaSemana("Terça-feira", "Tuesday"), new DiaDaSemana("Quarta-feira", "Wednesday"),
			new DiaDaSemana("Quinta-feira", "Thursday"), new DiaDaSemana("Sexta-feira", "Friday"),
			new DiaDaSemana("Sábado", "Saturday"), new DiaDaSemana("Domingo", "Sunday"), };

	public static int previousMonth;
	public static int previousYear;

	private Color baseLightColor = Color.WHITE; // Base ambient light color
	private Color nightLightColor = new Color(30, 30, 30); // Night-time light color
	private float lightIntensity = 1.0f; // Ambient light intensity (0.0 - 1.0)

	private UnidadeTempo unidadeTempo;
	private String formatoData;

	public void Tempo() {
		this.timeStart = 0;
		this.timeElapsedSeconds = 0;
		this.secondsInGameMinute = 1 * 60 * 24;
		;
		this.minutes = 0;
		this.hours = 0;
		this.days = offset;
		this.months = offset;
		this.years = offset;
		this.resto = 0;
		this.unidadeTempo = UnidadeTempo.minutes;
		this.formatoData = "HH:mm:ss";
	}

	public void tick() {
//		// Converte para a unidade de tempo desejada
//		switch (unidadeTempo) {
//		case SEGUNDOS:
//			break;
//		case MINUTOS:
//			tempoDecorridoNoFrame /= 60000;
//			break;
//		case HORAS:
//			tempoDecorridoNoFrame /= 3600000;
//			break;
//		}
		

		long currentTime = System.currentTimeMillis();
		long deltaTime = currentTime - timeStart;

		
		
		if (deltaTime > 0) {
			// Calcula minutos no jogo, considerando a velocidade ajustada
			minutes = (int) (deltaTime / (24 * 60) * secondsInGameMinute);
			// Calcula hora no jogo, considerando a velocidade ajustada
			hours = (int) (minutes / 60.0);

			days = (int) (hours / 24) + offset;

			// Calcula anos (considerando 12 meses por ano)
			years = (int) (months / 12) + offset;


			if (days == 0) {
				days = 1;
			}
			if (months == 0) {
				months = 1;
			}
			if (years == 0) {
				years = 1;
			}
			if (days >= 31) {
				days = 1;
			}
			if (months == 0) {
				months = 1;
			}
			if (years == 0) {
				years = 1;
			}


			previousMonth = months;
			previousYear = years;

		} else {
			minutes = 0; // Define as horas como 0 se deltaTime for menor ou igual a 0
			hours = 0; // Define as horas como 0 se deltaTime for menor ou igual a 0
			days = offset;
			months = offset;
			years = offset;
		}

		// Adjust light intensity based on time
		if (minutes >= (12 * 60) && minutes < (18 * 60)) { // Evening
			lightIntensity = 0.75f;
		} else if (minutes >= (18 * 60)) { // Night
			lightIntensity = 0.25f;
		} else { // Daytime
			lightIntensity = 1.0f;
		}

		// Fazer eventos específicos acontecerem em determinados momentos
		if (minutes == 10) {
			// Fazer algo específico
		}
	}

	public void render() {
		// Exibe o tempo total decorrido
		String tempoTotalStr = formatarTempo(timeElapsedSeconds);
		System.out.println("Tempo total: " + tempoTotalStr);

		// Exibe o tempo decorrido no último frame (opcional)
		// System.out.println("Tempo decorrido no frame: " + tempoDecorridoNoFrame + " "
		// + unidadeTempo.toString());
	}

	private String formatarTempo(long tempo) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatoData);
		return sdf.format(new Date(tempo));
	}

	public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
		this.unidadeTempo = unidadeTempo;
	}

	public void setFormatoData(String formatoData) {
		this.formatoData = formatoData;
	}

	public enum UnidadeTempo {
		minutes, hours, days, months, years
	}
}

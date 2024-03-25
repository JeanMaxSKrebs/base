package tempo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.Game;
import entities.Bala;
import graficos.UI;

public class Tempo {

	// mudar iluminação
	public static int offset = 1; // Ajusta o valor inicial de dias, meses e anos para 1
	public static long timeStart; // Total time in seconds since game start
	public static long timeElapsedSeconds; // Total time in seconds since game start
	private int secondsInGameMinute = 1; // Adjust this to control in-game time progression
	public static long minutes;
	public static int hours;
	public static int days = 1;
	public static int months = 1;
	public static int years = 1;

	public static int restoDia = 0;
	DiaDaSemana diaDaSemana = DIAS_DA_SEMANA[restoDia];

	public static final DiaDaSemana[] DIAS_DA_SEMANA = { new DiaDaSemana("Sábado", "Saturday"),
			new DiaDaSemana("Domingo", "Sunday"), new DiaDaSemana("Segunda-feira", "Monday"),
			new DiaDaSemana("Terça-feira", "Tuesday"), new DiaDaSemana("Quarta-feira", "Wednesday"),
			new DiaDaSemana("Quinta-feira", "Thursday"), new DiaDaSemana("Sexta-feira", "Friday"), };

	
	public static int weeksTotal; // Calculate total weeks
	public static int moonPhase;

	public static int restoLua = 0;
    private static int lastRestoLua = -1;

	FaseDaLua faseDaLua = FASES_DA_LUA[restoLua];

	public static final  FaseDaLua[] FASES_DA_LUA = { new FaseDaLua(1),
			new FaseDaLua(2), new FaseDaLua(3),
			new FaseDaLua(4), };

	public static boolean show = true;;

	private Color baseLightColor = Color.WHITE; // Base ambient light color
	private Color nightLightColor = new Color(30, 30, 30); // Night-time light color
	private float lightIntensity = 1.0f; // Ambient light intensity (0.0 - 1.0)

	private UnidadeTempo unidadeTempo;
	private String formatoData;

	public void Tempo() {
		Tempo.timeStart = 0;
		Tempo.timeElapsedSeconds = 0;
		this.secondsInGameMinute = 1;
		;
		Tempo.minutes = 0;
		Tempo.hours = 0;
		Tempo.days = offset;
		Tempo.months = offset;
		Tempo.years = offset;
		Tempo.restoDia = 0;
		Tempo.restoLua = 0;
		Tempo.lastRestoLua = -1;
		Tempo.weeksTotal = 0;
		Tempo.moonPhase = 0;
		this.unidadeTempo = UnidadeTempo.minutes;
		this.formatoData = "HH:mm:ss";
	}

	public void tick() {

//		timeElapsedSeconds+=500;
		timeElapsedSeconds++;
		restoDia = days % 7;
				
		if (timeElapsedSeconds > 0) {

			// Calcula minutos no jogo, considerando a velocidade ajustada
			int offsetmin = 50;
			int offsethour = 23 * 60;
			int offsetday = 60 * 60 * 24;
			int offsetmonth = 60 * 60 * 24 * 28;
//			minutes = (int) (((timeElapsedSeconds / 60) * secondsInGameMinute) + offsetmin + offsethour + offsetday);
			minutes = (int) (((timeElapsedSeconds / 60) * secondsInGameMinute));
			// Calcula hora no jogo, considerando a velocidade ajustada
			hours = (int) (minutes / 60);

			days = (int) (hours / 24) + offset;

			months = (int) (days / 29) + offset;
			// Calcula anos (considerando 12 meses por ano)
			years = (int) (months / 13) + offset;

			weeksTotal = hours / (24 * 7); // Calculate total weeks
			
			restoLua = weeksTotal % 4;

			 if (restoLua != lastRestoLua) {
			        // Atualizar lastRestoLua para o novo valor
			        lastRestoLua = restoLua;

			        // Executar ação quando restoLua mudar
			        Game.ui.mensagem = true;
			        Game.messageDisplayStartTime = System.currentTimeMillis(); // Inicia a contagem do tempo de exibição da mensagem
			    }
			
			// Limit values
			minutes %= 60;
			hours %= 24;
			days %= 29;
			months %= 13;

			if (days == 0) {
				timeElapsedSeconds = timeElapsedSeconds + offsetday;
			}
			if (months == 0) {
				timeElapsedSeconds = timeElapsedSeconds + offsetmonth;
			}
			if (years == 0) {
				years = 1;
			}
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

	public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
		this.unidadeTempo = unidadeTempo;
	}

	public void setFormatoData(String formatoData) {
		this.formatoData = formatoData;
	}

	public enum UnidadeTempo {
		minutes, hours, days, months, years
	}

	public static void addHoras() {
		int offsetmin = 60;
		int offsethour = 60 * 60;
		int offsetday = 1 * 60 * 60 * 24;
		// TODO Auto-generated method stub
		timeElapsedSeconds = timeElapsedSeconds + offsetday;
	}
}

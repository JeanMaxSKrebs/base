package tempo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import base.Game;
import entities.Bala;
import graficos.UI;

public class Tempo {

	// mudar iluminação
	public static int offset = 1; // Ajusta o valor inicial de dias, meses e anos para 1
	public static long timeStart; // Total time in seconds since game start
	public static long timeElapsedSeconds; // Total time in seconds since game start
	private int secondsInGameMinute = 1; // Adjust this to control in-game time progression
	public static int minutes;
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

	public static final FaseDaLua[] FASES_DA_LUA = { new FaseDaLua(1), new FaseDaLua(2), new FaseDaLua(3),
			new FaseDaLua(4), };

	public static boolean show = true;;

	private Color baseLightColor = Color.WHITE; // Base ambient light color
	private Color nightLightColor = new Color(30, 30, 30); // Night-time light color
	private float lightIntensity = 1.0f; // Ambient light intensity (0.0 - 1.0)

	private UnidadeTempo unidadeTempo;
	private String formatoData;

	public Tempo() {
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
		this.unidadeTempo = UnidadeTempo.MINUTOS;
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
				Game.messageDisplayStartTime = System.currentTimeMillis(); // Inicia a contagem do tempo de exibição da
																			// mensagem
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

	public static long getNow(UnidadeTempo unidade) {
		switch (unidade) {
		case MINUTOS:
			return minutes % 60; // Retorna os minutos atuais
		case HORAS:
			return hours % 24; // Retorna as horas atuais
		case DIAS:
			return days; // Retorna os dias atuais
		case MESES:
			return months; // Retorna os meses atuais
		case ANOS:
			return years; // Retorna os anos atuais
		default:
			return -1; // Retorna -1 se a unidade de tempo não for reconhecida
		}
	}

	// Mapeia as variáveis de controle para os valores anteriores
	private static HashMap<String, Double> valoresAnteriores = new HashMap<>();
	private static HashMap<String, int[]> tempoInicio = new HashMap<>(); // Armazena o tempo de início da contagem

	// Método para iniciar a contagem
	public static void iniciarContagem(String contexto, double variavel) {
		valoresAnteriores.put(contexto, variavel);
	    int[] tempoAtual = {minutes, hours, days, years};
		tempoInicio.put(contexto, tempoAtual); // Armazena o tempo de início
	}

	public static void removerContagem(String contexto) {
		valoresAnteriores.remove(contexto); // Remove o valor anterior
		tempoInicio.remove(contexto); // Remove o tempo de início
	}

	// Método para verificar se uma variável permanece inalterada após um tempo
	// específico
	public static boolean verificarTempo(String contexto, int tempo, UnidadeTempo unidade, double variavel) {

		long tempoLimite = 0;
		int minutoAtual = minutes;
		int horaAtual = hours;
		int diaAtual = days;
		int anoAtual = years;
	    int[] tempoAtual = {minutoAtual, horaAtual, diaAtual, anoAtual};
		int[] tempoInicial = tempoInicio.getOrDefault(contexto, tempoAtual);

		double variavelAnterior = valoresAnteriores.getOrDefault(contexto, variavel);

		// Calcula o tempo limite em segundos com base na unidade de tempo
		switch (unidade) {
		case DIAS:
			tempoLimite = tempo * 24 * 60 * 60; // dias para segundos
			break;
		case HORAS:
			tempoLimite = tempo * 60 * 60; // horas para segundos
			break;
		case MINUTOS:
			tempoLimite = tempo * 60; // minutos para segundos
			break;
		default:
			break;
		}

	    // Calcula o tempo decorrido desde o início
	    long tempoDecorrido = (minutoAtual - tempoInicial[0]) * 60 +
	                          (horaAtual - tempoInicial[1]) * 60 * 60 +
	                          (diaAtual - tempoInicial[2]) * 24 * 60 * 60 +
	                          (anoAtual - tempoInicial[3]) * 365 * 24 * 60 * 60;

	    // Verifica se o tempo passado é maior ou igual ao tempo limite e se a variável permaneceu inalterada
	    if (tempoDecorrido >= tempoLimite && variavel == variavelAnterior) {
	        return true; // Retorna true se o tempo passado for maior ou igual ao tempo limite e a variável permaneceu inalterada
	    }
	    
		System.out.println(tempoAtual);
		System.out.println(tempoDecorrido);
		System.out.println(tempoLimite);


		return false; // Retorna false caso contrário
	}

	// Enum para indicar qual variável será alterada
	public enum UnidadeTempo {
		ANOS, MESES, DIAS, HORAS, MINUTOS
	}

	public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
		this.unidadeTempo = unidadeTempo;
	}

	public void setFormatoData(String formatoData) {
		this.formatoData = formatoData;
	}

	public static void addHoras() {
		int offsetmin = 60;
		int offsethour = 60 * 60;
		int offsetday = 1 * 60 * 60 * 24;
		// TODO Auto-generated method stub
		timeElapsedSeconds = timeElapsedSeconds + offsetday;
	}

	public static void add(int tempo, UnidadeTempo unidade) {
		long tempoLimite = 0;

		// Calcula o tempo limite em segundos com base na unidade de tempo
		switch (unidade) {
		case DIAS:
			tempoLimite = tempo * 24 * 60 * 60; // dias para segundos
			break;
		case HORAS:
			tempoLimite = tempo * 60 * 60; // horas para segundos
			break;
		case MINUTOS:
			tempoLimite = tempo * 60; // minutos para segundos
			break;
		default:
			break;
		}

		timeElapsedSeconds = timeElapsedSeconds + tempoLimite;
	}

	public Tempo(int minutes, int hours, int days, int months, int years) {
		Tempo.minutes = minutes;
		Tempo.hours = hours;
		Tempo.days = days;
		Tempo.months = months;
		Tempo.years = years;
	}

	public static boolean isTimeToAutoSave() {
	    return hours % 6 == 0 && minutes == 0; // Verifica se o número de minutos é um múltiplo de 6 e segundos é zero
	}
	
	// Método para obter o tempo atual
	public static Tempo getNow() {
		return new Tempo(Tempo.minutes % 60, Tempo.hours % 24, Tempo.days, Tempo.months, Tempo.years);
	}

	// Métodos getters
	public long getMinutes() {
		return minutes;
	}

	public int getHours() {
		return hours;
	}

	public int getDays() {
		return days;
	}

	public int getMonths() {
		return months;
	}

	public int getYears() {
		return years;
	}
}

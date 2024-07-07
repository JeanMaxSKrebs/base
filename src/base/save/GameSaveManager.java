package base.save;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import base.Game;
import entities.Player;
import tempo.Tempo;
import world.World;

public class GameSaveManager {

	public static String checkpoint = "1";
	public static int slotPlayer = 3;
	public static int slotTime = 1; // 6 em 6 horas do jogo, minutos da vida real
	public static int slotSystem = 2; // ao passar por uma fogueira // ao deitar numa cama
	public static int slotMaximo = slotPlayer * (slotTime + slotSystem); // 9 vão ser 1 de tempo e 2 de sistema a cada

	protected static int encode = 5;
	protected static String[] options = { "nivel", "vida", "estamina", "premium", "gameState", "previousGameState" };
	protected static String[] values = {};

	public static String[] saveNames = new String[3];// 0 1 2
	public static boolean[] saveExists = new boolean[9];
	public static String[] saveLoads = new String[3];
	public static String[] saveLoadsTempo = new String[3];

	public static String userHorario;
	public static String autoSaveHorario;
	public static String lastBedHorario;
	public static Date lastAccessDate;

	public static int slot;// 1 2 3
	public static String SAVE_DIRECTORY = "saves/";

	protected static String[] getOptions() {
		return new String[] { "nivel", "vida", "estamina", "premium", "gameState", "previousGameState" };
	}

	protected static String lineSearch = "Tempo Total de Jogo em TimeElapsedSeconds: ";

	public static Map<String, String> loadHorariosFromSave() {// nunca usado
		String fileName = "save_slot_" + slot + "_horario.txt";
		Map<String, String> horariosMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(":");
				if (data.length == 2) {
					String key = data[0].trim();
					String value = data[1].trim();
					switch (key) {
					case "user":
						userHorario = value;
						break;
					case "autosave":
						autoSaveHorario = value;
						break;
					case "lastbed":
						lastBedHorario = value;
						break;
					case "lastAccessDate":
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						try {
							lastAccessDate = sdf.parse(value);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
					horariosMap.put(key, value); // Adiciona ao mapa
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return horariosMap; // Retorna o mapa com os horários
	}

	protected static String getFileName(String currentOption, String tipo) {
		String filename = "Vazio";

		if (currentOption.equals("0") || currentOption.equals("_User")) {
			filename = SAVE_DIRECTORY + "save_slot_" + slot + "_User";
		}
		if (currentOption.equals("1") || currentOption.equals("_AutoSaveEvery6Minutes")) {
			filename = SAVE_DIRECTORY + "save_slot_" + slot + "_AutoSaveEvery6Minutes";
		}
		if (currentOption.equals("2") || currentOption.equals("_LastBed")) {
			filename = SAVE_DIRECTORY + "save_slot_" + slot + "_LastBed";
		}

//	    System.out.println(filename+tipo);
		return filename += tipo;

	}

	public static void ensureSaveDirectoryExists() {
		try {
			mkdirExists(SAVE_DIRECTORY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ensureDirectoryExists(String saveDirectory) {
		try {
			mkdirExists(saveDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String readSaveName(String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return decodeString(stringBuilder.toString());
	}

	public static void checkAndInitializeSaveNames() {
		ensureSaveDirectoryExists(); // Garante que o diretório de saves existe

		for (int i = 0; i < saveNames.length; i++) {
			setSlot(i + 1);
			String baseFileName = SAVE_DIRECTORY + "save_slot_" + (i + 1) + "_name.txt";
//			System.out.println(baseFileName + "y");
			if (fileExists(baseFileName)) {
				saveNames[i] = readSaveName(baseFileName);
//				System.out.println("Loaded save name for slot " + (i + 1) + ": " + saveNames[i]); // Mensagem para debug
			} else {
				saveNames[i] = null;
//				System.out.println("No save name found for slot " + (i + 1)); // Mensagem para debug
			}
		}
	}

	public static void checkAndInitializeSaveLoads() {
		ensureSaveDirectoryExists(); // Garante que o diretório de saves existe

		for (int i = 0; i < saveLoads.length; i++) {
			String strI = String.valueOf(i);
			File file = new File(getFileName(strI, ".txt"));

			if (!file.exists()) {
				saveLoads[i] = "Nunca acessado";
				saveLoadsTempo[i] = "N/A"; // Exemplo de tempo de jogo não disponível
//		            System.out.println("No save file found for slot " + (i + 1)); // Mensagem para debug
			} else {
				updateSaveLoads(file, i);
				if (!updateSaveLoadsTempo(file, i)) {
					saveLoadsTempo[i] = "01/01/0001 00:01"; // Exemplo de tempo de jogo não disponível
				}
			}
		}
	}

	// Métodos para atualizar saveLoads
	private static void updateSaveLoads(File file, int i) {
		long lastModified = file.lastModified();
		Date lastModifiedDate = new Date(lastModified);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		String lastAccessTime = sdf.format(lastModifiedDate);
		saveLoads[i] = lastAccessTime;
	}

	// Métodos para atualizar saveLoadsTempo
	private static boolean updateSaveLoadsTempo(File file, int i) {
		String gameTime = readGameTimeFromSave(file);

		if (gameTime != null && !gameTime.isEmpty()) {
			saveLoadsTempo[i] = gameTime;
			return true;
		}
		return false;

	}

	private static String readGameTimeFromSave(File file) {
		String timeElapsedString = "";
		long timeElapsedSeconds = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(lineSearch)) {
					timeElapsedString = line.substring(lineSearch.length()).trim();
					timeElapsedSeconds = Long.parseLong(timeElapsedString);

					break; // Uma vez que encontramos o tempo, não precisamos continuar lendo
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String formattedAll = Tempo.getNowToString("TODOS", timeElapsedSeconds);
		return formattedAll;
	}

	private static boolean fileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	private static void mkdirExists(String directoryPath) {
		File directory = new File(directoryPath);

		if (!directory.exists()) {
			boolean created = directory.mkdirs();

			if (created) {
				System.out.println("Directory created: " + directoryPath);
			} else {
				System.out.println("Failed to create directory: " + directoryPath);
			}
		}
	}

	protected static String encodeString(String input) {
		byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
		return new String(encodedBytes, StandardCharsets.UTF_8);
	}

	private static String decodeString(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes(StandardCharsets.UTF_8));
		return new String(decodedBytes, StandardCharsets.UTF_8);
	}

	public static void tick() {

		if (Tempo.isTimeToAutoSave()) {
			Save.updateSaveAutoSaveEvery6Minutes();
		}
	}

	public static void setSlot(int newSlot) {
		slot = newSlot;
		updateSaveDirectory();
	}

	private static void updateSaveDirectory() {
		SAVE_DIRECTORY = "saves/" + slot + "/";
	}

}

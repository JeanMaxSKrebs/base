package base;

import java.io.*;
import java.time.LocalDateTime;

import entities.Player;
import tempo.Tempo;
import world.World;

public class GameSaveManager {

	public static String checkpoint = "1";
	public static int slot = 1;
	public static int slotPlayer = 3;
	public static int slotTime = 1; // 6 em 6 horas do jogo, minutos da vida real
	public static int slotSystem = 2; // ao passar por uma fogueira // ao deitar numa cama
	public static int slotMaximo = slotPlayer * (slotTime + slotSystem); // 9 vão ser 1 de tempo e 2 de sistema a cada

	private static int encode = 5;
	private static String[] options = { "nivel", "vida", "estamina", "premium", "gameState", "previousGameState" };
	private static String[] values = { String.valueOf(Player.getNivel()), String.valueOf(50), String.valueOf(50),
			String.valueOf(Player.getPremium()), "NORMAL", String.valueOf(Game.previousGameState) };

	public static void saveGame(String[] val1, String[] val2, int encode, int slot) {
		System.out.println("Saving to slot " + slot);
		try (BufferedWriter write = new BufferedWriter(new FileWriter("SaveSlot" + slot + ".txt"))) {
			for (int i = 0; i < val1.length; i++) {
				String current = val1[i] + ":";
				char[] value = val2[i].toCharArray();

				for (int j = 0; j < value.length; j++) {
					value[j] += encode;
					current += value[j];
				}
				write.write(current);
				if (i < val1.length - 1)
					write.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void applySave(String str) {
		System.out.println(str);
		String[] spl = str.split("/");
		for (String s : spl) {
			String[] spl2 = s.split(":");
			System.out.println("spl2[0]: " + spl2[0]);
			System.out.println("spl2[1]: " + spl2[1]);
			switch (spl2[0]) {
			case "fase": {
				int spl2inteiro = (int) Double.parseDouble(spl2[1]);
				System.out.println("spl2inteiro: " + spl2inteiro);
				World.restartGame("fase" + spl2inteiro + ".png");
				Game.gameState = "NORMAL";
				break;
			}

			default:
				break;
			}
		}
	}

	public static String loadGame() {
		StringBuilder line = new StringBuilder();
		File file = new File("SaveSlot" + slot + "_Checkpoint" + checkpoint + ".txt");
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String singleLine;
				while ((singleLine = reader.readLine()) != null) {
					String[] trans = singleLine.split(":");
					char[] val = trans[1].toCharArray();
					trans[1] = "";
					for (int i = 0; i < val.length; i++) {
						val[i] -= encode;
						trans[1] += val[i];
					}
					line.append(trans[0]).append(":").append(trans[1]).append("/");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("line: " + line);

		return line.toString();
	}

	public static void autoSaveEvery6Minutes() {
		saveCheckpoint("AutoSaveEvery6Minutes");
	}

	public static void saveCheckpoint(String checkpointType) {
		System.out.println("Saving checkpoint: " + checkpointType);
		try (BufferedWriter write = new BufferedWriter(new FileWriter(checkpointType + ".txt"))) {
			for (int i = 0; i < options.length; i++) {
				String current = options[i] + ":";
				char[] value = values[i].toCharArray();

				for (int j = 0; j < value.length; j++) {
					value[j] += encode;
					current += value[j];
				}
				write.write(current);
				if (i < values.length - 1)
					write.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void saveSaveName(int slot, String saveName) {
        File file = new File("save_slot_" + slot + "_name.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(saveName);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	  public static String loadSaveName(int slot) {
	        File file = new File("save_slot_" + slot + "_name.txt");
	        if (file.exists()) {
	            try {
	                BufferedReader reader = new BufferedReader(new FileReader(file));
	                String saveName = reader.readLine();
	                reader.close();
	                return saveName;
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }

	public static void saveAtLastBed(String[] val1, String[] val2, int encode) {
		saveCheckpoint("LastBed");
	}

	public static void tick() {
		if (Tempo.isTimeToAutoSave()) {
			autoSaveEvery6Minutes();
		}
	}
}

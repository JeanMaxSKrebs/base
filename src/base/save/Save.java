package base.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import entities.Player;
import tempo.Tempo;
import tempo.Tempo.UnidadeTempo;

public class Save extends GameSaveManager {

	private static final UnidadeTempo TODOS = null;

	public static void initializeSavesForSlot() {
		ensureSaveDirectoryExists();
		try {
			saveCheckpoint("_User");
			saveCheckpoint("_AutoSaveEvery6Minutes");
			saveCheckpoint("_LastBed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTxt(String fileName) {
		// Salvando como arquivo .txt
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        // Adiciona data e hora atual no formato brasileiro
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
	        String currentTime = sdf.format(new Date());
	        
	        Tempo currentGameTime = Tempo.getNow();
	        
	        String unidade = "TODOS";
	        String currentGameTimeString = Tempo.getNowToString(unidade, 0);
	        // Obtém o tempo do jogo

	        // Escreve a data/hora atual e o tempo do jogo no arquivo
	        writer.write("Data/Hora Atual: " + currentTime);
	        writer.newLine();
	        writer.write("Tempo Total de Jogo em TimeElapsedSeconds: " + Tempo.timeElapsedSeconds);
	        writer.newLine();
	        writer.write("Tempo Total de Jogo: " + currentGameTime);
	        writer.newLine();
	        System.out.println("currentGameTimeString");
	        System.out.println(currentGameTimeString);
	        writer.write("Tempo Total de Jogo: " + currentGameTimeString);
	        writer.newLine();

	        String[] options = GameSaveManager.getOptions();
	        String[] values = GameSaveManager.getValues();

	        for (int i = 0; i < options.length; i++) {
	            writer.write(options[i] + ": " + values[i]);
	            writer.newLine();
	        }

	        System.out.println("Save successful.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    public static void saveDat(String fileName) {
        // Salvando como arquivo .dat
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(Player.getInstance()); // Assuming Player is a singleton
            System.out.println("Save successful.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void saveCheckpoint(String checkpointType) {
		
		String fileName = "Vazio";
		fileName = getFileName(checkpointType, ".txt");
		saveTxt(fileName);
//		System.out.println("Saving checkpoint: " + fileName);

		fileName = getFileName(checkpointType, ".dat");
		saveDat(fileName);
//		System.out.println("Saving checkpoint: " + fileName);

	}
	
	public static void saveHorarios() {
		String fileName = SAVE_DIRECTORY + "save_slot_" + slot + "_horario.txt";
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			writer.println("user:" + userHorario);
			writer.println("autosave:" + autoSaveHorario);
			writer.println("lastbed:" + lastBedHorario);
			writer.println("lastAccessDate:" + sdf.format(new Date())); // Salva a data atual
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSaveName(String saveName) {
		File file = new File(SAVE_DIRECTORY + "save_slot_" + slot + "_name.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String encodedSaveName = encodeString(saveName);
			writer.write(encodedSaveName);
			System.out.println("Save name created at: " + SAVE_DIRECTORY + "save_slot_" + slot + "_name");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSaveUser() {
		try {
			saveCheckpoint("_User");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSaveLastBed() {
		saveCheckpoint("_LastBed");
	}

	public static void updateSaveAutoSaveEvery6Minutes() {
		saveCheckpoint("_AutoSaveEvery6Minutes");
	}
}

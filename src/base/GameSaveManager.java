package base;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entities.Player;
import tempo.Tempo;

public class GameSaveManager {

	public static String checkpoint = "1";
	public static int slot;//1 2 3
	public static int slotPlayer = 3;
	public static int slotTime = 1; // 6 em 6 horas do jogo, minutos da vida real
	public static int slotSystem = 2; // ao passar por uma fogueira // ao deitar numa cama
	public static int slotMaximo = slotPlayer * (slotTime + slotSystem); // 9 vão ser 1 de tempo e 2 de sistema a cada

	private static int encode = 5;
	private static String[] options = { "nivel", "vida", "estamina", "premium", "gameState", "previousGameState" };
	private static String[] values = { String.valueOf(Player.getNivel()), String.valueOf(50), String.valueOf(50),
			String.valueOf(Player.getPremium()), "NORMAL", String.valueOf(Game.previousGameState) };

	public static String[] saveNames = new String[3];// 0 1 2
	public static boolean[] saveExists = new boolean[9];
	
	 public static boolean isSaveSlotUsed(int slot) {
	        String fileName = "save_slot_" + slot + ".txt"; // Nome do arquivo de salvamento
	        File file = new File(fileName);
	        return file.exists(); // Verifica se o arquivo existe
	    }
	
    public static void saveToSave(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < options.length; i++) {
                String encodedValue = encodeValue(values[i]);
                writer.write(options[i] + ":" + encodedValue + "\n");
            }
            System.out.println("Salvamento concluído em: " + fileName);
        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void loadFromSave(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length == 2) {
                    String key = data[0].trim();
                    String value = decodeValue(data[1].trim());
                    switch (key) {
                        case "nivel":
                            Player.setNivel(Integer.parseInt(value));
                            break;
                        case "vida":
                            // Aqui você atribuiria o valor de "vida" para o jogador (exemplo: Player.setVida(Integer.parseInt(value));)
                            break;
                        case "estamina":
                            // Aqui você atribuiria o valor de "estamina" para o jogador (exemplo: Player.setEstamina(Integer.parseInt(value));)
                            break;
                        case "premium":
                            Player.setPremium(Integer.parseInt(value));
                            break;
                        case "gameState":
                            // Aqui você atribuiria o valor de "gameState" para o jogo (exemplo: Game.setGameState(value);)
                            break;
                        case "previousGameState":
                            // Aqui você atribuiria o valor de "previousGameState" para o jogo (exemplo: Game.setPreviousGameState(value);)
                            break;
                        default:
                            System.out.println("Chave não reconhecida: " + key);
                            break;
                    }
                }
            }
            System.out.println("Carregamento concluído de: " + fileName);
        } catch (IOException e) {
            System.err.println("Erro ao carregar do arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static String userHorario;
    public static String autoSaveHorario;
    public static String lastBedHorario;
    public static Date lastAccessDate;
    
    public static String tempoDesdeUltimoAcesso() {
        if (lastAccessDate != null) {
            Date hoje = new Date(); // Data atual

            // Calcula a diferença em milissegundos
            long diff = hoje.getTime() - lastAccessDate.getTime();

            // Calcula dias, horas e minutos
            long segundos = diff / 1000;
            long minutos = segundos / 60;
            long horas = minutos / 60;
            long dias = horas / 24;

            // Calcula os valores restantes
            horas = horas % 24;
            minutos = minutos % 60;
            segundos = segundos % 60;

            // Monta a string formatada
            StringBuilder sb = new StringBuilder();
            if (dias > 0) {
                sb.append(dias).append(" dia(s) ");
            }
            if (horas > 0) {
                sb.append(horas).append(" hora(s) ");
            }
            if (minutos > 0) {
                sb.append(minutos).append(" minuto(s) ");
            }
            if (segundos > 0) {
                sb.append(segundos).append(" segundo(s) ");
            }

            return sb.toString();
        }
        return "Não disponível"; // Retorna "Não disponível" se a data de último acesso não estiver definida
    }

    public static Map<String, String> loadHorariosFromSave() {
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

    
    public static void saveHorarios(int slot) {
        String fileName = "save_slot_" + slot + "_horario.txt";
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



    private static String encodeValue(String value) {
        // Implemente seu método de codificação aqui, se necessário
        // Este é um exemplo simples de codificação
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            encoded.append((char) (value.charAt(i) + encode));
        }
        return encoded.toString();
    }

    private static String decodeValue(String encodedValue) {
        // Implemente seu método de decodificação aqui, se necessário
        // Este é um exemplo simples de decodificação
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < encodedValue.length(); i++) {
            decoded.append((char) (encodedValue.charAt(i) - encode));
        }
        return decoded.toString();
    }
    
    public static void loadPlayerFromSave() {
    	  String fileName = "save_slot_" + slot + "_User.txt";
    	    try {
    	        loadFromSave(fileName);
    	        System.out.println("Player loaded successfully from: " + fileName);
    	    } catch (Exception e) {
    	        System.err.println("Error loading player from save: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    }

	public static void addSaveName(String saveName) {
		File file = new File("save_slot_" + slot + "_name.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(saveName);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initializeSavesForSlot(int slot) {
		try {
			saveCheckpoint("save_slot_" + slot + "_User");
			saveCheckpoint("save_slot_" + slot + "_AutoSaveEvery6Minutes");
			saveCheckpoint("save_slot_" + slot + "_LastBed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String readSaveName(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath))).trim();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void checkAndInitializeSaveNames() {
		for (int i = 0; i < saveNames.length; i++) {
			String baseFileName = "save_slot_" + (i + 1) + "_name.txt";
			if (fileExists(baseFileName)) {
				saveNames[i] = readSaveName(baseFileName);
				System.out.println("Entrou: " + baseFileName + " Nome: " + saveNames[i]); // Mensagem para debug
//				System.out.println("O slot de save não está vazio: " + saveNames[i]);

			} else {
				saveNames[i] = null;
				System.out.println("Saiu: " + baseFileName); // Mensagem para debug
//				System.out.println("O slot de save está vazio.");

			}
		}
	}

	private static boolean fileExists(String filePath) {
		// Substitua pelo caminho correto onde os arquivos estão localizados
		File file = new File(filePath);
		return file.exists();
	}
	
	public static void saveAtLastBed(String[] val1, String[] val2, int encode) {
		saveCheckpoint("LastBed");
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

	public static void tick() {

		if (Tempo.isTimeToAutoSave()) {
			autoSaveEvery6Minutes();
		}
	}
}

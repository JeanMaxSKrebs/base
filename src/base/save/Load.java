package base.save;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import base.Game;
import entities.Player;
import tempo.Tempo;

public class Load extends GameSaveManager {

	private static ObjectInputStream ois;

	public static void loadCheckpoint(int currentOption) {
		String fileName = "Vazio";
		String strI = String.valueOf(currentOption);

		fileName = getFileName(strI, ".dat");
		if (fileName != "Vazio") {
			loadPlayerFromDat(fileName);
		}
		fileName = getFileName(strI, ".txt");
		if (fileName != "Vazio") {
			loadPlayerFromTxt(fileName);
		}
	}

	public static void loadPlayerFromDat(String fileName) {
//		System.out.println(fileName);
		try {
			// Inicializa o ObjectInputStream
			ois = new ObjectInputStream(new FileInputStream(fileName));

			Player playerFromDat = (Player) ois.readObject();
			if (playerFromDat != null) {
				// Restante do código para atualizar o estado do jogador
				updatePlayerStateFromDat(playerFromDat);

			} else {
				System.out.println("Objeto Player lido do fluxo de entrada é nulo.");
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); // Trate ou registre adequadamente exceções de leitura
		} finally {
			// Fecha o ObjectInputStream no bloco finally para garantir que seja fechado
			// corretamente
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void loadPlayerFromTxt(String fileName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			String[] parts = null;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith(lineSearch)) {
					parts = line.split(": ");
					if (parts.length == 2) {
						Tempo.timeElapsedSeconds = Long.parseLong(parts[1].trim());
						break; // Se encontrou, não precisa continuar lendo
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void updatePlayerStateFromDat(Player playerFromDat) {
		// Atualiza as variáveis de estado do jogador com base nos dados recebidos
		if (playerFromDat != null) {
			Game.player.setInstance(playerFromDat);
			Game.player.updateFrom();

//			Game.player.hasBagpack = playerFromDat.hasBagpack;

//			Game.player.setInventory(playerFromDat.getInventory()
			
			// Atualiza outras variáveis de estado do jogo, se necessário
			// Exemplo: Game.player.setInventory(playerFromDat.getInventory());
		}
	}

}

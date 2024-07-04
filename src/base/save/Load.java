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

	public static void loadPlayerFromSave(int currentOption) {
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
			System.out.println("Player from dat: " + playerFromDat); // Adicione esta linha para ver o que está sendo
																		// lido
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

			String key = parts[0];
			String value = parts[1];

			// Decodificar o valor
			char[] valueChars = value.toCharArray();
			for (int i = 0; i < valueChars.length; i++) {
//					valueChars[i] -= encode;
			}
			value = new String(valueChars);

			// Atualizar o estado do jogador com base na chave e valor
			updatePlayerStateFromTxt(key, value);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void updatePlayerStateFromTxt(String key, String value) {
		Player player = Player.getInstance();
		System.out.println("teste");
		switch (key) {
		case "life":
			System.out.println(player.life);
			System.out.println(value);
			player.life = Double.parseDouble(value);
			break;
		case "stamine":
			player.stamine = Double.parseDouble(value);
			break;
		case "hunger":
			player.hunger = Double.parseDouble(value);
			break;
		case "thirsth":
			player.thirsth = Double.parseDouble(value);
			break;
		case "itensColetados":
			// Lógica para restaurar itens coletados
			break;
		// Adicione mais cases conforme necessário para restaurar outras propriedades do
		// jogador
		}
	}

	private static void updatePlayerStateFromDat(Player playerFromDat) {
		// Atualiza as variáveis de estado do jogador com base nos dados recebidos
		if (playerFromDat != null) {
			System.out.println(playerFromDat.getLife());
			System.out.println(Game.player.getLife());
			Game.player.setLife(playerFromDat.getLife());
			Game.player.setStamine(playerFromDat.getStamine());
			Game.player.setHunger(playerFromDat.getHunger());
			Game.player.setThirsth(playerFromDat.getThirsth());

			// Atualiza os itens coletados pelo jogador
			Game.player.setItensColetados(playerFromDat.getItensColetados());
			Game.player.setComidasColetadas(playerFromDat.getComidasColetadas());
			Game.player.setFrutasColetadas(playerFromDat.getFrutasColetadas());
		}
		// Atualiza outras variáveis de estado do jogo, se necessário
		// Exemplo: Game.player.setInventory(playerFromDat.getInventory());
		// Certifique-se de implementar os métodos getters e setters necessários na
		// classe Player.
	}

}

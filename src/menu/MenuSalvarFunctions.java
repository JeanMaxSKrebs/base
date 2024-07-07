package menu;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import base.Game;
import base.save.GameSaveManager;
import base.save.Save;

public class MenuSalvarFunctions {
	// Configura o JOptionPane para exibir opções em português
	public static void OptionPaneLanguage() {
		switch (Game.linguagem) {
		case "Português": {
			UIManager.put("OptionPane.yesButtonText", "Sim");
			UIManager.put("OptionPane.noButtonText", "Não");
	        UIManager.put("OptionPane.okButtonText", "Aceitar");
	        UIManager.put("OptionPane.cancelButtonText", "Cancelar");

			break;
		}
		case "Inglês": {
			UIManager.put("OptionPane.yesButtonText", "Yes");
			UIManager.put("OptionPane.noButtonText", "No");
	        UIManager.put("OptionPane.okButtonText", "Ok");
	        UIManager.put("OptionPane.cancelButtonText", "Cancel");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + Game.linguagem);
		}

	}

	public static void showInputDialogNewSave(int currentOption) {
		String saveName = JOptionPane.showInputDialog("Digite o nome do arquivo de salvamento:");

		if (saveName != null && !saveName.trim().isEmpty()) {
			if (saveName.trim().length() > 3) {

				saveName = saveName.trim();
				Save.initializeSavesForSlot();
				Save.addSaveName(saveName);
				GameSaveManager.saveNames[currentOption] = saveName;
				for (int i = 1; i <= 3; i++) {
					if (i == GameSaveManager.slot) {
						GameSaveManager.saveExists[currentOption * i] = true;
						GameSaveManager.saveExists[currentOption * i + 1] = true;
						GameSaveManager.saveExists[currentOption * i + 2] = true;
					}
				}
			} else {
				return;
			}
		} else {
			return;
		}		
	}

	public static void showInputDialogNewSaveOverwritten(int currentOption) {
		 int response = JOptionPane.showConfirmDialog(null,
                 "Este slot já existe. Deseja sobrescrever?", "Confirmar Salvamento", JOptionPane.YES_NO_OPTION);

		if (response != JOptionPane.NO_OPTION) {
			String saveName = JOptionPane
					.showInputDialog("Digite o nome do novo arquivo de salvamento:");
			if (saveName != null && !saveName.trim().isEmpty()) {
				if (saveName.trim().length() > 3) {

					saveName = saveName.trim();
					Save.initializeSavesForSlot();
					Save.addSaveName(saveName);
					GameSaveManager.saveNames[currentOption] = saveName;
					for (int i = 1; i <= 3; i++) {
						if (i == GameSaveManager.slot) {
							GameSaveManager.saveExists[currentOption * i] = true;
							GameSaveManager.saveExists[currentOption * i + 1] = true;
							GameSaveManager.saveExists[currentOption * i + 2] = true;
						}
					}
					Game.previousGameState = Game.gameState;
					Game.gameState = "CARREGAR";
				} else {
					return;
				}
			}
		} else {
			Game.previousGameState = Game.gameState;
			Game.gameState = "CARREGAR";
		}		
	}
}

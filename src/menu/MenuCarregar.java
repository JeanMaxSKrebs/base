package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import base.Game;
import base.GameSaveManager;

public class MenuCarregar extends Menu {

    public static final Option[] options = { new Option("Slot 1", "Slot 1"), new Option("Slot 2", "Slot 2"),
            new Option("Slot 3", "Slot 3"), new Option("Voltar", "Back") };

    private String[] saveNames = new String[3];
    private boolean[] saveExists = new boolean[3];

    public MenuCarregar() {
        super(options);
        loadSaveNames();
    }

    private void loadSaveNames() {
        for (int i = 0; i < 3; i++) {
            File file = new File("SaveSlot" + (i + 1) + "_Checkpoint.txt");
            saveExists[i] = file.exists();
            if (saveExists[i]) {
                saveNames[i] = GameSaveManager.loadSaveName(i + 1);
            }
        }
    }

    public void tick() {
        for (int i = 0; i < 3; i++) {
            File file = new File("SaveSlot" + (i + 1) + "_Checkpoint.txt");
            saveExists[i] = file.exists();
        }

        if (up) {
            up = false;
            currentOption--;

            if (currentOption < 0) {
                currentOption = maxOption;
            }
        }

        if (down) {
            down = false;
            currentOption++;

            if (currentOption > maxOption) {
                currentOption = 0;
            }
        }

        if (enter) {
            enter = false;
            if (currentOption < 3 && saveExists[currentOption]) {
                String saveData = GameSaveManager.loadGame();
                GameSaveManager.applySave(saveData);
                Game.gameState = "NORMAL";
            } else if (options[currentOption].getNomePortugues().equals("Voltar")) {
                Game.gameState = "MENUPRINCIPAL";
            }
        }
    }

    public void render(Graphics g) {
        int larguraDesejada = Game.getWIDTH() * Game.getSCALE();
        int alturaDesejada = Game.getHEIGHT() * Game.getSCALE();
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.fillRect(0, 0, larguraDesejada, alturaDesejada);
        g.setColor(Color.WHITE);
        g.drawString("Carregar Jogo", larguraDesejada / 4, alturaDesejada / 5);

        // menu
        g.setFont(new Font("Arial", Font.BOLD, 48));

        int alturaDesejadaUMTERCO = alturaDesejada / 2 - 30;
        int larguraDesejadaUMTERCO = larguraDesejada / 3;

        int spacingRows = 90;
        switch (Game.linguagem) {
            case "Inglês":
                for (int i = 0; i < options.length - 1; i++) {
                    String text = options[i].getNomeIngles();
                    if (i < 3 && saveExists[i]) {
                        text += " (Saved)";
                    } else if (i < 3) {
                        text += " (Empty)";
                    }
                    g.drawString(text, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
                }
                g.drawString(options[options.length - 1].getNomeIngles(), larguraDesejada - 325,
                        alturaDesejada - 30);

                break;
            case "Português":
                for (int i = 0; i < options.length - 1; i++) {
                    String text = options[i].getNomePortugues();
                    if (i < 3 && saveExists[i]) {
                        text += " (Salvo)";
                    } else if (i < 3) {
                        text += " (Vazio)";
                    }
                    g.drawString(text, larguraDesejadaUMTERCO, alturaDesejadaUMTERCO + spacingRows * i);
                }
                g.drawString(options[options.length - 1].getNomePortugues(), larguraDesejada - 325,
                        alturaDesejada - 30);

                break;
            default:
                break;
        }
        int spacingWidth = 60;

        if (currentOption >= options.length - 1) {
            g.drawString(" > ", larguraDesejada - 325 - spacingWidth, alturaDesejada - 30);
        } else {
            g.drawString(" > ", larguraDesejadaUMTERCO - spacingWidth,
                    alturaDesejadaUMTERCO + spacingRows * currentOption);
        }
    }
}

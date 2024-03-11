package entities.itens.utensilios;

import java.awt.image.BufferedImage;

import entities.itens.Item;
import entities.itens.comidas.Comida;

public class Panela extends Item {

    public Panela(int x, int y, int width, int height, BufferedImage sprite, String nome) {
        super(x, y, width, height, sprite, nome);
    }

    public boolean cookFood(Comida comida) {  // Method to comida food using Panela
        if (!comida.isCooked()) {
        	comida.cook();
            return true;
        } else {
            System.out.println("A comida já está pronta!");
            return false;
        }
    }
}

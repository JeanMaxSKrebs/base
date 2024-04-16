package entities.itens.utensilios;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;
import entities.itens.comidas.Comida;
import entities.itens.comidas.frutas.Maca;
import entities.itens.comidas.frutas.Uva;

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

	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}

	public Panela(Panela outraPanela) {
		super(outraPanela);
	}
	
	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new Panela(this);
	}
}

package entities.itens.utensilios;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class Utensilio extends Item {


	
	  public Utensilio(int x, int y, int width, int height, BufferedImage sprite, String nome) {
	        super(x, y, width, height, sprite, nome);
	    }

	    public Utensilio(int x, int y, int width, int height, BufferedImage sprite) {
	        super(x, y, width, height, sprite);
	    }
}

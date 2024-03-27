package entities.itens.utensilios;

import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;

public class BagPack extends Utensilio {

	public static BufferedImage BAGPACK_EN = Game.spritesheet_UsableItems.getSprite(112 * 0, 112 * 1, 112, 112);
	public static BufferedImage BAGPACK_RIGHT = Game.spritesheet_UsableItems.getSprite(112 * 1, 112 * 1, 112, 112);
	public static BufferedImage BAGPACK_LEFT = Game.spritesheet_UsableItems.getSprite(112 * 2, 112 * 1, 112, 112);
	public static BufferedImage BAGPACK_UP = Game.spritesheet_UsableItems.getSprite(112 * 3, 112 * 1, 112, 112);
	
    public static BufferedImage[] spritesBackpack;

	
	public BagPack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
	    // Inicialize o array de sprites da mochila
        spritesBackpack = new BufferedImage[4];
        spritesBackpack[0] = BAGPACK_EN;
        spritesBackpack[1] = BAGPACK_RIGHT;
        spritesBackpack[2] = BAGPACK_LEFT;
        spritesBackpack[3] = BAGPACK_UP;
	}


	public static BufferedImage getSpritesBackpack(int index) {
	    return spritesBackpack[index];
	}


	public void setSpritesBackpack(BufferedImage[] spritesBackpack) {
		BagPack.spritesBackpack = spritesBackpack;
	}

}

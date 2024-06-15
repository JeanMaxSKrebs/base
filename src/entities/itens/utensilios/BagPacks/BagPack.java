package entities.itens.utensilios.BagPacks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;
import entities.itens.Item;
import entities.itens.comidas.frutas.Uva;
import entities.itens.utensilios.Utensilio;
import world.Camera;

public class BagPack extends Utensilio {

	public static BufferedImage BAGPACK_EN = Game.spritesheet_UsableItens.getSprite(112 * 0, 112 * 1, 112, 112);
	public static BufferedImage BAGPACK_RIGHT = Game.spritesheet_UsableItens.getSprite(112 * 1, 112 * 1, 112, 112);
	public static BufferedImage BAGPACK_LEFT = Game.spritesheet_UsableItens.getSprite(112 * 2, 112 * 1, 112, 112);
	public static BufferedImage BAGPACK_UP = Game.spritesheet_UsableItens.getSprite(112 * 3, 112 * 1, 112, 112);

	public static BufferedImage[] spritesBackpack;

	public static int armorBase = 5;

	public static int getArmorBase() {
		return armorBase;
	}

	public static void setArmorBase(int armorBase) {
		BagPack.armorBase = armorBase;
	}

	public BagPack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		// Inicialize o array de sprites da mochila
		spritesBackpack = new BufferedImage[4];
		spritesBackpack[0] = BAGPACK_EN;
		spritesBackpack[1] = BAGPACK_RIGHT;
		spritesBackpack[2] = BAGPACK_LEFT;
		spritesBackpack[3] = BAGPACK_UP;
	}

	public BagPack(BagPack outraBagPack) {
		super(outraBagPack);
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

	@Override
	public Item clone() {
		// Crie uma nova instância do subtipo de item usando o construtor de cópia
		return new BagPack(this);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(spritesBackpack[0], this.getX() - Camera.x, this.getY() - Camera.y, 64, 64, null);
	}
}

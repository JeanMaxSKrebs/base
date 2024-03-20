package tempo;

import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class FaseDaLua {

	private String nomePortugues;
	private String nomeIngles;
	private BufferedImage[] spritesheet;

	private int qtdSprites;

	private BufferedImage[] minguante;
	private BufferedImage[] nova;
	private BufferedImage[] crescente;
	private BufferedImage[] cheia;

	public FaseDaLua(int lua) {
		switch (lua) {
		case 1: {
			this.nomePortugues = "Minguante";
			this.nomeIngles = "Waning Moon";

			this.qtdSprites = 3;
			minguante = new BufferedImage[qtdSprites];

			for (int i = 0; i < qtdSprites; i++) {
				minguante[i] = Game.spritesheet_Moons.getSprite((i * 112), 0, 112, 112);
			}

			this.setSpritesheet(minguante);
		}
		case 2: {
			this.nomePortugues = "Nova";
			this.nomeIngles = "New Moon";

			this.qtdSprites = 2;
			nova = new BufferedImage[qtdSprites];

			for (int i = 0; i < qtdSprites; i++) {
				nova[i] = Game.spritesheet_Moons.getSprite((i * 112), 112, 112, 112);
			}

			this.setSpritesheet(nova);
		}
		case 3: {
			this.nomePortugues = "Crescente";
			this.nomeIngles = "Waxing Moon";

			this.qtdSprites = 3;
			crescente = new BufferedImage[qtdSprites];

			for (int i = 0; i < qtdSprites; i++) {
				crescente[i] = Game.spritesheet_Moons.getSprite((i * 112), 112 * 2, 112, 112);
			}

			this.setSpritesheet(crescente);
		}
		case 4: {
			this.nomePortugues = "Cheia";
			this.nomeIngles = "Full Moon";

			this.qtdSprites = 2;
			cheia = new BufferedImage[qtdSprites];

			for (int i = 0; i < qtdSprites; i++) {
				cheia[i] = Game.spritesheet_Moons.getSprite((i * 112), 112 * 3, 112, 112);
			}

			this.setSpritesheet(cheia);
		}
		default:
			this.nomePortugues = "Cheia";
			this.nomeIngles = "Full Moon";

			qtdSprites = 2;
			cheia = new BufferedImage[qtdSprites];

			for (int i = 0; i < qtdSprites; i++) {
				cheia[i] = Game.spritesheet_Moons.getSprite((i * 112), 112 * 3, 112, 112);
			}
			this.setSpritesheet(cheia);
		}
	}

	public String getNomePortugues() {
		return "Lua " + nomePortugues;
	}

	public String getNomeIngles() {
		return "Moon " + nomeIngles;
	}

	public BufferedImage[] getSpritesheet() {
		return spritesheet;
	}

	public void setSpritesheet(BufferedImage[] spritesheet) {
		this.spritesheet = spritesheet;
	}

	public int getQtdSprites() {
		return qtdSprites;
	}

	public void setQtdSprites(int qtdSprites) {
		this.qtdSprites = qtdSprites;
	}

}

package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import base.Game;
import entities.Bala;
import entities.Enemy;
import entities.EnemyNormal;
import entities.EnemyStrong;
import entities.Entity;
import entities.Player;
import entities.itens.Key;
import entities.itens.SpecialKey;
import entities.itens.comidas.frutas.Fruta;
import entities.itens.comidas.frutas.Maca;
import entities.itens.comidas.frutas.Uva;
import entities.itens.utensilios.BagPack;
import entities.itens.utensilios.Fogueira;
import base.Spritesheet;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;

	public static boolean isDoor = false;
	public static int xDoor = 0;
	public static int yDoor = 0;

	public static final int TILE_SIZE = 112;
	public static final int TILE_SIZE_64 = 64;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			System.out.println("WIDTH");
			System.out.println(WIDTH);
			System.out.println("HEIGHT");
			System.out.println(HEIGHT);
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH * HEIGHT];

			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			for (int xx = 0; xx < WIDTH; xx++) {
				for (int yy = 0; yy < HEIGHT; yy++) {
					int pixelAtual = pixels[xx + (yy * WIDTH)];
					tiles[xx + (yy * WIDTH)] = new Tilefloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
							Tile.TILE_FLOOR);

					if (pixelAtual == 0xFFFFFFFF) {
						// wall
						tiles[xx + (yy * WIDTH)] = new Tilewall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tile.TILE_WALL);
					} else if (pixelAtual == 0xFF7F0037) {
						// door
						tiles[xx + (yy * WIDTH)] = new Normaldoor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tiledoor.TILE_NORMALDOOR);
						Tiledoor door = new Normaldoor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tiledoor.TILE_NORMALDOOR);
						Game.tiledoors.add(door);
					} else if (pixelAtual == 0xFF7F006E) {
						// special door
						tiles[xx + (yy * WIDTH)] = new Specialdoor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tiledoor.TILE_SPECIALDOOR);
						Tiledoor door = new Specialdoor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tiledoor.TILE_SPECIALDOOR);
						Game.tiledoors.add(door);
					} else if (pixelAtual == 0xFF000CFF) {
						// player
						Game.player.setX(xx * TILE_SIZE);
						Game.player.setY(yy * TILE_SIZE);
						Game.player.setWidth(64);
						Game.player.setHeight(96);
						Game.player.setMask(20, 10, 64, 96);

					} else if (pixelAtual == 0xFFFF1500) {
						// normal enemy
						Enemy en = new EnemyNormal(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Entity.ENEMY_EN);
						en.setMask(7, 0, 18, 32);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if (pixelAtual == 0xFFF75D16) {
						// strong enemy
						Enemy en = new EnemyStrong(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2,
								Entity.ENEMY_EN);
						en.setMask(4, 10, 24, 16);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if (pixelAtual == 0xFF4E3333) {
						// bagpack
						BagPack bagpack = new BagPack(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								BagPack.BAGPACK_EN);
						bagpack.setMask(0, 0, 112, 112);
						Game.entities.add(bagpack);
					} else if (pixelAtual == 0xFFD1951F) {
						// Fogueira
						Fogueira fogueira = new Fogueira(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.FOGUEIRA_IT);
						fogueira.setMask(0, 0, 112, 112);
						Game.entities.add(fogueira);
		

					} else if (pixelAtual == 0xFFD4195E) {
						// Maca
						Maca maca = new Maca(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Fruta.MACA_FR);
						maca.setMask(8, 8, 48, 48);
						Game.entities.add(maca);

					} else if (pixelAtual == 0xFF953FFF) {
						// Uva
						Uva uva = new Uva(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Fruta.UVA_FR);
						uva.setMask(8, 8, 48, 48);
						Game.entities.add(uva);

					} else if (pixelAtual == 0xFFFF00A5) {
						// key
						Key key = new Key(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.KEY_EN);
						key.setMask(10, 20, 84, 56);
						Game.entities.add(key);

					} else if (pixelAtual == 0xFFF0BAFF) {
						// specialkey
						SpecialKey specialkey = new SpecialKey(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Entity.SPECIALKEY_EN);
						specialkey.setMask(10, 20, 84, 56);
						Game.entities.add(specialkey);
						
					} else {
						// Piso
						tiles[xx + (yy * WIDTH)] = new Tilefloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tile.TILE_FLOOR);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isDoor() {
		if (isDoor) {
			isDoor = false;
			return true;
		} else
			return isDoor;
	}

	public static void troca() {
		tiles[xDoor + (yDoor * WIDTH)] = new Tilefloor(xDoor * TILE_SIZE, yDoor * TILE_SIZE, TILE_SIZE, TILE_SIZE,
				Tile.TILE_FLOOR);
	}

	public static boolean isFree(int xNext, int yNext, String dir) {
		int x1 = 0, y1 = 0;

		if (dir == "right") {
			x1 = (xNext) / TILE_SIZE;
			y1 = yNext / TILE_SIZE;
		} else if (dir == "left") {
			x1 = (xNext) / TILE_SIZE;
			y1 = yNext / TILE_SIZE;
		} else if (dir == "up") {
			x1 = xNext / TILE_SIZE;
			y1 = (yNext) / TILE_SIZE;
		} else if (dir == "down") {
			x1 = xNext / TILE_SIZE;
			y1 = (yNext) / TILE_SIZE;
		}
//		System.out.println(tiles[x1 + (y1*World.WIDTH)]);
		if (tiles[x1 + (y1 * World.WIDTH)] instanceof Tilewall) {
//			System.out.println("é parede");
			return false;
		} else if (tiles[x1 + (y1 * World.WIDTH)] instanceof Tiledoor) {
			xDoor = x1;
			yDoor = y1;
			isDoor = true;
			return false;
		} else {
			return true;
		}
	}

	public static void restartGame(String fase) {
		Game.entities.clear();
		Game.balas.clear();
		Game.tiledoors.clear();
		Game.enemies.clear();
		Game.frutas.clear();
		Game.tiledoors = new ArrayList<Tiledoor>();
		Game.entities = new ArrayList<Entity>();
		Game.balas = new ArrayList<Bala>();
		Game.enemies = new ArrayList<Enemy>();
		Game.frutas = new ArrayList<Fruta>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.spritesheet_Walls = new Spritesheet("/spritesheet_Walls.png");
		Game.spritesheet_Player = new Spritesheet("/spritesheet_Player.png");
		Game.player = new Player(0, 0, 112, 112, Game.spritesheet_Player.getSprite(0, 112, 112, 112));
		System.out.println("fasen");
		System.out.println(fase);
		Game.world = new World("/" + fase);
		Game.entities.add(Game.player);
		return;
	}

	public void render(Graphics g) {
		int xStart = Camera.x / TILE_SIZE;
		int yStart = Camera.y / TILE_SIZE;

		int xFinal = xStart + (Game.getWIDTH() / TILE_SIZE);
		int yFinal = yStart + (Game.getHEIGHT() / TILE_SIZE);

		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);

			}
		}
	}
}
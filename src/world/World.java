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
import entities.arvores.Arvore;
import entities.arvores.RandomArvoreFactory;
import entities.itens.Item;
import entities.doors.*;
import entities.itens.Key;
import entities.itens.SpecialKey;
import entities.itens.comidas.frutas.Fruta;
import entities.itens.comidas.frutas.Maca;
import entities.itens.comidas.frutas.Uva;
import entities.itens.utensilios.Fogueira;
import entities.itens.utensilios.BagPacks.BagPack;
import base.Spritesheet;

public class World {

	public static Tile[] tiles;
	public static int WIDTH = Game.getWIDTH() * Game.getSCALE();
	public static int HEIGHT = Game.getHEIGHT() * Game.getSCALE();

	public static int xDoor = 0;
	public static int yDoor = 0;

	public final static int TILE_SIZE = 112;
	public static final int TILE_SIZE_64 = 64;

	private static final int NUMERO_DE_MACAS = 25;
	private static final int NUMERO_DE_UVAS = 25;

	private static final int NUMERO_DE_ARVORES = 50;

	private int contadorArvores = 0; // Variável para controlar o contador de hordas
	private int contadorHordas = 0; // Variável para controlar o contador de hordas
	private int FREQUENCIA_HORDE = 10; // Definindo a frequência de horda para 10, o que representa 50% de chance
	private int MAX_HORDE = 10; // Definindo a EM 20 para 10, o que representa 50% de chance

	private static final int NUMERO_DE_INIMIGOS = 50;
	private int NUMERO_DE_INIMIGOS_POR_HORDE = 5;

	int totalZumbisGerados = 0; // Variável para armazenar o total de zumbis gerados

	private RandomArvoreFactory arvoreFactory; // Fábrica de árvores

	public World(String path) {

		try {
			loadMap(path);
		} catch (Exception e) {
			System.out.println("Erro ao carregar mapa.");
			e.printStackTrace();
		}
		try {
			arvoreFactory = new RandomArvoreFactory(); // Inicializa a fábrica de árvores
			spawnEntities();
		} catch (Exception e) {
			System.out.println("Erro ao gerar entidades aleatórias.");
			e.printStackTrace();
		}
	}

	private void spawnEntities() {

//		System.out.println("WIDTH");
//		System.out.println(WIDTH);
		// Gera aleatoriamente arvores de varios tipos e classes diferentes
		for (int i = 0; i < NUMERO_DE_ARVORES; i++) {
//			int x = Game.random(WIDTH);
//			int y = Game.random(HEIGHT);
			int x, y;
			do {
				x = Game.random(WIDTH);
				y = Game.random(HEIGHT);
			} while (!isFree(x * TILE_SIZE, y * TILE_SIZE));
			if (isFree(x * TILE_SIZE, y * TILE_SIZE)) {

				int posX = x * TILE_SIZE;
				int posY = y * TILE_SIZE;
				
				// Cria uma árvore aleatória usando a fábrica
				Arvore arvore = arvoreFactory.createArvore(posX, posY, 112, 112, null);

				if (arvore != null) {
					arvore.setMask(11, 8, 48, 48);
					Game.arvores.add(arvore);
					atualizarContadorArvores();
				}
				
			}
		}

		// Gera aleatoriamente inimigos
		while (totalZumbisGerados < NUMERO_DE_INIMIGOS) {
			verificarNovaHorda();
//			System.out.println("totalZumbisGerados");
//			System.out.println(totalZumbisGerados);
		}
//		System.out.println("contadorArvores");
//		System.out.println(contadorArvores);
//		System.out.println("contadorHordas");
//		System.out.println(contadorHordas);
//		System.out.println("totalZumbisGerados");
//		System.out.println(totalZumbisGerados);
	}

	private void loadMap(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
//			System.out.println("WIDTH");
//			System.out.println(WIDTH);
//			System.out.println("HEIGHT");
//			System.out.println(HEIGHT);
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
					} else if (pixelAtual == 0xFF7F0040) {
						// door
						int adjacentPixel = (xx + 1 < WIDTH) ? pixels[(xx + 1) + (yy * WIDTH)] : -1;
						if (adjacentPixel == pixelAtual) { // Check right
							tiles[xx + (yy * WIDTH)] = new DoubleDoor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE,
									TILE_SIZE, Tile.TILE_WALL, pixelAtual, adjacentPixel);
							// Assuming DoubleDoor is a subclass of Door that handles double doors
						}
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
						Game.player.setMask(5, 10, 54, 84);

					} else if (pixelAtual == 0xFF4E3333) {
						// bagpack
						BagPack bagpack = new BagPack(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								BagPack.BAGPACK_EN);
						bagpack.setMask(0, 0, 112, 112);
						Game.itens.add(bagpack);
					} else if (pixelAtual == 0xFFD1951F) {
						// Fogueira
						Fogueira fogueira = new Fogueira(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Entity.FOGUEIRA_IT);
						fogueira.setMask(0, 0, 112, 112);
						Game.itens.add(fogueira);

					} else if (pixelAtual == 0xFFFF00A5) {
						// key
						Key key = new Key(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Item.KEY_EN);
						key.setMask(10, 20, 84, 56);
						Game.itens.add(key);

					} else if (pixelAtual == 0xFFF0BAFF) {
						// specialkey
						SpecialKey specialkey = new SpecialKey(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Item.SPECIALKEY_EN);
						specialkey.setMask(10, 20, 84, 56);
						Game.itens.add(specialkey);

					} else {
						// Piso
						tiles[xx + (yy * WIDTH)] = new Tilefloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE,
								Tile.TILE_FLOOR);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Erro ao carregar mapa.");
			e.printStackTrace();
		}
	}

	// Método para verificar e iniciar a geração de uma nova horda de zumbis
	private void verificarNovaHorda() {

		if (Game.random(MAX_HORDE) <= MAX_HORDE) {
			int spacingWidth = 40;
			int x, y;

			do {
				x = Game.random(WIDTH);
				y = Game.random(HEIGHT);
			} while (!isFree(x * TILE_SIZE, y * TILE_SIZE)
					|| !isFree(x * TILE_SIZE + (NUMERO_DE_INIMIGOS_POR_HORDE - 1) * spacingWidth,
							y * TILE_SIZE + (NUMERO_DE_INIMIGOS_POR_HORDE - 1) * spacingWidth));

			// Gera aleatoriamente inimigos para uma nova horda
			for (int i = 0; i < NUMERO_DE_INIMIGOS_POR_HORDE; i++) {

				// Decide aleatoriamente entre inimigo normal e forte
				Enemy enemy;
				if (Game.random(10) == 0) { // Chance de 1 em 10
					enemy = new EnemyStrong(x * TILE_SIZE + i * spacingWidth, y * TILE_SIZE, 32, 32, Entity.ENEMY_EN);
					enemy.setMask(7, 0, 18, 32);
				} else {
					enemy = new EnemyNormal(x * TILE_SIZE + i * spacingWidth, y * TILE_SIZE, 32, 32, Entity.ENEMY_EN);
					enemy.setMask(4, 10, 24, 16);
				}
				Game.enemies.add(enemy);
				totalZumbisGerados++; // Incrementa o contador de zumbis gerados
			}
			atualizarContadorHordas();
		} else {
			// Gera apenas um zumbi
			int x, y;
			do {
				x = Game.random(WIDTH);
				y = Game.random(HEIGHT);
			} while (!isFree(x * TILE_SIZE, y * TILE_SIZE));

			// Decide aleatoriamente entre inimigo normal e forte
			Enemy enemy;
			if (Game.random(10) == 0) { // Chance de 1 em 10
				enemy = new EnemyStrong(x * TILE_SIZE, y * TILE_SIZE, 32, 32, Entity.ENEMY_EN);
				enemy.setMask(7, 0, 18, 32);
			} else {
				enemy = new EnemyNormal(x * TILE_SIZE, y * TILE_SIZE, 32, 32, Entity.ENEMY_EN);
				enemy.setMask(4, 10, 24, 16);
			}
			Game.enemies.add(enemy);
			totalZumbisGerados++; // Incrementa o contador de zumbis gerados
		}
	}

	// Método para atualizar o contador de hordas
	public void atualizarContadorArvores() {
		contadorArvores++;
	}

	// Método para atualizar o contador de hordas
	public void atualizarContadorHordas() {
		contadorHordas++;
	}

	public static void troca() {
		tiles[xDoor + (yDoor * WIDTH)] = new Tilefloor(xDoor * TILE_SIZE, yDoor * TILE_SIZE, TILE_SIZE, TILE_SIZE,
				Tile.TILE_FLOOR);
	}

	public static boolean isFree(int xNext, int yNext) {
		int x1 = (xNext) / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;

		// Verifica se x1 e y1 estão dentro dos limites da matriz tiles
		if (x1 < 0 || x1 >= WIDTH || y1 < 0 || y1 >= HEIGHT) {
			return false; // Fora dos limites, não livre
		}

//		System.out.println(tiles[x1 + (y1*World.WIDTH)]);
		if (tiles[x1 + (y1 * World.WIDTH)] instanceof Tilewall) {
//			System.out.println("é uma parede");
			return false; // É uma parede
		} else if (tiles[x1 + (y1 * World.WIDTH)] instanceof Tiledoor) {
			xDoor = x1;
			yDoor = y1;
			return false;
		} else {
			return true; // Livre
		}
	}

	public static boolean isFree(double xNext, double yNext, String dir) {
		double x1 = 0, y1 = 0;

		if (dir == "right") {
			x1 = (int) ((xNext) / TILE_SIZE);
			y1 = (int) (yNext / TILE_SIZE);
		} else if (dir == "left") {
			x1 = (int) ((xNext) / TILE_SIZE);
			y1 = (int) (yNext / TILE_SIZE);
		} else if (dir == "up") {
			x1 = (int) (xNext / TILE_SIZE);
			y1 = (int) ((yNext) / TILE_SIZE);
		} else if (dir == "down") {
			x1 = (int) (xNext / TILE_SIZE);
			y1 = (int) ((yNext) / TILE_SIZE);
		}
		// Verifica se x1 e y1 estão dentro dos limites da matriz tiles
		if (x1 < 0 || x1 >= WIDTH || y1 < 0 || y1 >= HEIGHT) {
			return false; // Fora dos limites, não livre
		}

//		System.out.println(tiles[x1 + (y1*World.WIDTH)]);
		if (tiles[(int) x1 + (int) y1 * World.WIDTH] instanceof Tilewall) {
			return false; // É uma parede
		} else if (tiles[(int) x1 + (int) y1 * World.WIDTH] instanceof Tiledoor) {
			xDoor = (int) x1;
			yDoor = (int) y1;
			return false;
		} else {
			return true; // Livre
		}
	}

	public static void restartGame(String fase) {
		Game.entities.clear();
		Game.balas.clear();
		Game.tiledoors.clear();
		Game.itens.clear();
		Game.frutas.clear();
		Game.enemies.clear();
		Game.tiledoors = new ArrayList<Tiledoor>();
		Game.entities = new ArrayList<Entity>();
		Game.balas = new ArrayList<Bala>();
		Game.itens = new ArrayList<Item>();
		Game.frutas = new ArrayList<Fruta>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.spritesheet_Walls = new Spritesheet("/spritesheet_Walls.png");
		Game.spritesheet_Player = new Spritesheet("/spritesheet_Player.png");
		Game.player = new Player(336, 336, 112, 112, Game.spritesheet_Player.getSprite(0, 112, 112, 112));
		Game.world = new World(fase);
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

	private static World instance;

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public static void setInstance(World world) {
        instance = world;
    }

    public static void createWorld() {
        // Implementação da criação do mundo com base no estado atual
    }
}
package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import base.Game;
import entities.BagPack;
import entities.Bala;
import entities.Boss;
import entities.Enemy;
import entities.EnemyNormal;
import entities.EnemyStrong;
import entities.EnemyX;
import entities.EnemyY;
import entities.Entity;
import entities.HpBag;
import entities.Key;
import entities.Player;
import entities.Power;
import entities.Premium;
import entities.SpecialKey;
import entities.StamineBag;
import graficos.Spritesheet;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH , HEIGHT;

	
	public static final int TILE_SIZE = 32;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH *  HEIGHT];
			
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			for (int xx = 0; xx < WIDTH; xx++) {
				for (int yy = 0; yy < HEIGHT; yy++) {
					int pixelAtual = pixels[xx+(yy*WIDTH)];
					tiles[xx+(yy*WIDTH)] =  new Tilefloor(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);

					if(pixelAtual == 0xFFFFFFFF) {
						//wall
						tiles[xx+(yy*WIDTH)] =  new Tilewall(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_WALL);
					} else if(pixelAtual == 0xFF7F0037) {
						//door
						Tiledoor door = new Normaldoor(xx * TILE_SIZE, yy * TILE_SIZE, Tiledoor.TILE_NORMALDOOR);
						Game.tiledoors.add(door);
					} else if(pixelAtual == 0xFF7F006E) {
						//special door
						Tiledoor door = new Specialdoor(xx * TILE_SIZE, yy * TILE_SIZE, Tiledoor.TILE_SPECIALDOOR);		 
						Game.tiledoors.add(door);
					} else if(pixelAtual == 0xFF000CFF) {
						//player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
						Game.player.setWidth(24);
						Game.player.setHeight(24);
						Game.player.setMask(3, 3, 26, 26);
						
					} else if(pixelAtual == 0xFFFF1500) {
						//normal enemy
						Enemy en = new EnemyNormal(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
						en.setMask(7, 0, 18, 32);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if(pixelAtual == 0xFFF75D16) {
						//strong enemy
						Enemy en = new EnemyStrong(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE*2, TILE_SIZE*2, Entity.ENEMY_EN);
						en.setMask(4, 10, 24, 16);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if(pixelAtual == 0xFF8CC100) {
						Enemy en = new EnemyY(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
						en.setMask(4, 10, 24, 16);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if(pixelAtual == 0xFF8C1500) {
						Enemy en = new EnemyX(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
						en.setMask(4, 10, 24, 16);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if(pixelAtual == 0xFFBF0092) {
						Enemy en = new Boss(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
						en.setMask(4, 10, 24, 16);
//						Game.entities.add(en);
						Game.enemies.add(en);
					} else if(pixelAtual == 0xFFFF00A5) {
						//key
						Key key = new Key(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.KEY_EN);
						key.setMask(6, 10, 18, 10);
						Game.entities.add(key);

					} else if(pixelAtual == 0xFF4E3333) {
						//bagpack
						BagPack bagpack = new BagPack(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.BAGPACK_EN);
						bagpack.setMask(7, 7, 18, 18);
						Game.entities.add(bagpack);
					} else if(pixelAtual == 0xFF00FF15) {
						//hpbag
						HpBag hpbag = new HpBag(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.HPBAG_EN);
						hpbag.setMask(0, 10, 32, 16);
						Game.entities.add(hpbag);
					} else if(pixelAtual == 0xFFFFFF00) {
						//staminebag
						StamineBag staminebag = new  StamineBag(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.STAMINEBAG_EN);
						staminebag.setMask(0, 10, 32, 16);
						Game.entities.add(staminebag);
					} else if(pixelAtual == 0xFFF0BAFF) {
						//specialkey
						SpecialKey specialkey = new SpecialKey(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.SPECIALKEY_EN);
						specialkey.setMask(6, 10, 18, 10);
						Game.entities.add(specialkey);
					} else if(pixelAtual == 0xFF8300FF) {
						//premium
						Game.entities.add(new Premium(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.PREMIUM_EN));
					} else {						
						//floor
						tiles[xx+(yy*WIDTH)] =  new Tilefloor(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
					} 

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 0) / TILE_SIZE;
		int y2 = (yNext + TILE_SIZE - 0) / TILE_SIZE;
		
		int x3 = (xNext + TILE_SIZE - 0)/ TILE_SIZE;
		int y3 = yNext / TILE_SIZE;

		int x4 = xNext / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 0) / TILE_SIZE;

		return !(
				 (tiles[x1 + (y1*World.WIDTH)] instanceof Tilewall) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof Tilewall) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof Tilewall) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof Tilewall)
				 ); 
	}
	
	public static void restartGame(String fase) {
		Game.entities.clear();
		Game.powers.clear();
		Game.balas.clear();
		Game.tiledoors.clear();
		Game.enemies.clear();
		Game.powers = new ArrayList<Power>();
		Game.tiledoors = new ArrayList<Tiledoor>();
		Game.entities = new ArrayList<Entity>();
		Game.balas = new ArrayList<Bala>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.PREMIUM = Player.getPremium();
		Game.player = new Player(0, 0, 32, 32, Game.spritesheet.getSprite(0, 32, 32, 32));
		Game.entities.add(Game.player);
		System.out.println(fase);
		Game.world = new World("/"+fase);
		return;
	}
	
	public void render(Graphics g){
		int xStart = Camera.x / 32;
		int yStart = Camera.y / 32;
		
		int xFinal =  xStart + (Game.WIDTH / 32);
		int yFinal =  yStart + (Game.HEIGHT / 32);
		
		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0	|| xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
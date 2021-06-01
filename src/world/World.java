package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import base.Game;
import entities.Entity;
import entities.Player;
import graficos.Spritesheet;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH , HEIGHT;
	
	public static boolean isDoor = false;
	public static int xDoor = 0;
	public static int yDoor = 0;
	
	public static final int TILE_SIZE = 32;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH * HEIGHT];
			
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			for (int xx = 0; xx < WIDTH; xx++) {
				for (int yy = 0; yy < HEIGHT; yy++) {
					int pixelAtual = pixels[xx+(yy*WIDTH)];
					tiles[xx+(yy*WIDTH)] =  new Tilesky(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_SKY);


					if(pixelAtual == 0xFF000000) {
						//wall
						tiles[xx+(yy*WIDTH)] = new Tilewall(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
						if(yy - 1 > 0 && pixels[xx+( (yy-1) * map.getWidth())] == 0xFF000000) {
							tiles[xx+(yy*WIDTH)] = new Tilewall(xx * TILE_SIZE,  yy * TILE_SIZE, Tile.TILE_GROUND);
							if(xx == 0 || yy == HEIGHT - 1 ||  xx == WIDTH - 1) {
								tiles[xx+(yy*WIDTH)] = new Tilewall(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_WALL);
							}
						}
					} else if(pixelAtual == 0xFF000CFF) {
						//player
						Game.player.setX(xx*Game.TILE);
						Game.player.setY(yy*Game.TILE);
						Game.player.setWidth(Game.TILE-(Game.TILE*25/100));
						Game.player.setHeight(Game.TILE-(Game.TILE*25/100));
						Game.player.setMask(0, 4, Game.TILE, Game.TILE-(Game.TILE*25/100));						
					} else if(pixelAtual == 0xFFFFF38E) {
						//npc
						Game.npc.setX(xx*Game.TILE);
						Game.npc.setY(yy*Game.TILE);
						Game.npc.setWidth(Game.TILE-(Game.TILE*25/100));
						Game.npc.setHeight(Game.TILE-(Game.TILE*25/100));
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isDoor() {
		if(isDoor) {
			isDoor = false;
			return true;
		} else 
			return isDoor;
	}

	public static void troca() {
		tiles[xDoor+(yDoor*WIDTH)] = new Tilefloor(xDoor * TILE_SIZE, yDoor * TILE_SIZE, Tile.TILE_FLOOR);
	}
	
	public static boolean isFree(int xNext, int yNext, String dir) {
		int x1 = 0, y1 = 0;
		
		if(dir == "right") {
			x1 = (xNext) / TILE_SIZE;
			y1 = yNext / TILE_SIZE;
		} else if(dir == "left") {
			x1 = (xNext) / TILE_SIZE;
			y1 = yNext / TILE_SIZE;
		} else if(dir == "up") {
			x1 = xNext / TILE_SIZE;
			y1 = (yNext) / TILE_SIZE;
		} else if(dir == "down") {
			x1 = xNext / TILE_SIZE;
			y1 = (yNext) / TILE_SIZE;
		}
//		System.out.println(tiles[x1 + (y1*World.WIDTH)]);
		if(tiles[x1 + (y1*World.WIDTH)] instanceof Tilewall) {
			return false;
		} else if(tiles[x1 + (y1*World.WIDTH)] instanceof Tilefloor) { 
			return false;
		}  else if(tiles[x1 + (y1*World.WIDTH)] instanceof Tileground) { 
			return false;
		} else {
			return true;
		}
	}
	
	public static void restartGame(String fase) {
		if(fase == "npc.png") {
			Game.world = new World("/"+fase);

		} else {
			Game.entities = new ArrayList<Entity>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(0, 16, 16, 16));
			System.out.println(fase);
			Game.world = new World("/"+fase);
			Game.entities.add(Game.player);
		}
		return;
	}
	
	public void render(Graphics g){
		int xStart = Camera.x / Game.TILE;
		int yStart = Camera.y / Game.TILE;
		
		int xFinal =  xStart + (Game.WIDTH / Game.TILE);
		int yFinal =  yStart + (Game.HEIGHT / Game.TILE);
		
		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0	|| xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

	public static void renderMinimapa() {
		for (int i = 0; i < Game.minimapaPixels.length; i++) {
			Game.minimapaPixels[i] = 0x0000ff;
		}
		for (int xx = 0; xx < WIDTH; xx++) {
			for (int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy * WIDTH)] instanceof Tilewall) {
					Game.minimapaPixels[xx + (yy * WIDTH)] = 0;
				}
			}
		}
		int xPlayer = Game.player.getX()/Game.TILE;
		int yPlayer = Game.player.getY()/Game.TILE;

		Game.minimapaPixels[xPlayer + (yPlayer * WIDTH)] = 0xffffff;
	}
}
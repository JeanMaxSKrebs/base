package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import base.Game;
import entities.itens.Item;
import entities.itens.Key;
import entities.itens.SpecialKey;
import entities.itens.comidas.Comida;
import entities.itens.comidas.frutas.Fruta;
import entities.itens.utensilios.BagPack;
import world.Camera;
import world.Normaldoor;
import world.Tiledoor;
import world.World;

public class Player extends Entity {

	public boolean left, right, up, down;
	public int down_dir = 1, left_dir = 2, right_dir = 3, up_dir = 4;
	public int dir = 1;
	public static double speed = 12;
	private static int keys = 0;
	private static int specialKeys = 0;

	public int premium = 0;

	private static int dodgeChance = 20;
	private static int armor = 0;

	public boolean hasBagpack = false;

	private static List<Item> itens = new ArrayList<>();
	private static List<Fruta> frutasColetadas = new ArrayList<>();
	private static List<Comida> comidasColetadas = new ArrayList<>();

	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = (qtdSprites - 1);
	private boolean moved;

	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;

	public double life = 100;
	public static double maxLife = 100;
	public double stamine = 0;
	public static double maxStamine = 100;

	public boolean usingPower = false;
	public boolean atirar = false;
	public double balas = 0;
	public double maxBalas = 600;

	public double nivel;
	public double qtdNivel;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		itens = new ArrayList<>();
		frutasColetadas = new ArrayList<>();
		comidasColetadas = new ArrayList<>();

		rightPlayer = new BufferedImage[qtdSprites];
		leftPlayer = new BufferedImage[qtdSprites];
		upPlayer = new BufferedImage[qtdSprites];
		downPlayer = new BufferedImage[qtdSprites];

//		public int down_dir = 1, left_dir = 2, right_dir = 3, up_dir = 4;

		for (int i = 0; i < qtdSprites; i++) {
			leftPlayer[i] = Game.spritesheet_Player.getSprite((i * 112), 112 * left_dir, 112, 112);
		}

		for (int i = 0; i < qtdSprites; i++) {
			rightPlayer[i] = Game.spritesheet_Player.getSprite((i * 112), 112 * right_dir, 112, 112);
		}
		for (int i = 0; i < qtdSprites; i++) {
			upPlayer[i] = Game.spritesheet_Player.getSprite((i * 112), 112 * up_dir, 112, 112);
		}

		for (int i = 0; i < qtdSprites; i++) {
			downPlayer[i] = Game.spritesheet_Player.getSprite((i * 112), 112 * down_dir, 112, 112);
		}

	}

	public int danoRecebido(int dano) {

		int danoRecebido = dano - armor;

		if (Game.random(Game.maximumDodge) <= dodgeChance) {
			return 0;
		}

		return danoRecebido;
	}

	public boolean beingAttacked(int dano) {
		int danoReal = danoRecebido(dano);

		if (danoReal > 0) {
			life = life - danoReal;
		}

//		System.out.println(danoReal);
//		System.out.println(life);
		if (iamDead())
			Game.gameState = "GAME_OVER";

		return true;
	}

	public boolean iamDead() {
		if (life <= 0) {
			life = 0;
			return true;
		}

		return false;
	}

	public void obtainItem(Item newItem) {
	    boolean itemExists = false;

	    // Verifica se o item já existe na lista
	    for (Item item : itens) {
	        if (item.getNome().equals(newItem.getNome())) {
	            item.incrementQuantity(); // Incrementa a quantidade do item existente
	            itemExists = true;
	            break;
	        }
	    }

	    // Se o item não foi encontrado na lista, adiciona-o
	    if (!itemExists) {
	        // Adiciona uma nova instância do item final à lista
	        if (newItem instanceof Fruta) {
	            itens.add(new Fruta((Fruta) newItem));
	        } else if (newItem instanceof Comida) {
	            itens.add(new Comida((Comida) newItem));
	        } else {
	            itens.add(newItem); // Se não for uma subclasse, adiciona diretamente
	        }
	    }
	}

	public void checkItems() {
		for (int i = 0; i < Game.entities.size(); i++) {
//			System.out.println("Game.entities.size()");
//			System.out.println(Game.entities.size());
			Entity e = Game.entities.get(i);
//			System.out.println("e");
//			System.out.println(e);

			if (e instanceof BagPack) {
				if (Entity.isColliding(this, e)) {
					hasBagpack = true;
					speed = 10;

					Game.entities.remove(i);

					return;
				}
			}

			// Itens que são guardados
			if (hasBagpack) {
				if (e instanceof Fruta) {
					if (Entity.isColliding(this, e)) {
						Fruta frutaColetada = (Fruta) e;
						System.out.println(e);
						System.out.println(e);

						obtainItem(frutaColetada); // itens.add(frutaColetada);

						frutasColetadas.add(frutaColetada);
						Game.entities.remove(i);
						Game.frutas.remove(e); // Remover da lista de frutas

						return;
					}
				}

				if (e instanceof Comida) {
					if (Entity.isColliding(this, e)) {
						System.out.println("CREDO");

						Comida comidaColetada = (Comida) e;

						obtainItem(comidaColetada);

						comidasColetadas.add(comidaColetada);
						Game.entities.remove(i);
						Game.comidas.remove(e); // Remover da lista de comidas

						return;
					}

				}
				if (e instanceof Key) {
					if (Entity.isColliding(this, e)) {
						Key keyColetada = (Key) e;

						keys++;
						obtainItem(keyColetada); // itens.add(frutaColetada);

						Game.entities.remove(i);
						return;
					}
				}
				if (e instanceof SpecialKey) {
					if (Entity.isColliding(this, e)) {
						SpecialKey specialKeyColetada = (SpecialKey) e;
						specialKeys++;
						obtainItem(specialKeyColetada);

						Game.entities.remove(i);
						return;
					}
				}
//			System.out.println("frutasColetadas");
//			System.out.println(frutasColetadas);
			}
		}
	}

	public void checkDoor() {
		for (int i = 0; i < Game.tiledoors.size(); i++) {
			Tiledoor t = Game.tiledoors.get(i);

//			System.out.println(Game.tiledoors.size());
			if (Tiledoor.willCollide(this, t, (int) speed)) {
//				System.out.println("teste");
				if (t instanceof Normaldoor) {
					if (keys > 0) {
						keys--;
						Game.tiledoors.remove(i);
						World.troca();

					}
				}
				return;

			}

		}
	}

	public int countFrutaEspecifica(String nome) {
		int fruitCount = 0;
		List<Fruta> frutasColetadas = Player.getFrutasColetadas();
		for (Fruta fruta : frutasColetadas) {
			if (nome.equals(fruta.getNome().toUpperCase())) {
				fruitCount++;

			}
		}
		return fruitCount;
	}

	public int countComidaEspecifica(String nome) {
		int foodCount = 0;
		List<Comida> comidasColetadas = Player.getComidasColetadas();
		for (Comida comida : comidasColetadas) {
			if (nome.equals(comida.getNome().toUpperCase())) {
				foodCount++;

			}
		}
		return foodCount;
	}

	public int countItemEspecifico(String nome) {
		int itemCount = 0;
		List<Item> itens = Player.getItens();
		for (Item item : itens) {
			if (nome.equals(item.getNome().toUpperCase())) {
				itemCount++;

			}
		}
		return itemCount;
	}

	public void tick() {
		if (atirar) {
			atirar = false;
			if (balas > 0) {
//					System.out.println("teste");
				balas--;
				atirar = false;
				int dx = 0;
				int dy = 0;
				if (dir == right_dir) {
					dx = 1;
				} else if (dir == left_dir) {
					dx = -1;
				} else if (dir == down_dir) {
					dy = 1;
				} else if (dir == up_dir) {
					dy = -1;
				}

				Bala bala = new Bala(this.getX(), this.getY(), 6, 6, null, dx, dy);
				bala.setMask(13, 13, 6, 6);
				Game.balas.add(bala);
			}
		}

		setMoved(false);

		int midy = (int) (y + masky + mheight / 2);
		int plusy = (int) (y + masky + mheight);
		int minusy = (int) (y + masky);

		int midx = (int) (x + maskx + mwidth / 2);
		int plusx = (int) (x + maskx + mwidth);
		int minusx = (int) (x + maskx);

		if (right) {
			dir = right_dir;
			if (World.isFree(plusx + (int) speed, minusy, "right") && World.isFree(plusx + (int) speed, midy, "right")
					&& World.isFree(plusx + (int) speed, plusy, "right")) {
				x += speed;
				setMoved(true);
			} else if (World.isDoor()) {
				checkDoor();
			}
		}

		if (left) {
			dir = left_dir;
			if (World.isFree(minusx - (int) speed, minusy, "left") && World.isFree(minusx - (int) speed, midy, "left")
					&& World.isFree(minusx - (int) speed, plusy, "left")) {
				x -= speed;
				setMoved(true);
			} else if (World.isDoor()) {
				checkDoor();
			}
		}

		if (down) {
			dir = down_dir;
			if (World.isFree(midx, plusy + (int) speed, "down") && World.isFree(minusx, plusy + (int) speed, "down")
					&& World.isFree(plusx, plusy + (int) speed, "down")) {
				y += speed;
				setMoved(true);
			} else if (World.isDoor()) {
				checkDoor();
			}
		}

		if (up) {
			dir = up_dir;
			if (World.isFree(midx, minusy, "up") && World.isFree(minusx, minusy - (int) speed, "up")
					&& World.isFree(plusx, minusy - (int) speed, "up")) {
				y -= speed;
				setMoved(true);
			} else if (World.isDoor()) {
				checkDoor();
			}
		}
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}

		checkItems();

		Camera.x = Camera.clamp(this.getX() - (Game.getWIDTH() / 2), 0, World.WIDTH * 112 - Game.getWIDTH());
		Camera.y = Camera.clamp(this.getY() - (Game.getHEIGHT() / 2), 0, World.HEIGHT * 112 - Game.getHEIGHT());
	}

	public void render(Graphics g) {
		if (dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasBagpack) {
				g.drawImage(BagPack.getSpritesBackpack(1), this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if (dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasBagpack) {
				g.drawImage(BagPack.getSpritesBackpack(2), this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if (dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasBagpack) {
				g.drawImage(BagPack.getSpritesBackpack(3), this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if (dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

//		// Desenha a caixa delimitadora
//		g.setColor(Color.pink);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
//
//		// Desenha a borda da caixa delimitadora
//		g.setColor(Color.orange);
//		g.drawRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	public void heal(double amount) {
		this.life += amount;

		// Ensure health doesn't exceed maximum
		if (this.life > 100) {
			this.life = 100;
		}
	}

	public double getLife() {
		return life;
	}

	public void setLife(int newLife) {
		this.life = newLife;
	}

	public double getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int newMaxLife) {
		Player.maxLife = newMaxLife;
	}

	public static int getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(int newDodgeChance) {
		Player.dodgeChance = newDodgeChance;
	}

	public static int getArmor() {
		return armor;
	}

	public void setArmor(int newArmor) {
		Player.armor = newArmor;
	}

	public static int getKeys() {
		return keys;
	}

	public static void setKeys(int keys) {
		Player.keys = keys;
	}

	public static int getSpecialKeys() {
		return specialKeys;
	}

	public static void setSpecialKeys(int specialKeys) {
		Player.specialKeys = specialKeys;
	}

	public double getStamine() {
		return stamine;
	}

	public void setStamine(double stamine) {
		this.stamine = stamine;
	}

	public static double getMaxStamine() {
		return maxStamine;
	}

	public static void setMaxStamine(double maxStamine) {
		Player.maxStamine = maxStamine;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public static List<Fruta> getFrutasColetadas() {
		return frutasColetadas;
	}

	public static void setFrutasColetadas(List<Fruta> frutasColetadas) {
		Player.frutasColetadas = frutasColetadas;
	}

	public static List<Comida> getComidasColetadas() {
		return comidasColetadas;
	}

	public static void setComidasColetadas(List<Comida> comidasColetadas) {
		Player.comidasColetadas = comidasColetadas;
	}

	public static double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	public void setSpeed(double speed) {
		Player.speed = speed;
	}

	public static List<Item> getItens() {
		return itens;
	}

	public static void setItens(List<Item> itens) {
		Player.itens = itens;
	}

}

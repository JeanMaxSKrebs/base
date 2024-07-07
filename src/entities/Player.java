package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import base.Game;
import entities.arvores.Arvore;
import entities.doors.Door;
import entities.itens.Item;
import entities.itens.Key;
import entities.itens.SpecialKey;
import entities.itens.comidas.Comida;
import entities.itens.comidas.frutas.Fruta;
import entities.itens.comidas.frutas.Maca;
import entities.itens.comidas.frutas.Uva;
import entities.itens.utensilios.BagPacks.BagPack;
import graficos.ItemAnimation;
import graficos.UI;
import menu.Inventory;
import tempo.Tempo;
import tempo.Tempo.UnidadeTempo;
import world.Camera;
import world.Normaldoor;
import world.Specialdoor;
import world.Tiledoor;
import world.World;

public class Player extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean hasBagpack;
	public boolean run = false;
	public boolean left, right, up, down;
	public int down_dir = 1, left_dir = 2, right_dir = 3, up_dir = 4, dirNone = 5;
	public int dir = 1;
	public static double velocidadeMinimaPermitida = 2;
	public static double velocidadeMaximaPermitida = 32;
	public static double velocidadeMinima = 5;
	public static double velocidadeMaxima = 16;

	public static double bagPackSpeed = 7;
	public static double normalSpeed = 8;
	public static double temporarySpeed;

	public static double speed = 8;
	private static double diagonalSpeed = speed / Math.sqrt(2);
	private static double speedAceleracao = 0.05;

	public static int premium = 0;

	private static int dodgeChance = 20;
	private static int armor = 0;

	private transient List<Item> itensColetados = new ArrayList<>();
	private transient List<Fruta> frutasColetadas = new ArrayList<>();
	private transient List<Comida> comidasColetadas = new ArrayList<>();

	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = (qtdSprites - 1);
	private int qtdSpritesOcioso = 4;
	private int framesOcioso = 0, maxFramesOcioso = 20, indexOcioso = 0, maxIndexOcioso = (qtdSpritesOcioso - 1);
	private boolean moved;

	private transient BufferedImage[] ocioso;
	private transient BufferedImage[] rightPlayer;
	private transient BufferedImage[] leftPlayer;
	private transient BufferedImage[] downPlayer;
	private transient BufferedImage[] upPlayer;

	public double life = 100;
	public static double minLife = 0;
	public static double maxLife = 100;
	public double stamine = 100;
	public static double minStamine = 0;
	public static double maxStamine = 100;
	public static double gastoStamine = 0.065;
	public static double regeneracaoStamine = 0.01;
	public double hunger = 100;
	public static double minHunger = 0;
	public static double maxHunger = 100;
	public double thirsth = 100;
	public static double minThirsth = 0;
	public static double maxThirsth = 100;

	// Constantes para o gasto de estamina e fome/sede
	public double gastoFomeNormal = 0.001;
	public double gastoFomeCorrendo = 0.005;
	public double gastoSedeNormal = 0.001;
	public double gastoSedeCorrendo = 0.005;

	// Constantes para a contagem de tempo de fome/sede
	public boolean contandoFome = false;
	public boolean contandoSede = false;
	// Constantes para o tempoMax de fome/sede
	public double tempoSemFomeMax = 3;
	public double tempoSemSedeMax = 3;
	// Constantes para o dano de fome/sede
	public double danoFome = 0.1;
	public double danoSede = 0.01;

	public static int inventario = 16;

	public boolean usingPower = false;
	public boolean atirar = false;
	public double balas = 0;
	public double maxBalas = 600;

	private static int nivel = 1;
	public static int qtdNivel = 100;
	public static boolean isCollidingItem = false;

	public boolean coletando = false;
	public int tempoColeta = 0; // 3 segundos
//	public int tempoColetaMax = 180; // 3 segundos
	public int tempoColetaMax = 30; // 3 segundos
	public boolean possoColetar = true;
	public boolean coletar = false;
	public int tempoEspera = 0; // 3 segundos
//	public int tempoEsperaMax = 180; // 3 segundos
	public int tempoEsperaMax = 30; // 3 segundos

	private static Player instance;

	private Player() {

	}
    
	public Player getInstance() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
	}

	public void setInstance(Player player) {
		instance = player;
	}

	// Método para atualizar a instância com os valores de outra instância
	public void updateFrom() {
		life = instance.life;
		stamine = instance.stamine;
		hunger = instance.hunger;
		thirsth = instance.thirsth;
		if (instance.itensColetados != null) {
			itensColetados = new ArrayList<>(instance.itensColetados);
		} else {
			itensColetados = new ArrayList<>(); // Ou inicialize com sua implementação padrão
		}

		if (instance.comidasColetadas != null) {
			comidasColetadas = new ArrayList<>(instance.comidasColetadas);
		} else {
			comidasColetadas = new ArrayList<>(); // Ou inicialize com sua implementação padrão
		}

		if (instance.frutasColetadas != null) {
			frutasColetadas = new ArrayList<>(instance.frutasColetadas);
		} else {
			frutasColetadas = new ArrayList<>(); // Ou inicialize com sua implementação padrão
		}
		System.out.println("////////////// comeco");

		System.out.println("instance");
		System.out.println(instance);
		System.out.println("hasBagpack");
		System.out.println(hasBagpack);
		setHasBagpack(instance.hasBagpack);
		

		System.out.println("other.hasBagpack");
		System.out.println(instance.hasBagpack);
		System.out.println("hasBagpack");
		System.out.println(hasBagpack);
		System.out.println("////////////// fim");

	}
    // Outros campos e métodos da classe Player
	//serialização de arrays escrever 
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serializa os campos não-transientes
        oos.writeInt(itensColetados.size());
        for (Item item : itensColetados) {
            oos.writeObject(item);
        }
        oos.writeInt(frutasColetadas.size());
        for (Fruta fruta : frutasColetadas) {
            oos.writeObject(fruta);
        }
        oos.writeInt(comidasColetadas.size());
        for (Comida comida : comidasColetadas) {
            oos.writeObject(comida);
        }
    }
	//serialização de arrays ler 
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Desserializa os campos não-transientes
        int itensSize = ois.readInt();
        itensColetados = new ArrayList<>(itensSize);
        for (int i = 0; i < itensSize; i++) {
            itensColetados.add((Item) ois.readObject());
        }
        int frutasSize = ois.readInt();
        frutasColetadas = new ArrayList<>(frutasSize);
        for (int i = 0; i < frutasSize; i++) {
            frutasColetadas.add((Fruta) ois.readObject());
        }
        int comidasSize = ois.readInt();
        comidasColetadas = new ArrayList<>(comidasSize);
        for (int i = 0; i < comidasSize; i++) {
            comidasColetadas.add((Comida) ois.readObject());
        }
    }

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		itensColetados = new ArrayList<>();
		frutasColetadas = new ArrayList<>();
		setComidasColetadas(new ArrayList<>());

		rightPlayer = new BufferedImage[qtdSprites];
		leftPlayer = new BufferedImage[qtdSprites];
		upPlayer = new BufferedImage[qtdSprites];
		downPlayer = new BufferedImage[qtdSprites];
		ocioso = new BufferedImage[qtdSpritesOcioso];

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

		for (int i = 0; i < qtdSpritesOcioso; i++) {
			ocioso[i] = Game.spritesheet_Player.getSprite((i * 112), 112 * dirNone, 112, 112);
		}

	}

	private void updateSpeed() {
		boolean hasBagpack = Game.player.hasBagpack;
		temporarySpeed = (hasBagpack ? bagPackSpeed : normalSpeed);
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
		iamDead();

		return true;
	}

	public boolean iamDead() {
		if (life <= minLife) {
			life = minLife;
			Game.gameState = "GAME_OVER";
			return true;
		}

		return false;
	}

	public void use(Item newItem) {

		Iterator<Item> iterator = itensColetados.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item.getClass().equals(newItem.getClass())) {
				if (item.getQuantidade() >= 1) {
					item.decrementQuantity();
					if (item instanceof Comida) { // Verifica se o item é uma instância de Comida
						Comida comida = (Comida) item; // Faz o cast para Comida
						this.comer(comida); // Executa a ação de comer
					}
				}
				// Remove o item existente se ele for o ultimo
				if (item.getQuantidade() == 0) {
					iterator.remove();
				}
				break;
			}
		}

	}

	private void comer(Item item) {

	}

	public void dropar(Item newItem) {
		Item newItemCopy = newItem.clone(); // Supondo que a classe Item implemente o método clone()
		Iterator<Item> iterator = itensColetados.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item.getClass().equals(newItem.getClass())) {

				if (item.getQuantidade() >= 1) {
					newItemCopy.setX(Game.player.getX());
					newItemCopy.setY(Game.player.getY());
					newItemCopy.setMask(item.getMaskX(), item.getMaskY(), item.getMaskWidth(), item.getMaskHeight());

					item.decrementQuantity();

					Game.itens.add(newItemCopy);
				}
				// Remove o item existente se ele for o ultimo
				if (item.getQuantidade() == 0) {
					iterator.remove();
				}
				break;
			}

		}
	}

	public void coletar(Item newItem) {
		boolean itemExists = false;

		// Verifica se o item já existe na lista
		for (Item item : itensColetados) {
//			System.out.println(item.getClass());
//			System.out.println(newItem.getClass());
//			System.out.println(item.getNome());
//			System.out.println(newItem.getNome());
			if (item.getClass().equals(newItem.getClass())) {
				item.incrementQuantity(); // Incrementa a quantidade do item existente
				itemExists = true;
				break;
			}

		}

		// Se o item não foi encontrado na lista, adiciona-o
		if (!itemExists) {
//			System.out.println("add");
//			System.out.println(newItem);

			newItem.incrementQuantity(); // Incrementa a quantidade do item existente
//			System.out.println(newItem);
			itensColetados.add(newItem); // Se não for uma subclasse, adiciona diretamente
		}
		iniciarAnimacaoColeta(newItem);
	}

	public void coletar(Fruta newFruta) {
		boolean frutaExists = false;

		// Verifica se o item já existe na lista
		for (Fruta fruta : frutasColetadas) {
//			System.out.println(item.getClass());
//			System.out.println(newItem.getClass());
//			System.out.println(item.getNome());
//			System.out.println(newItem.getNome());
			if (fruta.getClass().equals(newFruta.getClass())) {
				fruta.incrementQuantity(); // Incrementa a quantidade do item existente
				frutaExists = true;
				break;
			}

		}

		// Se o item não foi encontrado na lista, adiciona-o
		if (!frutaExists) {
//			System.out.println("add");
//			System.out.println(newFruta.getSprite());

			newFruta.incrementQuantity(); // Incrementa a quantidade do item existente
//			System.out.println(newItem);
			frutasColetadas.add(newFruta); // Se não for uma subclasse, adiciona diretamente
		}
		iniciarAnimacaoColeta(newFruta);
	}

	public void iniciarAnimacaoColeta(Item item) {
		int startX = Game.getWIDTH() * Game.getSCALE() / 2;
		int startY = Game.getHEIGHT() * Game.getSCALE() / 2;

		// Coordenadas finais na mochila
		int mochilaX = Game.getWIDTH() * Game.getSCALE() - 112 - 5; // Ajuste o tamanho conforme necessário
		int mochilaY = 5; // Ajuste o tamanho conforme necessário

		int targetX = mochilaX; // Defina a coordenada X no meio da mochila
		int targetY = mochilaY; // Defina a coordenada Y no meio da mochila

		ItemAnimation itemAnimation = new ItemAnimation(item.getSprite(), 6000); // Supondo que a duração da animação
																					// seja 2000ms
		Game.itemAnimations.add(itemAnimation);
		itemAnimation.startAnimation(startX, startY, targetX, targetY);
	}

	public void checkFruits() {
		for (int j = 0; j < Game.frutas.size(); j++) {
//			System.out.println("Game.entities.size()");
//			System.out.println(Game.entities.size());
			Item i = Game.frutas.get(j);
//			System.out.println("e");
//			System.out.println(e);
			if (Item.isColliding(this, i)) {
				isCollidingItem = true;
				UI.showColetar = true;
			}

			if (isCollidingItem) {
				isCollidingItem = false;

				if (possoColetar) {

					if (coletando) {
						if (tempoColeta >= tempoColetaMax) {
							coletar = true;
							tempoColeta = 0;

						} else {
							tempoColeta += 1;
						}
					} else {
						tempoColeta = 0;
					}
					if (coletar) {
						coletar = false;
						possoColetar = false;

						// Itens que são guardados
						if (hasBagpack) {

							if (i instanceof Item) {

								Fruta newFruta = (Fruta) i;

								coletar(newFruta);

								Game.frutas.remove(j);
								tempoEspera = tempoEsperaMax;
								return;
							}
						} else {
							Game.openInventory = true;
							Game.messageDisplayStartTime = System.currentTimeMillis(); // Inicia a contagem do tempo de
																						// exibição da
						}

					}
				}
			}
		}
	}

	public void checkItens() {
		for (int j = 0; j < Game.itens.size(); j++) {
//			System.out.println("Game.entities.size()");
//			System.out.println(Game.entities.size());
			Item i = Game.itens.get(j);
//			System.out.println("e");
//			System.out.println(e);
			if (Item.isColliding(this, i)) {
				isCollidingItem = true;
				UI.showColetar = true;
			}

			if (isCollidingItem) {
				isCollidingItem = false;

				if (possoColetar) {

					if (coletando) {
						if (tempoColeta >= tempoColetaMax) {
							coletar = true;
							tempoColeta = 0;

						} else {
							tempoColeta += 1;
						}
					} else {
						tempoColeta = 0;
					}
					if (coletar) {
						coletar = false;
						possoColetar = false;
						tempoEspera = tempoEsperaMax;

						if (i instanceof BagPack) {
							hasBagpack = true;

							Game.itens.remove(j);
							return;
						}

						// Itens que são guardados
						if (hasBagpack) {

							if (i instanceof Item) {

								Item newItem = (Item) i;

								coletar(newItem);

								Game.itens.remove(j);
								return;
							}
						} else {
							Game.openInventory = true;
							Game.messageDisplayStartTime = System.currentTimeMillis(); // Inicia a contagem do tempo de
																						// exibição da
						}

					}
				}
			}
		}
	}

	public void checkDoor() {
		escolhaDoor("Vazio");

		for (int i = 0; i < Game.tiledoors.size(); i++) {
			Tiledoor t = Game.tiledoors.get(i);

//			System.out.println(Game.tiledoors.size());
			if (Tiledoor.willCollide(this, t, (int) speed)) {

				if (t instanceof Normaldoor) {
					escolhaDoor("Normal Key");
//					if (keys > 0) {
//						keys--;
//						Game.tiledoors.remove(i);
//						World.troca();
//
//					}
				}
				if (t instanceof Specialdoor) {
					escolhaDoor("Special Key");
//					if (specialKeys > 0) {
//						specialKeys--;
//						Game.tiledoors.remove(i);
//						World.troca();
//
//					}
				}

				return;

			}
		}
	}

	private void escolhaDoor(String tipo) {
		if (tipo != "Vazio") {
			UI.usarKey = true;
			UI.tipoKey = tipo;
		} else {
			UI.usarKey = false;
			UI.tipoKey = tipo;
		}
	}

	public int countFrutaEspecifica(String nome) {
		int fruitCount = 0;
		List<Fruta> frutasColetadas = getFrutasColetadas();
		for (Fruta fruta : frutasColetadas) {
			if (nome.equals(fruta.getNome().toUpperCase())) {
				fruitCount++;

			}
		}
		return fruitCount;
	}

	public int countComidaEspecifica(String nome) {
		int foodCount = 0;
		List<Comida> comidasColetadas = getComidasColetadas();
		for (Comida comida : comidasColetadas) {
			if (nome.equals(comida.getNome().toUpperCase())) {
				foodCount++;

			}
		}
		return foodCount;
	}

	public int countItemEspecifico(String nome) {
		int itemCount = 0;
		List<Item> itens = getItensColetados();
		for (Item item : itens) {
			if (nome.equals(item.getNome().toUpperCase())) {
				itemCount++;

			}
		}
		return itemCount;
	}

	int umaVez = 0;

	public void tick() {
		
//		System.out.println(getHasBagpack());

		velocidadeMaxima = 16;
		updateSpeed();

		if (hasBagpack) {
			if (umaVez == 0) {
				umaVez++;
				velocidadeMaxima = 15;
				inventario = 40;
				armor = armor + BagPack.getArmorBase();
			}

		} else {
			umaVez = 0;
		}

		if (atirar) {
			atirar = false;
			if (balas > 0) {
//					System.out.println("teste balas");
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

		fome();
		sede();
		correndo();
		mover();
		checkStatus();
		checkItens();
		checkFruits();
		if (moved) {
			checkCollisions();
		}
		if (tempoEspera <= 0) {
			possoColetar = true;

			tempoEspera = tempoEsperaMax;
		} else {
			tempoEspera -= 1;
		}

		Camera.x = Camera.clamp(this.getX() - (Game.getWIDTH() / 2), 0, World.WIDTH * 112 - Game.getWIDTH());
		Camera.y = Camera.clamp(this.getY() - (Game.getHEIGHT() / 2), 0, World.HEIGHT * 112 - Game.getHEIGHT());

		iamDead();
	}

	public void checkCollisions() {
		checkTreeCollision();
		checkEnemyCollision();
		checkItemCollision();
		checkBulletCollision();
		checkDoor();

	}

	private void checkTreeCollision() {
		for (Arvore arvore : Game.arvores) {
			if (Entity.isColliding(this, arvore)) {
				// Lógica de colisão com a árvore
			}
		}
	}

	private void checkEnemyCollision() {
		for (Enemy enemy : Game.enemies) {
			if (Entity.isColliding(this, enemy)) {
				// Lógica de colisão com o inimigo
			}
		}
	}

	private void checkItemCollision() {
		for (Item item : Game.itens) {
			if (Entity.isColliding(this, item)) {
				// Lógica de colisão com o item
			}
		}
	}

	private void checkBulletCollision() {
		for (Bala bala : Game.balas) {
			if (Entity.isColliding(this, bala)) {
				// Lógica de colisão com a bala
			}
		}
	}

	private void checkStatus() {

	}

	void fome() {
		if (hunger > 0) {
			if (run) {
				hunger -= gastoFomeCorrendo;
			} else {
				hunger -= gastoFomeNormal;
			}

		} else if (hunger < 0) {
			hunger = 0;
		}

		if (hunger == 0) {
			if (contandoFome == false) {
				Tempo.iniciarContagem("hunger", hunger);
				contandoFome = true;
			}
			if (Tempo.verificarTempo("hunger", 3, Tempo.UnidadeTempo.DIAS, hunger)) {
				System.out.println("fome");
				life = life - danoFome;
			}
		} else {
			// Se a fome não estiver em 0, remove a contagem
			if (contandoFome == true) {
				Tempo.removerContagem("hunger");
				contandoFome = false;
			}
		}
	}

	void sede() {
		if (thirsth > 0) {
			if (run) {
				if (stamine < 30)
					thirsth -= gastoSedeCorrendo * 2;
				else
					thirsth -= gastoSedeCorrendo;

			} else {
				thirsth -= gastoSedeNormal;
			}

		} else if (thirsth < 0) {
			thirsth = 0;
		}

		if (thirsth == 0) {
			if (contandoSede == false) {
				Tempo.iniciarContagem("thirsth", thirsth);
				contandoSede = true;
			}
			if (Tempo.verificarTempo("thirsth", 3, Tempo.UnidadeTempo.HORAS, thirsth)) {
				System.out.println("sede");
				life = life - danoSede;
			}
		} else {
			if (contandoSede == true) {
				// Se a sede não estiver em 0, remove a contagem
				Tempo.removerContagem("thirsth");
				contandoSede = false;
			}
		}

	}

	private void correndo() {
		if (run) {
			if (stamine >= minStamine) {
				stamine = stamine - gastoStamine;
			}

			if (stamine >= 5) {
				if (speed < velocidadeMaxima) {
					speed = speed + speedAceleracao;
				}
				if (speed >= velocidadeMaxima) {
					speed = velocidadeMaxima;
				}
			} else {
				speed = speed - speedAceleracao;
				if (speed < temporarySpeed) {
					speed = temporarySpeed;
				}
			}

		} else {
			if (stamine < maxStamine) {
				stamine = stamine + regeneracaoStamine;
			}
			speed = speed - speedAceleracao;
			if (speed < temporarySpeed) {
				speed = temporarySpeed;
			}
		}

		diagonalSpeed = speed / Math.sqrt(2);

	}

	private void mover() {

		setMoved(false);

		int plusy = (int) (y + masky + mheight);
		int midy = (int) (y + masky + mheight / 2);
		int minusy = (int) (y + masky);

		int midx = (int) (x + maskx + mwidth / 2);
		int plusx = (int) (x + maskx + mwidth);
		int minusx = (int) (x + maskx);

		// Define as variáveis para as direções diagonais
		boolean upLeft = up && left;
		boolean upRight = up && right;
		boolean downLeft = down && left;
		boolean downRight = down && right;

//		if (upLeft || upRight || downLeft || downRight) {
//			System.out.println(upLeft);
//			System.out.println(upRight);
//			System.out.println(downLeft);
//			System.out.println(downRight);
//		}

		// Move o jogador nas direções diagonais
		if (upLeft) {
			dir = up_dir;
			if (World.isFree(minusx, minusy - diagonalSpeed, "up")
					&& World.isFree(minusx - diagonalSpeed, minusy, "left")) {
				x -= diagonalSpeed;
				y -= diagonalSpeed;
				setMoved(true);
			}
		} else if (upRight) {
			dir = up_dir;
			if (World.isFree(plusx, minusy - diagonalSpeed, "up")
					&& World.isFree(plusx + diagonalSpeed, minusy, "right")) {
				x += diagonalSpeed;
				y -= diagonalSpeed;
				setMoved(true);
			}
		} else if (downLeft) {
			dir = down_dir;
			if (World.isFree(minusx, plusy + diagonalSpeed, "down")
					&& World.isFree(minusx - diagonalSpeed, plusy, "left")) {
				x -= diagonalSpeed;
				y += diagonalSpeed;
				setMoved(true);
			}
		} else if (downRight) {
			dir = down_dir;
			if (World.isFree(plusx, plusy + diagonalSpeed, "down")
					&& World.isFree(plusx + diagonalSpeed, plusy, "right")) {
				x += diagonalSpeed;
				y += diagonalSpeed;
				setMoved(true);
			}
		} else {
			if (right) {
				dir = right_dir;
				if (World.isFree(plusx + (int) speed, minusy, "right")
						&& World.isFree(plusx + (int) speed, midy, "right")
						&& World.isFree(plusx + (int) speed, plusy, "right")) {
					x += speed;
					setMoved(true);
				}
			} else if (left) {
				dir = left_dir;
				if (World.isFree(minusx - (int) speed, minusy, "left")
						&& World.isFree(minusx - (int) speed, midy, "left")
						&& World.isFree(minusx - (int) speed, plusy, "left")) {
					x -= speed;
					setMoved(true);
				}
			}

			if (down) {
				dir = down_dir;
				if (World.isFree(midx, plusy + (int) speed, "down") && World.isFree(minusx, plusy + (int) speed, "down")
						&& World.isFree(plusx, plusy + (int) speed, "down")) {
					y += speed;
					setMoved(true);
				}
			} else if (up) {
				dir = up_dir;
				if (World.isFree(midx, minusy, "up") && World.isFree(minusx, minusy - (int) speed, "up")
						&& World.isFree(plusx, minusy - (int) speed, "up")) {
					y -= speed;
					setMoved(true);
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
			} else {
				framesOcioso++;
				if (framesOcioso == maxFramesOcioso) {
					framesOcioso = 0;
					indexOcioso++;
					if (indexOcioso > maxIndexOcioso)
						indexOcioso = 0;
				}
			}
		}
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

		if (dir == dirNone) {
			g.drawImage(ocioso[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
////		// Desenha a caixa delimitadora
//		g.setColor(Color.pink);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
////
////		// Desenha a borda da caixa delimitadora
//		g.setColor(Color.orange);
//		g.drawRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
	

	private void setHasBagpack(boolean hasBagpack) {
		this.hasBagpack = hasBagpack;
	}
	
	private boolean getHasBagpack() {
		return hasBagpack;
	}

	public void heal(double amount) {
		this.life += amount;

		// Ensure health doesn't exceed maximum
		if (this.life > maxLife) {
			this.life = maxLife;
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

	public static double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	public void setSpeed(double speed) {
		Player.speed = speed;
	}

	public List<Item> getItensColetados() {
		return itensColetados;
	}

	public List<Fruta> getFrutasColetadas() {
		return frutasColetadas;
	}

	public List<Comida> getComidasColetadas() {
		return comidasColetadas;
	}

	public void setItensColetados(List<Item> itensColetados) {
		this.itensColetados = itensColetados;
	}

	public void setFrutasColetadas(List<Fruta> frutasColetadas) {
		this.frutasColetadas = frutasColetadas;
	}

	public void setComidasColetadas(List<Comida> comidasColetadas) {
		this.comidasColetadas = comidasColetadas;
	}

	public int getNivel() {
		return nivel;
	}

	public static void setNivel(int nivel) {
		Player.nivel = nivel;
	}

	public static double getVelocidadeMinima() {
		return velocidadeMinima;
	}

	public static void setVelocidadeMinima(double velocidadeMinima) {
		Player.velocidadeMinima = velocidadeMinima;
	}

	public static double getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

	public static void setVelocidadeMaxima(double velocidadeMaxima) {
		Player.velocidadeMaxima = velocidadeMaxima;
	}

	public int getPremium() {
		return premium;
	}

	public static void setPremium(int premium) {
		Player.premium = premium;
	}

	public static double getNormalSpeed() {
		return normalSpeed;
	}

	public static void setNormalSpeed(double normalSpeed) {
		Player.normalSpeed = normalSpeed;
	}

	public static double getMinLife() {
		return minLife;
	}

	public static void setMinLife(double minLife) {
		Player.minLife = minLife;
	}

	public static double getMinStamine() {
		return minStamine;
	}

	public static void setMinStamine(double minStamine) {
		Player.minStamine = minStamine;
	}

	public double getHunger() {
		return hunger;
	}

	public void setHunger(double hunger) {
		this.hunger = hunger;
	}

	public static double getMinHunger() {
		return minHunger;
	}

	public static void setMinHunger(double minHunger) {
		Player.minHunger = minHunger;
	}

	public static double getMaxHunger() {
		return maxHunger;
	}

	public static void setMaxHunger(double maxHunger) {
		Player.maxHunger = maxHunger;
	}

	public static double getMinThirsth() {
		return minThirsth;
	}

	public static void setMinThirsth(double minThirsth) {
		Player.minThirsth = minThirsth;
	}

	public static double getMaxThirsth() {
		return maxThirsth;
	}

	public static void setMaxThirsth(double maxThirsth) {
		Player.maxThirsth = maxThirsth;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public static void setMaxLife(double maxLife) {
		Player.maxLife = maxLife;
	}

	public double getThirsth() {
		return thirsth;
	}

	public void setThirsth(double thirsth) {
		this.thirsth = thirsth;
	}

	public static int getInventario() {
		return inventario;
	}

	public static void setInventario(int inventario) {
		Player.inventario = inventario;
	}

	public boolean isAtirar() {
		return atirar;
	}

	public void setAtirar(boolean atirar) {
		this.atirar = atirar;
	}

	public static int getQtdNivel() {
		return qtdNivel;
	}

	public void setQtdNivel(int qtdNivel) {
		Player.qtdNivel = qtdNivel;
	}

}

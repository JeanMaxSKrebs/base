package base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import entities.Bala;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import entities.arvores.Arvore;
import entities.doors.Door;
import entities.itens.Item;
import entities.itens.comidas.Comida;
import entities.itens.comidas.frutas.Fruta;
import graficos.ItemAnimation;
import graficos.UI;
import menu.Inventory;
import menu.MenuCarregar;
import menu.MenuPause;
import menu.MenuPrincipal;
import menu.MenuSalvar;
import menu.Options;
import menu.Status;
import tempo.DiaDaSemana;
import tempo.Tempo;
import world.Tile;
import world.Tiledoor;
import world.World;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable, KeyListener {

	private final static int WIDTH = 1120;
	private final static int HEIGHT = 560;
	private final static int SCALE = 1;

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static int FPS;
	private Thread thread;
	private boolean isRunning;

	public static int maximumCritic = 100;
	public static int maximumDodge = 50;

	public static Spritesheet spritesheet;
	public static Spritesheet spritesheet_Moons;
	public static Spritesheet spritesheet_Items;
	public static Spritesheet spritesheet_UsableItens;
	public static Spritesheet spritesheet_Walls;
	public static Spritesheet spritesheet_Doors;
	public static Spritesheet spritesheet_Foods;
	public static Spritesheet spritesheet_Fruits;
	public static Spritesheet spritesheet_Trees;
	public static Spritesheet spritesheet_Player;

	public static Player player;
	public static List<Entity> entities;
	public static List<Arvore> arvores;
	public static List<Enemy> enemies;
	public static List<Item> itens;
	public static List<Comida> comidas;
	public static List<Fruta> frutas;
	public static List<Tile> tiles;
	public static List<Tiledoor> tiledoors;
	public static List<Bala> balas;
	public static List<ItemAnimation> itemAnimations;

	public BufferedImage image;

	public static World world;
	public static String previousGameState = "MENUPRINCIPAL";
	public static String gameState = "MENUPRINCIPAL";
	public static String ILHA = "INICIAL2";

	public static UI ui;

	public Tempo tempo;
	public static Status status;
	public static Options options;
	public MenuPrincipal menuPrincipal;
	public MenuPause menuPause;
	public MenuSalvar menuSalvar;
	public MenuCarregar menuCarregar;

	public Inventory inventory;
	public static boolean openInventory = false;
	public static boolean movimentarEnemys = false;

	public static long messageDisplayStartTime = 0;
	public static long MESSAGE_DISPLAY_DURATION = 3000; // 3 segundos em milissegundos

	public static boolean saveGame = false;
	private boolean restartGame = false;
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;

	// mudar linguagem
//	public static String linguagem = "Inglês";
	public static String linguagem = "Português";

	public Game() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(getWIDTH() * getSCALE(), getHEIGHT() * getSCALE()));

		initFrame();
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ui.mouseClicked(e);
			}
		});
		this.requestFocusInWindow();
		ui = new UI();
		image = new BufferedImage(getWIDTH(), getHEIGHT(), BufferedImage.TYPE_INT_RGB);

		balas = new ArrayList<Bala>();
		tiles = new ArrayList<Tile>();
		tiledoors = new ArrayList<Tiledoor>();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		itens = new ArrayList<Item>();
		comidas = new ArrayList<Comida>();
		arvores = new ArrayList<Arvore>();
		frutas = new ArrayList<Fruta>();
		itemAnimations = new ArrayList<ItemAnimation>();

		spritesheet = new Spritesheet("/spritesheet.png");
		spritesheet_Moons = new Spritesheet("/spritesheet_Moons.png");
		spritesheet_Items = new Spritesheet("/spritesheet_Items.png");
		spritesheet_UsableItens = new Spritesheet("/spritesheet_UsableItens.png");
		spritesheet_Walls = new Spritesheet("/spritesheet_Walls.png");
		spritesheet_Doors = new Spritesheet("/spritesheet_Doors.png");
		spritesheet_Foods = new Spritesheet("/spritesheet_Foods.png");
		spritesheet_Fruits = new Spritesheet("/spritesheet_Fruits.png");
		spritesheet_Trees = new Spritesheet("/spritesheet_Trees.png");
		spritesheet_Player = new Spritesheet("/spritesheet_Player.png");

		player = new Player(0, 0, 112, 112, spritesheet_Player.getSprite(0, 112, 112, 112));
//		world = new World("/teste.png");	
		world = new World("/" + ILHA + ".png");
		entities.add(player);
		status = new Status();
		options = new Options();
		menuPause = new MenuPause();
		menuPrincipal = new MenuPrincipal();
		menuSalvar = new MenuSalvar();
		menuCarregar = new MenuCarregar();
		tempo = new Tempo();
		inventory = new Inventory();

		// Start in-game time counter
		Tempo.timeStart = System.currentTimeMillis();
		Tempo.timeElapsedSeconds = 0;
	}

	public void initFrame() {
		frame = new JFrame("Sobrevivência");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
//		System.out.println("Game.frutas");
//		System.out.println(Game.frutas.size());

		System.out.println("gameState123");
		System.out.println(gameState);
		

		if (gameState == "NORMAL") {
			tempo.tick();

			// colocar entidades na tela
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			// colocar itemAnimations na tela
			for (int i = 0; i < itemAnimations.size(); i++) {
				ItemAnimation e = itemAnimations.get(i);
				e.tick();
			}

			// movimentar enemies
			for (int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				e.tick();
			}
			// mover itens
			for (int i = 0; i < itens.size(); i++) {
				Item e = itens.get(i);
				e.tick();
			}
			// mover comidas
			for (int i = 0; i < comidas.size(); i++) {
				Comida e = comidas.get(i);
				e.tick();
			}
			// girar frutas
			for (int i = 0; i < frutas.size(); i++) {
				Fruta e = frutas.get(i);
				e.tick();
			}
			// mecher arvores
			for (int i = 0; i < arvores.size(); i++) {
				Arvore e = arvores.get(i);
				e.tick();
			}
			// mudar tiles
			for (int i = 0; i < tiles.size(); i++) {
				Tile e = tiles.get(i);
				e.tick();
			}

			for (int i = 0; i < balas.size(); i++) {
				balas.get(i).tick();
			}

		} else if (gameState == "GAME_OVER") {
			framesGameOver++;

			if (framesGameOver == 30) {
				framesGameOver = 0;
				if (showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver = true;
			}
			if (restartGame) {
				restartGame = false;
				previousGameState = gameState;
				gameState = "NORMAL";
				String newWorld = ("/" + ILHA + ".png");
				World.restartGame(newWorld);

			}
			GameSaveManager.tick();

		} else if (gameState == "MENUPRINCIPAL") {
			menuPrincipal.tick();
		} else if (gameState == "CARREGAR") {
			menuCarregar.tick();
		} else if (gameState == "SALVAR") {
			menuSalvar.tick();
		} else if (gameState == "MENUPAUSE") {
			menuPause.tick();
		} else if (gameState == "STATUS") {
			status.tick();
		} else if (gameState == "OPTIONS") {
			options.tick();
		} else if (gameState == "INVENTORY") {
			inventory.tick();
		}
		
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

//		Graphics2D g2 = (Graphics2D) g;
		// render mundo
		world.render(g);

//		//render itens
		for (int i = 0; i < itens.size(); i++) {
			Item f = itens.get(i);
			f.render(g);
		}
//		//render comidas
		for (int i = 0; i < comidas.size(); i++) {
			Comida f = comidas.get(i);
			f.render(g);
		}

//		//render arvores
		for (int i = 0; i < arvores.size(); i++) {
			Arvore a = arvores.get(i);
			a.render(g);
		}
//		//render frutas
		for (int i = 0; i < frutas.size(); i++) {
			Fruta f = frutas.get(i);
			f.render(g);
		}
		// render tiles
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);
			t.render(g);
		}
		// render tiledoors
		for (int i = 0; i < tiledoors.size(); i++) {
			Tiledoor t = tiledoors.get(i);
			t.render(g);
		}

//		//render enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.render(g);
		}

		// render balas
		for (int i = 0; i < balas.size(); i++) {
			balas.get(i).render(g);
		}

		// render entidades
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}

		ui.render(g);
//		//render itemAnimations
		for (int i = 0; i < itemAnimations.size(); i++) {
			ItemAnimation f = itemAnimations.get(i);
			f.render(g);
		}
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * getSCALE(), HEIGHT * getSCALE(), null);

		if (Game.saveGame == true) {
			g.setColor(Color.black);
			g.setFont(new Font("calibri", Font.BOLD, 48));
			g.drawString("Jogo Salvo", ((WIDTH * getSCALE() / 3)), ((HEIGHT * getSCALE() / 3) + 50));
		}

		if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * getSCALE(), HEIGHT * getSCALE());
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.fillRect(0, 0, WIDTH * getSCALE(), HEIGHT * getSCALE());
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", (WIDTH / 2), (HEIGHT * getSCALE() / 2));
			if (showMessageGameOver)
				g.drawString(">> PRESSIONE ENTER <<", 24, (HEIGHT * getSCALE() / 2) + 96);
		} else if (gameState == "WIN") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 0, WIDTH * getSCALE(), HEIGHT * getSCALE());
			g.setFont(new Font("Arial", Font.BOLD, 32));
			g.fillRect(0, 0, WIDTH * getSCALE(), HEIGHT * getSCALE());
			g.setColor(Color.white);
			g.drawString("Parabéns você Finalizou o Jogo", WIDTH * getSCALE() / 8, HEIGHT * getSCALE() / 2);
			if (showMessageGameOver)
				g.drawString(">> PRESSIONE ENTER <<", WIDTH * getSCALE() / 5, (HEIGHT * getSCALE() / 2) + 96);
		} else if (gameState == "MENUPRINCIPAL") {
			menuPrincipal.render(g);
		} else if (gameState == "CARREGAR") {
			menuCarregar.render(g);
		} else if (gameState == "SALVAR") {
			menuSalvar.render(g);
		} else if (gameState == "MENUPAUSE") {
			menuPause.render(g);
		} else if (gameState == "STATUS") {
			status.render(g);
		} else if (gameState == "OPTIONS") {
			options.render(g);
		} else if (gameState == "INVENTORY") {
			inventory.render(g);
		}

		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		System.out.println("Jogo Rodando...");
		requestFocus();

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
//				System.out.println("FPS: " + frames);
				FPS = frames;

				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// Método para lidar com os eventos de mouse
	public void mouseClicked(MouseEvent e) {
		ui.mouseClicked(e);
		System.out.println("teste clque");
	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if (gameState == "STATUS") {
				status.up = true;
			}
			if (gameState == "OPTIONS") {
				options.up = true;
			}
			if (gameState == "CARREGAR") {
				menuCarregar.up = true;
			}
			if (gameState == "SALVAR") {
				menuSalvar.up = true;
			}
			if (gameState == "MENUPRINCIPAL") {
				menuPrincipal.up = true;
			}
			if (gameState == "MENUPAUSE") {
				menuPause.up = true;
			}
			if (gameState == "INVENTORY") {
				inventory.up = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if (gameState == "STATUS") {
				status.down = true;
			}
			if (gameState == "OPTIONS") {
				options.down = true;
			}
			if (gameState == "CARREGAR") {
				menuCarregar.down = true;
			}
			if (gameState == "SALVAR") {
				menuSalvar.down = true;
			}
			if (gameState == "MENUPRINCIPAL") {
				menuPrincipal.down = true;
			}
			if (gameState == "MENUPAUSE") {
				menuPause.down = true;
			}
			if (gameState == "INVENTORY") {
				inventory.down = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			if (gameState == "INVENTORY") {
				inventory.right = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			if (gameState == "INVENTORY") {
				inventory.left = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.atirar = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;
			if (gameState == "STATUS") {
				status.enter = true;
			}

			if (gameState == "OPTIONS") {
				options.enter = true;
			}
			if (gameState == "CARREGAR") {
				menuCarregar.enter = true;
			}
			if (gameState == "SALVAR") {
				menuSalvar.enter = true;
			}

			if (gameState == "MENUPRINCIPAL") {
				menuPrincipal.enter = true;
			}

			if (gameState == "MENUPAUSE") {
				menuPause.enter = true;
			}

			if (gameState == "INVENTORY") {
				inventory.enter = true;
			}
			if (gameState == "NORMAL") {
				player.coletando = true;
			}
		}

		if (gameState.equals("NORMAL")) {

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
				previousGameState = gameState;
				gameState = "MENUPAUSE";
			}
			if (e.getKeyCode() == KeyEvent.VK_I) {
				if (!Game.player.getHasBagpack()) {
					Game.openInventory = true;
					messageDisplayStartTime = System.currentTimeMillis(); // Inicia a contagem do tempo de exibição da
																			// mensagem
				} else {
					Inventory.entrouInventario = true;
					Inventory.pause = true;
					previousGameState = gameState;
					gameState = "INVENTORY";
				}

			}

			if (e.getKeyCode() == KeyEvent.VK_L) {
				ui.mensagem = true;
				messageDisplayStartTime = System.currentTimeMillis(); // Inicia a contagem do tempo de exibição da

			}
			if (e.getKeyCode() == KeyEvent.VK_K) {
				movimentarEnemys = !movimentarEnemys;
			}

			if (e.getKeyCode() == KeyEvent.VK_R) {
				String newWorld = "/" + ILHA + ".png";
				World.restartGame(newWorld);
			}

			if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
				player.run = true;
//				player.stamine = 0;
//				player.hunger = 0;
//				player.thirsth = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_3) {
				Tempo.add(3, Tempo.UnidadeTempo.HORAS);
			}
			if (e.getKeyCode() == KeyEvent.VK_1) {
				Tempo.add(1, Tempo.UnidadeTempo.HORAS);
			}
		} else if (gameState.equals("INVENTORY")) {
			if (e.getKeyCode() == KeyEvent.VK_I) {
				previousGameState = gameState;
				gameState = "NORMAL";
				Game.openInventory = false;
				Inventory.pause = false;
			}

		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
			player.run = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			player.coletando = false;
		}
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static int getSCALE() {
		return SCALE;
	}

	public static int random(int value) {
		Random random = new Random();
		int r = random.nextInt(value);
		return r;
	}

	// Método random que aceita um intervalo de inteiros
	public static int random(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

}
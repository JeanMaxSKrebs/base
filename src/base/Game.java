package base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning;

	public static final int WIDTH = 320;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
	
	private BufferedImage image;
	
	public static List<Entity> entities;

	
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public UI ui;
	public MenuPrincipal menu_principal;
	public MenuPersonagem menu_personagem;
	public MenuCriacao menu_criacao;

	
	public static String gameState = "MENU_CRIACAO";
	public static int maximumDodge = 100;
	public static int maximumCritic = 100;
	private boolean restartGame;
	private static boolean saveGame;
	private int framesGameOver;
	private boolean showMessageGameOver = false;
	
	public static double LIFE = 100;
	public static double STAMINE = 0;
	
	public static int CUR_LEVEL = 1;
	public static int MAX_LEVEL = 5;
	
	 
	public Game() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		initFrame();
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();

		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 32, 32, spritesheet.getSprite(0, 32, 32, 32));
//		world = new World("/teste.png");
		world = new World("/fase"+CUR_LEVEL+".png");
		entities.add(player);
		
		menu_principal = new MenuPrincipal();
		menu_personagem = new MenuPersonagem();
		menu_criacao = new MenuCriacao();
		
	}

	public void initFrame() {
		frame = new JFrame("Teste");
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
//		System.out.println("teste");
		Game game = new Game();
		game.start();
	}
	
	
	public void tick() {
//		System.out.println("Tick");
		if(gameState  == "NORMAL") {
			if(saveGame) {
				saveGame = false;
				String[] opt1 = {"fase"};
				int[] opt2 = {CUR_LEVEL};
				Menu.saveGame(opt1, opt2, 10);

			}
			restartGame = false;
//			for(int i=0; i<entities.size(); i++) {
//				Entity e = entities.get(i);
//				e.tick();
//			}
		} else if(gameState  == "MENU_PRINCIPAL") {
			menu_principal.tick();
		} else if(gameState  == "MENU_PERSONAGEM") {
			menu_personagem.tick();
		} else if(gameState  == "MENU_CRIACAO") {
			menu_criacao.tick();
		} else if(gameState == "NEXT") {
			CUR_LEVEL = CUR_LEVEL + 1;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL = 1;
			}
			LIFE = Game.player.getLife();
			STAMINE = Game.player.getStamine();
			String newWorld = "fase"+CUR_LEVEL+".png";
			World.restartGame(newWorld);
			Game.player.setLife((int) LIFE);
			Game.player.setStamine(STAMINE);
			Game.player.setArmor(0);

			gameState = "NORMAL";
		} else if(gameState == "GAME_OVER") {
			framesGameOver++;
			
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver = true;
			}
			if(restartGame) {
				restartGame = false;
				gameState = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "fase"+CUR_LEVEL+".png";
				World.restartGame(newWorld);

			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//render mundo
		world.render(g);
		//render entidades
		for(int i=0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		//render UI
		ui.render(g);
		
		g.dispose();		

		g = bs.getDrawGraphics();
		g.setColor(new Color(51, 51, 51));
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		if(Game.saveGame == true) {
			g.setColor(Color.black);
			g.setFont(new Font("calibri", Font.BOLD, 48));
			g.drawString("Jogo Salvo",  ((Game.WIDTH*Game.SCALE/3)), ((Game.HEIGHT*Game.SCALE/3)+50));
		}
		
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER",  (WIDTH/2), (HEIGHT*SCALE/2));
			if(showMessageGameOver)
				g.drawString(">> PRESSIONE ENTER <<",  24, (HEIGHT*SCALE/2)+96);
		} else if(gameState == "WIN") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("Arial", Font.BOLD, 32));
			g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setColor(Color.white);
			g.drawString("Parabéns você Finalizou o Jogo",  WIDTH*SCALE/8, HEIGHT*SCALE/2);
			if(showMessageGameOver)
				g.drawString(">> PRESSIONE ENTER <<",  WIDTH*SCALE/5, (HEIGHT*SCALE/2)+96);
		} else if(gameState == "MENU_PRINCIPAL") {
			menu_principal.render(g);
		} else if(gameState == "MENU_PERSONAGEM") {
			menu_personagem.render(g);
		} else if(gameState  == "MENU_CRIACAO") {
			menu_criacao.render(g);
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
		//click na tela do inicio
		requestFocus();
		System.out.println("Jogo Rodando...");
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now-lastTime) / ns;
			lastTime = now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if(gameState == "MENU_PRINCIPAL") {
				menu_principal.up = true;
			} else if(gameState == "MENU_PERSONAGEM") {
				menu_personagem.up = true;
			} else if(gameState == "MENU_CRIACAO") {
				menu_criacao.up = true;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S){
			player.down = true;
			if(gameState == "MENU_PRINCIPAL") {
				menu_principal.down = true;
			} else if(gameState == "MENU_PERSONAGEM") {
				menu_personagem.down = true;
			} else if(gameState == "MENU_CRIACAO") {
				menu_criacao.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;
			if(gameState == "MENU_PRINCIPAL") {
				menu_principal.enter = true;
			} else if(gameState == "MENU_PERSONAGEM") {
				menu_personagem.enter = true;
			}  else if(gameState == "MENU_CRIACAO") {
				menu_criacao.enter = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
			gameState = "MENU_PRINCIPAL";
			Menu.pause = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(gameState == "NORMAL")
				saveGame = true;
		}
	}

 	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			player.down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
	}
 	
	public static int random(int value) {
		Random random = new Random();
		int r = random.nextInt(value);
		return r;
	}
}
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
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Entity;
import entities.Npc;
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
	public static final int TILE = 32;
	
	private BufferedImage image;
	
	public static List<Entity> entities;

	
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Npc npc;
	
	public UI ui;
	public MenuPrincipal menu_principal;
	public MenuPersonagem menu_personagem;
	public static MenuCriacao menu_criacao;
	public static MenuClasse menu_classe;
	public MenuPause menu_pause;
	
	public int[] pixels;
	public static int[] minimapaPixels;
	public static BufferedImage minimapa;
	
	public static String cutsceneState = "npc";
	public static int state = 0;

	
	public static String gameState = "MENU_PRINCIPAL";
	public static double gravidade = 5;
	public static int maximumDodge = 100;
	public static int maximumCritic = 100;
	private boolean restartGame;
	private static boolean saveGame;
	private int framesGameOver;
	private boolean showMessageGameOver = false;
	private int cont = 60;
	private int contMax = 60;

	private int cutsceneCont = 0;
	private int cutsceneContMax = 180;
	private int signalState = 0;
	private String cutsceneMsg;
	
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
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();

		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 32, 32, spritesheet.getSprite(0, 32, 32, 32));
		npc = new Npc(0, 0, 32, 32, spritesheet.getSprite(0, 288, 32, 32));
		
		//		world = new World("/teste.png");
		world = new World("/npc.png");

		entities.add(npc);
		entities.add(player);
		
		menu_principal = new MenuPrincipal();
		menu_personagem = new MenuPersonagem();		
		menu_criacao = new MenuCriacao();
		menu_classe = new MenuClasse();
		menu_pause = new MenuPause();
		
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
		
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
//		System.out.println(Game.player);
//		System.out.println("Tick");
		if(gameState  == "NORMAL") {
			if(saveGame) {
				saveGame = false;
				String[] opt1 = {"fase"};
				int[] opt2 = {CUR_LEVEL};
				Menu.saveGame(opt1, opt2, 10);

			}
			restartGame = false;
			if(cutsceneState == "entrada") {
				Player p = Game.player;
				p.speed = 1;
				p.tick();

				if(p.getX() < 500) {
					p.right = true;
				} else {
					p.right = false;
				}
				
				if(p.getY() > 900) {
					p.up = true;
				} else {
					p.up = false;
				}
				if(p.right == false && p.up == false) {
					cutsceneState = "jogando";
				}

			} else if(cutsceneState == "npc") {
				Player p = Game.player;
				double xPlayer = p.getX() + 1;
				double yPlayer = p.getY() - 2;

				for(int i=0; i<entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();
				}

				if(state == 0) {
//					System.out.println(yPlayer);
					if(yPlayer > 32) {
//						p.setY(32);
//						p.setX(250);
						p.setY((int)yPlayer);
					} else {
						state++;
					}
				} else if(state == 1) {
					if(xPlayer < 250) {
						p.setX((int)xPlayer);
					} else {
						state++;
					}
				} else if(state == 2) {
					cutsceneState = "jogando";
				}
//				System.out.println(state);

				
			} else if(cutsceneState == "comecar") {
				
			} else if(cutsceneState == "jogando") {
				for(int i=0; i<entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();
				}
			}
		} else if(gameState  == "MENU_PRINCIPAL") {
			menu_principal.tick();
		} else if(gameState  == "MENU_PERSONAGEM") {
			menu_personagem.tick();
		} else if(gameState  == "MENU_CRIACAO") {
			 if(cutsceneState == "nascimento") {
				if(signalState == 0) {
					cutsceneCont++;
				} else if(signalState == 1) {
					cutsceneCont--;
				}
				
				if(cutsceneCont == cutsceneContMax) {
					signalState++;
				} else if(cutsceneCont == 0) {
					cutsceneState = "";
					signalState--;
				}
			} else if(cutsceneState == "morte") { 
				if(player.getIdade() == "5") {
					cutsceneMsg = "Você morreu atropelado, enquanto aprendia a andar de bicicleta";
				} else if(player.getIdade() == "25") {
					cutsceneMsg = "Você morreu drogado em um beco escuro, quando descobriu que foi corno";
				} else if(player.getIdade() == "45") {
					cutsceneMsg = "Você morreu por estresse acumulado, quando descobriu que foi demitido";
				} else if(player.getIdade() == "65") {
					cutsceneMsg = "Você morreu de problemas cardiovasculares, logo que se aposentou";
				}
				
//				System.out.println(cutsceneMsg);
//				System.out.println(signalState);
				if(signalState == 0) {
					cutsceneCont++;
				} else if(signalState == 1) {
					cutsceneCont--;
				}
				
				if(cutsceneCont == cutsceneContMax) {
					signalState++;
				} else if(cutsceneCont < -100) {
					signalState--;
					
					cutsceneState = "npc";
					gameState = "NORMAL";
				}
			} else {
				menu_criacao.tick();
			}
		} else if(gameState  == "MENU_CLASSE") {
			 if(cutsceneState == "nascimento") {
					if(signalState == 0) {
						cutsceneCont++;
					} else if(signalState == 1) {
						cutsceneCont--;
					}
					
					if(cutsceneCont == cutsceneContMax) {
						signalState++;
					} else if(cutsceneCont == 0) {
						cutsceneState = "";
						signalState--;
					}
			 } else if(cutsceneState == "morte") { 
					if(signalState == 0) {
						cutsceneCont++;
					} else if(signalState == 1) {
						cutsceneCont--;
					}
					
					if(cutsceneCont == cutsceneContMax) {
						signalState++;
					} else if(cutsceneCont == 0) {
						cutsceneState = "";
						signalState--;
					}
			 } else {
				 menu_classe.tick();
			 }
		} else if(gameState  == "MENU_PAUSE") {
			menu_pause.tick();
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
		
		if(cont < contMax) {
			cont++;
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
			if(cutsceneState == "nascimento") {
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 32));
				int camera = 2;
				
				if(signalState == 0) {
					g.setColor(Color.white);
					g.fillOval(cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont));
					g.setColor(Color.black);
					g.drawString("Você nasceu no planeta TERRA",  WIDTH*SCALE/6, HEIGHT*SCALE/2);
				} else if(signalState == 1) {
					g.setColor(Color.white);
					g.fillOval(cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont));
					if(cutsceneCont < cutsceneContMax-15)
						g.drawImage(Game.menu_criacao.spriteBase, 288, 288, 64, 64, null);
//					g.drawImage(Game.spritesheet.getSprite(64, 256, 32, 32), cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont), null);
				}
			} else if(cutsceneState == "morte") {
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 32));
				int camera = 2;
				
				if(signalState == 0) {
					g.setColor(Color.white);
					g.fillOval(cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont));
					if(cutsceneCont < cutsceneContMax-30) {
						g.drawImage(Game.menu_criacao.spriteBase, 288, 288, 64, 64, null);
						if(Game.menu_criacao.currentIdade > 0) {
							g.drawImage(Game.menu_criacao.spriteBarba[Game.menu_criacao.currentIdade-1], 295, 323, 50, 30, null);
						}
						if(Game.menu_criacao.currentIdade == Game.menu_criacao.maxIdade) {
							g.drawImage(Game.menu_criacao.spriteCabelo[1], 295, 288, 50, 44, null);				
						} else {
							g.drawImage(Game.menu_criacao.spriteCabelo[0], 295, 288, 50, 44, null);				
						}				
					}
				} else if(signalState == 1) {
					g.setColor(Color.white);
					g.fillOval(cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont));
					g.setColor(Color.black);
				
					int tam = cutsceneMsg.length();
					g.drawString(cutsceneMsg.substring(0, tam/2 + 1), tam/2, HEIGHT*SCALE/2 -32);
					g.drawString(cutsceneMsg.substring(tam/2 + 1), tam, HEIGHT*SCALE/2);
					
					
//					g.drawString(cutsceneMsg, 0, HEIGHT*SCALE/2);
				}
			} else {
				menu_criacao.render(g);				
			}
		} else if(gameState  == "MENU_CLASSE") {
			if(cutsceneState == "nascimento") {
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 32));
				int camera = 2;
				
				if(signalState == 0) {
					g.setColor(Color.white);
					g.fillOval(cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont));
					g.setColor(Color.black);
					g.drawString("Você nasceu no <<insira nome do planeta>>", 50, HEIGHT*SCALE/2 - 50);
					g.drawString("Descendência paterna: "+Game.player.getClassePai(),  100, HEIGHT*SCALE/2);
					g.drawString("Descendência materna: "+Game.player.getClasseMae(),  100, HEIGHT*SCALE/2 + 50);
				} else if(signalState == 1) {
					g.setColor(Color.white);
					g.fillOval(cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont));
					if(cutsceneCont < cutsceneContMax - 15)
						g.drawImage(Game.spritesheet.getSprite(64, 256, 32, 32), 288, 288, 64, 64, null);
//					g.drawImage(Game.spritesheet.getSprite(64, 256, 32, 32), cutsceneCont * camera, cutsceneCont * camera, (int)(WIDTH*SCALE - camera * 2 * cutsceneCont), (int)(HEIGHT*SCALE - camera * 2 * cutsceneCont), null);
				}
			} else {
				menu_classe.render(g);				
			}
		} else if(gameState  == "MENU_PAUSE") {
			menu_pause.render(g);
		} else {	
			World.renderMinimapa();
			if(!npc.showMessage)
				g.drawImage(minimapa, WIDTH/10 * SCALE * 4, HEIGHT * SCALE - (World.HEIGHT *5), WIDTH/10  * SCALE * 2, World.HEIGHT*5, null);
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
		if(e.getKeyCode() == KeyEvent.VK_T) {
			ui.atributosBox();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState == "MENU_PRINCIPAL") {
				menu_principal.up = true;
			} else if(gameState == "MENU_PERSONAGEM") {
				menu_personagem.up = true;
			} else if(gameState == "MENU_CRIACAO") {
				menu_criacao.up = true;
			} else if(gameState == "MENU_PAUSE") {
				menu_pause.up = true;
			} else if(gameState == "MENU_CLASSE") {
				menu_classe.up = true;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S){
			if(gameState == "MENU_PRINCIPAL") {
				menu_principal.down = true;
			} else if(gameState == "MENU_PERSONAGEM") {
				menu_personagem.down = true;
			} else if(gameState == "MENU_CRIACAO") {
				menu_criacao.down = true;
			} else if(gameState == "MENU_PAUSE") {
				menu_pause.down = true;
			} else if(gameState == "MENU_CLASSE") {
				menu_classe.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(npc.showMessage) {
				npc.proximaFrase();
			} else {
				if(gameState == "NORMAL" && cutsceneState != "npc") {
					player.jump = true;				
				}
			}
			
			 if(gameState == "MENU_CLASSE") {
				menu_classe.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D) {
			if(gameState == "NORMAL" && cutsceneState != "npc") {
				player.right = true;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A) {
			if(gameState == "NORMAL" && cutsceneState != "npc") {
				player.left = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;
			if(gameState == "MENU_PRINCIPAL") {
				menu_principal.enter = true;
			} else if(gameState == "MENU_PERSONAGEM") {
				menu_personagem.enter = true;
			}  else if(gameState == "MENU_CRIACAO") {
				menu_criacao.enter = true;
			} else if(gameState == "MENU_PAUSE") {
				menu_pause.enter = true;
			} else if(gameState == "MENU_CLASSE") {
				menu_classe.enter = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
			if(gameState == "NORMAL") {
				gameState = "MENU_PAUSE";
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(gameState == "NORMAL") {
				cont = 0;
				saveGame = true;
			}
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
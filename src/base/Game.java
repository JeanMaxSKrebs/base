package base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	
	public static int WIDTH = 320;
	public static int HEIGHT = 160;
	private final int SCALE = 2;

	public static Player player;
	public static Enemy enemy;
	public static Enemy enemy2;
	public static Ball ball;
	
	private BufferedImage plano = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.addKeyListener(this);
		
		player = new Player(10, HEIGHT/2-16);
		enemy = new Enemy(WIDTH-10, HEIGHT/2-16);
		ball = new Ball(WIDTH/2-3, HEIGHT/2-3);

		initFrame();
	
	}

	public void initFrame() {
		frame = new JFrame("PONG the GAME");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
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
	
	public static void main(String[] args){
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		player.tick();
		enemy.tick();
		ball.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = plano.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player.render(g);
		enemy.render(g);
		ball.render(g);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString(Player.pontos+"| |"+Enemy.pontos, WIDTH/2-20, 20);
		
		g = bs.getDrawGraphics();
		g.drawImage(plano, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		bs.show();
		
		

//		
//		g.setColor(new Color(100,100,100));
//		g.fillRect(0, 0, WIDTH, HEIGHT);
//		g.setFont(new Font("Arial", Font.BOLD, 20));
//		g.setColor(Color.white);
//		g.drawString("PONG the GAME", WIDTH/2, HEIGHT/2);
//		g = bs.getDrawGraphics();
//		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			player.down = true;
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			player.down = false;
		}			
	}

}
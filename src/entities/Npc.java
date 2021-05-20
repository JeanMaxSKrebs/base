package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Npc extends Entity {

	public String[] frases = new String[5];
	
	public boolean showMessage = false;

	public int frase = 0;
	public int fraseMax = frases.length - 1;
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "Olá <<nome do Player>>";
		frases[1] = "Olá <<nome do Player>>";
		frases[2] = "Olá <<nome do Player>>";
		frases[3] = "Olá <<nome do Player>>";
		frases[4] = "Olá <<nome do Player>>";
	}
	
	public void tick() {
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xNpc = (int)x;
		int yNpc = (int)y;
		
		if(Math.abs(xPlayer - xNpc) < 32 && Math.abs(yPlayer - yNpc) < 32) {
			showMessage = true;
		}
	}
	
	public void proximaFrase() {
		if(frase == fraseMax) {
			frase = 0;
			showMessage = false;
			Game.gameState = "MENU_CLASSE";
		} else {
			frase++;
		}
	}
	
	public void render(Graphics g) {
		System.out.println(Game.cutsceneState);
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
		if(showMessage) {
			g.setColor(Color.white);
			g.fillRect(27, 210, Game.WIDTH - 54, 106);
			g.setColor(Color.black);
			g.fillRect(32, 215, Game.WIDTH - 64, 96);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.drawString((frase+1)+"/"+frases.length, 38, 225);
			g.setFont(new Font("arial", Font.BOLD, 10));
			g.drawString(frases[frase], 64, 240);
			g.drawString("Pressione SPACE", 64, 250);

			

		} else {
//			g.setColor(Color.white);
//			g.fillRect(32, 32, Game.WIDTH - 64, 96);
		}
//		g.setColor(Color.red);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
}

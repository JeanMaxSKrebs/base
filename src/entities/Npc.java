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
	public boolean show = false;

	public int curIndex = 0;
	
	public int frase = 0;
	public int fraseMax = frases.length - 1;
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "Caro <<nome do Player>>,   por causa de suas escolhas durante sua vida na Terra,você chegou nesse momento, porém vejo   algo em você.....";
		frases[1] = "Além disso acabei de perder meu preciosoGuardião.......";
		frases[2] = "Então lhe darei uma segunda chance numa outra dimensão no planeta <<nome do planeta>>";
		frases[3] = "Porém suas escolhas durante a vida pa -  ssada ainda o influenciam";
		frases[4] = "Portanto, você ................ será um habitante da Região:  "+Game.player.getRegiao();
	}
	
	public void tick() {
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xNpc = (int)x;
		int yNpc = (int)y;
		
		if(Math.abs(xPlayer - xNpc) < 32 && Math.abs(yPlayer - yNpc) < 32) {
			if(show == false) {
				showMessage = true;
				show = true;
			}
		}
		
		if(showMessage) {
			if(curIndex < frases[frase].length())
				curIndex++;
		}
	}
	
	public void proximaFrase() {
		if(frase == fraseMax) {
			frase = 0;
			showMessage = false;
			Game.gameState = "MENU_CLASSE";
			Game.cutsceneState = "";
		} else {
			frase++;
			curIndex = 0;
		}
	}
	
	public void render(Graphics g) {
//		System.out.println(Game.cutsceneState);
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
			
			if(curIndex < 40) {				
				g.drawString(frases[frase].substring(0, curIndex), 64, 240);
			} else if(curIndex > 40 && curIndex < 80) {
				g.drawString(frases[frase].substring(0, 40), 64, 240);
				g.drawString(frases[frase].substring(40, curIndex), 64, 240 + 15);
			} else if(curIndex > 80 && curIndex < 100) {
				g.drawString(frases[frase].substring(0, 40), 64, 240);
				g.drawString(frases[frase].substring(40, 80), 64, 240 + 15);
				g.drawString(frases[frase].substring(80, curIndex), 64, 240 + 30);
			} else if(curIndex > 120) {
				g.drawString(frases[frase].substring(0, 40), 64, 240);
				g.drawString(frases[frase].substring(40, 80), 64, 240 + 15);
				g.drawString(frases[frase].substring(80, 120), 64, 240 + 30);
				g.drawString(frases[frase].substring(120, curIndex), 64, 240 + 45);
			}
			
			g.drawString("Pressione SPACE", 64, 300);

			

		} else {
//			g.setColor(Color.white);
//			g.fillRect(32, 32, Game.WIDTH - 64, 96);
		}
//		g.setColor(Color.red);
//		g.fillRect(x - Camera.x, y - Camera.y, 32, 32);
	}
}

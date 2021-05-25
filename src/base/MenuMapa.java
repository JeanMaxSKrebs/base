package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graficos.Spritesheet;

public class MenuMapa extends Menu {

	public static Spritesheet map = new Spritesheet("/map.png");
	public static Spritesheet mapP = new Spritesheet("/mapP.png");
	public static Spritesheet mapM = new Spritesheet("/mapM.png");
	public static Spritesheet mapT = new Spritesheet("/mapT.png");
	private int cont = -300;
	private int contMax = 1000;
	private boolean regiao = false;


	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(cont < 0) {
			cont++;
		} else if(cont < contMax) {
			regiao = true;
			cont++;			
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(map.getSprite(0, 0, 640, 640), 0, 0, 640, 640, null);

		System.out.println(cont);
		if(regiao) {
			g.setColor(new Color(51, 51, 51));
			g.fillOval(320 - (cont/2), 320 - (cont/2), cont, cont);

			if(cont >= contMax) {
				g.fillRect(0, 0, 640, 640);
			}
			if(Game.player.getRegiao() == "P") {
				g.drawImage(mapP.getSprite(0, 0, 640, 640), 0, 0, 640, 640, null);
			}
			if(Game.player.getRegiao() == "M") {
				g.drawImage(mapM.getSprite(0, 0, 640, 640), 0, 0, 640, 640, null);
			}
			if(Game.player.getRegiao() == "T") {
				g.drawImage(mapT.getSprite(0, 0, 640, 640), 0, 0, 640, 640, null);
			}
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 48));
		g.drawString("<<Nome do Jogo>>", 100, 150);
	}

}

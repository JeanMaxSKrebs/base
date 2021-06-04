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
	private int contMax = 1500;
	private boolean regiao = false;
	private boolean aumentar = false;

	@Override
	public void tick() {
		System.out.println(Game.player.getClasseMae());
		System.out.println(Game.player.getClassePai());

		// TODO Auto-generated method stub
		if(cont < 0) {
//			cont++;
			cont += 10;
		} else if(cont < contMax) {
			regiao = true;
			cont++;		
//			cont += 10;
		} else if(cont == 1000) {
			aumentar = true;
		} else if(cont == contMax) {
			Game.gameState = "TUTORIAL";
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

				if(aumentar) {
					g.fillRect(0, 0, 640, 640);
					g.drawImage(mapP.getSprite(0, 0, 640, 640), 0, 0, 840, 640, null);
				}
			}
			if(Game.player.getRegiao() == "M") {
				g.drawImage(mapM.getSprite(0, 0, 640, 640), 0, 0, 640, 640, null);
				if(aumentar) {
					g.fillRect(0, 0, 640, 640);
					g.drawImage(mapM.getSprite(0, 0, 640, 640), -250, 0, 890, 640, null);
				}
			}
			if(Game.player.getRegiao() == "T") {
				g.drawImage(mapT.getSprite(0, 0, 640, 640), 0, 0, 640, 640, null);
				
				if(aumentar) {
					g.fillRect(0, 0, 640, 640);
					g.drawImage(mapT.getSprite(0, 0, 640, 640), -280, -100, 920, 740, null);
				}
			}
			
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 48));
		g.drawString("<<Nome do Jogo>>", 100, 150);
	}

}

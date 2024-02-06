package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import base.Game;
import entities.Player;

public class UI {
	
	int frame;
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(16, 8, 66, 32);
		g.setColor(Color.green);
		g.fillRect(24, 16, (int)((Game.player.life / Player.maxLife) * 50), 16);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 11));
		g.drawString((int)(Game.player.life)+" / "+(int)(Player.maxLife),  26, 28);
		

		
		if(Game.iamMAX_LEVEL()) {
			g.setColor(Color.black);
			g.fillRect(84, 8, 72, 16);
			g.setColor(Color.yellow);
			g.fillRect(88, 12, (int)((Game.player.balas / Game.player.maxBalas) * 64), 8);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.drawString((int)(Game.player.balas)+" / "+(int)(Game.player.maxBalas),  105, 19);
		} else {
			g.setColor(Color.black);
			g.fillRect(84, 8, 16, 32);
			g.setColor(Color.yellow);
			g.fillRect(88, 12, 8, (int)((Game.player.stamine / Player.maxStamine) * 24));
			g.setColor(Color.black);
			g.setFont(new Font("arial", Font.BOLD, 11));
			g.drawString((int)(Game.player.stamine)+"",  90, 28);
		}


		if(Game.player.stamine == Player.getMaxStamine()) {
			if(frame >= 10) {	
					g.setColor(Color.yellow);
					g.fillRect(84, 8, 16, 32);
					g.setColor(Color.black);
					g.fillRect(88, 12, 8, (int)((Game.player.stamine / Player.maxStamine) * 24));
					frame = 0;
			}
			frame++;
		}
		
		g.setColor(Color.gray);
		g.fillRect(0, Game.HEIGHT-32, 64, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 8));
		
		g.drawString("CHAVE:  "+Player.getKeys() , 0,  Game.HEIGHT-25);
		g.drawString("SUPER CHAVE: "+Player.getSpecialKeys() , 0,  Game.HEIGHT-15);
		g.drawString("PREMIUM: "+Game.player.premium , 0,  Game.HEIGHT-5);
		
		
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH-64, Game.HEIGHT-32, 64, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 8));
		g.drawString("ARMADURA: "+Player.getArmor(), Game.WIDTH-64,  Game.HEIGHT-25);
		g.drawString("ESQUIVA: "+Player.getDodgeChance() , Game.WIDTH-64, Game.HEIGHT-15);

		
	}
}

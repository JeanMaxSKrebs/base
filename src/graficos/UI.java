package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import base.Game;
import entities.Enemy;
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
		
		g.setColor(Color.black);
		g.fillRect(84, 8, 16, 32);
		g.setColor(Color.yellow);
		g.fillRect(88, 12, 8, (int)((Game.player.stamine / Player.maxStamine) * 24));
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 11));
		g.drawString((int)(Game.player.stamine)+"",  90, 28);


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
		g.fillRect(0, Game.HEIGHT-64, 32, 64);
		g.setColor(Color.white);
		g.setFont(new Font("calibri", Font.BOLD, 10));
		g.drawString("A: "+Player.getArmor(), 0,  Game.HEIGHT-55);
		g.drawString("Dod: "+Player.getDodgeChance() , 0,  Game.HEIGHT-45);
		g.drawString("DI: "+Enemy.getDano() , 0,  Game.HEIGHT-35);
		g.drawString("PA: "+Enemy.isPreparedAttack() , 0,  Game.HEIGHT-25);
		g.drawString("Keys: "+Player.getKeys() , 0,  Game.HEIGHT-15);
		g.drawString("St: "+Game.player.getStamine() , 0,  Game.HEIGHT-5);
		

		
	}
}

package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

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
		g.fillRect(0, Game.getHEIGHT()-32, 96, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 10));
		
		
//		quadroEsquerdo();
//		quadroDireito();

		//frutas da UI

		g.drawString("Frutas:  "+Game.player.getFrutasColetadas().size() , 0,  Game.getHEIGHT()-25);
		g.drawString("Inventário:  Press I", 0,  Game.getHEIGHT()-5);
		g.drawString("Pause:  Press P", 0,  Game.getHEIGHT()-15);

		
		g.setColor(Color.gray);
		g.fillRect(Game.getWIDTH()-80, Game.getHEIGHT()-32, 80, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 9));
		g.drawString("ARMADURA: "+Player.getArmor(), Game.getWIDTH()-72,  Game.getHEIGHT()-25);
		g.drawString("ESQUIVA: "+Player.getDodgeChance() , Game.getWIDTH()-72, Game.getHEIGHT()-15);
		g.drawString("VELOCIDADE: "+Player.getSpeed() , Game.getWIDTH()-72, Game.getHEIGHT()-5);

		g.setColor(Color.black);
		g.drawString("MAÇÃS:  "+Game.player.countFrutaEspecifica("MACA") , 0,  Game.getHEIGHT()-35);


		
	}
	
}

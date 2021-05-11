package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import base.Game;
import entities.Player;

public class UI {
	
	private int boxWidth = 10;
	private int boxHeight = 62;
	private boolean openAtributosBox = false;
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 64, 32);
		g.setColor(Color.green);
		g.fillRect(6, 6, (int)((Game.player.life / Player.maxLife) * 52), 20);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 11));
		g.drawString((int)(Game.player.life)+" / "+(int)(Player.maxLife),  10, 20);
		

			
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH - boxWidth, 0, boxWidth, boxHeight);
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString(Game.player.getCriatividade() + " Criatividade", Game.WIDTH - boxWidth, 10);
		g.drawString(Game.player.getInteligencia() + " Inteligência", Game.WIDTH - boxWidth, 20);
		g.drawString(Game.player.getForca() + " Força", Game.WIDTH - boxWidth, 30);
		g.drawString(Game.player.getAgilidade() + " Agilidade", Game.WIDTH - boxWidth, 40);
		g.drawString(Game.player.getArmadura() + " Armadura", Game.WIDTH - boxWidth, 50);
		g.setFont(new Font("arial", Font.BOLD, 9));
		if(isOpenAtributosBox()) {
			boxWidth = 85;
			boxHeight = 52;

		} else {
			boxWidth = 25;
			g.drawString("   T", Game.WIDTH - boxWidth, 60);
			boxHeight = 62;
		}

		
	}

	public boolean isOpenAtributosBox() {
		return openAtributosBox;
	}

	public void setOpenAtributosBox(boolean openAtributosBox) {
		this.openAtributosBox = openAtributosBox;
	}

	public void atributosBox() {
		if(openAtributosBox) {
			openAtributosBox = false;
		} else {
			openAtributosBox = true;
		}
	}
}
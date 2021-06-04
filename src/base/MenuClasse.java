package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MenuClasse extends Menu {

	public String[] options = {"mae", "pai", "aleatorio", "criar", "sair"};
	public String[] tipoClasse = Game.player.getClasse();
	public BufferedImage[] arrayPaiSprites = Game.player.getSpritesPersonagens();
	public BufferedImage[] arrayMaeSprites = Game.player.getSpritesPersonagensOrelha();

	private String pai = "Humano";
	private String mae = "Humano";
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public int currentClasseMae = 0;
	public int currentClassePai = 0;
	public int maxClasse = tipoClasse.length;
	
//	private boolean showOptions = false;
	private boolean maeShowOptions = false;
	private boolean paiShowOptions = false;

	public void tick() {
		if(maeShowOptions) {
			if(currentClasseMae < maxClasse) {				
				mae = this.tipoClasse[currentClasseMae];
			}
			if(currentClasseMae == maxClasse) {
				mae = this.tipoClasse[0];
			}

			if(up) {
				up = false;
				currentClasseMae--;
				if(currentClasseMae < 0) {
					currentClasseMae = maxClasse;
				}
			}
			if(down) {
				down = false;
				currentClasseMae++;
				if(currentClasseMae > maxClasse) {
					currentClasseMae = 0;
				}
			}
			if(enter) {
				enter = false;
				maeShowOptions = false;

				for (int i = 0; i < tipoClasse.length; i++) {
					if(currentClasseMae == i) {
						mae = tipoClasse[i];
						System.out.println(tipoClasse[i]);
					}
				}
				
			}
		} else if(paiShowOptions) {
			if(currentClassePai < maxClasse) {				
				pai = this.tipoClasse[currentClassePai];
			}
			if(currentClassePai == maxClasse) {
				pai = this.tipoClasse[0];
			}
			
			if(up) {
				up = false;
				currentClassePai--;
				if(currentClassePai < 0) {
					currentClassePai = maxClasse;
				}
			}
			if(down) {
				down = false;
				currentClassePai++;
				if(currentClassePai > maxClasse) {
					currentClassePai = 0;
				}
			}
			if(enter) {
				enter = false;
				paiShowOptions = false;
				
				for (int i = 0; i < tipoClasse.length; i++) {
					if(currentClassePai == i) {
						pai = tipoClasse[i];
						System.out.println(tipoClasse[i]);
					}
				}
			}
		}else {
		
			if(up) {
				up = false;
				currentOption--;
				if(currentOption < 0) {
					currentOption = maxOption;
				}
			}
			if(down) {
				down = false;
				currentOption++;
				if(currentOption > maxOption) {
					currentOption = 0;
				}
			}
			if(enter) {
				enter = false;
//				showOptions = false;
				
				enter = false;
				if(options[currentOption] == "mae") {
					maeShowOptions = true;
				} else if(options[currentOption] == "pai") {
					paiShowOptions = true;
				} else if(options[currentOption] == "aleatorio") {
					int p = Game.random(maxClasse);
					int m = Game.random(maxClasse);
					pai = this.tipoClasse[p];
					mae = this.tipoClasse[m];
					currentClassePai = p;
					currentClasseMae = m;
				} else if(options[currentOption] == "criar") {
					System.out.println("CRIOU PERSONAGEM");
					Game.player.setClasseMae(mae);
					Game.player.setClassePai(pai);
					Game.cutsceneState = "nascimento";
					Game.gameState = "MENU_MAPA";
				} else if(options[currentOption] == "sair") {
					System.exit(1);
				}
			}	
		}
	}

	@Override
	public void render(Graphics g) {
		int valorwidth = 8;
		int valorheigth = 4;
		int width = Game.WIDTH * Game.SCALE / valorwidth;
		int height = Game.HEIGHT * Game.SCALE / valorheigth;
		int multi = Game.HEIGHT*Game.SCALE/10;
		int soma = 100;

		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.fillRect(0, 0,Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.WHITE);
		g.drawString("<<NOME DA TERRA>>", (width), (height - soma));

		
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(Color.black);
		g.drawString("Mãe: "+mae,  width + soma * 2 + 32, height);
		g.drawString("Pai: "+pai,  width + soma * 2 + 32, height + 32);
		g.fillRect(width + soma * 2 + 32, height + multi, width + soma * 2, height + multi);
		//desenhar imagem personagem
		System.out.println(currentClasseMae);
		System.out.println(currentClassePai);

		if(currentClassePai < maxClasse) {
			g.drawImage(arrayPaiSprites[currentClassePai], width + 32 * 9, height + multi + 16, width + soma, height, null);
		}
		if(currentClasseMae < maxClasse) {
			g.drawImage(arrayMaeSprites[currentClasseMae], width + 32 * 9, height + multi + 16, width + soma, height, null);
		}
		if(currentClassePai == maxClasse) {
			g.drawImage(arrayPaiSprites[0], width + 32 * 9, height + multi + 16, width + soma, height, null);
		}
		if(currentClasseMae == maxClasse) {
			g.drawImage(arrayMaeSprites[0], width + 32 * 9, height + multi + 16, width + soma, height, null);
		}
		g.setColor(Color.WHITE);
		
		
		
		g.setFont(new Font("Arial", Font.BOLD, 32));
		if(!maeShowOptions && !paiShowOptions) {
			g.drawString("Mãe", width, height);			
			g.drawString("Pai", width, (height + multi));
			g.drawString("Aleatório", width, (height + multi * 2));
		} else if(!maeShowOptions) {
			g.drawString("Pai", width, (height));			
		} else if(!paiShowOptions) {
			g.drawString("Mãe", width, height);
		}
		g.drawString("Criar", (width + multi), (Game.HEIGHT * Game.SCALE - soma/2));

		g.drawString("Sair", (width + multi * 5), (Game.HEIGHT * Game.SCALE - soma/2));
		
		
		
		
		if(options[currentOption] == "mae") {
			if(maeShowOptions) {
				g.setFont(new Font("Arial", Font.BOLD, 28));
				for (int i = 0; i < tipoClasse.length; i++) {					
					g.drawString(tipoClasse[i],  (width + multi), (height + 35 + multi * (i)));
				}
				for (int i = 0; i < tipoClasse.length; i++) {					
				}

				g.drawString("Voltar",  width, (height + soma/2 + multi * 5));

				if(currentClasseMae == 5) {
					g.drawString(" > ", width - 50, (height + soma/2 + multi * 5));
				} else if(tipoClasse[currentClasseMae] == tipoClasse[currentClasseMae]) {
					g.drawString(" > ",  (width + multi - 50), (height + 35 + multi * currentClasseMae));
				}
			} else {
				g.drawString(" > ",  (width-50), height);
			}
		} else if(options[currentOption] == "pai") {
			if(paiShowOptions) {
				g.setFont(new Font("Arial", Font.BOLD, 28));
				for (int i = 0; i < tipoClasse.length; i++) {					
					g.drawString(tipoClasse[i],  (width + multi), (height + 35 + multi * (i)));
				}
				for (int i = 0; i < tipoClasse.length; i++) {					
				}
				
				g.drawString("Voltar",  width, (height + soma/2 + multi * 5));

				if(currentClassePai == 5) {
					g.drawString(" > ", width - 50, (height + soma/2 + multi * 5));
				} else if(tipoClasse[currentClassePai] == tipoClasse[currentClassePai]) {
					g.drawString(" > ",  (width + multi - 50), (height + 35 + multi * currentClassePai));
				}
			} else {
				g.drawString(" > ",  (width - 50), (height + multi));
			}
		} else if(options[currentOption] == "aleatorio") {
			g.drawString(" > ",  (width - 50), (height + multi * 2));
		} else if(options[currentOption] == "criar") {
			g.drawString(" > ",  (width + multi - 50), (Game.HEIGHT * Game.SCALE - soma/2));
		} else if(options[currentOption] == "sair") {
			g.drawString(" > ",  (width + multi * 5 - 50), (Game.HEIGHT * Game.SCALE - soma/2));
		}
	}

}

package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

public class MenuCriacao extends Menu {
	
	public String[] options = {"idade", "criar", "voltar"};
	public String[] idades = {"5", "25", "45", "65"};
	public String[] tipoAtributos = {"idade", "criatividade", "inteligencia", "forca", "agilidade", "armadura"};
	private int[] atributos = {0, 5, 5, 5, 5, 5};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public int currentIdade = 0;
	public int maxIdade = idades.length - 1;
	public int maxAtributos = tipoAtributos.length - 1;
	
	public boolean up, down, enter;
	private boolean showOptions = false;

	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists())
			saveExists = true;
		else
			saveExists = false; 
		
		if(showOptions) {
			if(up) {
				up = false;
				currentIdade++;
				if(currentIdade > maxIdade) {
					currentIdade = 0;
				}
			}
			if(down) {
				down = false;
				currentIdade--;
				if(currentIdade < 0) {
					currentIdade = maxIdade;
				}

			}
			if(enter) {
				enter = false;				
				showAtributos(idades[currentIdade]);
			}
		} else {
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
				if(options[currentOption] == "idade") {
					showOptions = true;
				} else if(options[currentOption] == "criar") {
					System.out.println("CRIOU PERSONAGEM");
				} else if(options[currentOption] == "voltar") {
					Game.gameState = "MENU_PERSONAGEM";
				}
			}
		}
	}
	
	private void showAtributos(String idade) {
		if(idade == "5") {
			atributos[0] = 5;
			atributos[1] = 7;
			atributos[2] = 3;
			atributos[3] = 3;
			atributos[4] = 7;
			atributos[5] = 5;	
		} else if(idade == "25") {
			atributos[0] = 25;
			atributos[1] = 3;
			atributos[2] = 6;
			atributos[3] = 7;
			atributos[4] = 6;
			atributos[5] = 3;	
		} else if(idade == "45") {
			atributos[0] = 45;
			atributos[1] = 3;
			atributos[2] = 7;
			atributos[3] = 5;
			atributos[4] = 3;
			atributos[5] = 7;	
		} if(idade == "65") {
			atributos[0] = 65;
			atributos[1] = 5;
			atributos[2] = 5;
			atributos[3] = 5;
			atributos[4] = 5;
			atributos[5] = 5;
		}
	}

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
		g.drawString("Criação", (width - multi), (height - soma));

		g.setFont(new Font("Arial", Font.BOLD, 32));
		
		g.drawString("Idade", width, (height));
		if(showOptions) {
			g.drawString("5",  width + multi + soma, height);
			g.drawString("25",  width + multi + soma * 2, height);
			g.drawString("45",  width + multi + soma * 3, height);
			g.drawString("65",  width + multi + soma * 4, height);
		}
		
		g.drawString("Criar", (width + multi), (Game.HEIGHT * Game.SCALE - soma/2));

		g.drawString("Voltar", (width + multi * 5), (Game.HEIGHT * Game.SCALE - soma/2));


//		System.out.println(idades[currentIdade]);
		
		if(options[currentOption] == "idade") {
			if(showOptions) {
				if(idades[currentIdade] == "5") {
					g.drawString(" > ",  (width + multi + soma - 50), height);
				} else if(idades[currentIdade] == "25") {
					g.drawString(" > ",  (width + multi + soma * 2 - 50), (height));
				} else if(idades[currentIdade] == "45") {
					g.drawString(" > ",  (width + multi + soma * 3 - 50), (height));
				} else if(idades[currentIdade] == "65") {
					g.drawString(" > ",  (width + multi + soma * 4 - 50), (height));
				}
				g.setFont(new Font("Arial", Font.BOLD, 24));
				
				g.drawString("Criatividade",  (width), height + multi * 1 - 10);
				g.fillRect(width, height + multi * 1, 500, multi/2);
				g.drawString("Inteligência",  (width), height + multi * 2 - 10);
				g.fillRect(width, height + multi * 2, 500, multi/2);
				g.drawString("Força",  (width), height + multi * 3 - 10);
				g.fillRect(width, height + multi * 3, 500, multi/2);
				g.drawString("Agilidade",  (width), height + multi * 4 - 10);
				g.fillRect(width, height + multi * 4, 500, multi/2);
				g.drawString("Armadura",  (width), height + multi * 5 - 10);
				g.fillRect(width, height + multi * 5, 500, multi/2);
				
				if(atributos[0] != 0) {
					g.setColor(new Color(51, 51, 51));
					System.out.println(atributos.length);
					for (int i = 1; i < atributos.length; i++) {
						g.fillRect(5 + width, 5 + height + multi * i, atributos[i] * 500 / 25, multi/2 - 10);
					}
				}
			} else {
				g.drawString(" > ",  (width-50), (height));
			}
		} else if(options[currentOption] == "criar") {
			g.drawString(" > ",  (width + multi - 50), (Game.HEIGHT * Game.SCALE - soma/2));
		} else if(options[currentOption] == "voltar") {
			g.drawString(" > ",  (width + multi * 5 - 50), (Game.HEIGHT * Game.SCALE - soma/2));
		}
	}
}
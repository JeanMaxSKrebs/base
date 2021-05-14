package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuCriacao extends Menu {
	
	public String[] options = {"idade", "atributos", "resetar", "criar", "voltar"};
	public String[] idades = {"5", "25", "45", "65"};
	public String[] tipoAtributos = {"Criatividade", "Inteligencia", "Forca", "Agilidade", "Armadura"};
	private int[] atributos = {5, 5, 5, 5, 5};
	private int[] atributosExtras = {0, 0, 0, 0, 0};
	private int[] atributosExtrasMax = {15, 15, 15, 15, 15};

	

	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public int currentIdade = 0;
	public int maxIdade = idades.length - 1;
	public int currentAtributo = 0;
	public int maxAtributos = tipoAtributos.length;
	
	private boolean showOptions = false;
	private boolean showAlterOptions = false;
	private int pontos = 25;
	
	public void tick() {
		
		if(showOptions) {
			showAtributos(idades[currentIdade]);

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
				showOptions = false;
			}
		} else if(showAlterOptions){
			showAtributos(idades[currentIdade]);
			if(down) {
				down = false;
				currentAtributo++;
				if(currentAtributo > maxAtributos) {
					currentAtributo = 0;
				}
			}
			if(up) {
				up = false;
				currentAtributo--;
				if(currentAtributo < 0) {
					currentAtributo = maxAtributos;
				}
			}
			if(enter) {
				enter = false;
				if(currentAtributo == maxAtributos) {
					showAlterOptions = false;
				} else {
					if(pontos  <= 0) {
						showAlterOptions = false;
					} else {
						if(adicionaAtributos()) {							
							pontos--;
						}
					}
				}
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
					resetar();
					showOptions = true;
				} else if(options[currentOption] == "atributos") {
					showAlterOptions = true;
				} else if(options[currentOption] == "resetar") {
					resetar();
				} else if(options[currentOption] == "criar") {
					if(verificaPontos()) {
						if(pontos == 0) {
							System.out.println("CRIOU PERSONAGEM");
							Game.player.createPlayer(atributos, idades[currentIdade]);
							Game.cutsceneState = "morte";
						} else {
							System.out.println("UTILIZE SEUS PONTOS");
						}
					} else {
						System.out.println("ERRO, REUTILIZE SEUS PONTOS");
					}
				} else if(options[currentOption] == "voltar") {
					Game.gameState = "MENU_PERSONAGEM";
				}
			}
		}
	}
	private void resetar() {
		for (int i = 0; i < atributos.length; i++) {
			pontos = 25;
			atributos[i] = 5;
			atributosExtras[i] = 0;
		}
	}
	
	private boolean verificaPontos() {
		for (int j = 0; j < atributos.length; j++) {
			if(atributos[j] > 15) {
				resetar();
				return false;
			}
		}
		return true;
	}

	private boolean adicionaAtributos() {
//		System.out.println(atributosExtras[currentAtributo]);
		if(atributos[currentAtributo] >= atributosExtrasMax[currentAtributo]) {
			System.out.println("Atributo no maximo: "+tipoAtributos[currentAtributo]);
			return false;
		} else {			
			atributosExtras[currentAtributo]++;
//			System.out.println(atributosExtras[currentAtributo]);
			System.out.println("adiciona atributo "+tipoAtributos[currentAtributo]);
			return true;
		}
	}

	private void showAtributos(String idade) {
		if(idade == "5") {
			atributos[0] = 7 + atributosExtras[0];
			atributos[1] = 3 + atributosExtras[1];
			atributos[2] = 3 + atributosExtras[2];
			atributos[3] = 7 + atributosExtras[3];
			atributos[4] = 5 + atributosExtras[4];	
		} else if(idade == "25") {
			atributos[0] = 3 + atributosExtras[0];
			atributos[1] = 6 + atributosExtras[1];
			atributos[2] = 7 + atributosExtras[2];
			atributos[3] = 6 + atributosExtras[3];
			atributos[4] = 3 + atributosExtras[4];
		} else if(idade == "45") {
			atributos[0] = 3 + atributosExtras[0];
			atributos[1] = 7 + atributosExtras[1];
			atributos[2] = 5 + atributosExtras[2];
			atributos[3] = 3 + atributosExtras[3];
			atributos[4] = 7 + atributosExtras[4];	
		} if(idade == "65") {
			atributos[0] = 5 + atributosExtras[0];
			atributos[1] = 5 + atributosExtras[1];
			atributos[2] = 5 + atributosExtras[2];
			atributos[3] = 5 + atributosExtras[3];
			atributos[4] = 5 + atributosExtras[4];
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
		
		if(!showAlterOptions && !showOptions) {
			g.drawString("Idade", width, height);			
			g.drawString("Atributos", width, (height + multi));
			g.drawString("Resetar", width, (height + multi * 2));
		} else if(!showAlterOptions) {
			g.drawString("Idade", width, height);			
		} else if(!showOptions) {
			g.drawString("Atributos", width, height);
			g.drawString("Pontos :"+pontos+"/25", (width + soma * 2), height);
		}
		
		g.drawString("Criar", (width + multi), (Game.HEIGHT * Game.SCALE - soma/2));

		g.drawString("Voltar", (width + multi * 5), (Game.HEIGHT * Game.SCALE - soma/2));
		
		if(options[currentOption] == "idade") {
			if(showOptions) {
				g.setFont(new Font("Arial", Font.BOLD, 28));
				for (int i = 0; i < idades.length; i++) {					
					g.drawString(idades[i],  (width + multi + soma * (i+1)), height);
				}
				
				if(idades[currentIdade] == idades[currentIdade]) {
					g.drawString(" > ",  (width + multi + soma * (currentIdade+1) - 50), height);
				}
				
				g.setFont(new Font("Arial", Font.BOLD, 24));
				for (int i = 0; i < tipoAtributos.length; i++) {
					g.drawString(tipoAtributos[i],  width, (height + soma/2 + multi * i - 10));
					g.fillRect(width, (height + soma/2 + multi * i), 510, multi/2);
				}
				
				if(atributos[0] != 0) {
					g.setColor(new Color(51, 51, 51));
					for (int i = 0; i < atributos.length; i++) {
						g.fillRect((width + 5), (height + soma/2 + multi * i + 5), (atributos[i] * 500 / 15), (multi/2 - 10));
					}
				}
			} else {
				g.drawString(" > ",  (width-50), height);
			}
		} else if(options[currentOption] == "atributos") {
			if(showAlterOptions) {
//				System.out.println(tipoAtributos[currentAtributo]);
				g.drawString("Criação",  width, (height + soma/2 + multi * 5));
				if(currentAtributo == 5) {
					g.drawString(" > ",  (width-50), (height + soma/2 + multi * currentAtributo));
				} else if(tipoAtributos[currentAtributo] == tipoAtributos[currentAtributo]) {
					g.drawString(" > ",  (width-50), (height + soma/2 + multi * currentAtributo - 10));
				}
				
				g.setFont(new Font("Arial", Font.BOLD, 24));
				for (int i = 0; i < tipoAtributos.length; i++) {
					g.drawString(tipoAtributos[i],  width, (height + soma/2 + multi * i - 10));
					g.fillRect(width, (height + soma/2 + multi * i), 510, multi/2);
				}
				if(atributos[0] != 0) {
					g.setColor(new Color(51, 51, 51));
//					System.out.println(atributos.length);
					for (int i = 0; i < atributos.length; i++) {
//						System.out.println(atributos[i]+atributosExtras[i]);
						g.fillRect(5 + width, 5 + (height + soma/2 + multi * i), (atributos[i] * 500 / 15), (multi/2 - 10));
					}
				}
			} else {
				g.drawString(" > ",  (width-50), (height + multi));
			}
		} else if(options[currentOption] == "resetar") {
			g.drawString(" > ",  (width - 50), (height + multi * 2));
		} else if(options[currentOption] == "criar") {
			g.drawString(" > ",  (width + multi - 50), (Game.HEIGHT * Game.SCALE - soma/2));
		} else if(options[currentOption] == "voltar") {
			g.drawString(" > ",  (width + multi * 5 - 50), (Game.HEIGHT * Game.SCALE - soma/2));
		}
	}
}
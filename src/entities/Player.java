package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;
import world.World;

public class Player extends Entity{

	public boolean left, right, up, down;
	public int right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	public int dir = 1;
	
	public boolean jump = false;
	
	public boolean isJumping = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;
	
	
	
	public double speed = 3.5;
	private String idade = "5";
	private int criatividade = 10, inteligencia = 10, forca = 10, agilidade = 10, armadura = 10;
	private int nivel = 1;
	private int atributos = 50;
	private String regiao = "";
//	private int atributosMax = 500;
			
	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = (qtdSprites-1);
	private boolean moved;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;
	
	public double life = 100;
	public static double maxLife = 100;
	public double stamine = 100;
	public static double maxStamine = 100;


	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
	
		rightPlayer = new BufferedImage[qtdSprites];
		leftPlayer = new BufferedImage[qtdSprites];
		upPlayer = new BufferedImage[qtdSprites];
		downPlayer = new BufferedImage[qtdSprites];
		
		for(int i=0; i<qtdSprites; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite((i*32), 0, 32, 32);
		}
		
		for(int i=0; i<qtdSprites; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((i*32), 32, 32, 32);
		}
		for(int i=0; i<qtdSprites; i++) {
			upPlayer[i] = Game.spritesheet.getSprite((i*32), 64, 32, 32);
		}
		
		for(int i=0; i<qtdSprites; i++) {
			downPlayer[i] = Game.spritesheet.getSprite((i*32), 96, 32, 32);
		}

	}
	public Player(int x, int y, int width, int height, BufferedImage sprite, String idade, int criatividade, int inteligencia, int forca, int agilidade, int armadura) {
		super(x, y, width, height, sprite);
		criatividade = this.criatividade;
		inteligencia = this.inteligencia;
		forca = this.forca;
		agilidade = this.agilidade;
		armadura = this.armadura;
	}
	public int danoRecebido(int dano) {
		
		int danoRecebido = dano - armadura;
		
//		if(Game.random(Game.maximumDodge) <= dodgeChance) {
//			return 0;
//		}
		
		return danoRecebido;
	}
	
	public boolean beingAttacked(int dano) {
		int danoReal = danoRecebido(dano);
		
		if(danoReal > 0) {
			life = life - danoReal;
		}
		
//		System.out.println(danoReal);
//		System.out.println(life);
		if(iamDead())
			Game.gameState = "GAME_OVER";
		
		return true;
	}
	public boolean iamDead() {
		if(life <= 0) {
			life = 0;
			return true;
		}
		
		return false;
	}
	
	public void tick() {
//		System.out.println("teste");
		setMoved(false);

		int midy = (int)(y + masky + mheight/2);
		int plusy = (int)(y + masky + mheight);
		int minusy = (int)(y + masky);
		
		int midx = (int)(x + maskx + mwidth/2);
		int plusx = (int)(x + maskx + mwidth);
		int minusx = (int)(x + maskx);
		
		if(World.isFree(midx, (int)(plusy + Game.gravidade), "down") && isJumping == false) {
			y+=Game.gravidade;
			System.out.println("teste");
		}
		
		if(right) {
			setMoved(true);
			dir = right_dir;
			x++;
			if(World.isFree(plusx, midy, "right"))			
				x += speed;
		} else if(left) {
			setMoved(true);
			dir = left_dir;
			if(World.isFree(minusx, midy, "left"))
				x -= speed;
		}


		if(jump) {
			if(!World.isFree(midx, (int)(plusy + Game.gravidade), "down")) {
				setMoved(true);
				dir = up_dir;
				isJumping = true;
			} else {				
				jump = false;
			}
		}
		
		if(isJumping) {
//			System.out.println(World.isFree(midx, (minusy - 2), "up"));
			if(World.isFree(midx, (int)(minusy - Game.gravidade), "up")) {
				y -= Game.gravidade;
				jumpFrames += 2;
				if(jumpFrames >= jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			} else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}			
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 32 - Game.HEIGHT);
		
	}

	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(dir == down_dir) {			
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
	public double getLife() {
		return life;
	}
	public void setLife(int newLife) {
		this.life = newLife;
	}
	public double getMaxLife() {
		return maxLife;
	}
	public void setMaxLife(int newMaxLife) {
		Player.maxLife = newMaxLife;
	}
	public double getStamine() {
		return stamine;
	}
	public void setStamine(double stamine) {
		this.stamine = stamine;
	}
	public static double getMaxStamine() {
		return maxStamine;
	}
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public void createPlayer(int[] atributo, String idade) {

		setIdade(idade);
		setCriatividade(atributo[0]);
		setInteligencia(atributo[1]);
		setForca(atributo[2]);
		setAgilidade(atributo[3]);
		setArmadura(atributo[4]);
		
		setRegiao(calculaRegiao());
		System.out.println(regiao);
	}
	
	private String calculaRegiao() {

		double calculo = ((getCriatividade() * 1) 
					+ (getAgilidade() * 2)
					+ (getForca() * 3)
					+ (getArmadura() * 4)
					+ (getInteligencia() * 5));
		System.out.println(calculo);
		if(getIdade() == "5") {
			calculo -= 11;
		} else if(getIdade() == "25") {
			if(calculo > 150) {				
				calculo -= 5;
			}
			else {
				calculo += 5;
			}
		} else if(getIdade() == "45") {
			calculo += 11;
		}
		System.out.println(calculo);
		if(calculo < 140) {
			return "P";
		} else if(calculo > 160) {
			return "T";
		} else {
			return "M";
		}
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	public int getCriatividade() {
		return criatividade;
	}
	public void setCriatividade(int criatividade) {
		this.criatividade = criatividade;
	}
	public int getInteligencia() {
		return inteligencia;
	}
	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}
	public int getForca() {
		return forca;
	}
	public void setForca(int forca) {
		this.forca = forca;
	}
	public int getAgilidade() {
		return agilidade;
	}
	public void setAgilidade(int agilidade) {
		this.agilidade = agilidade;
	}
	public int getArmadura() {
		return armadura;
	}
	public void setArmadura(int armadura) {
		this.armadura = armadura;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getAtributos() {
		return atributos;
	}
	public void setAtributos(int atributos) {
		this.atributos = atributos;
	}
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
}
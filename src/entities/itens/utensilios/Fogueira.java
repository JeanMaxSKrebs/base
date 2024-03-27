package entities.itens.utensilios;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import base.Game;
import entities.itens.Item;
import entities.itens.comidas.Comida;
import world.Camera;

public class Fogueira extends Utensilio {

	protected boolean isLit;
    protected Map<Comida, Integer> cookingFoods; // Map of cooking foods to their remaining cook time

    protected BufferedImage[] spritesFogueira;

    protected static final String nome = "Fogueira";

	public void tick() {

		girar();
		verificaGiro();

	}
    

	
	public Fogueira(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite, nome);
		
		spritesFogueira = new BufferedImage[4];

		for (int i = 0; i < qtdDirecoes; i++) {
			spritesFogueira[i] = Game.spritesheet_Items.getSprite(112 * i, 112*1, 112, 112);
		}
	}


    public void ligar() {
        isLit = true;
        System.out.println("A fogueira foi acesa!");
    }

    public void desligar() {
        isLit = false;
        cookingFoods.clear(); // Reseta a comida cozinhando
        System.out.println("A fogueira foi apagada!");
    }

    public void aplicarCalor(Comida comida) {
        if (isLit && !comida.isCooked()) {
            if (!cookingFoods.containsKey(comida)) {
                cookingFoods.put(comida, comida.tickRegen); // Adiciona comida ao mapa
            }
            int remainingTime = cookingFoods.get(comida);
            remainingTime--;
            cookingFoods.put(comida, remainingTime);
            if (remainingTime <= 0) {
            	comida.cook(); // Comida cozida
                cookingFoods.remove(comida); // Remove do mapa
            }
        } else if (!isLit) {
            System.out.println("A fogueira precisa estar acesa para cozinhar!");
        } else {
            System.out.println("A comida já está cozida!");
        }
    }
    
	public void render(Graphics g) {

		g.drawImage(spritesFogueira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.green);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
		
		renderLuz(g);
	}
	
	public void renderLuz(Graphics g) {
		  Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D

		  // Draw fogueira sprite (existing code)

		  // Light effect
		  int lightRadius = 2000; // Adjust radius based on desired effect
		  int lightAlpha = 50; // Adjust transparency (0-255)
		  Color lightColor = new Color(255, 200, 0, lightAlpha); // Orange light with transparency

		  g2d.setColor(lightColor);
		  g2d.fillOval(this.getX() + + this.getWidth()/2 - Camera.x - lightRadius / 2, this.getY()  + this.getHeight()/2 - Camera.y - lightRadius / 2, lightRadius, lightRadius);
		}

    // Getters and setters (optional, based on your needs)

    public boolean isLit() {
        return isLit;
    }

    public void setLit(boolean lit) {
        isLit = lit;
    }

    public Map<Comida, Integer> getCookingFoods() {
        return cookingFoods;
    }

    public void setCookingFoods(Map<Comida, Integer> cookingFoods) {
        this.cookingFoods = cookingFoods;
    }



	@Override
	public void coletarEspecifico() {
		// TODO Auto-generated method stub
		
	}
}
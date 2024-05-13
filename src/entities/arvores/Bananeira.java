package entities.arvores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.comidas.frutas.Banana;
import world.Camera;

public class Bananeira extends Arvore {
	
	protected static final String nome = "Bananeira";
	protected int qtdFrutos = Game.random(3, 7); 


	private BufferedImage[] spritesBananeira;
	
	public Bananeira(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		spritesBananeira = new BufferedImage[qtdDirecoes];
		for (int i = 0; i < qtdDirecoes; i++) {

			spritesBananeira[i] = Game.spritesheet_Trees.getSprite(tamanhoBase * i, tamanhoBase*7, tamanhoBase, tamanhoBase);

		}
	}
	
	public void tick() {

		girar();
		verificaGiro();

	}

	public void render(Graphics g) {

			g.drawImage(spritesBananeira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);		

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
	
	  // Método para gerar frutos específicos para a bananeira (neste caso, bananas)
    @Override
    protected void gerarFrutos() {
    	   for (int i = 0; i < qtdFrutos; i++) {
               int bananaX = this.getX() + Game.random(-this.getWidth() / 2, this.getWidth() / 2);
               int bananaY = this.getY() + Game.random(-this.getHeight() / 2, this.getHeight() / 2);
               Banana banana = new Banana(bananaX, bananaY, 16, 16, null);
               Game.frutas.add(banana);
           }
    }

	@Override
	public void metodoAbstrato() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarFrutosAleatoriamente() {
        gerarFrutos();		
	}
}

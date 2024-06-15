package graficos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import base.Game;
import entities.itens.Item;

public class ItemAnimation {
	private BufferedImage sprite; // Imagem do item a ser animado
	private int startX, startY; // Coordenadas iniciais do item
	private int endX, endY; // Coordenadas finais do item
	private double currentX, currentY;
	private double angle;
	private double speed = 4.0;
	private long animationDuration; // Duração da animação em milissegundos
	private long startTime; // Tempo de início da animação
	public boolean animacaoColeta;

	public ItemAnimation(BufferedImage sprite, long animationDuration) {
		this.sprite = sprite;
		this.animationDuration = animationDuration;
	}

	// Iniciar a animação
	public void startAnimation(int startX, int startY, int endX, int endY) {
        this.animacaoColeta = true;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.currentX = startX;
		this.currentY = startY;
		this.startTime = System.currentTimeMillis();
	}

	public void tick() {
		animacaoColeta = true;
		if (animacaoColeta) {
			long elapsedTime = System.currentTimeMillis() - startTime;
			if (elapsedTime >= animationDuration) {

				currentX = endX;
				currentY = endY;
				animacaoColeta = false;
			} else {

				double dx = endX - currentX;
				double dy = endY - currentY;
				double distanceSquared = dx * dx + dy * dy; // Evita calcular a raiz quadrada
				double speedSquared = speed * speed;

				if (distanceSquared > speedSquared) {
					double distance = Math.sqrt(distanceSquared); // Calcule a raiz quadrada apenas uma vez
					double directionX = dx / distance;
					double directionY = dy / distance;
					currentX += directionX * speed;
					currentY += directionY * speed;
					angle += Game.random(5, 15); // Random rotation speed
				} else {
					currentX = endX;
					currentY = endY;
					animacaoColeta = false;
				}
			}
		}
	}

	public void render(Graphics g) {
		if (animacaoColeta) {
			Graphics2D g2d = (Graphics2D) g;
			int spriteWidth = sprite.getWidth();
			int spriteHeight = sprite.getHeight();

			g2d.translate(currentX + spriteWidth / 2, currentY + spriteHeight / 2);
			g2d.rotate(Math.toRadians(angle));
			g2d.drawImage(sprite, -spriteWidth / 2, -spriteHeight / 2, null);
			g2d.rotate(-Math.toRadians(angle));
			g2d.translate(-currentX - spriteWidth / 2, -currentY - spriteHeight / 2);
		}
	}
}

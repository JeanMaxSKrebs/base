package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;

public class TileArvore extends Tile {

    public TileArvore(int x, int y) {
        super(x, y, 112, 112, Game.spritesheet_Walls.getSprite(336, 0, 112, 112)); // Ajuste o índice do spritesheet para as árvores
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
}

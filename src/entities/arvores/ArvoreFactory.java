package entities.arvores;

import java.awt.image.BufferedImage;

//Interface para a fábrica de árvores
interface ArvoreFactory<Arvore> {
	Arvore createArvore(int x, int y, int width, int height, BufferedImage sprite);
}
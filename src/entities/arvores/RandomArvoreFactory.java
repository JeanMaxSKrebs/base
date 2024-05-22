package entities.arvores;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import base.Game;

//Implementação da fábrica que gera aleatoriamente subclasses de Arvore
public class RandomArvoreFactory implements ArvoreFactory {
	private static final Class<?>[] ARVORE_CLASSES = new Class<?>[] { Tomateiro.class, Parreira.class,
			Morangueiro.class, Macieira.class, Meloeiro.class, Batateira.class, Bananeira.class, Melancieira.class,
			Nogueira.class
			// Adicione mais subclasses aqui, se necessário
	};

	@Override
	public Arvore createArvore(int x, int y, int width, int height, BufferedImage sprite) {
		// Gera aleatoriamente uma classe de árvore
		Class<?> arvoreClass = ARVORE_CLASSES[Game.random(ARVORE_CLASSES.length)];

		try {
			// Obtém o construtor da classe escolhida
			Constructor<?> constructor = arvoreClass.getConstructor(int.class, int.class, int.class, int.class,
					BufferedImage.class);

			// Cria uma nova instância da classe escolhida com os parâmetros fornecidos
			Arvore arvore = (Arvore) constructor.newInstance(x, y, width, height, sprite);

			// Adiciona frutos aleatoriamente (30% de chance de ter frutos)
            if (Game.random(10) < 3) {
                System.out.println("gerou frutos: " + arvore.getClass().getSimpleName());
                arvore.adicionarFrutosAleatoriamente();
            } else {
                System.out.println("não gerou frutos: " + arvore.getClass().getSimpleName());
            }

			return arvore;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
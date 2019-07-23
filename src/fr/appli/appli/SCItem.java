package fr.appli.appli;

import java.awt.Graphics2D;

public interface SCItem extends Runnable {
	void draw(Graphics2D g);

	void start();

	void stop();
}

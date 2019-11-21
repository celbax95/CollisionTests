package fr.appli.scitem.objet.rock;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import fr.appli.scitem.objet.Objet;
import fr.util.point.Point;

public abstract class Rock extends Objet {

	public Color color;

	public Rock() {
		super();
		this.color = Color.white;
	}

	public void defaultInit() {

		Random r = new Random();

		this.pos.set(new Point(50 + (int) (r.nextDouble() * 1250), 50 + (int) (r.nextDouble() * 650)));
		this.pos.set(new Point(500, 500));
		this.speed = 0;
		this.color = Color.DARK_GRAY;

		this.dir.set(new Point(0, 0));
		this.setMass(5000);
	}

	@Override
	public abstract void draw(Graphics2D g);

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	protected void inputs() {
		this.dir.rotate(0.02);
	}

	private void move(double time) {
		this.pos.add(new Point(this.dir).mult(this.speed).mult(time));
		this.speed *= 1 - 10 * time;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	protected void update(double tick) {
		// this.inputs();
		this.move(tick);
	}
}

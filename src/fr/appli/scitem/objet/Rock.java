package fr.appli.scitem.objet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import fr.col.Circle;
import fr.util.point.Point;

public class Rock extends Objet {

	private Circle personalHitbox;

	private double rad;

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
		this.rad = 40;
		this.color = Color.DARK_GRAY;

		this.personalHitbox = new Circle(this.pos, this.pos, this.rad);

		this.hitbox = this.personalHitbox;
		this.dir.set(new Point(1, 0));
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(this.color);

		int radmult2 = (int) (this.rad * 2);
		int radint = (int) this.rad;
		g.fillOval(this.pos.ix() - radint, this.pos.iy() - radint, radmult2, radmult2);
		this.personalHitbox.draw(g);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	public Circle getPersonalHitbox() {
		return this.personalHitbox;
	}

	private void inputs() {
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

	public void setPersonalHitbox(Circle personalHitbox) {
		super.setHitbox(personalHitbox);
		this.personalHitbox = personalHitbox;
	}

	@Override
	protected void update(double tick) {
		// this.inputs();
		this.move(tick);
	}
}

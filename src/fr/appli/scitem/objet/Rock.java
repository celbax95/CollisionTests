package fr.appli.scitem.objet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import fr.col.Circle;
import fr.util.point.Point;

public class Rock extends Objet {

	private Circle personalHitbox;

	public Color color;

	public Rock() {
		super();
		this.personalHitbox = new Circle(new Point(), 0d);
		this.color = Color.white;
	}

	public void defaultInit() {

		Random r = new Random();

		double mutationRate = 1.3;

		double mutation = 0.8 + (r.nextDouble() * mutationRate - mutationRate / 2);

		this.pos.set(new Point(50 + (int) (r.nextDouble() * 1250), 50 + (int) (r.nextDouble() * 650)));
		this.speed = 0;
		this.color = Color.DARK_GRAY;
		this.personalHitbox = new Circle(this.pos, 45 * mutation);
		this.hitbox = this.personalHitbox;
		this.dir.set(new Point(1, 0));
		this.setMass(5000 * mutation * 1.8);
	}

	@Override
	public void draw(Graphics2D g) {
		int raddiv2 = (int) this.personalHitbox.rad();
		int radmult2 = (int) (this.personalHitbox.rad() * 2);
		g.setColor(this.color);
		g.fillOval(this.pos.ix() - raddiv2, this.pos.iy() - raddiv2, radmult2, radmult2);
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
		System.out.println(this.dir);
		this.dir.rotate(0.02);
		System.out.println(this.dir);
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

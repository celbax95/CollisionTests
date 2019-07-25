package fr.appli.scitem.objet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import fr.col.AABB;
import fr.util.point.Point;

public class Rock extends Objet {

	private AABB personalHitbox;

	private Point size;

	public Color color;

	public Rock() {
		super();
		this.color = Color.white;
		this.size = new Point();
	}

	public void defaultInit() {

		Random r = new Random();

		this.pos.set(new Point(50 + (int) (r.nextDouble() * 1250), 50 + (int) (r.nextDouble() * 650)));
		this.pos.set(new Point(500, 500));
		this.speed = 0;
		this.size = new Point(150, 150);
		this.color = Color.DARK_GRAY;

		Point padding = new Point(1, 1);

		this.personalHitbox = new AABB(this.pos, new Point(this.pos).add(padding),
				new Point(this.pos).add(this.size).sub(new Point(padding).mult(2)));

		this.hitbox = this.personalHitbox;
		this.dir.set(new Point(1, 0));
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(this.color);
		g.fillRect(this.pos.ix(), this.pos.iy(), this.size.ix(), this.size.iy());
		this.personalHitbox.draw(g);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	public AABB getPersonalHitbox() {
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

	public void setPersonalHitbox(AABB personalHitbox) {
		super.setHitbox(personalHitbox);
		this.personalHitbox = personalHitbox;
	}

	@Override
	protected void update(double tick) {
		// this.inputs();
		this.move(tick);
	}
}

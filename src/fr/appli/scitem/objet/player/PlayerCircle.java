package fr.appli.scitem.objet.player;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.col.Circle;

public class PlayerCircle extends Player {

	private Circle personalHitbox;

	private double rad;

	public PlayerCircle() {
		super();
	}

	@Override
	public void defaultInit() {
		super.defaultInit();

		this.rad = 40;
		this.personalHitbox = new Circle(this.pos, this.pos, this.rad);

		this.hitbox = this.personalHitbox;
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(200, 0, 0));
		int radmult2 = (int) (this.rad * 2);
		int radint = (int) this.rad;
		g.fillOval(this.pos.ix() - radint, this.pos.iy() - radint, radmult2, radmult2);
		this.personalHitbox.draw(g);
	}

	/**
	 * @return the personalHitbox
	 */
	public Circle getPersonalHitbox() {
		return this.personalHitbox;
	}

	/**
	 * @return the rad
	 */
	public double getRad() {
		return this.rad;
	}

	/**
	 * @param personalHitbox the personalHitbox to set
	 */
	public void setPersonalHitbox(Circle personalHitbox) {
		this.personalHitbox = personalHitbox;
		this.hitbox = this.personalHitbox;
	}

	/**
	 * @param rad the rad to set
	 */
	public void setRad(double rad) {
		this.rad = rad;
	}
}

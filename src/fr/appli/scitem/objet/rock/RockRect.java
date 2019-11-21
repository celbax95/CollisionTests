package fr.appli.scitem.objet.rock;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.col.AABB;
import fr.util.point.Point;

public class RockRect extends Rock {

	private AABB personalHitbox;

	private Point size;

	public RockRect() {
		super();
	}

	@Override
	public void defaultInit() {
		super.defaultInit();
		this.size = new Point(80, 80);
		this.color = Color.DARK_GRAY;
		this.personalHitbox = new AABB(this.pos, new Point(this.pos), new Point(this.pos).add(this.size));
		this.hitbox = this.personalHitbox;
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(this.color);
		g.fillRect(this.pos.ix(), this.pos.iy(), this.size.ix(), this.size.iy());
		this.personalHitbox.draw(g);
	}

	/**
	 * @return the personalHitbox
	 */
	public AABB getPersonalHitbox() {
		return this.personalHitbox;
	}

	/**
	 * @return the size
	 */
	public Point getSize() {
		return this.size;
	}

	/**
	 * @param personalHitbox the personalHitbox to set
	 */
	public void setPersonalHitbox(AABB personalHitbox) {
		this.personalHitbox = personalHitbox;
		this.hitbox = this.personalHitbox;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Point size) {
		this.size = size;
	}
}

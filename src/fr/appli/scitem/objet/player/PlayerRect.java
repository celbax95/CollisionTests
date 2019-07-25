package fr.appli.scitem.objet.player;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.col.AABB;
import fr.util.point.Point;

public class PlayerRect extends Player {

	private AABB personalHitbox;

	private Point size;

	public PlayerRect() {
		super();
	}

	@Override
	public void defaultInit() {
		super.defaultInit();
		this.size = new Point(80, 80);
		Point padding = new Point(1, 1);
		this.personalHitbox = new AABB(this.pos, new Point(this.pos).add(padding),
				new Point(this.pos).add(this.size).sub(new Point(padding).mult(2)));
		this.personalHitbox = new AABB(this.pos, this.pos, new Point(this.pos).add(this.size));
		this.hitbox = this.personalHitbox;
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(200, 0, 0));
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

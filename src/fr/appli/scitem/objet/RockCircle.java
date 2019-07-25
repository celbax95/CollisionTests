package fr.appli.scitem.objet;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.col.Circle;

public class RockCircle extends Rock {

	private Circle personalHitbox;

	private double rad;

	public RockCircle() {
		super();
	}

	@Override
	public void defaultInit() {
		super.defaultInit();
		this.rad = 40;
		this.color = Color.DARK_GRAY;
		this.personalHitbox = new Circle(this.pos, this.pos, this.rad);
		this.hitbox = this.personalHitbox;
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
}

package fr.col;

import java.awt.Graphics2D;

import fr.util.point.Point;

public class Circle extends HitboxElement {

	private double rad;

	private Point pos;

	public Circle(Point pos, double rad) {
		super();
		this.pos = pos;
		this.rad = rad;
	}

	@Override
	public void draw(Graphics2D g) {
		int radD2 = (int) (this.rad / 2);
		int radM2 = (int) (this.rad * 2);
		g.setColor(this.COLOR);
		g.drawOval(this.pos.ix() - radD2, this.pos.iy() - radD2, radM2, radM2);
	}

	@Override
	public int getType() {
		return 2;
	}

	public Point pos() {
		return this.pos;
	}

	public void pos(Point pos) {
		this.pos = pos;
	}

	public double rad() {
		return this.rad;
	}

	public void rad(double rad) {
		this.rad = rad;
	}
}

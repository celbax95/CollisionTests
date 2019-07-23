package fr.col;

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

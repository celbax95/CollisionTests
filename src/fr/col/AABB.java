package fr.col;

import fr.util.point.Point;

public class AABB extends HitboxElement {

	private Point min;

	private Point max;

	private Point vectMinMax;

	public AABB(Point min, Point max) {
		this.min = min;
		this.max = max;
		this.vectMinMax = new Point(max).sub(min);
	}

	@Override
	public int getType() {
		return 1;
	}

	public Point max() {
		return this.max;
	}

	public void max(Point max) {
		this.max = max;
	}

	public Point min() {
		return this.min;
	}

	public void min(Point min) {
		this.min = min;
	}

	public void updateMax() {
		this.max.set(new Point(this.min).add(this.vectMinMax));
	}
}

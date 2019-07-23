package fr.col;

import fr.appli.scitem.objet.Objet;
import fr.util.point.Point;

public class Manifold {
	public Objet a;
	public Objet b;

	public Point normal;
	public double penetration;

	/**
	 *
	 */
	public Manifold() {
	}

	/**
	 * @param a
	 * @param b
	 * @param normal
	 * @param penetration
	 */
	public Manifold(Manifold m) {
		this.a = m.a;
		this.b = m.b;
		this.normal = m.normal;
		this.penetration = m.penetration;
	}
}

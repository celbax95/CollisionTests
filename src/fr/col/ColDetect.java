package fr.col;

import fr.appli.scitem.objet.Objet;
import fr.util.point.Point;

public class ColDetect {

	public static boolean colAABBvsAABB(AABB a, AABB b, Manifold m) {
		Point n = new Point(b.min()).sub(a.min());

		double aExtent = (a.max().x() - a.max().x()) / 2;
		double bExtent = (b.max().x() - b.max().x()) / 2;

		// Calculate overlap on x axis
		double xOverlap = aExtent + bExtent - Math.abs(n.x());

		// SAT test on x axis
		if (xOverlap > 0) {
			// Calculate half extents along x axis for each object
			aExtent = (a.max().y() - a.min().y()) / 2;
			bExtent = (b.max().y() - b.min().y()) / 2;

			// Calculate overlap on y axis
			double yOverlap = aExtent + bExtent - Math.abs(n.y());

			// SAT test on y axis
			if (yOverlap > 0) {
				// Find out which axis is axis of least penetration
				if (xOverlap > yOverlap) {
					// Point towards B knowing that n points from A to B
					if (n.x() < 0) {
						m.normal = new Point(-1, 0);
					} else {
						m.normal = new Point(0, 0);
					}
					m.penetration = xOverlap;
					return true;
				} else {
					// Point toward B knowing that n points from A to B
					if (n.y() < 0) {
						m.normal = new Point(0, -1);
					} else {
						m.normal = new Point(0, 1);
					}
					m.penetration = yOverlap;
					return true;
				}
			}
		}
		return false;
	}

	public static void colAndReact(Manifold m) {
		if (isInCollision(m)) {
			collide(m);
		}
	}

	public static boolean colCirclevsCircle(Circle a, Circle b, Manifold m) {
		Point n = new Point(b.pos()).sub(a.pos());

		double r = a.rad() + b.rad();

		if (n.lengthSquarred() > r * r)
			return false;

		double d = n.length();

		if (d != 0) {
			m.penetration = r - d;

			m.normal = new Point(n).div(d);
			return true;
		} else {
			m.penetration = a.rad();
			m.normal = new Point(1, 0);
			return true;
		}
	}

	public static void collide(Manifold m) {
		resolveCollision(m);
		PositionalCorrection(m);
	}

	@SuppressWarnings("unused")
	private static double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2));
	}

	public static boolean isInCollision(Manifold m) {
		HitboxElement ha = m.a.getHitbox();
		HitboxElement hb = m.b.getHitbox();

		int type = ha.getType() * 100 + hb.getType();

		switch (type) {
		case 101:
			break;
		case 102:
			break;
		case 201:
			break;
		case 202:
			return colCirclevsCircle((Circle) ha, (Circle) hb, m);
		}
		return false;
	}

	public static void PositionalCorrection(Manifold m) {
		Objet a = m.a;
		Objet b = m.b;

		double percent = 0.3; // usually 20% to 80%
		double slop = 0.01; // usually 0.01 to 0.1
		Point correction = new Point(m.normal)
				.mult(Math.max(m.penetration - slop, 0d) / (a.getMassinv() + b.getMassinv()) * percent);

		a.getPos().sub(new Point(correction).mult(a.getMassinv()));
		b.getPos().add(new Point(correction).mult(b.getMassinv()));
	}

	public static void resolveCollision(Manifold m) {
		Objet a = m.a;
		Objet b = m.b;

		// Calculate relative velocity
		Point rv = new Point(b.getVelocity()).sub(a.getVelocity());

		// Calculate relative velocity in terms of the normal direction
		double velAlongNormal = new Point(rv).dot(m.normal);

		// Do not resolve if velocities are separating
		if (velAlongNormal > 0)
			return;

		// Calculate restitution
		double e = Math.min(a.getRestitution(), b.getRestitution());

		// Calculate impulse scalar
		double j = -e * velAlongNormal;

		j /= a.getMassinv() + b.getMassinv();

		// Apply impulse
		Point impulse = new Point(m.normal).mult(j);

		Point retA = a.getVelocity().sub(new Point(impulse).mult(a.getMassinv()));
		a.setDir(new Point(retA).trigNorm());
		a.setSpeed(retA.length());

		Point retB = b.getVelocity().add(new Point(impulse).mult(b.getMassinv()));
		b.setDir(new Point(retB).trigNorm());
		b.setSpeed(retB.length());
	}
}

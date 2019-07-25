package fr.col;

import fr.appli.scitem.objet.Objet;
import fr.util.point.Point;

public class ColDetect {

	public static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}

	public static boolean colAABBvsAABB(AABB a, AABB b, Manifold m) {

		Point n = new Point(b.getCenter()).sub(a.getCenter());

		double aExtent = (a.max().x() - a.min().x()) / 2;
		double bExtent = (b.max().x() - b.min().x()) / 2;

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
				if (xOverlap < yOverlap) {
					// Point towards B knowing that n points from A to B
					if (n.x() < 0) {
						m.normal = new Point(-1, 0);
					} else {
						m.normal = new Point(1, 0);
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

	public static boolean colAABBvsCircle(AABB a, Circle b, Manifold m) {
		// Vector from A to B
		Point aCenter = a.getCenter();
		Point bCenter = b.getCenter();
		Point amin = a.min();
		Point amax = a.max();

		Point n = new Point(b.getCenter()).sub(aCenter);

		// Closest point on A to center of B
		Point closest = new Point(n);

		// Calculate half extents along each axis
		double xExtent = (amax.x() - amin.x()) / 2;
		double yExtent = (amax.y() - amin.y()) / 2;

		// Clamp point to edges of the AABB
		closest.x(clamp(xExtent, -xExtent, closest.x()));
		closest.y(clamp(yExtent, -yExtent, closest.y()));

		boolean inside = false;

		// Circle is inside the AABB, so we need to clamp the circle's center
		// to the closest edge
		if (n.equals(closest)) {
			inside = true;

			// Find closest axis
			if (Math.abs(n.x()) > Math.abs(n.y())) {
				// Clamp to closest extent
				if (closest.x() > 0) {
					closest.x(xExtent);
				} else {
					closest.x(-xExtent);
				}
			}

			// y axis is shorter
			else {
				// Clamp to closest extent
				if (closest.y() > 0) {
					closest.y(yExtent);
				} else {
					closest.y(-yExtent);
				}
			}
		}

		Point normal = new Point(n).sub(closest);
		double d = normal.lengthSquarred();
		double r = b.rad();

		// Early out of the radius is shorter than distance to closest point and
		// Circle not inside the AABB
		if (d > r * r && !inside)
			return false;

		// Avoided sqrt until we needed
		d = Math.sqrt(d);

		// Collision normal needs to be flipped to point outside if circle was
		// inside the AABB
		if (inside) {
			m.normal = new Point(n).mult(-1);
			m.penetration = r - d;
		} else {
			m.normal = n;
			m.penetration = r - d;
		}

		if (amin.x() < bCenter.x() && bCenter.x() < amax.x() || amin.y() < bCenter.y() && bCenter.y() < amax.y()) {
			m.normal.oneLibDegNorm();
		} else {
			m.normal.trigNorm();
		}
		return true;
	}

	public static void colAndReact(Manifold m) {
		if (isInCollision(m)) {
			collide(m);
		}
	}

	public static boolean colCirclevsCircle(Circle a, Circle b, Manifold m) {
		Point n = new Point(b.getCenter()).sub(a.getCenter());

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
			return colAABBvsAABB((AABB) ha, (AABB) hb, m);
		case 102:
			return colAABBvsCircle((AABB) ha, (Circle) hb, m);
		case 201:
			Objet tmp = m.a;
			m.a = m.b;
			m.b = tmp;
			return colAABBvsCircle((AABB) hb, (Circle) ha, m);
		case 202:
			return colCirclevsCircle((Circle) ha, (Circle) hb, m);
		}
		return false;
	}

	public static void PositionalCorrection(Manifold m) {
		Objet a = m.a;
		Objet b = m.b;

		double percent = 0.2; // usually 20% to 80%
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

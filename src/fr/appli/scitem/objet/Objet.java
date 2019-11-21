package fr.appli.scitem.objet;

import java.awt.Graphics2D;

import fr.appli.appli.SCItem;
import fr.col.AABB;
import fr.col.HitboxElement;
import fr.util.point.Point;
import fr.util.time.Timer;

public abstract class Objet implements SCItem {
	protected Thread myThread;

	protected Point pos;

	protected Point dir;

	protected double restitution;

	private double massinv;

	private double mass;

	protected double speed;

	protected HitboxElement hitbox;

	public Objet() {
		this.pos = new Point();
		this.dir = new Point();
		this.myThread = null;
		this.restitution = 0; // 1 is no bounce
		this.setMass(5000);
		this.speed = 0;
		this.hitbox = new AABB(this.pos, new Point(0, 0), new Point(0, 0));
	}

	@Override
	public abstract void draw(Graphics2D g);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Objet other = (Objet) obj;
		if (this.dir == null) {
			if (other.dir != null)
				return false;
		} else if (!this.dir.equals(other.dir))
			return false;
		if (this.hitbox == null) {
			if (other.hitbox != null)
				return false;
		} else if (!this.hitbox.equals(other.hitbox))
			return false;
		if (Double.doubleToLongBits(this.mass) != Double.doubleToLongBits(other.mass))
			return false;
		if (Double.doubleToLongBits(this.massinv) != Double.doubleToLongBits(other.massinv))
			return false;
		if (this.myThread == null) {
			if (other.myThread != null)
				return false;
		} else if (!this.myThread.equals(other.myThread))
			return false;
		if (this.pos == null) {
			if (other.pos != null)
				return false;
		} else if (!this.pos.equals(other.pos))
			return false;
		if (Double.doubleToLongBits(this.restitution) != Double.doubleToLongBits(other.restitution))
			return false;
		if (Double.doubleToLongBits(this.speed) != Double.doubleToLongBits(other.speed))
			return false;
		return true;
	}

	public Point getDir() {
		return this.dir;
	}

	public HitboxElement getHitbox() {
		return this.hitbox;
	}

	public double getMass() {
		return this.mass;
	}

	public double getMassinv() {
		return this.massinv;
	}

	public Thread getMyThread() {
		return this.myThread;
	}

	public Point getPos() {
		return this.pos;
	}

	public double getRestitution() {
		return this.restitution;
	}

	public double getSpeed() {
		return this.speed;
	}

	public Point getVelocity() {
		return new Point(this.dir).mult(this.speed);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.dir == null ? 0 : this.dir.hashCode());
		result = prime * result + (this.hitbox == null ? 0 : this.hitbox.hashCode());
		long temp;
		temp = Double.doubleToLongBits(this.mass);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(this.massinv);
		result = prime * result + (int) (temp ^ temp >>> 32);
		result = prime * result + (this.myThread == null ? 0 : this.myThread.hashCode());
		result = prime * result + (this.pos == null ? 0 : this.pos.hashCode());
		temp = Double.doubleToLongBits(this.restitution);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(this.speed);
		result = prime * result + (int) (temp ^ temp >>> 32);
		return result;
	}

	@Override
	public void run() {
		Timer t = new Timer();
		while (!Thread.currentThread().isInterrupted()) {
			this.update(t.tick());
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}
	}

	public void setDir(Point dir) {
		this.dir = dir;
	}

	public void setHitbox(HitboxElement hitbox) {
		this.hitbox = hitbox;
	}

	public void setMass(double mass) {
		if (mass <= 0) {
			this.mass = 0;
			this.massinv = 0;
		} else {
			this.mass = mass;
			this.massinv = 1 / mass;
		}
	}

	public void setMassinv(double massinv) {
		if (massinv <= 0) {
			this.massinv = 0;
			this.mass = 0;
		} else {
			this.massinv = massinv;
			this.mass = 1 / this.mass;
		}
	}

	public void setMyThread(Thread myThread) {
		this.myThread = myThread;
	}

	public void setPos(Point pos) {
		this.pos.set(pos);
	}

	public void setRestitution(double restitution) {
		this.restitution = restitution;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void start() {
		(this.myThread = new Thread(this)).start();
	}

	@Override
	public void stop() {
		this.myThread.interrupt();
	}

	protected abstract void update(double tick);
}
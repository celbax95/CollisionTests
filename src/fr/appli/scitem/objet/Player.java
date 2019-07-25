package fr.appli.scitem.objet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fr.col.AABB;
import fr.screen.keyboard.KeyBoard;
import fr.util.point.Point;

public class Player extends Objet {

	private AABB personalHitbox;

	private Point size;

	private boolean moving;

	private double maxSpeed;

	private double acceleration;

	public Player() {
		super();
		this.personalHitbox = new AABB(this.pos, new Point(), new Point());
		this.size = new Point();
	}

	public void defaultInit() {
		this.pos.set(new Point(100, 100));
		this.size = new Point(80, 80);
		this.speed = 0;

		Point padding = new Point(1, 1);

		this.personalHitbox = new AABB(this.pos, new Point(this.pos).add(padding),
				new Point(this.pos).add(this.size).sub(new Point(padding).mult(2)));
		this.hitbox = this.personalHitbox;
		this.moving = false;
		this.maxSpeed = 300;
		this.acceleration = 2000;
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(200, 0, 0));
		g.fillRect(this.pos.ix(), this.pos.iy(), this.size.ix(), this.size.iy());
		this.personalHitbox.draw(g);
	}

	private void inputs() {
		this.moving = false;

		boolean mx = false, my = false;

		if (KeyBoard.isPressed(KeyEvent.VK_Z)) {
			this.dir.y(-1);
			my = true;
		}
		if (KeyBoard.isPressed(KeyEvent.VK_S)) {
			this.dir.y(1);
			my = true;
		}
		if (KeyBoard.isPressed(KeyEvent.VK_Q)) {
			this.dir.x(-1);
			mx = true;
		}
		if (KeyBoard.isPressed(KeyEvent.VK_D)) {
			this.dir.x(1);
			mx = true;
		}

		if (mx && !my) {
			this.dir.y(0);
		} else if (!mx && my) {
			this.dir.x(0);
		}

		this.moving = mx || my;

		this.dir.trigNorm();
	}

	private void move(double time) {
		if (this.moving && this.speed < this.maxSpeed) {
			this.speed += this.acceleration * time;
		} else if (this.speed > this.maxSpeed) {
			this.speed = this.maxSpeed;
		} else {
			this.speed *= 1 - 10 * time;
			if (this.speed < 0.0001) {
				this.dir.set(new Point(0, 0));
			}
		}
		this.pos.add(new Point(this.dir).mult(this.speed).mult(time));
	}

	@Override
	protected void update(double tick) {
		this.inputs();
		this.move(tick);
	}
}

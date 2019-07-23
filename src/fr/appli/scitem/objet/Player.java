package fr.appli.scitem.objet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fr.col.Circle;
import fr.screen.keyboard.KeyBoard;
import fr.util.point.Point;

public class Player extends Objet {

	private Circle personalHitbox;

	private boolean moving;

	private double maxSpeed;

	private double acceleration;

	public Player() {
		super();
		this.personalHitbox = new Circle(new Point(), 0d);
	}

	public void defaultInit() {
		this.pos.set(new Point(150, 150));
		this.speed = 0;
		this.personalHitbox = new Circle(this.pos, 45);
		this.hitbox = this.personalHitbox;
		this.moving = false;
		this.maxSpeed = 300;
		this.acceleration = 2000;
		this.setMass(5000);
	}

	@Override
	public void draw(Graphics2D g) {
		int raddiv2 = (int) this.personalHitbox.rad();
		int radmult2 = (int) (this.personalHitbox.rad() * 2);
		g.setColor(new Color(200, 0, 0));
		g.fillOval(this.pos.ix() - raddiv2, this.pos.iy() - raddiv2, radmult2, radmult2);
	}

	private void inputs() {
		this.moving = false;
		if (KeyBoard.isPressed(KeyEvent.VK_Z)) {
			this.dir.y(-1);
			this.moving = true;
		}
		if (KeyBoard.isPressed(KeyEvent.VK_S)) {
			this.dir.y(1);
			this.moving = true;
		}
		if (KeyBoard.isPressed(KeyEvent.VK_Q)) {
			this.dir.x(-1);
			this.moving = true;
		}
		if (KeyBoard.isPressed(KeyEvent.VK_D)) {
			this.dir.x(1);
			this.moving = true;
		}
		this.dir.trigNorm();
	}

	private void move(double time) {
		if (this.moving && this.speed < this.maxSpeed) {
			this.speed += this.acceleration * time;
		} else if (this.speed > this.maxSpeed) {
			this.speed = this.maxSpeed;
		} else {
			this.speed *= 1 - 10 * time;
		}
		this.pos.add(new Point(this.dir).mult(this.speed).mult(time));
	}

	@Override
	protected void update(double tick) {
		this.inputs();
		this.move(tick);
	}
}

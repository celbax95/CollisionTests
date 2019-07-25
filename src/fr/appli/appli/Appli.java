package fr.appli.appli;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.appli.scitem.objet.Objet;
import fr.appli.scitem.objet.player.PlayerRect;
import fr.appli.scitem.objet.rock.RockCircle;
import fr.appli.scitem.objet.rock.RockRect;
import fr.col.AABB;
import fr.col.Circle;
import fr.main.Main;
import fr.screen.ScreenApp;
import fr.util.point.Point;

public class Appli implements ScreenApp {
	private static Color backgroundColor = new Color(20, 20, 20);

	private List<SCItem> lsci = new LinkedList<>();
	private Point pos, size;

	private double time;

	@Override
	public void appLoop(Graphics2D g, double time) {
		this.time = time;
		for (SCItem i : this.lsci) {
			i.draw(g);
		}
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public String getScreenTitle() {
		return "App";
	}

	@Override
	public ScreenApp init(int scrWidth, int scrHeight) {
		this.pos = new Point(0, 0);
		this.size = new Point(scrWidth, scrHeight);

		List<Objet> collider = new ArrayList<>();

		PlayerRect p = new PlayerRect();
		p.defaultInit();
		collider.add(p);
		this.lsci.add(p);

		RockCircle r = new RockCircle();
		r.defaultInit();
		r.setColor(Color.WHITE);
		r.setPos(new Point(680, 380));
		r.setPersonalHitbox(new Circle(r.getPos(), r.getPos(), 50));
		r.setRad(50);
		r.setMass(-1);
		collider.add(r);
		this.lsci.add(r);

		// Circles
		for (int i = 0; i < 1; i++) {
			RockCircle r1 = new RockCircle();
			r1.defaultInit();
			collider.add(r1);
			this.lsci.add(r1);
		}

		// Rects
		for (int i = 0; i < 1; i++) {
			RockRect r1 = new RockRect();
			r1.defaultInit();
			collider.add(r1);
			this.lsci.add(r1);
		}

		RockRect rr = new RockRect();
		rr.defaultInit();
		rr.setPos(new Point(100, Main.HEIGHT - 100));
		rr.setSize(new Point(Main.WIDTH - 100, 20));
		rr.setPersonalHitbox(new AABB(rr.getPos(), rr.getPos(), new Point(rr.getPos()).add(rr.getSize())));
		rr.setMass(0);
		rr.setColor(new Color(200, 100, 50));
		collider.add(rr);
		this.lsci.add(rr);

		new CollisionTester(collider);

		for (SCItem i : this.lsci) {
			i.start();
		}
		return this;
	}

	public double time() {
		return this.time;
	}

}

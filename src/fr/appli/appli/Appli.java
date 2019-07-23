package fr.appli.appli;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.appli.scitem.objet.Objet;
import fr.appli.scitem.objet.Player;
import fr.appli.scitem.objet.Rock;
import fr.col.Circle;
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

		Player p = new Player();
		p.defaultInit();
		collider.add(p);
		this.lsci.add(p);

		Rock r = new Rock();
		r.defaultInit();
		r.setColor(Color.WHITE);
		r.setPos(new Point(680, 380));
		r.setPersonalHitbox(new Circle(r.getPos(), 50));
		r.setMass(-1);
		collider.add(r);
		this.lsci.add(r);

		for (int i = 0; i < 20; i++) {
			Rock r1 = new Rock();
			r1.defaultInit();
			collider.add(r1);
			this.lsci.add(r1);
		}

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

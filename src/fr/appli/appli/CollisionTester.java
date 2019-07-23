package fr.appli.appli;

import java.util.List;

import fr.appli.scitem.objet.Objet;
import fr.col.ColDetect;
import fr.col.Manifold;

public class CollisionTester {

	List<Objet> o;

	public CollisionTester(List<Objet> o) {
		this.o = o;

		this.newCollider();
	}

	public void newCollider() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Manifold m = new Manifold();
				while (true) {
					for (Objet o1 : CollisionTester.this.o) {
						m.a = o1;
						for (Objet o2 : CollisionTester.this.o) {
							if (!o1.equals(o2)) {
								m.b = o2;
								ColDetect.colAndReact(m);
							}
						}
					}
				}
			}
		}).start();
	}
}

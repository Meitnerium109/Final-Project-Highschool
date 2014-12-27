package Projectiles;

public class SwordBullet extends Bullet {

	public int dieCounter = 0;
	public int dieTime = 5;

	public SwordBullet(int x, int y, int damage) {
		super(x, y, damage);

		width = 17;
		height = 22;

		makeGraphic((int) width, (int) height, 0x0000FF00);

	}

	public void update() {
		super.update();

		if (dieCounter < dieTime) {
			dieCounter++;
		} else {
			kill();
		}
	}
}

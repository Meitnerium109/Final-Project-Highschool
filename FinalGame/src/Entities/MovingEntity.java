package Entities;

/**
 * Anything that can move around that is an entity, has health (enemies, player,
 * etc.)
 * 
 * @author Andrew Lau
 * 
 */
public class MovingEntity extends Entity {

	public int health;
	public int speed;

	public MovingEntity(int x, int y, int health) {
		super(x, y);
		this.health = health;

		speed = 0;
	}

	/**
	 * Hits the enemy for damage
	 * 
	 * @param damage
	 */
	public void hit(int damage) {
		health -= damage;
		onHit();
		if (health <= 0) {
			onDeath();
		}
	}

	/**
	 * Override this to give the entity a function on hit
	 */
	public void onHit() {

	}

	/**
	 * Override this to give the entity a function on death (health <= 0)
	 */
	public void onDeath() {

	}

}

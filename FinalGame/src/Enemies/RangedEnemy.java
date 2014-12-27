package Enemies;

public class RangedEnemy extends Enemy {

	public boolean canAttack = true;
	public int attackCounter = 0;
	
	/**
	 * The cooldown before the alien can attack
	 */
	public int attackTime;

	/**
	 * The attack flag becomes true when the ranged enemy attacks, not just when
	 * it can attack
	 */
	public boolean attackFlag = false;

	public RangedEnemy(int x, int y, int health) {
		super(x, y, health);

	}

	public void update() {
		super.update();

		/**
		 * Lets the enemy attack after its cooldown has ended
		 */
		if (!canAttack) {
			if (attackCounter < attackTime) {
				attackCounter++;
			} else {
				attackCounter = 0;
				canAttack = true;
			}
		}

	}

	public void attack() {
		super.attack();
		attackFlag = false;
	}

}

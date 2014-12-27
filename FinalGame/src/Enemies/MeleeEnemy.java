package Enemies;

public abstract class MeleeEnemy extends Enemy {

	public boolean canDamage;
	
	public MeleeEnemy(int x, int y, int health) {
		super(x, y, health);
	}
	
	public void update(){
		super.update();
	}
}

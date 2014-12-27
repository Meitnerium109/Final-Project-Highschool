package Enemies;

public class BurrowingEnemy extends MeleeEnemy {

	/**
	 * Whether or not the burrowing enemy is burrowed. When the player touches
	 * it when burrowed, it explodes and damages the player
	 */
	public boolean burrowed;

	public BurrowingEnemy(int x, int y) {
		super(x, y, 50);

		height = 20;
		width = 20;

		makeGraphic((int) width, (int) height, 0xFFFF0000);

		//Does one full heart of damage 
		damage = 2;
		
	}
	
	public void generateAction(){
		//The burrowing enemy moves around and then burrows when close to the player
	}
	
	public void attack(){
		//play explosion animation and then leave after it is done
		
	}
}

package Weapons;

import org.flixel.FlxSprite;

import Misc.RandomNumber;
import Misc.Timer;

/**
 * The class that holds the weapon and main weapon functions
 * 
 * @author Andrew Lau
 * 
 */
public class Weapon extends FlxSprite {

	public String weaponName = "NO_NAME";
	
	// For ranged attacks
	public boolean canAttack = false;

	public int lowDamage = 10;
	public int highDamage = 20;

	/**
	 * The recoil on the weapon. The amount that the player moves back after he shoots it
	 */
	public float recoil = 0;
	
	public boolean animationFinished = false;
	public int animationFrames = 20;

	public int spread;
	
	/**
	 * Whether or not the player is currently holding the weapon
	 */
	public boolean equipped = false;

	/**
	 * This timer sets the cooldown and sets canAttack to true when they can
	 * attack
	 */
	public Timer cooldownTimer;
	public int cooldownTime = 10;

	/**
	 * Only for melee attacks
	 */
	public boolean meleeAttacking = false;

	public Weapon() {

		x = -100;
		y = 1000;

		makeGraphic(1, 1, 0x00ffbb66);

		cooldownTimer = new Timer(10);
		cooldownTimer.restart();
		
		spread = 10;
	}

	/**
	 * Resets the ability to attack. Resets the timer to 0 and restarts the
	 * timer
	 */
	public void attack() {
		canAttack = false;
		cooldownTimer.restart();
		animationFinished = false;
	}

	public void update() {
		super.update();

		cooldownTimer.update();
		
		if (cooldownTimer.isFinished()) {
			canAttack = true;
		}
		
		checkAnimationFinished();

	}

	public void checkAnimationFinished() {
		if (!animationFinished) {
			if ((int) cooldownTimer.getCurrentFrame() == animationFrames) {
				animationFinished = true;
			}
		}
	}

	/**
	 * Returns an integer representing the damage that is dealt
	 */
	public int dealDamage() {
		return RandomNumber.generateRandomNumber(lowDamage, highDamage);
	}
	
	public void resetWeapon(){
		canAttack = false;
		cooldownTimer.restart();
		animationFinished = true;
	}

}

package Enemies;

import org.flixel.FlxSprite;

import com.esotericsoftware.tablelayout.Value;

import Entities.MovingEntity;
import PlayerPackage.Player;

//Anything that moves, has AI, and can damage the player
public abstract class Enemy extends MovingEntity {

	/**
	 * Other stuff
	 */
	public int walkingSpeed;
	public int jumpSpeedY = 300;

	public boolean updateDirectionByVelocity = true;

	/**
	 * Whether or not the coins have been exploded after its death
	 */
	public boolean coinsGiven = false;

	/**
	 * Signifies whether the enemy is already performing an action
	 */
	public boolean acting = false;

	/**
	 * The amount of time that the dog does this certain action. When the time
	 * limit is up, then acting will become false
	 */
	public int actingTime = 0;
	public int actingCounter = 0;

	public int originalDrag = 1000;

	/**
	 * KNOCKBACK
	 */
	public int knockbackTime = 0;
	public int knockbackCounter = 0;
	public boolean knockedBack = false;
	public int knockbackForce = 0;

	/**
	 * Whether the enemy is still alive
	 */
	public boolean dead = false;

	/**
	 * Damage is in half hearts
	 */
	public int damage = 0;

	/**
	 * COIN VARIABLES
	 */
	public int numCoins = 5;
	public int value = 1;
	public int explosionForce = 100;

	public Enemy(int x, int y, int health) {
		super(x, y, health);

		drag.x = 1000;
		acceleration.y = 980;
		knockbackTime = 20;

	}

	public void update() {

		if (!dead) {

			AI();
			super.update();

			if (!acting) {
				generateAction();
			} else {
				// Reset the acting after the action is done so it can generate
				// another action

				actingCounter++;

				if (actingCounter >= actingTime) {
					acting = false;
					actingCounter = 0;
				}
			}

			if (knockedBack) {
				if (knockbackCounter < knockbackTime) {
					knockbackCounter++;
				} else if (knockbackCounter >= knockbackTime) {
					knockbackForce = 0;
					knockedBack = false;
					knockbackCounter = 0;
				}
			}
		}

	}

	public void AI() {

		// Corrects facing directions and all that stuff
		if (velocity.x > 0) {
			if (updateDirectionByVelocity) {
				_facing = FlxSprite.RIGHT;
			}
			showWalkingAnimation();
		} else if (velocity.x < 0) {
			if (updateDirectionByVelocity) {
				_facing = FlxSprite.LEFT;
			}
			showWalkingAnimation();
		} else {
			showStandingAnimation();
		}

		if (!acting) {
			generateAction();
		}

	}

	/**
	 * Generates an action for the enemy to do. Called when the acting variable
	 * is false
	 */
	public void generateAction() {

	}

	/**
	 * Stops the current action
	 */
	public void stopAction() {

	}

	public void showKnockbackAnimation() {

	}

	public void showWalkingAnimation() {

	}

	public void showStandingAnimation() {

	}

	public void attack() {

	}

	public void readyToAttack() {

	}

	public int dealDamage() {
		return damage;
	}

	public void knockback(int force) {
		if (!dead) {
			velocity.x = knockbackForce;
			knockbackCounter = 0;
			knockedBack = true;
			knockbackForce = force;
			drag.x = originalDrag;
		}
	}

	public void walkLeft(int numFrames) {
		acting = true;

		velocity.x = -walkingSpeed;
		drag.x = 0;
		actingTime = numFrames;
		setFacing(FlxSprite.LEFT);
		showWalkingAnimation();
	}

	public void walkRight(int numFrames) {
		acting = true;

		velocity.x = walkingSpeed;
		drag.x = 0;
		actingTime = numFrames;
		setFacing(FlxSprite.RIGHT);
		showWalkingAnimation();

	}

}

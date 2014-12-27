package PlayerPackage;

import java.util.ArrayList;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

import Entities.MovingEntity;
import Misc.RandomNumber;
import Misc.Timer;
import Weapons.MachineGun;
import Weapons.Pistol;
import Weapons.RocketLauncher;
import Weapons.Shotgun;
import Weapons.SniperRifle;
import Weapons.Sword;
import Weapons.Weapon;

/**
 * Main character in the game, lost of stuff will be in here.
 * 
 * @author Andrew Lau
 * 
 */
public class Player extends MovingEntity {

	// 32 * 32

	public static int playerX;
	public static int playerY;

	public int invulnerableCounter = 0;
	public int invulnerableTime = 2 * Timer.FRAMES_TO_SECONDS;
	public boolean invulnerable = false;

	public Weapon holdingWeapon;

	/**
	 * ONLY FOR MELEE
	 */
	public static boolean canAttack = true;
	/**
	 * ONLY FOR MELEE
	 */

	public boolean canJump;

	/**
	 * The player cannot move when he is knockedBack
	 */
	public boolean knockedBack = false;
	public int knockbackCounter = 0;
	public int knockbackTime = 10;
	public boolean onLadder = false;

	public static int playerJumpSpeed = 350;

	// Timers
	public final static int gameFPS = 60;

	public static int meleeAttackFPS = 20;
	public static int meleeAttackFrames = 4;

	ArrayList<Timer> timers = new ArrayList<Timer>(10);

	public static int equippedWeapon = 0;
	public static final int SWORD = 0;
	public static final int PISTOL = 1;
	public static final int SHOTGUN = 2;
	public static final int ROCKET_LAUNCHER = 4;
	public static final int MACHINE_GUN = 3;
	public static final int SNIPER_RIFLE = 5;

	/**
	 * Bonus damage
	 */
	public static int swordBonus = 0;
	public static int pistolBonus = 0;
	public static int shotgunBonus = 0;
	public static int rocketLauncherBonus = 0;
	public static int machineGunBonus = 0;
	public static int sniperRifleBonus = 0;

	public static int ladderClimbSpeed = 125;
	public static int gravity = 980;

	/**
	 * The number of extra coins that the enemy will drop
	 */
	public static int extraCoins = 0;

	/**
	 * Critical chance percent out of 100
	 */
	public static int criticalChance = 10;

	/**
	 * The chance to dodge the attack
	 */
	public static int dodgeChance = 1;

	/**
	 * ITEM VARIABLES
	 */
	public static boolean hasMagnet = false;
	public static boolean hasKey = false;

	/**
	 * Handles all the animations for this character. The hitbox will be handled
	 * by the Player class and animations will be handled by this sprite This
	 * sprite must be added in the PlayState
	 */
	public static FlxSprite playerAnimationSprite;

	/**
	 * Default to 100 health
	 * 
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		super(x, y, 100);

		drag.x = 500;

		// Width and Height for the animation
		width = 32;
		height = 32;

		speed = 150;

		playerAnimationSprite = new FlxSprite(this.x, this.y);

		playerAnimationSprite.loadGraphic("CharacterAnimations.png", true,
				true, (int) width, (int) height);

		// Width and height for the hitbox
		width = 10;
		height = 24;

		playerAnimationSprite.addAnimation("PistolWalk", new int[] { 7, 8, 9,
				10 }, 10, true);
		playerAnimationSprite.addAnimation("PistolStand", new int[] { 7 }, 1,
				true);
		playerAnimationSprite.addAnimation("PistolAttack", new int[] { 11, 11,
				11, 11 }, 10, true);

		playerAnimationSprite.addAnimation("Stand", new int[] { 0 }, 12, true);
		playerAnimationSprite.addAnimation("Walk", new int[] { 0, 1, 2, 3 },
				10, true);
		playerAnimationSprite.addAnimation("Attack", new int[] { 5, 6, 6, 6, 6,
				6, 6 }, meleeAttackFPS, true);

		makeGraphic((int) width, (int) height, 0x00FF0000);

		playerAnimationSprite.addAnimation("ShotgunWalk", new int[] { 12, 13,
				14, 15 }, 10, true);
		playerAnimationSprite.addAnimation("ShotgunStand", new int[] { 12 }, 1,
				true);
		playerAnimationSprite.addAnimation("ShotgunAttack", new int[] { 16, 17,
				18, 19 }, 7, true);

		playerAnimationSprite.addAnimation("RocketLauncherWalk", new int[] {
				20, 21, 22, 23 }, 10, true);
		playerAnimationSprite.addAnimation("RocketLauncherStand",
				new int[] { 20 }, 1, true);
		playerAnimationSprite.addAnimation("RocketLauncherAttack",
				new int[] { 21 }, 5, true);

		playerAnimationSprite.addAnimation("MachineGunWalk", new int[] { 24,
				25, 26, 27 }, 10, true);
		playerAnimationSprite.addAnimation("MachineGunStand", new int[] { 24 },
				10, true);
		playerAnimationSprite.addAnimation("MachineGunAttack",
				new int[] { 28 }, 1, true);

		playerAnimationSprite.addAnimation("Climbing", new int[] { 39, 40, 41, 42 }, 12,
				true);
		playerAnimationSprite.addAnimation("ClimbingStill", new int[] { 39 },
				1, true);

		playerAnimationSprite.addAnimation("SniperRifleWalk", new int[] { 31,
				32, 33, 34 }, 10, true);
		playerAnimationSprite.addAnimation("SniperRifleAttack",
				new int[] { 31 }, 1, true);
		playerAnimationSprite.addAnimation("SniperRifleStand",
				new int[] { 31 }, 1, true);

		playerAnimationSprite.addAnimation("SwordInvulnerable", new int[] { 0,
				50 }, 10, true);
		playerAnimationSprite.addAnimation("PistolInvulnerable", new int[] { 7,
				50 }, 10, true);
		playerAnimationSprite.addAnimation("ShotgunInvulnerable", new int[] {
				12, 50 }, 10, true);
		playerAnimationSprite.addAnimation("AssaultRifleInvulnerable",
				new int[] { 24, 50 }, 10, true);
		playerAnimationSprite.addAnimation("RocketLauncherInvulnerable",
				new int[] { 20, 50 }, 10, true);
		playerAnimationSprite.addAnimation("SniperRifleInvulnerable",
				new int[] { 31, 50 }, 10, true);

		playerAnimationSprite.addAnimation("ExplodeDie", new int[] { 35, 36,
				37, 38 }, 7, true);

		acceleration.y = 980;

		canJump = false;
		antialiasing = true;

		/**
		 * Timers for stuff
		 */

		/**
		 * Gives the first weapon for the player
		 */
		setWeapon(new Sword());
	}

	/**
	 * Sets the weapon of the player
	 * 
	 * @param weapon
	 */
	public void setWeapon(Weapon weapon) {
		dropCurrentWeapon();

		holdingWeapon = weapon;
		holdingWeapon.equipped = true;

		if (weapon instanceof Sword) {
			equipSword();
		} else if (weapon instanceof Pistol) {
			equipPistol();
		} else if (weapon instanceof Shotgun) {
			equipShotgun();
		} else if (weapon instanceof RocketLauncher) {
			equipRocketLauncher();
		} else if (weapon instanceof MachineGun) {
			equipMachineGun();
		} else if (weapon instanceof SniperRifle) {
			equipSniperRifle();
		}

	}

	public void equipSniperRifle() {
		equippedWeapon = SNIPER_RIFLE;
	}

	public void equipMachineGun() {
		equippedWeapon = MACHINE_GUN;
	}

	public void equipRocketLauncher() {
		equippedWeapon = ROCKET_LAUNCHER;
	}

	public void equipPistol() {
		equippedWeapon = PISTOL;
	}

	public void equipSword() {
		equippedWeapon = SWORD;
	}

	public void equipShotgun() {
		equippedWeapon = SHOTGUN;
	}

	/**
	 * Drops the current weapon
	 */
	public void dropCurrentWeapon() {
		// TODO dropCurrentWeapon() Drops the current weapon
	}

	/**
	 * Critical hits deal double damage
	 * 
	 * @return
	 */
	public boolean getCritical() {
		int number = RandomNumber.generateRandomNumber(0, 100);

		if (number <= criticalChance) {
			return true;
		}

		return false;
	}

	/**
	 * Whether or not you dodged the profesh attack
	 * 
	 * @return
	 */
	public boolean getDodge() {
		int number = RandomNumber.generateRandomNumber(0, 100);
		if (number <= dodgeChance) {
			return true;
		}

		return false;
	}

	public void update() {
		super.update();
		playerX = (int) x;
		playerY = (int) y;

		// Change the animation if the player is not attacking

		if (invulnerable) {
			if (velocity.x < 0) {
				playerAnimationSprite.setFacing(FlxSprite.LEFT);
				_facing = FlxSprite.LEFT;
			} else if (velocity.x > 0) {
				playerAnimationSprite.setFacing(FlxSprite.RIGHT);
				_facing = FlxSprite.RIGHT;
			}
			if (invulnerableCounter < invulnerableTime) {
				invulnerableCounter++;
			} else {
				invulnerable = false;
				invulnerableCounter = 0;
			}
		}

		if (holdingWeapon.animationFinished && !invulnerable) {

			if (onLadder) {

				if (Math.abs((int) velocity.y) > 0) {
					playerAnimationSprite.play("Climbing");
				} else {
					playerAnimationSprite.play("ClimbingStill");
				}

			} else {
				if (velocity.x > 0) {
					if (!FlxG.keys.pressed("X")) {
						playerAnimationSprite.setFacing(FlxSprite.RIGHT);
						_facing = FlxSprite.RIGHT;
					}
					playWalk();
				} else if (velocity.x < 0) {

					if (!FlxG.keys.pressed("X")) {
						playerAnimationSprite.setFacing(FlxSprite.LEFT);
						_facing = FlxSprite.LEFT;
					}
					playWalk();
				} else {
					playStand();
				}
			}
		} else if (invulnerable) {
			playInvulnerable();
		}

		// if (holdingWeapon.animationFinished) {
		// playStand();
		// }

		if (knockedBack) {
			if (knockbackCounter < knockbackTime) {
				knockbackCounter++;
			} else {
				knockbackCounter = 0;
				knockedBack = false;
			}
		}

		// Updates all the timers
		for (Timer timer : timers) {
			timer.update();
		}

		// System.out.println(holdingWeapon.cooldownTimer.getCurrentFrame() +
		// " " + holdingWeapon.cooldownTimer.totalFrames);
	}

	public void playInvulnerable() {
		switch (equippedWeapon) {
		case SWORD:
			playerAnimationSprite.play("SwordInvulnerable");
			break;
		case PISTOL:
			playerAnimationSprite.play("PistolInvulnerable");
			break;
		case SHOTGUN:
			playerAnimationSprite.play("ShotgunInvulnerable");
			break;
		case ROCKET_LAUNCHER:
			playerAnimationSprite.play("RocketLauncherInvulnerable");
			break;
		case MACHINE_GUN:
			playerAnimationSprite.play("AssaultRifleInvulnerable");
			break;
		case SNIPER_RIFLE:
			playerAnimationSprite.play("SniperRifleInvulnerable");
			break;
		}
	}

	public void playWalk() {

		// System.out.println("WALKING");

		switch (equippedWeapon) {

		case SWORD:
			playerAnimationSprite.play("Walk");
			break;
		case PISTOL:
			playerAnimationSprite.play("PistolWalk");
			break;
		case SHOTGUN:
			playerAnimationSprite.play("ShotgunWalk");
			break;
		case ROCKET_LAUNCHER:
			playerAnimationSprite.play("RocketLauncherWalk");
			break;
		case MACHINE_GUN:
			playerAnimationSprite.play("MachineGunWalk");
			break;
		case SNIPER_RIFLE:
			playerAnimationSprite.play("SniperRifleWalk");
			break;
		}
	}

	public void playStand() {
		// System.out.println("STANDING");

		switch (equippedWeapon) {
		case SWORD:
			playerAnimationSprite.play("Stand");
			break;
		case PISTOL:
			playerAnimationSprite.play("PistolStand");
			break;
		case SHOTGUN:
			playerAnimationSprite.play("ShotgunStand");
			break;
		case ROCKET_LAUNCHER:
			playerAnimationSprite.play("RocketLauncherStand");
			break;
		case MACHINE_GUN:
			playerAnimationSprite.play("MachineGunStand");
			break;
		case SNIPER_RIFLE:
			playerAnimationSprite.play("SniperRifleStand");
			break;
		}
	}

	public void playAttack() {
		switch (equippedWeapon) {
		case SWORD:
			playerAnimationSprite.play("Attack");
			break;
		case PISTOL:
			playerAnimationSprite.play("PistolAttack");
			break;
		case SHOTGUN:
			playerAnimationSprite.play("ShotgunAttack");
			break;
		case ROCKET_LAUNCHER:
			playerAnimationSprite.play("RocketLauncherAttack");
			break;
		case MACHINE_GUN:
			playerAnimationSprite.play("MachineGunAttack");
			break;
		case SNIPER_RIFLE:
			playerAnimationSprite.play("SniperRifleAttack");
			break;
		}
	}

	/**
	 * This method should be called at the end of the update to change the
	 * animation position
	 */
	public void setAnimationPosition() {
		// Changes the facing and stuff of the player
		if (_facing == FlxSprite.RIGHT) {
			playerAnimationSprite.x = x;
		} else if (_facing == FlxSprite.LEFT) {
			playerAnimationSprite.x = x - 32 + 10;
		}

		playerAnimationSprite.y = y - 8;
	}

	/**
	 * This method is called in the main playState. This method should be called
	 * when the player attacks. Calls the attacking method for the weapon the
	 * player is holding and restarts the current timer
	 */
	public void attack() {
		playAttack();
	}

	/**
	 * Called when the player jumps
	 */
	public void jump() {

	}

	public void knockback(float force) {
		if (!(force > 0 && velocity.x > 0 || force < 0 && velocity.x < 0)) {
			knockedBack = true;
			velocity.x += force;
		}
	}

	public boolean isOnLadder() {
		return onLadder;
	}

}

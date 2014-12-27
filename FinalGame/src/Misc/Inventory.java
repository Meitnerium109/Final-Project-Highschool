package Misc;

import org.flixel.FlxGroup;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

import PlayerPackage.Player;
import Weapons.MachineGun;
import Weapons.Pistol;
import Weapons.RocketLauncher;
import Weapons.Shotgun;
import Weapons.SniperRifle;
import Weapons.Sword;
import Weapons.Weapon;

/**
 * Holds the weapons and stuff for the player
 * 
 * @author Andrew Lau
 * 
 */
public class Inventory extends FlxGroup {

	int numSlots = 6;
	public static Weapon weaponArray[];
	public static FlxSprite spriteArray[];
	public static VariableText variableTextArray[];

	public int currentSlot = 0;

	public static int x;
	public static int y;

	public FlxSprite redBox;

	public Inventory(int x, int y) {
		this.x = x;
		this.y = y;

		weaponArray = new Weapon[numSlots];
		weaponArray[0] = new Sword();
		weaponArray[1] = new Pistol();
		weaponArray[2] = new Shotgun();
		weaponArray[3] = new MachineGun();
		weaponArray[4] = new RocketLauncher();
		weaponArray[5] = new SniperRifle();

		spriteArray = new FlxSprite[numSlots];
		variableTextArray = new VariableText[numSlots];

		for (int i = 0; i < numSlots; i++) {
			spriteArray[i] = loadPicture(weaponArray[i]);

			if (numSlots >= 1) {
				VariableText vText = new VariableText(0, 0, "", 999, false,
						false);
				variableTextArray[i] = vText;
			}
		}

		/**
		 * Moves the sprites into the correct locations
		 */
		for (int i = 0; i < numSlots; i++) {
			spriteArray[i].x = x + i * 35;
			spriteArray[i].y = y;

			if (i >= 1) {
				variableTextArray[i].x = x + i * 35;
				variableTextArray[i].y = y + 32 + 5;

				add(variableTextArray[i]);
			}

			add(spriteArray[i]);

		}

		redBox = new FlxSprite(x, y, "redBox.png");
		add(redBox);

		// pistolAmmo = new VariableText()

		setAll("scrollFactor", new FlxPoint(0, 0));
	}

	/**
	 * Gets the weapon in that slot
	 * 
	 * @param numSlot
	 * @return
	 */
	public Weapon getWeapon(int numSlot) {
		return weaponArray[numSlot];
	}

	/**
	 * Called when the intention is to change weapons
	 * 
	 * @param numSlot
	 * @return
	 */
	public Weapon changeWeapon(int numSlot, Player player) {
		if (weaponArray[numSlot] != null) {
			currentSlot = numSlot;
			Player.canAttack = true;
			player.setWeapon(weaponArray[numSlot]);
			weaponArray[numSlot].resetWeapon();

			redBox.x = x + numSlot * 35;

			return weaponArray[numSlot];
		} else {
			return null;
		}

	}

	/**
	 * Returns an flxsprite with the picture of the weapon
	 * 
	 * @param weapon
	 * @return
	 */
	public FlxSprite loadPicture(Weapon weapon) {

		if (weapon instanceof Sword) {
			FlxSprite sprite = new FlxSprite(0, 0, "SwordSprite.png");
			return sprite;
		} else if (weapon instanceof Pistol) {
			FlxSprite sprite = new FlxSprite(0, 0, "PistolSprite.png");
			return sprite;
		} else if (weapon instanceof Shotgun) {
			FlxSprite sprite = new FlxSprite(0, 0, "ShotgunSprite.png");
			return sprite;
		} else if (weapon instanceof MachineGun) {
			FlxSprite sprite = new FlxSprite(0, 0, "AssaultRifleIcon.png");
			return sprite;
		} else if (weapon instanceof SniperRifle) {
			FlxSprite sprite = new FlxSprite(0, 0, "SniperRifleIcon.png");
			return sprite;
		} else if (weapon instanceof RocketLauncher) {
			FlxSprite sprite = new FlxSprite(0, 0, "RocketLauncherIcon.png");
			return sprite;
		}

		return new FlxSprite(1, 1); // default for now
	}

	/**
	 * Returns the weapon that was previously in the slot and replaces with the
	 * input weapon
	 * 
	 * @param slot
	 * @param weapon
	 * @return
	 */
	public Weapon replaceWeapon(int slot, Weapon weapon) {

		Weapon previousWeapon = weaponArray[slot];
		weaponArray[slot] = weapon;

		FlxSprite sprite = loadPicture(weapon);
		sprite.x = x + slot * 32;
		sprite.y = y;

		spriteArray[slot].kill();
		spriteArray[slot] = sprite;

		add(sprite);

		setAll("scrollFactor", new FlxPoint(0, 0));

		return previousWeapon;

	}

	/**
	 * Drops a weapon when the player presses C
	 * 
	 * @return
	 */
	public Weapon dropWeapon(int slot) {

		Weapon previousWeapon = weaponArray[slot];
		weaponArray[slot] = null;

		return previousWeapon;
	}

}

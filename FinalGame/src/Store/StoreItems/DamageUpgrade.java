package Store.StoreItems;

import PlayerPackage.Player;
import Store.StoreItem;

public class DamageUpgrade extends StoreItem {

	public int weapon;

	/**
	 * Amount of damage upgraded
	 */
	public int amount;

	public DamageUpgrade(int x, int y, int price, int weapon) {
		super(x, y, price);

		this.weapon = weapon;
		
		switch (weapon) {
		case Player.PISTOL:
			amount = 3;
			description = "+ " + amount + " Pistol Damage";
			loadGraphic("StoreItems/PistolUpgrade.png");
			break;
		case Player.SHOTGUN:
			amount = 3;
			description = "+ " + amount + " Shotgun Damage Per Pellet";
			loadGraphic("StoreItems/ShotgunUpgrade.png");
			break;
		case Player.SWORD:
			amount = 20;
			description = "+ " + amount + " Sword Damage";
			loadGraphic("StoreItems/SwordUpgrade.png");
			break;
		case Player.MACHINE_GUN:
			amount = 2;
			description = "+ " + amount + " Machine Gun Damage";
			loadGraphic("StoreItems/AssaultRifleUpgrade.png");
			break;
		case Player.ROCKET_LAUNCHER:
			amount = 15;
			description = "+ " + amount + " Rocket Launcher Damage";
			loadGraphic("StoreItems/RocketLauncherUpgrade.png");
			break;
		case Player.SNIPER_RIFLE:
			amount = 20;
			description = "+ " + amount + " Sniper Rifle Damage";
			loadGraphic("StoreItems/SniperRifleUpgrade.png");
			break;
		}

		if (price != 0) {
			this.price = price;
		} else {
			price = 0;
		}
	}
}

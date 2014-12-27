package Store.StoreItems;

import org.flixel.FlxText;

import Store.Store;
import Store.StoreItem;

public class AmmoItem extends StoreItem {

	public int ammoType;
	public int amount;

	public AmmoItem(int x, int y, int price, int ammoType) {
		super(x, y, price);

		this.ammoType = ammoType;

		switch (ammoType) {
		case Store.PISTOL_AMMO:
			loadGraphic("PistolAmmo.png");
			amount = 75;
			description = "+ " + this.amount + " Pistol Ammo";
			break;
		case Store.ASSAULT_RIFLE_AMMO:
			loadGraphic("AssaultRifleAmmo.png");
			amount = 125;
			description = "+ " + this.amount + " Machine Gun Ammo";
			break;
		case Store.SHOTGUN_AMMO:
			loadGraphic("ShotgunAmmo.png");
			amount = 20;
			description = "+ " + this.amount + " Shotgun Ammo";
			break;
		case Store.ROCKET_LAUNCHER_AMMO:
			loadGraphic("RocketLauncherAmmo.png");
			amount = 10;
			description = "+ " + this.amount + " Rocket Launcher Ammo";
			break;
		case Store.SNIPER_RIFLE_AMMO:
			loadGraphic("SniperAmmo.png");
			amount = 25;
			description = "+ " + this.amount + " Sniper Rifle Ammo";
			break;
		}

		if (price != 0) {
			this.price = price;
			// TODO remove test

		} else {
			price = 0;
		}

	}

	public AmmoItem(int x, int y) {
		super(x, y);
	}

	public FlxText createPrice(int x, int y, int fontSize) {
		FlxText text = new FlxText(x, y - 15, 32, "$" + price);
		text.setAlignment("center");
		text.setFormat(null, fontSize);
		thingText = text;

		return text;
	}
}

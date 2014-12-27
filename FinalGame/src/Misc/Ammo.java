package Misc;

import org.flixel.FlxSprite;

import Entities.Entity;
import PlayerPackage.Player;

public class Ammo extends Entity {

	public int type = 0;
	public int amount;

	public String description;

	public Ammo(int x, int y, int type, int amount) {

		super(x, y);

		this.type = type;

		if (amount == 0) {

			switch (type) {
			
			case Player.PISTOL:
				loadGraphic("PistolAmmo.png");
				this.amount = 75;
				description = "+ " + this.amount + " Pistol Ammo";
				break;
			case Player.SHOTGUN:
				loadGraphic("ShotgunAmmo.png");
				this.amount = 20;
				description = "+ " +  this.amount + " Shotgun Ammo";
				break;
			case Player.MACHINE_GUN:
				loadGraphic("AssaultRifleAmmo.png");
				this.amount = 125;
				description = "+ " +  this.amount + " Machine Gun Ammo";
				break;
			case Player.ROCKET_LAUNCHER:
				loadGraphic("RocketLauncherAmmo.png");
				this.amount = 10;
				description = "+ " +  this.amount + " Rocket Launcher Ammo";
				break;
			case Player.SNIPER_RIFLE:
				loadGraphic("SniperAmmo.png");
				this.amount = 25;
				description = "+ " +  this.amount + " Sniper Rifle Ammo";
				break;
			}
		} else {
			this.amount = amount;
		}

		acceleration.y = 980;

	}
}

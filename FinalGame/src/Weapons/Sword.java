package Weapons;

import org.flixel.FlxSprite;

import Misc.Timer;
import PlayerPackage.Player;

public class Sword extends Weapon {

	public Player player;

	public Sword() {
		super();

		cooldownTime = (int) (1 * Timer.FRAMES_TO_SECONDS);
		cooldownTimer.setTotalFrames(cooldownTime);
		this.player = player;

		animationFrames = 20;
		
		lowDamage = 50;
		highDamage = 80;
		
		loadGraphic("SwordSprite.png");
	}

}

package Weapons;

import Misc.Timer;

public class Pistol extends Weapon {

	public Pistol() {
		super();

		lowDamage = 7;
		highDamage = 15;
		
		animationFrames = 10;
		
		canAttack = true;
		
		cooldownTime = (int)(0.3 * Timer.FRAMES_TO_SECONDS);
		cooldownTimer.setTotalFrames(cooldownTime);
		
		spread = 50;
		
		recoil = 200;
		
		loadGraphic("PistolSprite.png");
		
	}
	
}

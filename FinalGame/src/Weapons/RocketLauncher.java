package Weapons;

import Misc.Timer;

public class RocketLauncher extends Weapon {

	public RocketLauncher() {
		super();

		lowDamage = 40;
		highDamage = 60;

		animationFrames = 10;

		canAttack = true;
		cooldownTime = (int) (2 * Timer.FRAMES_TO_SECONDS);
		cooldownTimer.setTotalFrames(cooldownTime);

		spread = 0;

		recoil = 2500;
		
		loadGraphic("RocketLauncherIcon.png");

	}

}

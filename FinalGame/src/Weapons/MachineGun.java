package Weapons;

import Misc.Timer;

public class MachineGun extends Weapon {

	public MachineGun() {
		super();

		lowDamage = 5;
		highDamage = 11;

		animationFrames = 1;
		canAttack = true;

		cooldownTime = 2;
		cooldownTimer.setTotalFrames(cooldownTime);

		spread = 70;

		recoil = 200;

		loadGraphic("AssaultRifleIcon.png");

	}
}

package Misc;

import PlayerPackage.Player;

public class MagnetizedCoin extends Coin {

	public boolean moveThroughWalls;
	public int coinSpeed = 1;

	public int magnetCounter = 0;
	public int magnetTimer = 5 * Timer.FRAMES_TO_SECONDS + RandomNumber.generateRandomNumber(0, 1 * Timer.FRAMES_TO_SECONDS);

	public MagnetizedCoin(int x, int y, int value) {
		super(x, y, value);

		moveThroughWalls = false;

		acceleration.y = 980;
	}

	public void update() {
		super.update();

		if(!moveThroughWalls){
			if(magnetCounter < magnetTimer){
				magnetCounter++;
			} else {
				moveThroughWalls = true;
			}
		}

		if (moveThroughWalls) {

			if (coinSpeed < 500) {
				coinSpeed += 2;
			}

			if (Player.playerX - x > 0) {
				velocity.x = 50 + Player.playerX - x;
			} else {
				velocity.x = -50 + Player.playerX - x;
			}

			if (Player.playerY - y > 0) {
				velocity.y = 50 + Player.playerY - y;
			} else {
				velocity.y = -50 + Player.playerY - y;
			}

		}
	}

}

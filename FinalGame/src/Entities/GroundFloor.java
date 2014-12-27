package Entities;

public class GroundFloor extends Floor {

	public static final int TOP = 0;
	public static final int MID = 1;
	public static final int BOTTOM = 2;

	public GroundFloor(int x, int y, int placement, int number) {
		super(x, y, 32, 32);

		switch (placement) {
		case TOP:
			if (number == 1) {
				loadGraphic("GroundTop1.png");
			} else if (number == 2) {
				loadGraphic("GroundTop2.png");
			}
			break;
		case MID:
			if (number == 1) {
				loadGraphic("GroundMid1.png");
			} else if (number == 2) {
				loadGraphic("GroundMid2.png");
			}
			break;
		case BOTTOM:
			if (number == 1) {
				loadGraphic("GroundBottom1.png");
			} else if (number == 2) {
				loadGraphic("GroundBottom2.png");
			}
			break;

		}
	}
}

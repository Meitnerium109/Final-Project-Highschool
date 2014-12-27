package Entities;

/**
 * Most of the wall, ground, whatever will be composed of the floor
 * 
 * @author Andrew Lau
 * 
 */
public class Floor extends SolidEntity {

	public Floor(int x, int y, int width, int height) {
		super(x, y);

		this.width = width;
		this.height = height;

		//setColor(0xFF000000);
		//makeGraphic(width, height, 0xFF000000);

		immovable = true;
	}

}

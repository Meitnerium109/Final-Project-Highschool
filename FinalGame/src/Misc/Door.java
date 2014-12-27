package Misc;

import org.flixel.FlxSprite;

import Entities.Entity;

import com.FinalGame.flixelgame.PlayState;

public class Door extends Entity {

	public FlxSprite backDoor;

	public Door(int x, int y) {
		super(x, y);

		width = 20;
		height = 40;

		loadGraphic("Door.png");

		backDoor = new FlxSprite(x - 20, y - 63, "ShopBuilding.png");
		
		immovable = true;
		backDoor.visible = false;

	}

	/**
	 * This method is called when the player presses down, is in contact with
	 * the door, and has the key for the level
	 */
	public void open(PlayState playState) {
		// TODO open the door to go to the shop

		playState.teleportToStore();
		// System.out.println("DOOR OPENED");
	}
	
	public void kill(){
		backDoor.kill();
		super.kill();
	}
}

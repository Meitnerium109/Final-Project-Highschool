package Entities;

import org.flixel.FlxSprite;

//Any object that can exist in the game world... spikes, weapons, anything like that
public class Entity extends FlxSprite{

	/**
	 * For use with tiles. If top is true, then there is space on top of the tile to place something
	 */
	public boolean top = true;
	
	public Entity(int x, int y){
		super(x, y);
	}
}

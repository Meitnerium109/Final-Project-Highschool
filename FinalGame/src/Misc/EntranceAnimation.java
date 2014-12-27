package Misc;

import org.flixel.FlxSprite;

public class EntranceAnimation extends FlxSprite{

	public int lifeCounter = 0;
	public int lifeTime = (int)(0.4 * Timer.FRAMES_TO_SECONDS);
	
	public EntranceAnimation(int x, int y){
		super(x, y);
		
		loadGraphic("EntranceAnimation.png", true, true, 48, 48);
		addAnimation("Enter", new int[]{0, 1, 2}, 10, false);
		play("Enter");
	}
	
	public void update(){
		super.update();
		
		if(lifeCounter < lifeTime){
			lifeCounter ++;
		} else {
			kill();
		}
	}
	
}

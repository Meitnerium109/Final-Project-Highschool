package com.FinalGame.flixelgame;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.event.IFlxButton;

public class MainMenuState extends FlxState {

	FlxButton startButton;
	FlxButton highScoreButton;

	public void create() {
		FlxG.setBgColor(0xFF000000);

		startButton = new FlxButton(100, 100, "New Game", new IFlxButton() {
			
			@Override
			public void callback() {
				FlxG.switchState(new PlayState());
			}
		});
		add(startButton);
		
		highScoreButton = new FlxButton(300, 100, "Highscores",
				new IFlxButton() {

					@Override
					public void callback() {
						FlxG.switchState(new HighScoreState());
					}

				});
		add(highScoreButton);
		
	}

	public void update() {
		super.update();
	}

}

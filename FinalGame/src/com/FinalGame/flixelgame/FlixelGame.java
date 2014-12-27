package com.FinalGame.flixelgame;

import org.flixel.FlxGame;

public class FlixelGame extends FlxGame {
	public FlixelGame() {
		super(800, 600, PlayState.class, 1, 60, 60, true);
	}
}

package com.FinalGame.flixelgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "Awesome Game";
		cfg.width = 800;
		cfg.height = 600;
		cfg.fullscreen = true;

		new FlxDesktopApplication(new FlixelGame(), cfg);
	}
}

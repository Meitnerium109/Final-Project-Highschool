package com.FinalGame.flixelgame;

import org.flixel.FlxG;
import org.flixel.FlxGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class FlxDesktopApplication extends LwjglApplication
{
   	public FlxDesktopApplication(FlxGame Game, int Width, int Height, boolean fullscreen)
	{
        super((ApplicationListener)Game.stage, FlxG.getLibraryName(), Width, Height);
	}

   	public FlxDesktopApplication(FlxGame Game, LwjglApplicationConfiguration cfg){
   	 super((ApplicationListener)Game.stage, cfg);
   	}
   	
}

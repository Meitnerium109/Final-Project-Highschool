package Misc;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

public class Background extends FlxGroup {

	public Background() {

		for (int i = 0; i < 3; i++) {
			FlxSprite background = new FlxSprite(-400 + i * 1700, 0, "sky.png");
			add(background);
			background.scrollFactor = new FlxPoint(0.35f, 0);
		}

		FlxSprite sun = new FlxSprite(FlxG.width - 30, 40, "SunSprite.png");
		sun.scrollFactor = new FlxPoint(0.05f, 0f);
		add(sun);

		// Biggest Cloud
		for (int i = 0; i < 10; i++) {
			FlxSprite cloud1 = new FlxSprite(
					(int) (FlxG.width / 2.0 + i * 2500), 200, "Cloud1.png");
			cloud1.scrollFactor = new FlxPoint(0.35f, 0);
			cloud1.setAlpha(0.5f);
			add(cloud1);
		}

		for (int i = 0; i < 10; i++) {
			FlxSprite cloud2 = new FlxSprite((int) (100 + i * 1000), 50,
					"Cloud2.png");
			cloud2.scrollFactor = new FlxPoint(0.36f, 0);
			cloud2.setAlpha(0.5f);
			add(cloud2);
		}

		for (int i = 0; i < 10; i++) {
			FlxSprite cloud3 = new FlxSprite((int) (500 + i * 1000), 100,
					"Cloud3.png");
			cloud3.scrollFactor = new FlxPoint(0.27f, 0);
			cloud3.setAlpha(0.5f);
			add(cloud3);
		}

		for (int i = 0; i < 10; i++) {
			FlxSprite cloud4 = new FlxSprite((int) (100 + i * 800), 200,
					"Cloud4.png");
			cloud4.scrollFactor = new FlxPoint(0.4f, 0);
			cloud4.setAlpha(0.5f);
			add(cloud4);
		}

		for (int i = 0; i < 10; i++) {
			FlxSprite cloud5 = new FlxSprite((int) (450 + i * 900), 300,
					"Cloud5.png");
			cloud5.scrollFactor = new FlxPoint(0.26f, 0);
			cloud5.setAlpha(0.5f);
			add(cloud5);
		}

		for (int i = 0; i < 15; i++) {
			FlxSprite foreground = new FlxSprite(-400 + i * 360, 100,
					"cn background.png");
			add(foreground);
			foreground.scrollFactor = new FlxPoint(0.5f, 0);
		}

	}
}

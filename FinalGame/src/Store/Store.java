package Store;

import java.io.IOException;
import java.util.ArrayList;

import org.flixel.FlxG;
import org.flixel.FlxGroup;

import Entities.GroundFloor;
import PlayerPackage.Player;
import Store.StoreItems.AmmoItem;
import Store.StoreItems.DamageUpgrade;
import Store.StoreItems.HeartUp;
import Store.StoreItems.JumpingShoes;
import Store.StoreItems.Magnet;
import Store.StoreItems.MetalDetector;
import Store.StoreItems.RunningShoes;

import com.FinalGame.flixelgame.PlayState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Store extends FlxGroup {

	public static int x;
	public static int y;
	public static int width;
	public static int height;
	public static int randomPrice = 100;

	PlayState playState;

	public static ArrayList<StoreItem> storeItems = new ArrayList<StoreItem>();

	public Store(PlayState playState) {
		this.playState = playState;

		x = -1000;
		y = -1000;

		width = FlxG.width;
		height = FlxG.height;

		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 20; j++) {
				playState.createShopTile(x + i * 32, y + j * 32);
			}
		}

		generateEnvironment();
		generateStore();

	}

	/**
	 * Makes all the solid blocks and stuff
	 */
	public void generateEnvironment() {

		XmlReader xml = new XmlReader();
		try {
			Element root = xml.parse(Gdx.files.internal("Store.oel"));

			Element entities = root.getChildByName("Entities");
			for (Element ground : entities.getChildrenByName("Ground")) {
				playState.createGroundFloor(ground.getInt("x") + x,
						ground.getInt("y") + y, GroundFloor.TOP, 1);
			}

			for (Element playerElement : entities.getChildrenByName("Player")) {
				playState.player.x = playerElement.getInt("x") + x;
				playState.player.y = playerElement.getInt("y") + y;
			}

			/**
			 * Chests should be randomly generated
			 */
			for (Element chest : entities.getChildrenByName("Chest")) {

			}

			for (Element spike : entities.getChildrenByName("Spike")) {
				playState.createSpike(spike.getInt("x") + x, spike.getInt("y")
						+ y);
			}

			for (Element brick : entities.getChildrenByName("Brick")) {
				playState.createBrick(brick.getInt("x") + x, brick.getInt("y")
						+ y);
			}

			for (Element stone : entities.getChildrenByName("Stone")) {
				playState.createShopFloor(stone.getInt("x") + x,
						stone.getInt("y") + y);
			}

			// Create ladders later because they go over other tiles
			for (Element ladder : entities.getChildrenByName("Ladder")) {
				playState.createLadder(ladder.getInt("x") + x,
						ladder.getInt("y") + y);
			}

			for (Element ladderBottom : entities
					.getChildrenByName("LadderBottom")) {
				playState.createLadder(ladderBottom.getInt("x") + x,
						ladderBottom.getInt("y") + y, true);
			}

			for (Element spring : entities.getChildrenByName("Spring")) {
				playState.createSpring(spring.getInt("x") + x,
						spring.getInt("y") + y);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static final int RUNNING_SHOES = 0;
	public static final int MAGNET = 1;
	public static final int JUMPING_SHOES = 2;
	public static final int HEART = 3;
	public static final int METAL_DETECTOR = 4;

	public static final int PISTOL_AMMO = 100;
	public static final int SHOTGUN_AMMO = 101;
	public static final int ASSAULT_RIFLE_AMMO = 102;
	public static final int ROCKET_LAUNCHER_AMMO = 103;
	public static final int SNIPER_RIFLE_AMMO = 104;

	public static final int SWORD_UPGRADE = 199;
	public static final int PISTOL_UPGRADE = 200;
	public static final int SHOTGUN_UPGRADE = 201;
	public static final int ASSAULT_RIFLE_UPGRADE = 202;
	public static final int ROCKET_LAUNCHER_UPGRADE = 203;
	public static final int SNIPER_RIFLE_UPGRADE = 204;

	public void generateStore() {
		// Generates the background of the store

		playState.createCenteredText(x, y + 10, FlxG.width,
				"WELCOME TO THIS AWESOME STORE", 20);
		playState.createCenteredText(x, y + +60, FlxG.width,
				"Press down to buy an item", 12);

		createItem(x + 250, y + FlxG.height - 32 - 32, MAGNET);

		createItem(x + 250 + 70, y + FlxG.height - 32 - 32, RUNNING_SHOES);
		createItem(x + 250 + 70 * 2, y + FlxG.height - 32 - 32, JUMPING_SHOES);
		createItem(x + 250 + 70 * 3, y + FlxG.height - 32 - 32, HEART);
		createItem(x + 250 + 70 * 4, y + FlxG.height - 32 - 32, METAL_DETECTOR);

		for (int i = 0; i < 5; i++) {
			createItem(x + 65 + 30 * 6 + i * 70, y + FlxG.height - 403,
					PISTOL_AMMO + i);
		}

		for (int i = 0; i < 6; i++) {
			createItem(x + 65 + 30 * 6 + i * 70, y + FlxG.height - 190,
					SWORD_UPGRADE + i);
		}
	}

	/**
	 * Creates a specific item
	 * 
	 * @param string
	 */
	public StoreItem createItem(int x, int y, int weaponType) {

		StoreItem storeItem;

		switch (weaponType) {
		case MAGNET:
			storeItem = new Magnet(x, y, 100);
			break;
		case RUNNING_SHOES:
			storeItem = new RunningShoes(x, y, 100);
			break;
		case JUMPING_SHOES:
			storeItem = new JumpingShoes(x, y, 100);
			break;
		case HEART:
			storeItem = new HeartUp(x, y, 100);
			break;
		case METAL_DETECTOR:
			storeItem = new MetalDetector(x, y, 100);
			break;
		case PISTOL_AMMO:
			storeItem = new AmmoItem(x, y, 100, PISTOL_AMMO);
			break;
		case SHOTGUN_AMMO:
			storeItem = new AmmoItem(x, y, 100, SHOTGUN_AMMO);
			break;
		case ASSAULT_RIFLE_AMMO:
			storeItem = new AmmoItem(x, y, 100, ASSAULT_RIFLE_AMMO);
			break;
		case ROCKET_LAUNCHER_AMMO:
			storeItem = new AmmoItem(x, y, 100, ROCKET_LAUNCHER_AMMO);
			break;
		case SNIPER_RIFLE_AMMO:
			storeItem = new AmmoItem(x, y, 100, SNIPER_RIFLE_AMMO);
			break;
		case PISTOL_UPGRADE:
			storeItem = new DamageUpgrade(x, y, 100, Player.PISTOL);
			break;
		case SWORD_UPGRADE:
			storeItem = new DamageUpgrade(x, y, 100, Player.SWORD);
			break;
		case SHOTGUN_UPGRADE:
			storeItem = new DamageUpgrade(x, y, 100, Player.SHOTGUN);
			break;
		case ROCKET_LAUNCHER_UPGRADE:
			storeItem = new DamageUpgrade(x, y, 100, Player.ROCKET_LAUNCHER);
			break;
		case SNIPER_RIFLE_UPGRADE:
			storeItem = new DamageUpgrade(x, y, 100, Player.SNIPER_RIFLE);
			break;
		case ASSAULT_RIFLE_UPGRADE:
			storeItem = new DamageUpgrade(x, y, 100, Player.MACHINE_GUN);
			break;
		default:
			storeItem = new MetalDetector(x, y, 100);
			break;

		}

		add(storeItem.createPrice((int) storeItem.x - 4,
				(int) storeItem.y + 32 + 5, 9));
		add(storeItem);
		storeItems.add(storeItem);

		return storeItem;

	}

	public StoreItem createAmmo(int x, int y, int ammoType) {
		StoreItem storeItem = null;

		switch (ammoType) {
		case PISTOL_AMMO:
			storeItem = new AmmoItem(x, y, 0, PISTOL_AMMO);
			break;
		case SHOTGUN_AMMO:
			storeItem = new AmmoItem(x, y, 0, SHOTGUN_AMMO);
			break;
		case ASSAULT_RIFLE_AMMO:
			storeItem = new AmmoItem(x, y, 0, ASSAULT_RIFLE_AMMO);
			break;
		case ROCKET_LAUNCHER_AMMO:
			storeItem = new AmmoItem(x, y, 0, ROCKET_LAUNCHER_AMMO);
			break;
		case SNIPER_RIFLE_AMMO:
			storeItem = new AmmoItem(x, y, 0, SNIPER_RIFLE_AMMO);
			break;
		}

		return storeItem;
	}
}

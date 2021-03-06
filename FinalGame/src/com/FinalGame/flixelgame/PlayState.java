package com.FinalGame.flixelgame;

import java.io.IOException;

import org.flixel.FlxBasic;
import org.flixel.FlxCamera;
import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxObject;
import org.flixel.FlxRect;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxCollision;

import Enemies.BurrowingEnemy;
import Enemies.Dog;
import Enemies.Enemy;
import Enemies.ExplodingSkeleton;
import Enemies.RangedAlien;
import Enemies.RangedEnemy;
import Enemies.ShieldingEnemy;
import Enemies.Skeleton;
import Entities.Brick;
import Entities.Floor;
import Entities.GroundFloor;
import Entities.Ladder;
import Entities.LadderBlock;
import Entities.ShopFloor;
import Entities.ShopTile;
import Entities.Spike;
import Misc.Ammo;
import Misc.Background;
import Misc.Chest;
import Misc.Coin;
import Misc.DamageNumber;
import Misc.Door;
import Misc.EntranceAnimation;
import Misc.FloatyWord;
import Misc.Grass;
import Misc.HeartsCounter;
import Misc.Inventory;
import Misc.Key;
import Misc.MagnetizedCoin;
import Misc.RandomNumber;
import Misc.Smoke;
import Misc.Spring;
import Misc.Timer;
import Misc.VariableText;
import PlayerPackage.Player;
import Projectiles.Bullet;
import Projectiles.Explosion;
import Projectiles.PistolBullet;
import Projectiles.RangedAlienBullet;
import Projectiles.Rocket;
import Projectiles.ShotgunBullet;
import Projectiles.SwordBullet;
import Store.Store;
import Store.StoreItem;
import Store.StoreItems.AmmoItem;
import Store.StoreItems.DamageUpgrade;
import Store.StoreItems.HeartUp;
import Store.StoreItems.JumpingShoes;
import Store.StoreItems.Magnet;
import Store.StoreItems.MetalDetector;
import Store.StoreItems.RunningShoes;
import Weapons.MachineGun;
import Weapons.Pistol;
import Weapons.RocketLauncher;
import Weapons.Shotgun;
import Weapons.SniperRifle;
import Weapons.Sword;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class PlayState extends FlxState {

	/**
	 * The current level of the stage
	 */
	public static int level = 1;

	PlayState playState = this;

	/**
	 * GROUPS
	 */
	public FlxGroup movingEntityGroup;
	public FlxGroup solidEntityGroup;
	public FlxGroup miscEntityGroup; // Collides with the floor but not with
										// anything else
	public FlxGroup playerBulletGroup; // Anything that can damage any other
										// enemy
	public FlxGroup enemyGroup; // Enemy bullets are included in the enemyGroup
	public FlxGroup rangedEnemyGroup;
	public FlxGroup projectileGroup; // dies when it hits the floor
	public FlxGroup miscGroup; // Do miscellanous things in this group when the
	public FlxGroup shopTilesGroup;

	// The top tiles of the map. Anything that you can put something on
	public FlxGroup topTiles;

	public FlxGroup aliveEnemies;

	/**
	 * MISC ENTITIES
	 */
	public Player player;
	public HeartsCounter heartsCounter;
	public Inventory inventory;
	public Background background;
	public Store store;

	/**
	 * MISC VARIABLES
	 */
	public int playerMoveDirection = 0;
	public static final int MOVING_RIGHT = 1;
	public static final int MOVING_LEFT = 2;
	public static final int NOT_MOVING = 0;

	public boolean inStore = false;

	public VariableText coinsText;

	@Override
	public void create() {

		FlxG.playMusic("SFX/Launching and Landing loop.mp3");
		FlxG.setBgColor(0xFF927D99);

		background = new Background();
		add(background);

		projectileGroup = new FlxGroup(9999);
		solidEntityGroup = new FlxGroup();
		miscEntityGroup = new FlxGroup();
		playerBulletGroup = new FlxGroup(9999);
		enemyGroup = new FlxGroup();
		movingEntityGroup = new FlxGroup();
		rangedEnemyGroup = new FlxGroup();
		miscGroup = new FlxGroup();
		aliveEnemies = new FlxGroup();
		shopTilesGroup = new FlxGroup();
		topTiles = new FlxGroup();

		createStore();

		createChest(1000, 100, 20, 2);
		createLevelFromFile();

		// HeartsCounter should be made after the player
		heartsCounter = new HeartsCounter(10, 30, 10, player);
		add(heartsCounter.heartGroup);

		// Need to add heartsGroup every time a heart is added

		// INVENTORY
		inventory = new Inventory(FlxG.width - 32 * 8, 30);
		add(inventory);

		// createShieldingEnemy(1000, 100);

		/**
		 * for (int i = 1; i < 6; i++) { createAmmo(100 + i * 100, 100, i, 100);
		 * }
		 */

		coinsText = createVariableText(10, 70, "Coins", 0, false);

		createAmmo(500, 100, Player.PISTOL, 0);

		/*
		 * Door door = new Door((int)player.x + 100, (int)player.y - 10);
		 * add(door.backDoor); add(door); miscGroup.add(door);
		 * door.backDoor.visible = true;
		 */

	}

	/**
	 * Give the corresponding coins to the enemy
	 */
	public void giveCoins() {
		for (int i = 0; i < aliveEnemies.length; i++) {
			Enemy enemy = (Enemy) (aliveEnemies.members.get(i));

			if (enemy != null && enemy.dead && !enemy.coinsGiven) {

				// System.out.println("HELLO THERE");

				// Differentiate between different enemy types here later
				createCoinExplode((int) (enemy.x + enemy.width / 2.0),
						(int) (enemy.y + enemy.height / 2.0), enemy.numCoins
								+ Player.extraCoins, enemy.value,
						enemy.explosionForce);
				aliveEnemies.remove(enemy);
			}

		}
	}

	public boolean playerIsOnLadder = false;
	public boolean touchingLadderBottom = false;

	@Override
	public void update() {

		System.out.println("By Andrew Lau");

		super.update();

		/**
		 * COLLISIONS START
		 */

		playerIsOnLadder = false;

		// Player and miscellaneous random stuff
		FlxG.overlap(player, miscGroup, new IFlxCollision() {
			@Override
			public void callback(FlxObject thisPlayer, FlxObject thisMisc) {
				Player player = (Player) (thisPlayer);

				if (thisMisc instanceof Ammo) {
					// TODO assumed that ammo maps directly to a spot in the
					// inventory
					FlxG.play("SFX/ItemPickup.mp3");

					Ammo ammo = (Ammo) (thisMisc);
					VariableText vText = Inventory.variableTextArray[ammo.type];
					vText.setVariable(vText.getVariable() + ammo.amount);

					createFloatyWord((int) ammo.x - 30, (int) ammo.y - 20,
							ammo.description);
					ammo.kill();

				} else if (thisMisc instanceof Ladder) {

					if (thisMisc instanceof LadderBlock) {
						if (player.velocity.y > 0 && !player.onLadder
								&& !FlxG.keys.pressed("DOWN")) {

							player.acceleration.y = 0;
							player.velocity.y = 0;

							FlxObject.separateY(player, thisMisc);

							while (player.y + player.height >= thisMisc.y) {
								player.y--;
							}

							// Check left right collisions before up down
							// collisions
							if (player.isTouching(FlxSprite.RIGHT)) {
								while (player.x >= thisMisc.x) {
									player.x--;
								}
							}

							if (player.isTouching(FlxSprite.LEFT)) {
								while (player.x <= thisMisc.x) {
									player.x++;
								}
							}

							if (player.isTouching(FlxSprite.FLOOR)) {
								player.canJump = true;
							}

						} else {
							if (FlxG.keys.pressed("UP")
									|| FlxG.keys.pressed("DOWN")) {
								player.onLadder = true;
								player.immovable = true;
								player.acceleration.y = 0;
								player.canJump = true;

								if (player.getFacing() == FlxSprite.RIGHT) {
									player.x = thisMisc.x;
								} else if (player.getFacing() == FlxSprite.LEFT) {
									player.x = thisMisc.x;
								}
							}

							playerIsOnLadder = true;
						}
					} else {
						if (FlxG.keys.pressed("UP")
								|| FlxG.keys.pressed("DOWN")) {

							player.onLadder = true;

							Ladder ladder = (Ladder) (thisMisc);

							if (!ladder.bottom && !touchingLadderBottom) {
								player.immovable = true;
							}

							if (ladder.bottom) {
								touchingLadderBottom = true;
							}

							player.acceleration.y = 0;
							player.canJump = true;

							if (player.getFacing() == FlxSprite.RIGHT) {
								player.x = thisMisc.x;
							} else if (player.getFacing() == FlxSprite.LEFT) {
								player.x = thisMisc.x;
							}
						}

						playerIsOnLadder = true;
					}
				} else if (thisMisc instanceof Coin) {
					Coin coin = (Coin) (thisMisc);
					coinsText.setVariable(coinsText.getVariable() + coin.value);
					coin.kill();

					FlxG.play("SFX/Money Pickup.mp3");

					// Adds the coin's value stuffs
				} else if (thisMisc instanceof Key) {
					// TODO Show the key icon in the top right?
					Player.hasKey = true;
					thisMisc.kill();

				} else if (thisMisc instanceof Door) {
					if (FlxG.keys.justPressed("DOWN") && Player.hasKey) {
						Door.class.cast(thisMisc).open(playState);
						System.out.println("HELLO THERE");
					}
				} else if (thisMisc instanceof Chest) {
					if (FlxG.keys.justPressed("DOWN")) {

						FlxG.play("SFX/Chest.mp3");

						Chest chest = (Chest) (thisMisc);

						if (!chest.opened) {
							createCoinExplode(
									(int) (chest.x + chest.width / 2.0),
									(int) chest.y + 30, chest.numCoins,
									chest.value, chest.explosionForce);
							chest.open();
						}
					}
				} else if (thisMisc instanceof Spike
						&& player.velocity.y > 980 / 6) {
					heartsCounter.damage(100);
				} else if (thisMisc instanceof Spring) {
					Spring spring = (Spring) (thisMisc);
					spring.spring();
					FlxG.play("SFX/Spring.mp3");
					player.y--;
					player.velocity.y = -spring.springPower;
				}

			}
		});

		if (!playerIsOnLadder) {
			player.immovable = false;
			player.acceleration.y = Player.gravity;
			player.onLadder = false;
		}

		FlxG.overlap(projectileGroup, solidEntityGroup, new IFlxCollision() {

			@Override
			public void callback(FlxObject thisBullet, FlxObject thisSolidEntity) {

				if (!(thisBullet instanceof Explosion)) {
					if (thisBullet instanceof Rocket) {
						FlxG.camera.shake(0.02f, 0.1f);

						Rocket r = (Rocket) (thisBullet);

						if (thisBullet.velocity.x < 0) {
							createExplosion(
									(int) (thisBullet.x - Rocket.explosionRange),
									(int) (thisBullet.y - Rocket.explosionRange / 2),
									Rocket.explosionRange, r.lowRange,
									r.highRange, FlxSprite.LEFT);

						} else {
							createExplosion(
									(int) (thisBullet.x),
									(int) (thisBullet.y - Rocket.explosionRange / 2),
									Rocket.explosionRange, r.lowRange,
									r.highRange, FlxSprite.RIGHT);

						}

						thisBullet.kill();
					} else {

						thisBullet.kill();
					}

				}

			}

		});

		/**
		 * PLAYER AND ENEMY STUFF COLLIDE
		 */
		FlxG.overlap(player, enemyGroup, new IFlxCollision() {

			@Override
			public void callback(FlxObject thisPlayer, FlxObject thisEnemy) {

				boolean isDead = false;

				if (thisEnemy instanceof Enemy) {
					Enemy enemy = (Enemy) (thisEnemy);
					isDead = enemy.dead;
				}

				if (!isDead) {
					if (thisEnemy instanceof Dog) {
						Dog dog = Dog.class.cast(thisEnemy);
						if (dog.canDamage) {
							dog.canDamage = false;
							dog.onJumpAttack = false;
							heartsCounter.damage(dog.dealDamage());
							dog.stopAction();
							// Pauses the dog in the same place for 1 second
							dog.standStill(1 * Timer.FRAMES_TO_SECONDS);
						}
					} else if (thisEnemy instanceof RangedAlienBullet) {
						RangedAlienBullet rab = (RangedAlienBullet) (thisEnemy);
						heartsCounter.damage(rab.damage);
						rab.kill();
					} else if (thisEnemy instanceof BurrowingEnemy) {
						BurrowingEnemy burrowingEnemy = BurrowingEnemy.class
								.cast(thisEnemy);

						if (burrowingEnemy.burrowed) {
							heartsCounter.damage(burrowingEnemy.damage);
							burrowingEnemy.attack();
							// The burrowing enemy explodes and stuff
						}
					} else if (thisEnemy instanceof ExplodingSkeleton) {
						if (!((ExplodingSkeleton) thisEnemy).dead) {
							heartsCounter
									.damage(((ExplodingSkeleton) thisEnemy).damage);
							((ExplodingSkeleton) thisEnemy).explode();
						}
					} else if (thisEnemy instanceof ShieldingEnemy) {
						if (thisEnemy.velocity.x != 0) {
							ShieldingEnemy se = (ShieldingEnemy) (thisEnemy);
							heartsCounter.damage(se.damage);

							player.knockback(thisEnemy.velocity.x * 10);

							se.standStill((int) (2.5 * Timer.FRAMES_TO_SECONDS));

						}
					} else if (thisEnemy instanceof Skeleton) {
						Skeleton skelly = (Skeleton) (thisEnemy);

						if (skelly.canDamage) {
							heartsCounter.damage(skelly.damage);

							player.knockback(thisEnemy.velocity.x * 10);

						}

					}
				}
			}
		});

		FlxG.overlap(playerBulletGroup, enemyGroup, new IFlxCollision() {

			@Override
			public void callback(FlxObject thisBullet, FlxObject enemyCopy) {

				Bullet bullet = Bullet.class.cast(thisBullet);

				if (enemyCopy instanceof Enemy) {
					Enemy enemy = Enemy.class.cast(enemyCopy);

					if (!enemy.dead) {

						/**
						 * KNOCKBACK
						 */
						if (bullet.knockbackForce != 0) {
							if (bullet.velocity.x <= 0) {
								// WEIRD BUG you have to call it 2 times for it
								// to
								// work O.o
								for (int i = 0; i < 2; i++) {
									enemy.knockback(-bullet.knockbackForce);
								}

							} else {
								for (int i = 0; i < 2; i++) {
									enemy.knockback(bullet.knockbackForce);
								}
							}
						}

						/**
						 * DAMAGE THE ENEMY
						 */

						// When the bullet explodes
						if (bullet instanceof Rocket) {

							FlxG.camera.shake(0.02f, 0.1f);

							Rocket r = (Rocket) (bullet);

							if (bullet.velocity.x < 0) {
								createExplosion(
										(int) (bullet.x - Rocket.explosionRange),
										(int) (bullet.y - Rocket.explosionRange / 2),
										Rocket.explosionRange, r.lowRange,
										r.highRange, FlxSprite.LEFT);
							} else {
								createExplosion(
										(int) (bullet.x),
										(int) (bullet.y - Rocket.explosionRange / 2),
										Rocket.explosionRange, r.lowRange,
										r.highRange, FlxSprite.RIGHT);
							}

							bullet.kill();

						} else {

							/**
							 * SPECIAL CASE FOR SHIELDING ENEMY
							 */
							if (!(enemy instanceof ShieldingEnemy)
									|| (bullet.velocity.x > 0 && enemy
											.getFacing() == FlxSprite.RIGHT)
									|| (bullet.velocity.x < 0 && enemy
											.getFacing() == FlxSprite.LEFT)
									|| (bullet instanceof Explosion)
									|| (bullet instanceof SwordBullet)) {
								// If the enemy is not an instance of shielding
								// enemy or if the bullet is coming from the
								// other side from the enemy

								int damage = 0;

								if (!(bullet instanceof Explosion)) {
									damage = bullet.getDamage();
								} else {
									damage = Explosion.class.cast(bullet)
											.dealDamage();
									// System.out.println(damage);
								}

								boolean criticalHit = false;

								if (player.getCritical()) {
									damage *= 2;
									criticalHit = true;
								}

								enemy.hit(damage);

								if (bullet.velocity.x < 0) {
									if (bullet instanceof ShotgunBullet) {
										createDamageNumber(
												(int) (bullet.x)
														- (int) bullet.width
														* 2
														+ RandomNumber
																.generateRandomNumber(
																		-20, 20),
												(int) (bullet.y - 5)
														- RandomNumber
																.generateRandomNumber(
																		0, 50),
												damage, criticalHit);
									} else {

										createDamageNumber((int) (bullet.x)
												- (int) bullet.width * 2,
												(int) (bullet.y - 5), damage,
												criticalHit);
									}
								} else {

									if (bullet instanceof ShotgunBullet) {
										createDamageNumber(
												(int) (bullet.x)
														- (int) bullet.width
														* 2
														+ RandomNumber
																.generateRandomNumber(
																		-20, 20),
												(int) (bullet.y - 5)
														- RandomNumber
																.generateRandomNumber(
																		0, 50),
												damage, criticalHit);
									} else if (bullet instanceof Explosion) {
										createDamageNumber((int) enemy.x,
												(int) enemy.y, damage,
												criticalHit);
									} else {
										createDamageNumber((int) (bullet.x),
												(int) (bullet.y - 5), damage,
												criticalHit);
									}
								}

								if (!(bullet instanceof Explosion)) {
									bullet.kill();
								}

							} else {
								// if the shielding enemy blocks the bullet
								if (!(bullet instanceof Explosion)) {
									bullet.kill();
								}

								createFloatyWord((int) enemy.x
										+ (int) (enemy.width / 2.0),
										(int) enemy.y - 10, "Blocked");
							}
						}
					}

				}
			}
		});

		FlxG.collide(miscEntityGroup, solidEntityGroup);

		FlxG.collide(movingEntityGroup, solidEntityGroup, new IFlxCollision() {

			@Override
			public void callback(FlxObject playerCopy, FlxObject solidEntity) {

				if (playerCopy instanceof Player) {
					if (playerCopy.isTouching(FlxSprite.DOWN)) {
						player.canJump = true;
						player.onLadder = false;

					}
				}
			}

		});

		if (touchingLadderBottom && player.velocity.y > 0) {
			player.velocity.y = 0;
			player.immovable = false;
		}

		/**
		 * COLLISIONS END
		 */

		/**
		 * Everything else
		 */

		// Keypress needs to be after collision detection
		keyPress();
		checkAttacking();
		checkEnemyAttack();
		giveCoins();

		// Sets the player's animation position
		player.setAnimationPosition();

		touchingLadderBottom = false;

		if (!inStore) {
			// Make sure the player can't go offscreen
			if (player.x + player.width > levelWidth) {
				player.x = levelWidth - player.width;
			}

			if (player.x < 0) {
				player.x = 0;
			}

			if (player.y < 0) {
				player.y = 0;
			}

			if (player.y + player.height > levelHeight) {
				player.y = levelHeight - player.height;
			}
		}

		spawnEnemies();
	}

	/**
	 * CHECK ENEMY ATTACK
	 */
	public void checkEnemyAttack() {

		for (int i = 0; i < rangedEnemyGroup.length; i++) {

			RangedEnemy rangedEnemy = (RangedEnemy) (rangedEnemyGroup.members
					.get(i));

			if (rangedEnemy.attackFlag) {

				if (rangedEnemy instanceof RangedAlien) {

					rangedEnemy.attack();

					if (rangedEnemy.getFacing() == FlxSprite.LEFT) {
						createRangedAlienBullet((int) (rangedEnemy.x - 10),
								(int) (rangedEnemy.y + 15),
								rangedEnemy.dealDamage(),
								rangedEnemy.getFacing());
					} else if (rangedEnemy.getFacing() == FlxSprite.RIGHT) {
						createRangedAlienBullet((int) (rangedEnemy.x + 10),
								(int) (rangedEnemy.y + 15),
								rangedEnemy.dealDamage(),
								rangedEnemy.getFacing());
					}

				}

			}

		}

	}

	public void teleportToStore() {

		// Removes the key from the player
		Player.hasKey = false;

		player.x = Store.x + 300;
		player.y = Store.y - 600;

		FlxG.camera.setBounds(Store.x, Store.y, Store.width, Store.height);
		FlxG.worldBounds = new FlxRect(Store.x - 100, Store.y - 100,
				Store.width + 200, Store.height + 200);

		inStore = true;

	}

	public void teleportToStage() {
		player.x = 0;
		player.y = 0;

		FlxG.camera.setBounds(0, 0, levelWidth, levelHeight);
		FlxG.worldBounds = FlxG.camera.bounds;

		inStore = false;
	}

	/**
	 * KEYPRESSES
	 */

	public void keyPress() {
		/**
		 * MOVEMENT STUFF
		 */
		player.velocity.x = 0;

		// The player is immobile when he is attacking, but can move when he is
		// attacking in the air. Only lets the player
		// move when he is not attacking when on the ground;

		// If both keys are not pressed
		if (!player.onLadder) {

			if (!(FlxG.keys.pressed("A") || FlxG.keys.pressed("LEFT"))
					&& !(FlxG.keys.pressed("D") || FlxG.keys.pressed("RIGHT"))) {
				playerMoveDirection = NOT_MOVING;
			}

			if (FlxG.keys.pressed("D") || FlxG.keys.pressed("RIGHT")) {
				playerMoveDirection = MOVING_RIGHT;
			}

			if (FlxG.keys.pressed("A") || FlxG.keys.pressed("LEFT")) {
				playerMoveDirection = MOVING_LEFT;
			}

			if (FlxG.keys.justPressed("D") || FlxG.keys.justPressed("RIGHT")) {
				playerMoveDirection = MOVING_RIGHT;
			}

			if (FlxG.keys.justPressed("A") || FlxG.keys.justPressed("LEFT")) {
				playerMoveDirection = MOVING_LEFT;
			}

			if (playerMoveDirection == MOVING_LEFT) {
				if (!player.knockedBack) {
					player.velocity.x -= player.speed;
				}
			}

			if (playerMoveDirection == MOVING_RIGHT) {
				if (!player.knockedBack) {
					player.velocity.x += player.speed;
				}
			}
		}

		if ((FlxG.keys.justPressed("Z")) && player.canJump) {
			player.velocity.y = -Player.playerJumpSpeed;
			player.acceleration.y = Player.gravity;
			player.canJump = false;
			player.jump();

			if (player.onLadder) {
				player.onLadder = false;
			}
		}

		/**
		 * ATTACKING STUFF
		 */
		if (FlxG.keys.pressed("X") && !player.onLadder && !player.invulnerable) {

			if (player.holdingWeapon.canAttack
					&& (Inventory.variableTextArray[inventory.currentSlot]
							.getVariable() > 0 || player.holdingWeapon instanceof Sword)) {

				player.holdingWeapon.attack();

				// /CALCULATE KNOCKBKACK
				if (player.getFacing() == FlxSprite.RIGHT) {
					player.knockback(-player.holdingWeapon.recoil);
				} else if (player.getFacing() == FlxSprite.LEFT) {
					player.knockback(player.holdingWeapon.recoil);
				}

				// TAKES AWAY THE AMMO
				VariableText currentVariable = Inventory.variableTextArray[inventory.currentSlot];
				currentVariable.setVariable(currentVariable.getVariable() - 1);

				// THEN DO ALL THE ATTACKS HERE
				if (player.holdingWeapon instanceof Sword) {

					player.attack();

					if (player.getFacing() == FlxSprite.RIGHT) {
						createSwordBullet((int) player.x + 32 - 10,
								(int) player.y,
								player.holdingWeapon.dealDamage()
										+ Player.swordBonus);
					} else if (player.getFacing() == FlxSprite.LEFT) {
						createSwordBullet((int) player.x - 32, (int) player.y,
								player.holdingWeapon.dealDamage()
										+ Player.swordBonus);
					}

					FlxG.play("SFX/Sword.mp3");

				} else if (player.holdingWeapon instanceof MachineGun) {
					player.attack();

					FlxG.camera.shake(0.003f, 0.05f);

					if (player.getFacing() == FlxSprite.RIGHT) {
						createMachineGunBullet((int) player.x + 20,
								(int) player.y + 7,
								player.holdingWeapon.dealDamage()
										+ Player.machineGunBonus,
								player.getFacing(), player.holdingWeapon.spread);
					} else if (player.getFacing() == FlxSprite.LEFT) {
						createMachineGunBullet((int) player.x - 20,
								(int) player.y + 7,
								player.holdingWeapon.dealDamage()
										+ Player.machineGunBonus,
								player.getFacing(), player.holdingWeapon.spread);
					}

					FlxG.play("SFX/Machine Gun.mp3");

				} else

				if (player.holdingWeapon instanceof Pistol) {
					player.attack();

					if (player.getFacing() == FlxSprite.RIGHT) {
						createPistolBullet((int) player.x + 16,
								(int) player.y + 7,
								player.holdingWeapon.dealDamage()
										+ Player.pistolBonus,
								player.getFacing(), player.holdingWeapon.spread);
					} else if (player.getFacing() == FlxSprite.LEFT) {
						createPistolBullet((int) player.x - 16,
								(int) player.y + 7,
								player.holdingWeapon.dealDamage()
										+ Player.pistolBonus,
								player.getFacing(), player.holdingWeapon.spread);
					}

					FlxG.play("SFX/Machine Gun.mp3");
				} else

				if (player.holdingWeapon instanceof Shotgun) {

					// SHAKE THE SCREEN
					FlxG.camera.shake(0.005f, 0.10f);

					player.attack();

					Shotgun shotgun = Shotgun.class.cast(player.holdingWeapon);

					for (int i = 0; i < shotgun.numPellets; i++) {
						if (player.getFacing() == FlxSprite.RIGHT) {
							createShotgunBullet((int) player.x + 20,
									(int) player.y + 7,
									player.holdingWeapon.dealDamage()
											+ Player.shotgunBonus,
									player.getFacing(),
									player.holdingWeapon.spread);
						} else if (player.getFacing() == FlxSprite.LEFT) {
							createShotgunBullet((int) player.x - 20,
									(int) player.y + 7,
									player.holdingWeapon.dealDamage()
											+ Player.shotgunBonus,
									player.getFacing(),
									player.holdingWeapon.spread);
						}
					}

					FlxG.play("SFX/Shotgun.mp3");

				} else

				if (player.holdingWeapon instanceof RocketLauncher) {
					FlxG.camera.shake(0.01f, 0.15f);

					player.attack();

					RocketLauncher rocketLauncher = RocketLauncher.class
							.cast(player.holdingWeapon);

					if (player.getFacing() == FlxSprite.RIGHT) {
						createRocket((int) player.x + 20, (int) player.y,
								player.holdingWeapon.lowDamage
										+ Player.rocketLauncherBonus,
								player.holdingWeapon.highDamage
										+ Player.rocketLauncherBonus,
								player.getFacing(), player.holdingWeapon.spread);
					} else if (player.getFacing() == FlxSprite.LEFT) {
						createRocket((int) player.x - 20, (int) player.y,
								player.holdingWeapon.lowDamage
										+ Player.rocketLauncherBonus,
								player.holdingWeapon.highDamage
										+ Player.rocketLauncherBonus,
								player.getFacing(), player.holdingWeapon.spread);
					}

					FlxG.play("SFX/RocketLauncher.mp3");

				} else if (player.holdingWeapon instanceof SniperRifle) {

					FlxG.camera.shake(0.01f, 0.15f);

					player.attack();

					SniperRifle sniperRifle = (SniperRifle) (player.holdingWeapon);

					ShotgunBullet shotgunBullet;

					if (player.getFacing() == FlxSprite.RIGHT) {

						shotgunBullet = createShotgunBullet(
								(int) player.x + 20, (int) player.y + 2,
								player.holdingWeapon.dealDamage()
										+ Player.sniperRifleBonus,
								player.getFacing(), player.holdingWeapon.spread);

						createSmoke((int) player.x + 10, (int) player.y - 45);

						ShotgunBullet.bulletVelocity = 1500;

					} else if (player.getFacing() == FlxSprite.LEFT) {
						shotgunBullet = createShotgunBullet(
								(int) player.x - 20, (int) player.y + 2,
								player.holdingWeapon.dealDamage()
										+ Player.sniperRifleBonus,
								player.getFacing(), player.holdingWeapon.spread);

						createSmoke((int) player.x - 70, (int) player.y - 45);

						ShotgunBullet.bulletVelocity = 1500;
					}

					FlxG.play("SFX/Sniper Rifle.mp3");

				}

			}

		}

		// LADDER STUFF PLIS WORK
		if (player.onLadder) {
			// Move up and down on the ladder
			player.velocity.y = 0;
			if (FlxG.keys.pressed("UP") || FlxG.keys.pressed("W")) {
				player.velocity.y -= Player.ladderClimbSpeed;
			}

			if (FlxG.keys.pressed("DOWN") || FlxG.keys.pressed("S")) {
				player.velocity.y += Player.ladderClimbSpeed;
			}
		}

		if (FlxG.keys.justPressed("C")) {
			// TODO drop the weapon and pick up any other weapon near the player
			// If there is a weapon in the current inventory slot
			if (Inventory.weaponArray[inventory.currentSlot] != null) {
				// then drop the weapon
			}
		}

		/**
		 * STORE KEYPRESS
		 */

		if (inStore) {

			if (FlxG.keys.justPressed("DOWN")) {
				// Loop through all the items in the store
				for (int i = 0; i < Store.storeItems.size(); i++) {
					StoreItem item = Store.storeItems.get(i);
					// If the player is touching a particular item and the down
					// key is pressed
					if (FlxG.overlap(player, item)) {
						// Then buy the item if the player has enough money
						if (coinsText.getVariable() >= item.price) {
							// Buy the item
							coinsText.setVariable(coinsText.getVariable()
									- item.price);

							// TODO give the player individual item effects
							giveItemEffects(item);
							createFloatyWord((int) item.x - 30,
									(int) item.y - 30, item.description);

							if (!(item instanceof AmmoItem)) {
								item.kill();
							}

						}
					}
				}
			}

		}
		/**
		 * Dev checking stuff
		 */

		if (FlxG.mouse.justPressed()) {

			if (FlxG.keys.pressed("ONE")) {
				createDog((int) FlxG.mouse.x, (int) FlxG.mouse.y);
			} else if (FlxG.keys.pressed("TWO")) {
				createRangedAlien((int) FlxG.mouse.x, (int) FlxG.mouse.y);
			} else if (FlxG.keys.pressed("THREE")) {
				createExplodingSkeleton((int) FlxG.mouse.x, (int) FlxG.mouse.y);
			} else if (FlxG.keys.pressed("FOUR")) {
				createShieldingEnemy((int) FlxG.mouse.x, (int) FlxG.mouse.y);
			} else if (FlxG.keys.pressed("FIVE")) {
				createSkeleton((int) FlxG.mouse.x, (int) FlxG.mouse.y);
			} else if (FlxG.keys.pressed("P")) {
				createCoinExplode((int) FlxG.mouse.x, (int) FlxG.mouse.y, 20,
						1, 150);
			} else {
				player.x = FlxG.mouse.x;
				player.y = FlxG.mouse.y;
			}

		}

		if (FlxG.keys.justPressed("T")) {
			// Teleport to the shop
			teleportToStore();

		}

		if (FlxG.keys.justPressed("R")) {
			teleportToStage();
		}

		/**
		 * SWITCHING WEAPONS STUFF
		 */
		if (FlxG.keys.justPressed("ONE")) {
			player.setWeapon(inventory.changeWeapon(0, player));
			add(player.holdingWeapon);
		} else if (FlxG.keys.justPressed("TWO")) {
			player.setWeapon(inventory.changeWeapon(1, player));
			add(player.holdingWeapon);
		} else if (FlxG.keys.justPressed("THREE")) {
			player.setWeapon(inventory.changeWeapon(2, player));
			add(player.holdingWeapon);
		} else if (FlxG.keys.justPressed("FOUR")) {
			player.setWeapon(inventory.changeWeapon(3, player));
			add(player.holdingWeapon);
		} else if (FlxG.keys.justPressed("FIVE")) {
			player.setWeapon(inventory.changeWeapon(4, player));
			add(player.holdingWeapon);
		} else if (FlxG.keys.justPressed("SIX")) {
			player.setWeapon(inventory.changeWeapon(5, player));
			add(player.holdingWeapon);
		}
	}

	/**
	 * Gives the item's effects to the player
	 * 
	 * @param item
	 */
	public void giveItemEffects(StoreItem item) {

		FlxG.play("SFX/ItemPickup.mp3");

		if (item instanceof Magnet) {
			Player.hasMagnet = true;
		} else if (item instanceof RunningShoes) {
			player.speed += 30;
		} else if (item instanceof JumpingShoes) {
			Player.playerJumpSpeed += 40;
		} else if (item instanceof MetalDetector) {
			Player.extraCoins++;
		} else if (item instanceof HeartUp) {
			heartsCounter.addHeart(2);
		} else if (item instanceof AmmoItem) {
			AmmoItem ammo = (AmmoItem) (item);
			VariableText vText;
			switch (ammo.ammoType) {
			case Store.PISTOL_AMMO:
				vText = Inventory.variableTextArray[1];
				vText.setVariable(vText.getVariable() + ammo.amount);
				break;
			case Store.SHOTGUN_AMMO:
				vText = Inventory.variableTextArray[2];
				vText.setVariable(vText.getVariable() + ammo.amount);
				break;
			case Store.ASSAULT_RIFLE_AMMO:
				vText = Inventory.variableTextArray[3];
				vText.setVariable(vText.getVariable() + ammo.amount);
				break;
			case Store.ROCKET_LAUNCHER_AMMO:
				vText = Inventory.variableTextArray[4];
				vText.setVariable(vText.getVariable() + ammo.amount);
				break;
			case Store.SNIPER_RIFLE_AMMO:
				vText = Inventory.variableTextArray[5];
				vText.setVariable(vText.getVariable() + ammo.amount);
				break;
			}
		} else if (item instanceof DamageUpgrade) {
			DamageUpgrade dmg = (DamageUpgrade) (item);

			switch (dmg.weapon) {
			case Player.SWORD:
				Player.swordBonus += dmg.amount;
				break;
			case Player.PISTOL:
				Player.pistolBonus += dmg.amount;
				break;
			case Player.SHOTGUN:
				Player.shotgunBonus += dmg.amount;
				break;
			case Player.MACHINE_GUN:
				Player.machineGunBonus += dmg.amount;
				break;
			case Player.ROCKET_LAUNCHER:
				Player.rocketLauncherBonus += dmg.amount;
				break;
			case Player.SNIPER_RIFLE:
				Player.sniperRifleBonus += dmg.amount;
				break;
			}

		}

	}

	/**
	 * Checks whether or not the player is attacking 1. Checks if the player's
	 * weapon is attacking 2. Fires the bullet corresponding with that weapon
	 * 
	 * Essentially handles the whole attacking process after is sees that the
	 * holdingWeapon is attacking in that frame
	 * 
	 */
	public void checkAttacking() {
		if (player.holdingWeapon.meleeAttacking) {

			// Checks for each weapon and does something different for each
			if (player.holdingWeapon instanceof Sword) {

				if (player.getFacing() == FlxSprite.RIGHT) {
					createSwordBullet((int) player.x + 32 - 10, (int) player.y,
							player.holdingWeapon.dealDamage());
				} else if (player.getFacing() == FlxSprite.LEFT) {
					createSwordBullet((int) player.x - 32 + 10, (int) player.y,
							player.holdingWeapon.dealDamage());
				}
			}

		}
	}

	/**
	 * ALL CREATE STATEMENTS GO UNDER HERE
	 */
	public void createPlayer(int x, int y) {
		player = new Player(x, y);
		add(player);

		// Camera for the player
		FlxG.camera.follow(player, FlxCamera.STYLE_PLATFORMER);

		// Important, adds the player animation sprite;
		add(Player.playerAnimationSprite);

		// Important, adds the player's first weapon
		add(player.holdingWeapon);
		player.holdingWeapon.equipped = true;

		movingEntityGroup.add(player);

	}

	public Floor createFloor(int x, int y, int width, int height) {
		Floor floor = new Floor(x, y, width, height);
		solidEntityGroup.add(floor);
		add(floor);

		return floor;
	}

	public Dog createDog(int x, int y) {
		Dog dog = new Dog(x, y);
		enemyGroup.add(dog);
		movingEntityGroup.add(dog);
		aliveEnemies.add(dog);
		add(dog);

		return dog;
	}

	public ExplodingSkeleton createExplodingSkeleton(int x, int y) {
		ExplodingSkeleton es = new ExplodingSkeleton(x, y);
		add(es);
		movingEntityGroup.add(es);
		enemyGroup.add(es);
		aliveEnemies.add(es);

		return es;
	}

	public Skeleton createSkeleton(int x, int y) {
		Skeleton skeleton = new Skeleton(x, y);
		add(skeleton);
		movingEntityGroup.add(skeleton);
		enemyGroup.add(skeleton);
		aliveEnemies.add(skeleton);

		return skeleton;

	}

	public ShieldingEnemy createShieldingEnemy(int x, int y) {
		ShieldingEnemy se = new ShieldingEnemy(x, y);
		movingEntityGroup.add(se);
		enemyGroup.add(se);
		aliveEnemies.add(se);
		add(se);

		return se;
	}

	public RangedAlien createRangedAlien(int x, int y) {
		RangedAlien rg = new RangedAlien(x, y);
		movingEntityGroup.add(rg);
		enemyGroup.add(rg);
		rangedEnemyGroup.add(rg);
		aliveEnemies.add(rg);
		add(rg);

		return rg;
	}

	public void createDamageNumber(int x, int y, int number, boolean critical) {
		DamageNumber dn = new DamageNumber(x, y, number, critical);
		add(dn);
	}

	public void createSwordBullet(int x, int y, int damage) {
		SwordBullet swordBullet = new SwordBullet(x, y, damage);
		playerBulletGroup.add(swordBullet);
		projectileGroup.add(swordBullet);
		add(swordBullet);
	}

	public void createPistolBullet(int x, int y, int damage, int direction,
			int spread) {
		PistolBullet pistolBullet = new PistolBullet(x, y, damage, direction,
				spread);
		projectileGroup.add(pistolBullet);
		playerBulletGroup.add(pistolBullet);
		add(pistolBullet);
	}

	public Ladder createLadder(int x, int y) {
		Ladder ladder = new Ladder(x, y);
		add(ladder);
		add(ladder.ladderSprite);
		miscGroup.add(ladder);

		return ladder;
	}

	public Ladder createLadder(int x, int y, boolean bottom) {
		Ladder ladder = new Ladder(x, y, bottom);
		add(ladder);
		add(ladder.ladderSprite);
		miscGroup.add(ladder);

		return ladder;
	}

	public LadderBlock createLadderBlock(int x, int y) {
		LadderBlock ladder = new LadderBlock(x, y);
		add(ladder);
		add(ladder.blockSprite);
		add(ladder.ladderSprite);
		miscGroup.add(ladder);

		return ladder;
	}

	public void createRocket(int x, int y, int lowRange, int highRange,
			int direction, int spread) {
		Rocket r = new Rocket(x, y, lowRange, highRange, direction, spread,
				this);
		projectileGroup.add(r);
		playerBulletGroup.add(r);
		add(r);
	}

	public ShotgunBullet createShotgunBullet(int x, int y, int damage,
			int direction, int spread) {
		ShotgunBullet shotgunBullet = new ShotgunBullet(x, y, damage,
				direction, spread);
		projectileGroup.add(shotgunBullet);
		playerBulletGroup.add(shotgunBullet);
		add(shotgunBullet);

		return shotgunBullet;
	}

	public void createMachineGunBullet(int x, int y, int damage, int direction,
			int spread) {
		ShotgunBullet shotgunBullet = new ShotgunBullet(x, y, damage,
				direction, spread);
		projectileGroup.add(shotgunBullet);
		playerBulletGroup.add(shotgunBullet);
		add(shotgunBullet);
		Bullet.bulletLife = 5 * Timer.FRAMES_TO_SECONDS;
	}

	public void createRangedAlienBullet(int x, int y, int damage, int direction) {
		RangedAlienBullet rab = new RangedAlienBullet(x, y, damage, direction);
		enemyGroup.add(rab);
		projectileGroup.add(rab);
		add(rab);
	}

	public Coin createCoin(int x, int y, int value) {
		Coin coin = new Coin(x, y, value);
		miscEntityGroup.add(coin);
		miscGroup.add(coin);
		add(coin);

		return coin;
	}

	public Key createKey(int x, int y) {
		Key key = new Key(x, y);
		miscEntityGroup.add(key);
		miscGroup.add(key);
		add(key);

		return key;
	}

	public Door createDoor(int x, int y) {
		Door door = new Door(x, y);
		miscEntityGroup.add(door);
		miscGroup.add(door);
		add(door);

		return door;
	}

	public Smoke createSmoke(int x, int y) {
		Smoke smoke = new Smoke(x, y);
		add(smoke);

		return smoke;
	}

	public MagnetizedCoin createMagnetizedCoin(int x, int y, int value) {

		MagnetizedCoin coin = new MagnetizedCoin(x, y, value);
		miscEntityGroup.add(coin);
		miscGroup.add(coin);
		add(coin);

		return coin;

	}

	public Store createStore() {
		Store store = new Store(this);
		add(store);
		return store;
	}

	public FlxText createText(int x, int y, int width, String string) {
		FlxText text = new FlxText(x, y, width, string);
		add(text);

		return text;
	}

	public FlxText createCenteredText(int x, int y, int width, String string) {
		FlxText text = new FlxText(x, y, width, string);
		add(text);
		text.setAlignment("center");

		return text;
	}

	public FlxText createCenteredText(int x, int y, int width, String string,
			int fontsize) {
		FlxText text = new FlxText(x, y, width, string);
		add(text);
		text.setAlignment("center");
		text.setFormat(null, 20);

		return text;
	}

	public VariableText createVariableText(int x, int y, String variableName,
			int variable, boolean scrollable) {
		VariableText vt = new VariableText(x, y, variableName, variable,
				scrollable);
		add(vt);

		return vt;
	}

	public Explosion createExplosion(int x, int y, int size, int lowRange,
			int highRange, int direction) {
		Explosion ex = new Explosion(x, y, size, lowRange, highRange, 1);
		projectileGroup.add(ex);
		playerBulletGroup.add(ex);

		if (direction == FlxSprite.RIGHT) {
			createExplosionEffect(x + 25, y + 25);
		} else {
			createExplosionEffect(x + 75, y + 25);
		}

		FlxG.play("SFX/Explosion.mp3");

		add(ex);

		return ex;
	}

	public void createCoinExplode(int x, int y, int numCoins, int value,
			int explosionForce) {
		if (Player.hasMagnet) {
			for (int i = 0; i < numCoins; i++) {
				MagnetizedCoin coin = createMagnetizedCoin(x, y, value);
				coin.velocity.y = RandomNumber.generateRandomNumber(
						-explosionForce * 5, 0);
				coin.velocity.x = RandomNumber.generateRandomNumber(
						-explosionForce, explosionForce);
			}
		} else {
			for (int i = 0; i < numCoins; i++) {
				Coin coin = createCoin(x, y, value);
				coin.velocity.y = RandomNumber.generateRandomNumber(
						-explosionForce * 5, 0);
				coin.velocity.x = RandomNumber.generateRandomNumber(
						-explosionForce, explosionForce);
			}
		}
	}

	public void createExplosionEffect(int x, int y) {
		for (int i = 0; i < 20; i++) {
			Smoke smoke = createSmoke(x, y);
			smoke.velocity.x = RandomNumber.generateRandomNumber(-300, 300);
			smoke.velocity.y = RandomNumber.generateRandomNumber(-300, 300);
			smoke.setColor(0xFF000000);
			smoke.setAlpha(0.3f);
		}

		for (int i = 0; i < 30; i++) {
			Smoke smoke = createSmoke(x, y);
			smoke.velocity.x = RandomNumber.generateRandomNumber(-300, 300);
			smoke.velocity.y = RandomNumber.generateRandomNumber(-300, 300);
			smoke.setAlpha(0.5f);
		}
	}

	public Brick createBrick(int x, int y) {
		Brick brick = new Brick(x, y);
		solidEntityGroup.add(brick);
		add(brick);

		return brick;
	}

	public GroundFloor createGroundFloor(int x, int y, int placement, int number) {
		GroundFloor gf = new GroundFloor(x, y, placement, number);
		solidEntityGroup.add(gf);
		add(gf);

		return gf;
	}

	public ShopFloor createShopFloor(int x, int y) {
		ShopFloor sf = new ShopFloor(x, y);
		add(sf);
		solidEntityGroup.add(sf);
		return sf;
	}

	public ShopTile createShopTile(int x, int y) {
		ShopTile st = new ShopTile(x, y);
		add(st);
		shopTilesGroup.add(st);

		return st;
	}

	public FloatyWord createFloatyWord(int x, int y, String text) {
		FloatyWord fw = new FloatyWord(x, y, text);
		add(fw);

		return fw;
	}

	public Chest createChest(int x, int y, int numCoins, int value) {
		Chest chest = new Chest(x, y, numCoins, value);

		miscGroup.add(chest);
		miscEntityGroup.add(chest);
		add(chest);

		return chest;
	}

	public void createAmmo(int x, int y, int type, int amount) {
		Ammo ammo = new Ammo(x, y, type, amount);
		miscEntityGroup.add(ammo);
		miscGroup.add(ammo);
		add(ammo);
	}

	public Spike createSpike(int x, int y) {
		Spike spike = new Spike(x, y);
		add(spike);
		miscEntityGroup.add(spike);
		miscGroup.add(spike);

		return spike;
	}

	public Spring createSpring(int x, int y) {
		Spring spring = new Spring(x, y);
		miscGroup.add(spring);
		add(spring);

		return spring;
	}

	public Grass createGrass(int x, int y, int type) {
		Grass grass = new Grass(x, y, type);
		add(grass);

		return grass;

	}

	public void createEntranceAnimation(int x, int y) {
		EntranceAnimation ea = new EntranceAnimation(x, y);
		add(ea);
	}

	public static int levelWidth;
	public static int levelHeight;

	// Holds all the items in the current level
	public FlxGroup currentLevelGroup;

	public void createLevelFromFile() {

		currentLevelGroup = new FlxGroup();

		XmlReader xml = new XmlReader();
		try {
			Element root = xml.parse(Gdx.files.internal("Level1.oel"));

			levelWidth = Integer.parseInt(root.getAttribute("width"));
			levelHeight = Integer.parseInt(root.getAttribute("height"));

			// FlxG.camera.follow(player, FlxCamera.STYLE_PLATFORMER);

			Element entities = root.getChildByName("Entities");

			// GROUDN STUFF
			for (Element ground : entities.getChildrenByName("GroundTop1")) {
				GroundFloor ground2 = createGroundFloor(ground.getInt("x"),
						ground.getInt("y"), GroundFloor.TOP, 1);

				currentLevelGroup.add(ground2);
				topTiles.add(ground2);
			}

			for (Element ground : entities.getChildrenByName("GroundTop2")) {
				GroundFloor ground2 = createGroundFloor(ground.getInt("x"),
						ground.getInt("y"), GroundFloor.TOP, 2);

				currentLevelGroup.add(ground2);
				topTiles.add(ground2);

			}

			for (Element ground : entities.getChildrenByName("GroundMid1")) {
				currentLevelGroup.add(createGroundFloor(ground.getInt("x"),
						ground.getInt("y"), GroundFloor.MID, 1));
			}

			for (Element ground : entities.getChildrenByName("GroundMid2")) {
				currentLevelGroup.add(createGroundFloor(ground.getInt("x"),
						ground.getInt("y"), GroundFloor.MID, 2));
			}

			for (Element ground : entities.getChildrenByName("GroundBottom1")) {
				currentLevelGroup.add(createGroundFloor(ground.getInt("x"),
						ground.getInt("y"), GroundFloor.BOTTOM, 1));
			}

			for (Element ground : entities.getChildrenByName("GroundBottom2")) {
				currentLevelGroup.add(createGroundFloor(ground.getInt("x"),
						ground.getInt("y"), GroundFloor.BOTTOM, 2));
			}

			/**
			 * Chests should be randomly generated
			 */
			for (Element chest : entities.getChildrenByName("Chest")) {

			}

			for (Element spike : entities.getChildrenByName("Spike")) {
				currentLevelGroup.add(createSpike(spike.getInt("x"),
						spike.getInt("y")));
			}

			for (Element brick : entities.getChildrenByName("Brick")) {
				Brick brick2 = createBrick(brick.getInt("x"), brick.getInt("y"));
				currentLevelGroup.add(brick2);
				topTiles.add(brick2);
			}

			for (Element stone : entities.getChildrenByName("Stone")) {
				ShopFloor stone2 = createShopFloor(stone.getInt("x"),
						stone.getInt("y"));
				currentLevelGroup.add(stone2);
				topTiles.add(stone2);
			}

			// Create ladders later because they go over other tiles
			for (Element ladder : entities.getChildrenByName("Ladder")) {
				currentLevelGroup.add(createLadder(ladder.getInt("x"),
						ladder.getInt("y")));
			}

			for (Element ladderBottom : entities
					.getChildrenByName("LadderBottom")) {
				currentLevelGroup.add(createLadder(ladderBottom.getInt("x"),
						ladderBottom.getInt("y"), true));
			}

			for (Element spring : entities.getChildrenByName("Spring")) {
				currentLevelGroup.add(createSpring(spring.getInt("x"),
						spring.getInt("y")));
			}

			for (Element grass1 : entities.getChildrenByName("Grass1")) {
				currentLevelGroup.add(createGrass(grass1.getInt("x"),
						grass1.getInt("y"), 1));
			}

			for (Element grass2 : entities.getChildrenByName("Grass2")) {
				currentLevelGroup.add(createGrass(grass2.getInt("x"),
						grass2.getInt("y"), 2));
			}

			spawnRandomItems();

			// THE PLAYER IS CREATED HERE
			createPlayer(100, 100);

			for (Element playerElement : entities.getChildrenByName("Player")) {
				player.x = playerElement.getInt("x");
				player.y = playerElement.getInt("y");
			}

			FlxG.camera.follow(player, FlxCamera.STYLE_PLATFORMER);

			FlxG.camera.setBounds(0, 0, levelWidth, levelHeight);
			FlxG.worldBounds = FlxG.camera.bounds;
			System.out.println(FlxG.camera.bounds.x + " " + FlxG.camera.y + " "
					+ FlxG.camera.width + " " + FlxG.camera.height);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int spawnCounter = 0;
	public int spawnTime = 10 * Timer.FRAMES_TO_SECONDS;

	public void spawnEnemies() {
		if (spawnCounter < spawnTime) {
			spawnCounter++;
		} else {
			// Spawn random enemies here

			spawnCounter = 0;

			Outside: for (int i = 0; i < 100; i++) {

				int number = RandomNumber.generateRandomNumber(0,
						topTiles.members.size - 1);
				FlxSprite sprite = (FlxSprite) (topTiles.members.get(number));

				if (Math.abs(Player.playerX - sprite.x) < 400
						&& Math.abs(Player.playerY - sprite.y) < 300
						&& Math.abs(Player.playerX - sprite.x) > 100) {

					// Spawn enemy at that position
					chooseRandomEnemy((int) sprite.x, (int) sprite.y - 45);
					break;
				}

			}

		}
	}

	/**
	 * Spawns a random enemy at x and y
	 * 
	 * @param x
	 * @param y
	 */
	public void chooseRandomEnemy(int x, int y) {

		int number = RandomNumber.generateRandomNumber(0, 4);

		switch (number) {
		case 0:
			createDog(x, y);
			break;
		case 1:
			createRangedAlien(x, y);
			break;
		case 2:
			createShieldingEnemy(x, y);
			break;
		case 3:
			createExplodingSkeleton(x, y);
			break;
		case 4:
			createSkeleton(x - 5, y - 5);
			break;
		}

		createEntranceAnimation(x, y);
		spawnTime = (int) (Math.random() * 15 + 1) * Timer.FRAMES_TO_SECONDS;
	}

	public void spawnRandomItems() {
		int randomNumber = RandomNumber.generateRandomNumber(0,
				topTiles.members.size - 1);
		FlxSprite sprite = (FlxSprite) (topTiles.members.get(randomNumber));

		createKey((int) sprite.x, (int) sprite.y - 45);

		randomNumber = RandomNumber.generateRandomNumber(0,
				topTiles.members.size - 1);
		sprite = (FlxSprite) (topTiles.members.get(randomNumber));
		Door door = new Door((int) sprite.x, (int) sprite.y - 32);
		miscGroup.add(door);
		door.backDoor.visible = true;
		add(door.backDoor);
		add(door);

		for (int i = 0; i < 3; i++) {
			randomNumber = RandomNumber.generateRandomNumber(0,
					topTiles.members.size - 1);
			sprite = (FlxSprite) (topTiles.members.get(randomNumber));

			createChest((int) sprite.x, (int) sprite.y - (int) sprite.height
					* 2, 20, 1);
		}

		for (int i = 0; i < 10; i++) {
			randomNumber = RandomNumber.generateRandomNumber(0,
					topTiles.members.size - 1);
			sprite = (FlxSprite) (topTiles.members.get(randomNumber));

			int typeOfAmmo = RandomNumber.generateRandomNumber(1, 5);

			createAmmo((int) sprite.x, (int) sprite.y - 32 - (int)sprite.height * 3,
					typeOfAmmo, 0);

		}

	}
}
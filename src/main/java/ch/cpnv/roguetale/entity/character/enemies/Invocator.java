package ch.cpnv.roguetale.entity.character.enemies;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import ch.cpnv.roguetale.entity.Direction;
import ch.cpnv.roguetale.entity.character.Enemy;
import ch.cpnv.roguetale.entity.pickupableitem.PickupableLifePoint;
import ch.cpnv.roguetale.entity.pickupableitem.PickupableWeapon;
import ch.cpnv.roguetale.gui.guis.GameGui;
import ch.cpnv.roguetale.sound.SoundManager;
import ch.cpnv.roguetale.sound.SoundType;
import ch.cpnv.roguetale.weapon.Weapon;
import ch.cpnv.roguetale.weapon.other.CreationOfLife;

public class Invocator extends Enemy {
	private static final int SPEED = 0;
	private static final int MAX_HEALTH = 1000;
	private static final int MONEY_REWARD = 5;
	private static final int XP_REWARD = 50;
	private static final String SPRITESHEET_PATH = "ch\\cpnv\\roguetale\\images\\enemy\\bomber\\carac.png";
	private static final int  SPRITESHEET_DIMENSIONS = 48;

	public Invocator(Vector2f position) throws SlickException {
		super(
				"Invocateur", 
				getSpriteSheet(), 
				position, SPEED, Direction.DOWN, false, 
				new CreationOfLife(), null, 
				MAX_HEALTH, MONEY_REWARD, XP_REWARD
		);
		int lvl = getDistanceTo(new Vector2f(0, 0))/1000;
		for (int i = 0; i < lvl;i++) {
			this.levelup();
		}
	}
	
	public static SpriteSheet getSpriteSheet() throws SlickException {
		return new SpriteSheet(SPRITESHEET_PATH, SPRITESHEET_DIMENSIONS, SPRITESHEET_DIMENSIONS, 0);
	}
	
	@Override
	protected void dropOnDeath() throws SlickException {
		double alea = Math.random();
		if (alea < 0.3) {
			GameGui.getPickupableItemController().addPickupableItem(new PickupableWeapon(new CreationOfLife(), position));
		} else
			GameGui.getPickupableItemController().addPickupableItem(new PickupableLifePoint(position));
	}
	
	@Override
	public void die() throws SlickException {
		super.die();
		SoundManager.getInstance().play(SoundType.RobotDeath, 5f);
	}
	
	public void chooseAction() throws SlickException {
		if (this.getNearestOpponent() != null) {
			Weapon weapon = this.getWeapon();
			if (weapon.canAttack()) {
				weapon.attack(this);
			}
		}
	}
	
	public void setImageDirection() {
		int yPos = 0;
		switch (this.direction) {
			case LEFT:
				yPos = 1;
				break;
			case RIGHT:
				yPos = 2;
				break;
			case UP:
				yPos = 3;
				break;
			case DOWN:
				yPos = 0;
				break;
		}
		
		this.image = this.getSpritesheet().getSprite(0, yPos);
	}
}

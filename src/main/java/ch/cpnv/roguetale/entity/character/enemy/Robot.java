package ch.cpnv.roguetale.entity.character.enemy;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import ch.cpnv.roguetale.entity.Direction;
import ch.cpnv.roguetale.entity.character.Enemy;
import ch.cpnv.roguetale.weapon.Weapon;
import ch.cpnv.roguetale.weapon.ranged.Bow;

public class Robot extends Enemy {
	private static final int SPEED = 20;
	private static final Weapon PRIMARY_WEAPON = new Bow();
	private static final Weapon SECONDARY_WEAPON = null;
	private static final String SPRITESHEET_PATH = "ch\\cpnv\\roguetale\\images\\enemy\\carac.png";
	private static final int  SPRITESHEET_DIMENSIONS = 48;

	public Robot(Vector2f position) throws SlickException {
		super(new SpriteSheet(SPRITESHEET_PATH, SPRITESHEET_DIMENSIONS, SPRITESHEET_DIMENSIONS, 0), 
				position, SPEED, Direction.UP, false, PRIMARY_WEAPON, SECONDARY_WEAPON);
	}

}

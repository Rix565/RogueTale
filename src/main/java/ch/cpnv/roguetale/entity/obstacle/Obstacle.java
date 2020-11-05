package ch.cpnv.roguetale.entity.obstacle;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SpriteSheet;

import ch.cpnv.roguetale.entity.DrawableItem;

public abstract class Obstacle extends DrawableItem {
	public Obstacle(SpriteSheet ss, Vector2f position) {
		super(ss, position);
	}
}

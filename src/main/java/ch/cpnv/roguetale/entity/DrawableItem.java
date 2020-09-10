package ch.cpnv.roguetale.entity;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class DrawableItem {
	protected SpriteSheet spritesheet;
	protected Image image;
	protected Vector2f position;

	public DrawableItem(SpriteSheet ss, Vector2f position) {
		this.setSpritesheet(ss);
		this.position = position;
	}
	
	public String toString() {
		return "DrawableItem (" + position.x + ", " + position.y + ")";
	}
	
	public void draw(Vector2f origin, GameContainer gc) {
		// Note that the slick y coordinates go the opposite direction of the usual y axis
		if (isInScreen(gc, origin)) {
			this.image.draw(this.position.x - origin.x - this.image.getWidth() / 2, 
					 - (this.position.y - origin.y + this.image.getHeight() / 2));
		}
	}

	public SpriteSheet getSpritesheet() {
		return spritesheet;
	}

	public void setSpritesheet(SpriteSheet spritesheet) {
		this.spritesheet = spritesheet;
		this.image = this.spritesheet.getSprite(0, 0);
	}
	
	public Image getSprite() {		
		return this.image;
	}

	public Vector2f getPosition() {
		// clone of the position
		return new Vector2f(position.x, position.y);
	}
	
	public float getXLeft() {
		return position.x + image.getWidth() / 2;
	}
	
	public float getXRight() {
		return position.x - image.getWidth() / 2;
	}
	
	public float getYTop() {
		return position.y + image.getHeight() / 2;
	}
	
	public float getYBottom() {
		return position.y + image.getHeight() / 2;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public Boolean isInScreen(GameContainer gc, Vector2f screenOrigin) {
		// Note that the screen origin is the UP LEFT corner
		return getXRight() >= screenOrigin.x
				&& getXLeft() <= screenOrigin.x + gc.getWidth()
				&& getYTop() >= screenOrigin.y - gc.getWidth()
				&& getYBottom() <= screenOrigin.y;
	}
}

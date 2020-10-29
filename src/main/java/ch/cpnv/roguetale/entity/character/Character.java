package ch.cpnv.roguetale.entity.character;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import ch.cpnv.roguetale.entity.Direction;
import ch.cpnv.roguetale.entity.MovableItem;
import ch.cpnv.roguetale.entity.temporaryeffect.itemeffect.effects.Damage;
import ch.cpnv.roguetale.entity.temporaryeffect.itemeffect.effects.Heal;
import ch.cpnv.roguetale.sound.SoundManager;
import ch.cpnv.roguetale.sound.SoundType;
import ch.cpnv.roguetale.weapon.RangedWeapon;
import ch.cpnv.roguetale.weapon.Weapon;

public abstract class Character extends MovableItem {
	protected int currentHealth;
	protected int maxHealth;
	protected Weapon primaryWeapon;
	protected Weapon secondaryWeapon;

	public Character(SpriteSheet ss, 
			Vector2f position, 
			int speed, 
			Direction direction, 
			boolean moving, 
			Weapon primaryWeapon, 
			Weapon secondaryWeapon,
			int maxHealth
			) {
		super(ss, position, speed, direction, moving);
		this.primaryWeapon = primaryWeapon;
		this.secondaryWeapon = secondaryWeapon;
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
	public void move(int delta) throws SlickException {
		int oldSpeed = this.speed;
		if (isAiming()) {
			this.speed /= 2;
		}
		super.move(delta);
		Character collidingEntity = (Character) getCollidingEntity();
		// undo the move if there is a collision
		if (isCollidingWithAnotherCharacter()) {
			// We don't want to create an inifinite loop, 
			// so we really don't want to reuse this move
			Direction old = collidingEntity.getDirection();
			collidingEntity.setDirection(this.getDirection());
			super.move(delta * -1);
			collidingEntity.move(delta);
			collidingEntity.setDirection(old);
		}
		
		this.speed = oldSpeed;
	}
	
	public void draw(Vector2f origin, GameContainer gc, Color filter) {
		if (this.isDead() && this.deathAnimation != null) {
			this.deathAnimation.draw(this.position.x - origin.x - this.image.getWidth() / 2, 
					 - (this.position.y - origin.y + this.image.getHeight() / 2),
					 filter);
		} else
			super.draw(origin, gc, filter);
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setPrimaryWeapon(Weapon weapon) {
		this.primaryWeapon = weapon;
	}

	public void setSecondaryWeapon(Weapon weapon) {
		this.secondaryWeapon = weapon;
	}
	
	public Weapon getPrimaryWeapon() {
		return primaryWeapon;
	}

	public Weapon getSecondaryWeapon() {
		return secondaryWeapon;
	}

	// TODO prevent currentHealth to become higher than maxHealth
	public void updateHealth(int health) throws SlickException {
		if (health > 0) {
			this.activeEffects.add(new Heal(this.getPosition()));
		} else if (health < 0) {
			SoundManager.getInstance().play(SoundType.Hurt);
			this.activeEffects.add(new Damage(this.getPosition()));
		}
		this.currentHealth += health;
	}
	
	public void updateMaxHealth(int health) throws SlickException {
		maxHealth += health;
		updateHealth(health);
	}
	
	public Boolean isDead() {
		return this.currentHealth <= 0;
	}
	
	public void aimWeapon(Weapon weapon, int delta) {
		if (weapon != null && weapon instanceof RangedWeapon) {
			((RangedWeapon) weapon).aim(delta);
		}
	}
	
	public void attackWithWeapon(Weapon weapon) throws SlickException {
		if (weapon != null) {
			weapon.attack(this);
		}
	}
	
	public void reduceCooldown(int delta) {
		if (primaryWeapon != null)
			primaryWeapon.reduceCooldown(delta);
		if (secondaryWeapon != null)
			secondaryWeapon.reduceCooldown(delta);
	}
	
	public boolean isAiming() {
		Weapon first = this.getPrimaryWeapon();
		Weapon second = this.getSecondaryWeapon();
		
		return first instanceof RangedWeapon && ((RangedWeapon) first).isAiming() 
				|| second instanceof RangedWeapon && ((RangedWeapon) second).isAiming();
	}
}

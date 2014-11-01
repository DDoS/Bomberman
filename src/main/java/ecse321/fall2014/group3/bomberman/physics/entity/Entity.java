/*Stuff to fix or change:
 * 
 * 1. Unsure about type of textureInfo variable
 * 2. Unsure about collidingWith method algorithms
 * 3. did not include class inheritances because collidable and snapshottable have not been implemented
 * 
 */

package ecse321.fall2014.group3.bomberman.physics.entity;

import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.world.tile.*;

import java.util.LinkedList;

public abstract class Entity extends Collidable {

	protected double velocity;

	protected enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}

	Direction entityDirection;
	protected boolean collidingWith;
	LinkedList entityCollisionList = new LinkedList();
	LinkedList tileCollisionList = new LinkedList();

	public Entity() {

		velocity = 0;
		entityDirection = Direction.UP;
		collidingWith = false;
	}

	public double getVelocity() {
		return velocity;
	}

	public Direction getDirection() {
		return entityDirection;
	}

	public LinkedList getEntityCollisionList() {
		return entityCollisionList;
	}

	public LinkedList getTileCollisionList() {
		return tileCollisionList;

	}

	public boolean isCollidingWith(Tile t) {
		return collidingWith;
	}

	public boolean isCollidingWith(Entity e) {
		return collidingWith;
	}

	//	public abstract getTextureInfo();

}

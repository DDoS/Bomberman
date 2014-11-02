/*
 * Stuff to fix:
 * 
 * 1. What type is blastRadius?
 * 2. What is detonator variable?
 * 3. Powerup Class not implemented so powerup list is commented
 * 
 */

package ecse321.fall2014.group3.bomberman.physics.entity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import ecse321.fall2014.group3.bomberman.world.tile.Tile;

public class Player extends Entity {

	//protected ... blastRadius
	protected int placementBombCount;
	protected double speed = 0;
	protected boolean wallPass;
	protected boolean bombPass;
	protected boolean flamePass;
	protected boolean invisibility;
	//protected List<Powerup> powerups;

	public int getPlacementBombCount() {
		return placementBombCount;
	}

	public double getSpeed() {
		return speed;
	}
/*	
	public Set<Powerup> getPowerups() {
		// TODO: implement me
		return Collections.EMPTY_SET;
	}
*/
	public boolean isWallPass() {
		return wallPass;
	}

	public void setWallPass(boolean wallPass) {
		this.wallPass = wallPass;
	}

	public boolean isBombPass() {
		return bombPass;
	}

	public void setBombPass(boolean bombPass) {
		this.bombPass = bombPass;
	}

	public boolean isFlamePass() {
		return flamePass;

	}

	public void setFlamePass(boolean flamePass) {
		this.flamePass = flamePass;
	}

	public boolean isInvisibility() {
		return invisibility;
	}
	
	public void setInvisibility(boolean invisibility){
		this.invisibility = invisibility;
	}

}

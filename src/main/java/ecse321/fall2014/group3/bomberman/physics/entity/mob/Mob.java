/**
 * @author Phil Douyon
*/
package ecse321.fall2014.group3.bomberman.physics.entity.mob;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteTextured;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;


/**
 * The Class Mob.
 */
public abstract class Mob extends Entity implements SpriteTextured {
    
    /**
     * Instantiates a new mob.
     *
     * @param position the position
     * @param collisionBox the collision box
     */
    public Mob(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }
}

/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;

/**
 * The Class EnemyDeathEvent.
 */
public class EnemyDeathEvent extends Event {
    private final Enemy enemy;

    /**
     * Instantiates a new enemy death event.
     *
     * @param enemy the enemy
     */
    public EnemyDeathEvent(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * Gets the enemy.
     *
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }
}

package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;

/**
 *
 */
public class EnemyDeathEvent extends Event {
    private final Enemy enemy;

    public EnemyDeathEvent(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}

package ecse321.fall2014.group3.bomberman.world;

import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;

/**
 *
 */
public class World extends TickingElement {
	private final Game game;
	private Map map;

	public World(Game game) {
		super("World", 20);
		this.game = game;
	}

	@Override
	public void onStart() {
		map = new Map();
	}

	@Override
	public void onTick(long l) {

	}

	@Override
	public void onStop() {

	}

	public Map getMap() {
		return map;
	}
}

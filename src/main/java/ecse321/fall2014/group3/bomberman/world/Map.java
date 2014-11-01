package ecse321.fall2014.group3.bomberman.world;

import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.Wall;

public class Map {
	int columns;
	int rows;
	Tile tiles[][];

	public Map() {		
		columns = 13;
		rows = 31;
		tiles = new Tile[columns][rows];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				//place wall every 2 spots in x and y
				if ((x % 2 == 1) && (y % 2 == 1)) {
					tiles[x][y] = new Air();
				} else {
					tiles[x][y] = new Wall();
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public Tile[][] getTiles() {
		return tiles;
	}
}

package game.world;

import game.world.tile.*;

class Map {
	
	int columns = 13;
	int rows = 31; 
	Tile tiles [][] = new Tile [columns] [rows];
	
	public Map (){ 
		for (int i = 0; i < rows; i++){
			for (int j =0; j< columns; j++){
				//place wall every 2 spots in x
				//ignore 0,0
				if (j % 2 == 1){
					tiles [i][j]= new Tile ("wall", i, j);
					
				}else 
					tiles [i][j]= new Tile("air", i, j);
			}
		}
		
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public Tile[][] getTiles(){
		return tiles ;
	}

}

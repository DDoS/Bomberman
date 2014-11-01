package game.world.tile;


public  class Tile {
	
	int x,y;
	Tile tileType; 
	String type;
	
	public Tile (){
		
	}
	
	public Tile (String t,int x, int y){
		type = t;
		if (type.equals("Air")){
		tileType = new Air();	
		}else {
		tileType = new Wall();
		}
		this.x = x;
		this.y=y;
	
		
	}
	
	public String getType(){
		return type;
	}
	
	
	
		
	
}

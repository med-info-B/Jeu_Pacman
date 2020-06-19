package PacmanModel;

import java.awt.Color;

public class RoomMajic extends Room {
	
	private Room targetRoom;
	public RoomMajic(Maze maze, Coordinate location) {
		super(maze, location,Color.MAGENTA);
		
		
	}
	
	/**
	 * 
	 * @return le numéro (id) d'une pièce destination
	 */
	private int destination() {
		if( this.getRoomId() == 7) {
			return 56;	
		}
		else
			return 7;
	}
	@Override
	public void entrer(Personnage personnage)  {
		targetRoom = this.getMaze().getListRoom().get(destination());
		personnage.ChangeRoom(targetRoom);
	}



}

package PacmanModel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import  java.util.Iterator;


public class Maze implements Element{
	
	private Game game;
	private ArrayList<Room> listRoom;
	private ArrayList<PacGomme> listPacGomme;
	private Personnage pacman;
	private ArrayList<Personnage> fantomes;

	public Maze(Game game) {		
		this.game = game;
		listRoom = new ArrayList<Room>();
		fantomes = new ArrayList<>();
		listPacGomme = new ArrayList<>();		
	}
	
	public void fillMaze() {
		PacGomme pac = null;
		for(Room r : listRoom) {
			if(!r.existPacGomme()  && r instanceof RoomSimple) {
				if(r.getRoomId() == 0 ||  r.getRoomId() == 4 || r.getRoomId() == 10 || r.getRoomId() == 21 || r.getRoomId() == 24) {
					pac = FactoryMaze.makePacGommeActive(game, r, Color.ORANGE, 500,
									new SuperEffect(pacman));
				}
				else if(r.getRoomId() == 60 || r.getRoomId() == 48 || r.getRoomId() == 46 || r.getRoomId() == 34) {
					pac = FactoryMaze.makePacGommeActive(game, r, new Color(102- 0 -153), 300, 
							new WeakEffect(pacman));
				}
				else if(r.getRoomId() == 36) {
					pac = FactoryMaze.makePacGommeActive(game, r, Color.GREEN, 1000,
							new SuperEffect(this));	
				}
				else pac = FactoryMaze.makeSimplePacGomme(game, r, Color.BLUE, 100);
				r.setPacgomme(pac);
			}
		}
	}
	public Maze restructure() {
		for(Room r : listRoom) {
			r.resetRoom();
		}
		return game.getMazeGame().generate();
	}
	// Room
	public ArrayList<Room> getListRoom(){
		return this.listRoom;
	}
	public void addRoom(Room room){
		this.listRoom.add(room);
	}
	public Room findRoom(int id) {
		if(id < listRoom.size()) {
			Iterator<Room> iter = listRoom.iterator(); 
			while(iter.hasNext()){
			Room r = iter.next();
			if(r.getRoomId() == id)
				return r;
			}
		}
		throw new  IllegalArgumentException("Le Id de la pièce n'existe pas");
	}
	// Pac-Gomme
	public void setPacGomme(PacGomme pac){
		this.listPacGomme.add(pac);
	}
	public void removePacGomme(PacGomme pac){
		if(listPacGomme.contains(pac)){
				listPacGomme.remove(pac);
		}
	}	
	public  ArrayList<PacGomme> getlistPac(){
		return listPacGomme;
	}
	public boolean existPacgommes(){
		return this.listPacGomme.isEmpty();
	}
	
    // Personnage
	public void setPacman(Pacman pacman){
		this.pacman = pacman;
	}
	public void setFantome(Fantome fantome){
		this.fantomes.add(fantome);
	}
	public Pacman getPacman() {
		return (Pacman)this.pacman;
	}
	
	public  ArrayList<Personnage> getFantomes(){
		return this.fantomes;
	}
	
	/**
	 * deplacement des personnages sur le labyrinthe
	 */
	public void step(){
		pacman.move().entrer(pacman);
		for( Personnage ghost :fantomes)
			ghost.move().entrer(ghost);
	}
    
	
	/**
	 *  Dessin du labyrinthe 
	 */
	@Override
	public void draw_element(Graphics g, int Scale){
		//Dessin d'une pièce 
		for(Room room : listRoom){
			Coordinate location  = room.getLocation();
			room.draw(g, location.getX() * Scale, location.getY() * Scale, Scale, Scale);
		}
		// Dessin des cotés d'une pièces
		for(Room room : listRoom){
			Coordinate location = room.getLocation();
				room.getSite(Direction.Nord).draw(g, location.getX() * Scale, (location.getY()) * Scale, Scale , Scale  / 5);	
				room.getSite(Direction.Est).draw(g,(location.getX() + 1) * Scale, location.getY() * Scale, Scale  / 5, Scale );
				room.getSite(Direction.Sud).draw(g,location.getX() * Scale, (location.getY() + 1) * Scale , Scale , Scale  / 5);
				room.getSite(Direction.Ouest).draw(g,((location.getX()) * Scale), location.getY() * Scale, Scale  / 5, Scale  );			
		}
	}

	public Game getGame(){
		return game;
	}

}

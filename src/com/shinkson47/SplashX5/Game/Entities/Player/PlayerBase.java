package com.shinkson47.SplashX5.Game.Entities.Player;

import com.shinkson47.SplashX5.Game.Enumerator.Direction;
import com.shinkson47.SplashX5.Game.Enumerator.Gamemode;
import com.shinkson47.SplashX5.Game.Enumerator.Realms;

public class PlayerBase {
	public Direction direction;
	public int ID, X, Y, health = 20, hunger = 20, saturation = 100, SpeedMod = 0, Speed = 4, MoveTimer;
	public Gamemode gamemode = Gamemode.SurviveAndThrive;
	//public Direction direction = Direction.S;
	public boolean exists = false, isMoving = false, N = false, S = false, E = false, W = false;
	public Realms dimention;
	public Inventory inventory;
	
	public PlayerBase(int id, int x, int y, Gamemode gamemode, Realms d) {
		dimention = d;
		X = x;
		Y = y;
		ID = id;
		inventory = new Inventory();
	}	
}

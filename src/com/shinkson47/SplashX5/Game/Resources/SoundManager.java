package com.shinkson47.SplashX5.Game.Resources;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.Entity;
import com.shinkson47.SplashX5.Game.Enumerator.EntitySounds;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Game.Windows.Game;
import com.shinkson47.SplashX5.Game.World.CurrentMap;

public class SoundManager {
	public static Clip sfx = null, Music = null;
	public static boolean EngineEnabled = false;
	static int STIndex = 0;
	static final int TrackCount = 22;
	private static AudioInputStream audioInputStream;   
	
    public static void init() {
    	if (EngineEnabled) {return;}

    	try {
    		sfx = AudioSystem.getClip();
			Music = AudioSystem.getClip();
	    	PlayMenu();
	    	EngineEnabled = true;
		} catch (LineUnavailableException e) {
		}
    	
    	if (!EngineEnabled) {Logger.log("The sound engine was unable to start up correctly, so it has been disabled.", SoundManager.class, LogState.Error); return; }
    	EngineEnabled = true;
   }
    
    public static void Update() {
    	try {
    	if (Game.InGame) {
    		if (!Music.isActive()) {NextST();}
    	} else {
    	}
    	
    	if (!sfx.isActive() && sfx.isOpen()) { sfx.close(); }
    	if (!Music.isActive() && Music.isOpen()) { Music.close(); }
    	} catch (Exception e) {}
    }
        
    private static int OWC = 5, HC, EGC, UGC;
    private static void NextST() {
    	switch (CurrentMap.Diamention) {
		case Heaven:
			break;
		case Hell:
			break;
		case Mine:
			break;
		case Overworld:
			if (STIndex < OWC) {STIndex++;} else {STIndex = 0;}
			break;
		case Tundra:
			break;
		case Underworld:
			break;
		default:
			break;
    	}
    	PlayGame();
	}

	public static void PlayMenu() {
    	String Location = String.valueOf(ResourceManager.ResourcePath + "/Resources/Audio/Soundtrack/MenuTheme.wav");
    	PlayFile(Location, false);
    }
    
	public static void PlayGame() {
    	String Location = String.valueOf(ResourceManager.ResourcePath + "/Resources/Audio/Soundtrack/" + CurrentMap.Diamention.toString() + "/ST" +  STIndex + ".wav");
    	PlayFile(Location, false);
	}

    public static void PlayMisc(String name, boolean voice) {
    	if (voice) {
    		if (sfx.isOpen()) { return; }
    	} else {
    		if (Music.isOpen()) { return; }
    	}

    	if (!EngineEnabled) {return;}
    	String Location = String.valueOf(ResourceManager.ResourcePath + "/Resources/Audio/" + name + ".wav");
    	PlayFile(Location, voice);
    }
    
    public static void PlayEntity(Entity source, EntitySounds walk) {
   		if (sfx.isOpen()) { return; }

    	String Location = String.valueOf(ResourceManager.ResourcePath + "/Resources/Audio/Entities/" + source.toString() + "/" + walk.toString() + ".wav");
    	PlayFile(Location, true);
    }
    
    public static void close() {
    	try {Music.stop(); Music.drain(); Music.flush();}catch (Exception e) {}
    	try {sfx.stop(); sfx.drain(); sfx.flush();}catch (Exception e) {}
    	EngineEnabled = false;
    }

	    //Channel true = sfx, else music
	    private static void PlayFile(String filename, boolean Chanel){
	    	try {
	    		   audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filename)));
	            if (Chanel) {

	            	sfx.open(audioInputStream);
	            	sfx.start();
	            } else {
	            	try {	
	            		Music.stop();
	            		Music.drain();
	            		Music.close();
	            		}catch (Exception e) {}

	            	Music.open(audioInputStream);
	            	Music.start();         	
	            }
	        } catch (Exception ex) { ex.printStackTrace(); }
	    }
	    
	    public static int getST() {
	    	return STIndex;
	    }
	}

	


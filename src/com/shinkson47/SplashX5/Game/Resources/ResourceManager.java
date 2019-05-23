package com.shinkson47.SplashX5.Game.Resources;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

import com.shinkson47.SplashX5.Game.Enumerator.Direction;
import com.shinkson47.SplashX5.Game.Enumerator.Entity;

public class ResourceManager {
	public static java.nio.file.Path ResourcePath = Paths.get("./");
	//TODO resource encryption
	
	public static void init() {
		
	}
	
	public static Image getEntityTexture(Entity entity, Direction d) {
		String d1;
		if (d == null) {d1 = "Default";} else {d1 = d.toString();}
		
		
		
		
		ImageIcon img = new ImageIcon("./Resources/Entities/" + entity.toString() + "/"+ d1 + ".png");
		return img.getImage();
	}
	
	public static Image getTexture(String name) {
		ImageIcon img = new ImageIcon("./Resources/" + name + ".png");
		return img.getImage();
	}
	
	public static Font GetFont(String name) {
		Font font = new Font("sans", Font.PLAIN, 24);
		 try {
	            String fname = "./Resources/UI/" + name + ".ttf";
	            File fontFile = new File(fname);
	            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
	            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	            ge.registerFont(font);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return font;
		}
	
}

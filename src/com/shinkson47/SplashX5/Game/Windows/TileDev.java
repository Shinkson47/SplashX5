package com.shinkson47.SplashX5.Game.Windows;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Enumerator.LogState;
import com.shinkson47.SplashX5.Client.ClientRenderer;

public class TileDev{
	public static Color[][] TileColour = new Color[64][64];
	public static Color[] Pallette = new Color[10];
	public static Color BackgroundColor = Color.gray;
	public static int CursorX = 0, CursorY = 0, TileSize = 10, Indent = 24, CursorMultiplyer = 1, SelectedColor = 0, Spacing = 1;
	
	
	public static void Update() {
		
	}

	public static void init() {
		Indent = TileSize;
		ClientWindow.window.setSize(1000,800);
		ClientWindow.setFontSize(20f);
		
		for (int x = 0; x <= 9; x++) {
				Pallette[x] = Color.white;
		}
		

		for (int x = 0; x <= 63; x++) {
			for (int y = 0; y <= 63; y++) {
				TileColour[x][y] = BackgroundColor;
			}
		}
		
		File file = new File("./ExportedTiles/InProgress.png");
		if (file.exists()) {
			int result = JOptionPane.showConfirmDialog(ClientWindow.window, "A project cache file exists, recover it?", "[Init]",
		               JOptionPane.YES_NO_OPTION,
		               JOptionPane.QUESTION_MESSAGE);
		            if(result == JOptionPane.YES_OPTION){
		            	ImageIcon img = new ImageIcon("./ExportedTiles/InProgress.png");
						Image image = img.getImage();
						BufferedImage BFI = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
						BFI.getGraphics().drawImage(image, 0, 0, null);						
						
						
						if (BFI.getWidth() > 64  || BFI.getHeight() > 64) { JOptionPane.showMessageDialog(ClientWindow.window, "Invalid cache file"); Logger.log("The cache file could not be loaded.", TileDev.class, LogState.Error); return;}
						
						for (int x = 0; x <= 63; x++) {
							for (int y = 0; y <= 63; y++) {
								Color color = new Color(BFI.getRGB(x, y));
								
								if (color.getRGB() == -16777216) {
									color = BackgroundColor;
								}
								
								TileColour[x][y] = color;
							}
						}
						
						Logger.log("Recovered project", TileDev.class, LogState.Info);
		            	
		            }else {
		    			file.delete();
		    			
		    			try {
		    				file.createNewFile();
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}		
		    		}
		            }
			
		}

	public static void Zoom(int Change) {
		if ((TileSize == 1) && (Change == -1)) { Spacing = 0; } else { Spacing = 1; } 
		
		if ((TileSize + Change) <= 0) { return; }
		if ((TileSize + Change) >= 10) { return; }
		
		TileSize += Change;
		Indent = TileSize;
	}
	
	//TODO spacing
	

	public static void NewColour(int i) {
	if (i > Pallette.length) { return; };
    Pallette[i] = com.bric.colorpicker.ColorPickerDialog.showDialog(ClientWindow.window, Pallette[i]);
}

	/*
	 * TODO Warn if color is same as background. it will be ignored. 
	 */
	public static void SetColour(int i) {
		if (i > Pallette.length) { return; }
		
		SelectedColor = i;
	}
	
	public static void Delete() {
		for (int x = 0; x < CursorMultiplyer; x++) {
			if (x + CursorX > 63) {continue;}
			for (int y = 0; y < CursorMultiplyer; y++) {
				if (y + CursorY > 63) {continue;}
				TileColour[CursorX + x][CursorY + y] = BackgroundColor;	
			}
		}
	}

	public static void Print() {
		for (int x = 0; x < CursorMultiplyer; x++) {
			if (x + CursorX > 63) {continue;}
			for (int y = 0; y < CursorMultiplyer; y++) {
				if (y + CursorY > 63) {continue;}
				TileColour[CursorX + x][CursorY + y] = Pallette[SelectedColor];	
			}
		}
		
		try {
			saveImageToFile("InProgress.png");
		} catch (IOException e) {
			Logger.log("Failed to save image cache!", TileDev.class ,LogState.Error);
		}
	}

	public static void Export() {
		
		/*
		 * TODO 
		 * input validation
		 * rename / replace existing
		 * retry on fail
		 * 
		 *  
		 */
		String Texture = JOptionPane.showInputDialog("What is the texture called? [/Resource/Tiles/]");
	 	File file = new File("./ExportedTiles/" + Texture + "/");
		 
		if (file.exists()) {
			JOptionPane.showMessageDialog(ClientWindow.window, "That tile already exists in export folder!");
			return;
		}
		 
		 try {
		 Boolean Walkable = getResult("Can the player walk on this tile?");
		 
		 Boolean CausesDamage = getResult("Walking on this tile causes the player damage?");
		 int DamageMultiplyer = 0;
		 if (CausesDamage) {
			 DamageMultiplyer = Integer.parseInt(JOptionPane.showInputDialog("How much damage is caused?"));
		 }
		 
		 Boolean IsFluid = getResult("This tile is a fluid?");
		 Boolean IsHarvestable = getResult("Can this tile be harvested?");
		 Boolean CausesEvent = getResult("Does walking on this tile cause an event?");
		 
		 int Hardness = Integer.parseInt(JOptionPane.showInputDialog("What is the dig hardness level? (250Milliseconds * Hardness) = mine time"));
		 
				file.mkdirs();
				file = new File("./ExportedTiles/" + Texture + "/" + Texture + ".java");
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				
				writer.append("package com.shinkson47.SplashX5.Game.Tiles;");
				writer.append(System.lineSeparator() + "import java.awt.Image;");
				writer.append(System.lineSeparator() + "");
				writer.append(System.lineSeparator() + "import com.shinkson47.SplashX5.Game.Resources.ResourceManager;");
				writer.append(System.lineSeparator() + "import com.shinkson47.SplashX5.Interfaces.ITile;");
				writer.append(System.lineSeparator() + "");
				writer.append(System.lineSeparator() + "public class Grass1 implements ITile {");
				writer.append(System.lineSeparator() + "	public static Image Texture = ResourceManager.getTexture(\"Tiles/" + Texture + "\");");
				writer.append(System.lineSeparator() + "	public static boolean Walkable = " + Walkable.toString() + ", CausesDamage = " + CausesDamage.toString() + ", IsFluid = " + IsFluid.toString() + ", IsHarvestable =" + IsHarvestable.toString() + ", CausesEvent = " + CausesEvent.toString() + ";");

				writer.append(System.lineSeparator() + "	public static int DamageMulter = " + String.valueOf(DamageMultiplyer) + " , Hardness = " + Hardness + ";");
				
				writer.append(System.lineSeparator() + "	public static String TileData = \"\";");
				writer.append(System.lineSeparator() + "}");
				writer.close();
				
				saveImageToFile(Texture + "/" + Texture + ".png");
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(ClientWindow.window, "Failed to create the texture! : " + e.getMessage());
				return;
			}
		 Logger.log("Exported texture", TileDev.class, LogState.Info);
		 JOptionPane.showMessageDialog(ClientWindow.window, "Exported new tile!");
		 
		 try {
			Desktop.getDesktop().open(new File("./ExportedTiles/" + Texture + "/"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		}
		 
		 
	private static void saveImageToFile(String Texture) throws IOException {
		File file = new File("./ExportedTiles/");
		
		if (!file.exists()) {
			file.mkdir();
		}
		
		file = new File("./ExportedTiles/" + Texture);
		BufferedImage bufferedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < 64; x++) {
		    for (int y = 0; y < 64; y++) {
		    	Color color = null;
		    	if (TileColour[x][y] != BackgroundColor) {
		    		color = TileColour[x][y];
		    		bufferedImage.setRGB(x, y, color.getRGB());
		    	} else {
		    		bufferedImage.setRGB(x, y, Transparency.TRANSLUCENT);
		    	}

		        
		    }
		}
		ImageIO.write(bufferedImage, "png", file);
	}
	
	private static boolean getResult(String string) {
		int result = JOptionPane.showConfirmDialog(ClientWindow.window,string, "Export",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
	            if(result == JOptionPane.YES_OPTION){
	              return true;
	            }else {
	            	return false;
	            }
	}

	public static int MouseX = 1, MouseY = 1;
	public static void mouseMoved(MouseEvent event) {
		Point location = event.getPoint();
		location.x -= 3;
		location.y -= 26;
		
		MouseX = location.x;
		MouseY = location.y;
		
		
		
		if ((location.x >= Indent + Spacing) && (location.x <= ((TileSize + Spacing) * 64) + Indent)) {
			if ((location.y >= Indent + Spacing) && (location.y <= ((TileSize + Spacing) * 67) + Indent)) {
				CursorX = getGridPos(location.x);
				CursorY = getGridPos(location.y + 2);
			}
		
		}
}
	public static int getGridPos(int x) {
		return ((x / (TileSize + 1)) - 1);
	}
	
	public static void RenderFrame() {
		Graphics graphics = ClientRenderer.graphics;
		ClientRenderer.FrameUpdated = true;
		
		
		for (int x = 0; x <= 63; x++) {
			for (int y = 0; y <= 63; y++) {
				graphics.setColor(TileDev.TileColour[x][y]);
				graphics.fillRect((x * (TileDev.TileSize + TileDev.Spacing)) + TileDev.Indent, (y * (TileDev.TileSize + TileDev.Spacing)) + TileDev.Indent + 24, TileDev.TileSize, TileDev.TileSize);
			}
		}
		
		graphics.setColor(Color.WHITE);
		graphics.drawRect((TileDev.CursorX * (TileDev.TileSize + 1)) + TileDev.Indent, ((TileDev.CursorY + 2) * (TileDev.TileSize + 1)) + TileDev.Indent, (TileDev.TileSize + 1) * TileDev.CursorMultiplyer, (TileDev.TileSize + 1) * TileDev.CursorMultiplyer);
		
		for (int x = 0; x <= 9; x++) {
				graphics.setColor(TileDev.Pallette[x]);
				graphics.fillRect((x * 25 + (64 * (TileDev.TileSize + 1))) + (TileDev.Indent  * 2), (64 * (TileDev.TileSize + 1)) - 24 + TileDev.Indent, 24, 24);
		}
		
		graphics.drawString("Cursor Size: " + TileDev.CursorMultiplyer + " (Use [+] / [-] to alter)", (64 * (TileDev.TileSize + 1)) + TileDev.Indent + 30, 20);
		graphics.drawString("Cursor X: " + TileDev.CursorX, (64 * (TileDev.TileSize + 1)) + TileDev.Indent + 30, 40);
		graphics.drawString("Cursor Y: " + TileDev.CursorY, (64 * (TileDev.TileSize + 1)) + TileDev.Indent + 30, 60);
		graphics.drawString("Mouse X: " + TileDev.MouseX, (64 * (TileDev.TileSize + 1)) + TileDev.Indent + 30, 80);
		graphics.drawString("Mouse Y: " + TileDev.MouseY, (64 * (TileDev.TileSize + 1)) + TileDev.Indent + 30, 100);
		
		
		graphics.setColor(TileDev.Pallette[TileDev.SelectedColor]);
		graphics.fillOval((TileDev.SelectedColor * 25 + (64 * (TileDev.TileSize + 1))) + (TileDev.Indent  * 2) + 6, (64 * (TileDev.TileSize + 1)) - 40 + TileDev.Indent, 10, 10);
		graphics.setColor(Color.gray);
		graphics.drawRect((TileDev.SelectedColor * 25 + (64 * (TileDev.TileSize + 1))) + (TileDev.Indent  * 2), (64 * (TileDev.TileSize + 1)) - 24 + TileDev.Indent, 24, 24);
	}
}

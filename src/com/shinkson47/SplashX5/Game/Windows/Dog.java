package com.shinkson47.SplashX5.Game.Windows;

import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Dog {
	static JFrame window = new JFrame();
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public static void init() {
		window.setBounds(0,0,1000,1000);
		Scanner input = new Scanner(System.in);
		
		window.setVisible(true);
		ImageIcon img = new ImageIcon("./Resources/dog.jpg");
		window.getGraphics().drawImage(img.getImage(),0,0,null,null);
		
		input.close();
}}

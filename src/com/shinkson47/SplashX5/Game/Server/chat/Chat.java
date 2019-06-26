package com.shinkson47.SplashX5.Game.Server.chat;

import java.lang.reflect.Field;

import com.shinkson47.SplashX5.Client.Client;
import com.shinkson47.SplashX5.Client.ClientRenderer;
import com.shinkson47.SplashX5.Client.ClientWindow;
import com.shinkson47.SplashX5.Client.Logger;
import com.shinkson47.SplashX5.Game.Entities.Player.Player;
import com.shinkson47.SplashX5.Game.Enumerator.ETiles;
import com.shinkson47.SplashX5.Game.Enumerator.Windows;
import com.shinkson47.SplashX5.Game.Events.EventHandler;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileBase;
import com.shinkson47.SplashX5.Game.Resources.Tiles.TileStack;
import com.shinkson47.SplashX5.Game.Windows.Dog;
import com.shinkson47.SplashX5.Game.World.WorldLoader;

public class Chat {
	public static MessageBase[] log = new MessageBase[25565];
	public static int messagecount = 0;

	public static void newMessage(String message, int id) {
		String sub = message.substring(0, 1);
		if (sub.equals("/"))
			addMessage(ParseCommand(new MessageBase(message, id)), id);
		else
			addMessage(message, id);
	}

	private static void addMessage(String message, int id) {
		log[messagecount] = new MessageBase(message, id);
		messagecount++;
	}

	public static String ParseCommand(MessageBase messageBase) {
		// split the command
		String args[] = messageBase.Message.split(" ");

		// switch to command
		try {
			switch (args[0]) {
			case "/give":
				if (args.length < 2)
					return "'give' requires at least one argument";
				try {
					if (args.length < 4) { // if a player has not been specified
						Player.players[messageBase.PlayerID].inventory.collect(new TileStack(new TileBase(-1, -1, ETiles.valueOf(args[1]), ""), Integer.parseInt(args[2])));
						return "given player " + messageBase.PlayerID + " " + args[2] + " of " + args[1];
					} else { // else give to the specified player
						Player.players[Integer.parseInt(args[3])].inventory.collect(new TileStack(new TileBase(-1, -1, ETiles.valueOf(args[1]), ""), Integer.parseInt(args[2])));
						return "given player " + args[3] + " " + args[2] + " of " + args[1];
					}
				} catch (Exception e) {
					return "'give' failed to parse with these arguments, " + messageBase.Message;
				}
			case "/field":
				if (args.length < 3)
					return "'var' requires at least 'qualified class name' 'varname' ['value']";

				Class<?> varclass = null;
				try {
					varclass = Class.forName("com.shinkson47.SplashX5." + args[1]);
				} catch (Exception e) {
					return "Class cannot be found";
				}

				if (varclass == null)
					return "Class could not be parsed";

				Field field = null;

				try {
					field = varclass.getField(args[2]);
				} catch (Exception e) {
					return "Field cannot be found!";
				}

				if (field == null)
					return "Field could not be parsed";

				if (args.length < 4)
					try {
						return (String) field.get(field);
					} catch (Exception e) {
						return "Could not get value from field";
					}
				else {

					String prevval = null;
					try {
						prevval = (String) field.get(field);
					} catch (Exception e) {
						return "Could not read from field";
					}

					try {
						field.set(field, args[3]);
					} catch (Exception e) {
						return "Could not write to field";
					}

					return "Changed value of " + args[2] + " from " + prevval + " to " + field.get(field);
				}
			case "/ClientCommand":
				try {
					Client.RestartCommandThread();
					return "Restarted command thread";
				} catch (Exception e) {
					return "Failed to restart command thread";
				}

			case "/CauseDog":
				Dog.init();
				return "Enjoy!";

			case "/ForceFocus":
				ClientWindow.DoForceFocus = !ClientWindow.DoForceFocus;
				return String.valueOf(ClientWindow.DoForceFocus);

			case "/DoLogs":
				Logger.DoLogs = !Logger.DoLogs;
				return String.valueOf(Logger.DoLogs);

			case "/DoKeyEvents":
				EventHandler.DoKeyEvent = !EventHandler.DoKeyEvent;
				return String.valueOf(EventHandler.DoKeyEvent);

			case "/window":
				if (args.length < 2)
					return ClientWindow.GetWindow().toString();

				Windows current = ClientWindow.GetWindow();
				try {
					ClientWindow.SetWindow(Windows.valueOf(args[1]));
					return "Switched!";
				} catch (Exception e) {
					ClientWindow.SetWindow(current);
					return "Failed to switch windows correctly";
				}
			case "/CauseBackgroundUpdate":
				ClientRenderer.CauseBackgroundUpdate = true;
				return "An update should occour on the next frame!";

			case "/CauseHalt":
				Client.HaltClient(); // no need to return;
				break;

			case "/CauseCrash":
				Client.CauseCrash = true; // no need to return;
				break;

			case "/ClientVisible":
				ClientWindow.window.setVisible(!ClientWindow.window.isVisible());
				return "Toggled client visibility!";

			case "/Load":
				if (args.length < 2)
					return "'Load' requires 'name'";
				WorldLoader.Load(args[1]);
				return "Parsed load";

			case "/Save":
				if (args.length < 2)
					return "'Save' requires 'name'";
				WorldLoader.Save(args[1]);
				return "Parsed save";

			default:
				return "No such command, " + args[0];
			}

		} catch (Exception e) {
			return "'give' failed to parse with these arguments, " + messageBase.Message;
		}
		return "Did not parse command.";
	}
}

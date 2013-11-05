package com.example.cs356;

import java.io.*;

import android.content.Context;
import android.content.Context;

/**
 * This class saves and loads the game using serialization. Whenever the user quits the game, the
 * game will automatically save. Allowing the user to restart from where he/she
 * left off.
 * 
 */
public class SavingExperiment implements Serializable {
	

static Context fileContext;

	public static void test(Scoreboard game) throws IOException {
		FileOutputStream fos = fileContext.openFileOutput("fileName.txt", Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(game);
		os.close();
	}
	
	
	public static void fileWrite(Scoreboard game) throws IOException {
		FileOutputStream fos = new FileOutputStream("save.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(game);
		oos.flush();
		oos.close();
	}

	/**
	 * Writes the GameSetup object from the file {@code "save.dat"}.
	 * 
	 * @throws IOException
	 */
	public static Scoreboard fileRead() throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream("save.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Scoreboard game = (Scoreboard) ois.readObject();
		return game;
	}
}
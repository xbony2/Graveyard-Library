package ibittech.graveyard.log;

import ibittech.graveyard.Graveyard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Used to log through both the console and a log file. 
 * Remember to set the directory/name of the log!
 * 
 * It's pretty straight-forward, but feel free to look at
 * examples in xbony2's test package.
 * 
 * @author xbony2
 *
 */
public class GLog {

	/**
	 * Sets your prefix. This should be done on startup.
	 * 
	 * @param pref should be something like the name of your Program or an abbreviation, 
	 * without spaces, between 2-20 characters.
	 * For example, a Tic-Tac-Toe program could be "TicTacToe".
	 * A game named World Of Warcraft could be "WoW".
	 * A program named Celsus to Kalvin could be "CelsusToKalvin".
	 * 
	 * @author xbony2
	 */
	public GLog(String pref){
		if(isFirst){
			GLog.deleteLog();
			GLog.isFirst = false;
		}
		
		if(pref == null){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Prefix is null!");
			this.isDisabled = true;
			return;
		}
		
		boolean isUsed = false;
		
		for(int i = 1; i >= names.size(); i++){
			if(names.get(i) == pref){
				System.out.println("[" + Graveyard.NAME + "][ERROR] Prefix name already in use!");
				isUsed = true;
				this.isDisabled = true;
				return;
			}
		}
		
		if(pref.length() <= 2){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Prefix name is too short! Must be longer then 2 " +
					"characters! Attemped name: ");
			System.out.print(pref);
			this.isDisabled = true;
			return;
		}
		
		if(pref.length() >= 20){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Prefix name is too long! Must be under 20 characters!");
			this.isDisabled = true;
			return;
		}
		
		if(!isUsed) prefix = "[" + pref + "]";
		
	}
	
	/**
	 * The prefix.
	 */
	private String prefix;
	
	/**
	 * List of reserved names.
	 */
	private static List<String> names = Arrays.asList("Graveyard", "Java");
	
	/**
	 * True if the directory is already set.
	 */
	private static boolean isDirectorySet;
	
	/**
	 * True if this is the first prefix made.
	 */
	private static boolean isFirst = true;
	
	/**
	 * The directory (and name) for the log file.
	 */
	private static String directory = "log";
	
	private boolean isDisabled = false;
	
	/**
	 * Sets the directory and name of the log file.
	 * 
	 * @param dir is the file's name and directory. On default, the log file is
	 * simply "log" which can also be called "src/log" If I wanted to generate
	 * the log file in this very package, I'd input "src/ibittech/graveyard/log/log.
	 * If I wanted it to have a different extension or name, I can change that 
	 * as well, ex: "src/ibittech/graveyard/log/logger.txt" will make a file
	 * named "logger.txt" in this very package.
	 * 
	 * @author xbony2
	 */
	public static void setDirectory(String dir){
		if(isDirectorySet){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Log directory already set!");
		}else{
			if(dir == null){
				System.out.println("[" + Graveyard.NAME + "][ERROR] Directory String is null!");
				return;
			}
			directory = dir;
			isDirectorySet = true;
		}
	}
	
	/**
	 * Deletes the log automatically created by this class. Happens on startup.
	 */
	private static void deleteLog(){
		File log = new File(directory);
		
		if(!log.exists()){
			System.out.println("[" + Graveyard.NAME + "][ERROR] deleteLog was called, but no log exists!");
			return;
		}
		log.delete();	
	}
	
	/**
	 * @return number of used prefixes, always at least two (java + graveyard)
	 * 
	 * @author xbony2
	 */
	public static int getNumberOfUsedPrefixes(){
		return names.size();
	}
	
	/**
	 * To log ordinary messages, EX. a startup phases.
	 * 
	 * @param message the message you want to output.
	 * 
	 * @author xbony2
	 */
	public void log(String message){
		if(this.isDisabled) return;
		if(message == null){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Message is null! Caused by: " + this.prefix);
			return;
		}
		System.out.println(prefix + " " + message);
		
		File log = new File(directory);
		try{
			if(!log.exists()){
				System.out.println("[" + Graveyard.NAME + "] New log being created.");
				log.createNewFile();
			}
	    	
			PrintWriter out = new PrintWriter(new FileWriter(log, true));
			out.println(prefix + " " + message);
			out.close();
		}catch(IOException e){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Could not make file!");
			e.printStackTrace();
		}
	}
	
	/**
	 * To log an error, like if a value isn't what it should be (Ex: Let's say
	 * a byte was used to represent who's turn it was in a chess game, 1 for
	 * the computer, 0 for the player, but the value was 2).
	 * 
	 * @param message the message you want to output.
	 * 
	 * @author xbony2
	 */
	public void logError(String message){
		if(this.isDisabled) return;
		if(message == null){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Message is null! Caused by: " + this.prefix);
			return;
		}
		System.out.println(prefix + "[ERROR] " + message);
		
		File log = new File(directory);
		try{
			if(!log.exists()){
				System.out.println("[" + Graveyard.NAME + "] New log being created.");
				log.createNewFile();
			}
	    	
			PrintWriter out = new PrintWriter(new FileWriter(log, true));
			out.println(prefix + "[ERROR] " + message);
			out.close();
		}catch(IOException e){
			System.out.println("[" + Graveyard.NAME + "][ERROR] Could not make file!");
			e.printStackTrace();
		}
	}
}

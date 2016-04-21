/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import acm.util.*;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	
	public NameSurferDataBase(String filename) {
		try{
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				NameSurferEntry entertedName = new NameSurferEntry(line);
				namemap.put(entertedName.getName(), entertedName);
			}
			rd.close();
		} catch(IOException ex) {
				throw new ErrorException(ex);
			}
		}

	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		String entertedname=upper2lower(name);
		if(namemap.containsKey(entertedname)) {
			return namemap.get(entertedname);
		}
		else{
			return null;
		}
	}
	
	private String upper2lower(String name){
		char letter = name.charAt(0);
		String remainingAlphabets = name.substring(1);
		String lowercase=remainingAlphabets.toLowerCase();
		letter=Character.toUpperCase(letter);
		String fullname=letter+lowercase;
		return (fullname);
	}
	private Map <String, NameSurferEntry> namemap = new HashMap <String, NameSurferEntry>(); 
}


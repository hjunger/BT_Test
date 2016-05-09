package bt;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class BtDependencies {
	// Attributes
		private Hashtable<String, List<String>> packDependencies; // represents the rules in the file
		private Hashtable<String, List<String>> dependencies; // represents the final results 
		private String file; // path + file
		
		//Constructor
		public BtDependencies() {
			// TODO Auto-generated constructor stub
			packDependencies = new Hashtable<String, List<String>>();
			dependencies = new Hashtable<String, List<String>>();
		}
		
		
		// Methods
		/*
		 * Method to read the file and insert the rules in a HashTable
		 */
		public boolean readFile(String fileName){
			file = fileName;
			
			// Reads the whole file
			try {
				//Reads the file
				URL url = BtDependencies.class.getResource(file);
				File f = new File(url.toURI());
				Scanner input = new Scanner(f);
				
				// For each line it will break and insert in the Dictionary
				while(input.hasNextLine()){
					// Ignores lines without anything
					String line = input.nextLine();
					if(line != null && !line.isEmpty()){
						String[] parts = line.split("->");
						// If doesn't get two parts the line is invalid
						if(parts.length != 2){
							input.close();
							return false;
						}
						
						String keyPackage = parts[0].replaceAll("\\s+","");
						// If the package name doesn't exist the line is invalid
						if(keyPackage == null || keyPackage.isEmpty()){
							input.close();
							return false;
						}
						
						// Get the dependencies for this package
						List<String> dependencies = new ArrayList<String>();
						String[] deps = parts[1].split("\\s+");
						for(String dep : deps){
							if(dep != null && !dep.isEmpty())
								dependencies.add(dep);
						}
						
						// Insert in the Hashtable with the rules
						packDependencies.put(keyPackage, dependencies);
					}
				}
				// Closes the file
				input.close();

			} catch (Exception e) {
				// We could here save some log file
				//e.printStackTrace();
				return false;
			}
			
			return true;
		}
		
		/*
		 * Method that searches all the dependencies for one package and returns a string like: package -> list of dependencies
		 */
		public String getDependencies(String key){
			// Return, put the package
			String dependencyLine = key + " ->";
			
			// Checks if the Key has any dependency
			boolean hasDependencies = packDependencies.containsKey(key);
			// If it hasn't calculate yet and it has dependencies
			if(!dependencies.containsKey(key)&&hasDependencies){
				// Updates the result HashTable
				updateAllDependenciesForKey(key);
			}
			
			// If it has dependencies put them in the String
			if(hasDependencies){
				for(String depItem : dependencies.get(key)){
					dependencyLine = dependencyLine + " " + depItem;
				}
			}
			// Return the string
			return dependencyLine;
		}
		
		/*
		 * Method to update the Result HashTable
		 */
		private void updateAllDependenciesForKey(String key){
			// Instantiate the dependencies list
			List<String> dependenciesForKey = new ArrayList<String>();
			// If already exists, remove from the HashTable
			if(dependencies.containsKey(key))
				dependencies.remove(key);
			// Insert the result in the table
			dependencies.put(key, dependenciesForKey);
			
			// Gets all the dependencies for this key
			List<String> nextKeys = packDependencies.get(key); 
			for(String nextKey : nextKeys){
				// Checks if the result has already been processed
				boolean alreadyDone = dependencies.containsKey(nextKey);
				// Checks if has dependencies
				boolean hasDepen = packDependencies.containsKey(nextKey);
				
				// If this key has any dependency AND it hasn't been calculated yet
				if(hasDepen&&!alreadyDone){ 
					// Update the HashTable for the sub-results
					updateAllDependenciesForKey(nextKey);
				}
				
				if(hasDepen){
					// For each sub-result
					for(String dep : dependencies.get(nextKey)){
						// If it isn't in the result yet and it's not circular (equal to the key)
						if(!dependenciesForKey.contains(dep)&&!dep.equals(key))
							// Insert in the result
							dependenciesForKey.add(dep);
					}
				}
				
				// If the next key hasn't been inserted yet, put it in the table
				if(!dependenciesForKey.contains(nextKey))
					dependenciesForKey.add(nextKey);
			}
			// Sorts the values
			Collections.sort(dependenciesForKey);
		}
		
		// GETs and SETs
		public Hashtable<String, List<String>> getPackages(){
			return packDependencies;
		}
}

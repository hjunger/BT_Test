package bt;

/*
import java.util.List;
import java.util.Set;
*/

public class BtMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length >= 2){
			BtDependencies bt = new BtDependencies();
			// Read the file
			if(bt.readFile(args[0])){
				// Small test, after read the file check if the Hashtable is ok
				//testFileReader(bt);
				
				// Get the a String of dependencies
				for(int i = 1; i < args.length; i++){
					System.out.println(bt.getDependencies(args[i]));
				}
			}else{
				System.out.println("Ops, there is an invalid line in your file.");
			}
		}else{
			System.out.println("Invalid parameters. You need to pass at least the file name and one package.");
		}
	}
	
	/* TEST */
	/*
	private static void testFileReader(BtDependencies bt){
		Set<String> keys = bt.getPackages().keySet(); 
		for(String key : keys){
			System.out.print(key + " : ");
			List<String> deps = bt.getPackages().get(key);
			for(String dep : deps){
				System.out.print(dep + " - ");
			}
			System.out.println();
		}
	}
	*/

}

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoyerMooreTest {
	
	public static void main(String[] args) {
		if (args.length == 0) { // Perform default behavior: print test cases
			 
			
			// Test Case 1: Basic Test
	        testBoyerMoore("ABC", "ABCDAB", "Basic Test");
	        
	        // Test Case 2: Pattern at the Beginning
	        testBoyerMoore("ABC", "ABCDEF", "Pattern at the Beginning");
	      
	        // Test Case 3: Pattern at the End
	        testBoyerMoore("ABC", "XYZABC", "Pattern at the End");
	        
	        // Test Case 4: Multiple Occurrences
	        testBoyerMoore("AB", "ABABAB", "Multiple Occurrences");
	        
	        // Test Case 5: Pattern Not Found	       
	        testBoyerMoore("ABC", "XYZ", "Pattern Not Found");
	        
	        // Test Case 6: Empty String
	        testBoyerMoore("ABC", "", "Empty String");

	        // Test Case 7: Empty Pattern
	        testBoyerMoore("", "XYZ", "Empty Pattern");
	        
	        // Test Case 8: Longer text
	        testBoyerMoore("rab", "abacadabrabracabracadabrabrabracad", "Longer Text");
	        
           
        } else {

            String pattern = args[0];
            String text = args[1];
            
            if(isTxtFile(text)) {
            	readFileContents(pattern, text);
            } else {
               
                testBoyerMoore(pattern, text, "Boyer-Moore Result");
            }
        }
    
    }

	private static boolean isTxtFile(String filename) {
		return filename.toLowerCase().endsWith(".txt");
	}
	
	private static void readFileContents(String pattern, String filename) {
		System.out.println("Filename: " + filename);
		try {
	           
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            String content = scan.useDelimiter("\\A").next();
            scan.close();
            
            testBoyerMoore(pattern, content, "Boyer-Moore Result");
         
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
	}
	

    private static void testBoyerMoore(String pattern, String text, String testName) {
        System.out.println("------ " + testName + " ------");
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
		
		long usedMemoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long start = System.currentTimeMillis();
		
        List<Integer> result = BoyerMoore.stringSearch(pattern, text);
        long end = System.currentTimeMillis();
        long usedMemoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
        long timeTaken = end - start;
        long memoryUsed = usedMemoryAfter - usedMemoryBefore;
		
        
        if(!result.isEmpty()) {
        	System.out.println("Pattern found at index: " + result);
        } else {
        	System.out.println("Pattern not found.");
        }
        System.out.println("Elapsed Time in milli seconds: "+ timeTaken);
        System.out.println("Memory used: " + memoryUsed + " bytes");
        System.out.println();
    }
    
    
}

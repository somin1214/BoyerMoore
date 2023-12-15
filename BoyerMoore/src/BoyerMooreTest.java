import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoyerMooreTest {
	
	public static void main(String[] args) {
		if (args.length == 0) { // Perform default behavior: print test cases
			 
			
			// Test Case 1: Basic Test
			long start1 = System.currentTimeMillis();
	        testBoyerMoore("ABC", "ABCDAB", "Basic Test");
	        long end1 = System.currentTimeMillis();      
	        System.out.println("Elapsed Time in milli seconds: "+ (end1-start1));

	        // Test Case 2: Pattern at the Beginning
	        long start2 = System.currentTimeMillis();      
	        testBoyerMoore("ABC", "ABCDEF", "Pattern at the Beginning");
	        long end2 = System.currentTimeMillis();      
	        System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
	        
	        // Test Case 3: Pattern at the End
	        long start3 = System.currentTimeMillis();
	        testBoyerMoore("ABC", "XYZABC", "Pattern at the End");
	        long end3 = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end3-start3));

	        // Test Case 4: Multiple Occurrences
	        long start4 = System.currentTimeMillis();
	        testBoyerMoore("AB", "ABABAB", "Multiple Occurrences");
	        long end4 = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end4-start4));

	        // Test Case 5: Pattern Not Found
	        long start5 = System.currentTimeMillis();
	        testBoyerMoore("ABC", "XYZ", "Pattern Not Found");
	        long end5 = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end5-start5));

	        // Test Case 6: Empty String
	        long start6 = System.currentTimeMillis();
	        testBoyerMoore("ABC", "", "Empty String");
	        long end6 = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end6-start6));

	        // Test Case 7: Empty Pattern
	        long start7 = System.currentTimeMillis();
	        testBoyerMoore("", "XYZ", "Empty Pattern");
	        long end7 = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end7-start7));
	        
	        // Test Case 8: Longer text
	        long start8 = System.currentTimeMillis();
	        testBoyerMoore("rab", "abacadabrabracabracadabrabrabracad", "Longer Text");
	        long end8 = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end8-start8));
            
        } else {

            String pattern = args[0];
            String text = args[1];
            
            if(isTxtFile(text)) {
            	readFileContents(pattern, text);
            } else {
                long start = System.currentTimeMillis();
                testBoyerMoore(pattern, text, "Boyer-Moore Result");
                long end = System.currentTimeMillis();
    	        System.out.println("Elapsed Time in milli seconds: "+ (end-start));
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
            
            long start = System.currentTimeMillis();
            testBoyerMoore(pattern, content, "Boyer-Moore Result");
            long end = System.currentTimeMillis();
	        System.out.println("Elapsed Time in milli seconds: "+ (end-start));
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
	}
	
    private static void testBoyerMoore(String pattern, String text, String testName) {
        System.out.println("------ " + testName + " ------");
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);


        List<Integer> result = BoyerMoore.stringSearch(pattern, text);
        
        if(!result.isEmpty()) {
        	System.out.println("Pattern found at index: " + result);
        } else {
        	System.out.println("Pattern not found.");
        }
 
        System.out.println();
    }
    
    
}

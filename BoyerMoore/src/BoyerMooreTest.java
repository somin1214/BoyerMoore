import java.util.List;

public class BoyerMooreTest {
	
	public static void main(String[] args) {
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

        // Additional Test Cases:
        // Feel free to add more test cases based on your preferences.

        // Test Case 6: Empty String
        testBoyerMoore("ABC", "", "Empty String");

        // Test Case 7: Empty Pattern
        testBoyerMoore("", "XYZ", "Empty Pattern");
        
//        List<String> texts = List.of(
//    			"GCTAGCTCTACGAGTCTA",
//    		    "GGCTATAATGCGTA",
//    		    "there would have been a time for such a word",
//    		    "needle need noodle needle",
//    		    "DKnuthusesandshowsanimaginarycomputertheMIXanditsassociatedmachinecodeandassemblylanguagestoillustrate",
//    		    "Farms grew an acre of alfalfa on the dairy's behalf, with bales of that alfalfa exchanged for milk.");
//    		         
//    		List<String> patterns = List.of( "TCTA", "TAATAAA", "word", "needle", "put", "and", "alfalfa" );
//    		
//    		for ( int i = 0; i < texts.size(); i++ ) {
//    			System.out.println("text" + ( i + 1 ) + " = " + texts.get(i));
//    		}
//    		System.out.println();
//    		
//    		for ( int i = 0; i < patterns.size(); i++ ) {
//    			final int j = ( i < 5 ) ? i : i - 1;		
//    			System.out.println("Found \"" + patterns.get(i) + "\" in 'text" + ( j + 1 ) + "' at indexes "
//    				+ stringSearch(texts.get(j), patterns.get(i)));
//    		}
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class BoyerMoore {
	
	/**
	 * Return a list of indexes at which the given pattern matches the given text.
	 */	
	public static List<Integer> stringSearch(String pattern, String text) {	    
	    if ( pattern.isEmpty() || text.isEmpty() || text.length() < pattern.length() ) {
	        return Collections.emptyList();
	    }

	    List<Integer> matches = new ArrayList<Integer>();

	    // Preprocessing
	    List<List<Integer>> R = badCharacterTable(pattern);
	    int[] L = goodSuffixTable(pattern);
	    int[] F = fullShiftTable(pattern);

	    int k = pattern.length() - 1; 				//alignment of the end of pattern relative to text
	    int prevK = -1; 							//alignment in the previous phase
	    while ( k < text.length() ) {
	        int i = pattern.length() - 1; 			// Index of char to compare in pattern
	        int h = k; 								// Index of char to compare in text
	        while ( i >= 0 && h > prevK && pattern.charAt(i) == text.charAt(h) ) {
	            i -= 1;
	            h -= 1;
	        }
	        if ( i == -1 || h == prevK ) { 			// Match has been found
	            matches.add(k - pattern.length() + 1);
	            k += ( pattern.length() > 1 ) ? pattern.length() - F[1] : 1;
	        } else { 								// No match, shift by the maximum of the bad character and good suffix rules
	        	int suffixShift;
	            int charShift = i - R.get(alphabetIndex(text.charAt(h))).get(i);
	            if ( i + 1 == pattern.length() ) { 	// Mismatch occurred on the first char
	                suffixShift = 1;
	            } else if ( L[i + 1] == -1 ) { 		// Matched suffix does not appear anywhere in pattern
	                suffixShift = pattern.length() - F[i + 1];
	            } else { 							// Matched suffix appears in pattern
	                suffixShift = pattern.length() - 1 - L[i + 1];
	            }
	            int shift = Math.max(charShift, suffixShift);
	            if ( shift >= i + 1 ) { 			// Galil's rule
	            	prevK = k;
	            }
	            k += shift;
	        }
	    }
	    return matches;
	}
	
    /**
     * Create the shift table, F, for the given text, which is an array such that
     * F[i] is the length of the longest suffix of, aText.substring(i), which is also a prefix of the given text.
     * 
     * Use case: If a mismatch occurs at character index i - 1 in the pattern,
     * the magnitude of the shift of the pattern, P, relative to the text is: P.length() - F[i].
     */ 
	private static int[] fullShiftTable(String pattern) {	    
	    int[] F = new int[pattern.length()];
	    int[] Z = fundamentalPreprocess(pattern);
	    int longest = 0;
	    Collections.reverse(Arrays.asList(Z));
	    for ( int i = 0; i < Z.length; i++ ) {
	    	int zv = Z[i];	
	    	if ( zv == i + 1 ) {
	    		longest = Math.max(zv, longest);
	    	}
	        F[F.length - i - 1] = longest;
	    }
	    return F;
	}
	
	/**
     * Create the good suffix table, L, for the given text, which is an array such that
     * L[i] = k, is the largest index in the given text, S,
     * such that S.substring(i) matches a suffix of S.substring(0, k).
     * 
     * Use case: If a mismatch of characters occurs at index i - 1 in the pattern, P,
     * then a shift of magnitude, P.length() - L[i], is such that no instances of the pattern in the text are omitted. 
     * Furthermore, a suffix of P.substring(0, L[i]) matches the same substring of the text that was matched by a
     * suffix in the pattern on the previous matching attempt.
     * In the case that L[i] = -1, the full shift table must be used.
     */
	private static int[] goodSuffixTable(String pattern) {
		int[] L = IntStream.generate( () -> -1 ).limit(pattern.length()).toArray();
	    String reversed = new StringBuilder(pattern).reverse().toString();
	    int[] N = fundamentalPreprocess(reversed);
	    Collections.reverse(Arrays.asList(N));
	    for ( int j = 0; j < pattern.length() - 1; j++ ) {
	        int i = pattern.length() - N[j];
	        if ( i != pattern.length() ) {
	            L[i] = j;
	        }
	    }
	    return L;
	}
	
	/**
     * Create the bad character table, R, for the given text,
     * which is a list indexed by the ASCII value of a character, C, in the given text.
     * 
     * Use case: The entry at index i of R is a list of size: 1 + length of the given text.
     * This inner list gives at each index j the next position at which character C will be found
     * while traversing the given text from right to left starting from index j.
     */
	private static List<List<Integer>> badCharacterTable(String pattern) {	    
	    if ( pattern.isEmpty() ) {
	        return Collections.emptyList();
	    }
	    
	    List<List<Integer>> R = IntStream.range(0, ALPHABET_SIZE).boxed()
	    	.map( i -> new ArrayList<Integer>(Collections.nCopies(1,-1)) ).collect(Collectors.toList());
	    List<Integer> alpha = new ArrayList<Integer>(Collections.nCopies(ALPHABET_SIZE, -1));
	    
	    for ( int i = 0; i < pattern.length(); i++ ) {
	        alpha.set(alphabetIndex(pattern.charAt(i)), i);
	        for ( int j = 0; j < alpha.size(); j++ ) {
	            R.get(j).add(alpha.get(j));
	        }
	    }
	    return R;
	}
	
	/**
	 * Create the fundamental preprocess, Z, of the given text, which is an array such that
     * Z[i] is the length of the substring of the given text beginning at index i which is also a prefix of the text.
     */
	private static int[] fundamentalPreprocess(String pattern) {	    
	    if ( pattern.isEmpty() ) {
	        return new int[0];
	    }
	    if ( pattern.length() == 1 ) {
	        return new int[] { 1 };
	    }
	    
	    int[] Z = new int[pattern.length()];	
	    Z[0] = pattern.length();
	    Z[1] = matchLength(pattern, 0, 1);
	    for ( int i = 2; i <= Z[1]; i++ ) {  
	        Z[i] = Z[1] - i + 1;
	    }
	    
	    // Define the left and right limits of the z-box
	    int left = 0;
	    int right = 0;
	    for ( int i = 2 + Z[1]; i < pattern.length(); i++ ) {
	        if ( i <= right ) { 					// i falls within existing z-box
	            final int k = i - left;
	            final int b = Z[k];
	            final int a = right - i + 1;
	            if ( b < a ) { 						// b ends within existing z-box
	                Z[i] = b;
	            } else { 							// b ends at or after the end of the z-box,
	            		 							// an explicit match to the right of the z-box is required
	                Z[i] = a + matchLength(pattern, a, right + 1);
	                left = i;
	                right = i + Z[i] - 1;
	            }
	        } else { 								// i does not fall within existing z-box
	            Z[i] = matchLength(pattern, 0, i);
	            if ( Z[i] > 0 ) {
	                left = i;
	                right = i + Z[i] - 1;
	            }
	        }
	    }	    
	    return Z;
	}
	
	/**
	 * Return the length of the match of the two substrings of the given text beginning at each of the given indexes.
	 */ 
	private static int matchLength(String pattern, int aIndexOne, int aIndexTwo) {	    
	    if ( aIndexOne == aIndexTwo ) {
	        return pattern.length() - aIndexOne;
	    }
	    
	    int matchCount = 0;
	    while ( aIndexOne < pattern.length() && aIndexTwo < pattern.length() 
	    	    && pattern.charAt(aIndexOne) == pattern.charAt(aIndexTwo) ) {
	        matchCount += 1;
	        aIndexOne += 1;
	        aIndexTwo += 1;
	    }
	    return matchCount;
	}	
	
	/**
	 * Return the ASCII index of the given character, if it is such, otherwise throw an illegal argument exception.
	 */ 
	private static int alphabetIndex(char c) {		
		final int result = (int) c;
		if ( result >= ALPHABET_SIZE ) {
			throw new IllegalArgumentException("Not an ASCII character:" + c);
		}
		return result;
	} 	

	/* The range of ASCII characters is 0..255, both inclusive. */
	private static final int ALPHABET_SIZE = 256;
	
}
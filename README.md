# CS335 Final Project

This is a documentation on using the provided ‘BoyerMoore’ class and the associated test class 'BoyerMooreTest'. 
This assumes that you have a standard Java development environment set up. 

## 1. Compile the Code:
Open a terminal or command prompt.
Navigate to the directory containing the project files, 'BoyerMoore.java' and 'BoyerMooreTest.java'.
Compile the code by running the following commands:
```
javac BoyerMoore.java
javac BoyerMooreTest.java
```

This will generate the corresponding '.class' files.

## 2. Run the BoyerMooreTest Class:
To execute the provided test cases, run the following command:
```
java BoyerMooreTest
java BoyerMooreTest pattern text
java BoyerMooreTest pattern text.txt
```
The test cases will be executed, and the output will be displayed in the console.
The output will show information about each test case, including the text, pattern, and the result of the pattern search.
```
java BoyerMooreTest rab abacadabrabracabracadabrabrabracad
------ Boyer-Moore Result ------
Text: abacadabrabracabracadabrabrabracad
Pattern: rab
Pattern found at index: [8, 23, 26]
```

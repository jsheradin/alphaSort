//Data structure and methods for sorting a dictionary file and determining if a word is contained in it
//This is by far not the best way to do this but I wanted to practice tree structures and recursion

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class alphaTree {
    ArrayList<alphaBlock> tree; //Head of data structure

    //Test method
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();

        alphaTree test = new alphaTree(args[0]);

        //for(int i=0; i<test.tree.size(); i++){
        //    System.out.printf("%s\n", test.tree.get(i).getLetter());
        //}

        System.out.printf("First level size: %d\n", test.tree.size());

        String testWord = "programming";
        System.out.printf("Testing isWord with: \"%s\", %d\n", testWord, test.isWord(testWord));

        System.out.printf("Execution time: %d ms\n", System.currentTimeMillis() - startTime);
    }

    //Builds new tree from file
    public alphaTree(String file){
        ArrayList<alphaBlock> tree = new ArrayList<alphaBlock>();
        ArrayList<String> words = readList(file);

        ArrayList<Character> startingLetters = lettersAt(0, words);

        for(int i=0; i<startingLetters.size(); i++){
            Character letter = startingLetters.get(i);
            tree.add(buildHelper(letter, 0, startingWith(letter, words)));
        }

        this.tree = tree;
    }

    //Get tree element and recursively build the next letter array lists
    private alphaBlock buildHelper(Character letter, int depth, ArrayList<String> words){
        if(words.size() == 0 || words == null || letter == null){
            return null;
        }

        //System.out.printf("Letter: %s, Depth: %d\n", letter, depth);

        Boolean isWord = false;
        for(int i=0; i<words.size(); i++){
            if(words.get(i).length() == 0){
                isWord = true;
                //System.out.printf("End found, depth: %d\n", depth);
                break;
            }
        }

        ArrayList<alphaBlock> next = new ArrayList<alphaBlock>();

        ArrayList<Character> startingLetters = lettersAt(0, words);

        for(int i=0; i<startingLetters.size(); i++){
             Character startingLetter = startingLetters.get(i);
             ArrayList<String> wordsStartingWith = startingWith(startingLetter, words);
             next.add(buildHelper(startingLetter, depth+1, wordsStartingWith));
        }

        alphaBlock block = new alphaBlock(letter, depth, isWord, next);
        return block;
    }

    //Reads a file containing words into an ArrayList
    private static ArrayList<String> readList(String file){
        Scanner scan = null;
        try {
            scan = new Scanner(new File(file));
        } catch (Exception e){
            System.err.printf("File read error: %s\n", e);
            System.exit(1);
        }

        ArrayList words = new ArrayList<String>();
        while(scan.hasNextLine()){
            words.add(scan.next());
        }
        scan.close();

        return words;

    }

    //Gets letters at index from list of strings
    private static ArrayList<Character> lettersAt(int index, ArrayList<String> words) {
        ArrayList<Character> chars = new ArrayList<Character>();

        for (int i=0; i<words.size(); i++){
            String word = words.get(i);
            if(word.length() > index){
                if(!chars.contains(word.charAt(index))){
                    chars.add(word.charAt(index));
                }
            }
        }

        return chars;
    }

    //Returns remaining letters of words that start with letter
    private static ArrayList<String> startingWith(Character letter, ArrayList<String> words){
        ArrayList<String> matches = new ArrayList<String>();

        for(int i=0; i<words.size(); i++){
            String word = words.get(i);
            if(word.length() > 0) {
                if (word.charAt(0) == letter) { //.equals??
                    matches.add(word.substring(1));
                }
            }
        }

        return matches;
    }

    //Calls method with object tree
    public int isWord(String word){
        return isWord(word, tree);
    }

    //Checks if word is in tree through iteration
    //Returns the word's length or -1 if it is not a word
    private int isWord(String word, ArrayList<alphaBlock> tree){
        if(word == "" || word == null || tree.size() == 0 || tree == null){
            return -1;
        }

        for(int i=0; i<tree.size(); i++){
            if(word.charAt(0) == tree.get(i).getLetter()){
                if(word.length() == 1){
                    if(tree.get(i).isWord()){
                        return tree.get(i).getDepth()+1;
                    }
                } else {
                    return isWord(word.substring(1), tree.get(i).getNext());
                }
            }
        }
        return -1;
    }

}

//Letters Problem 7 (0 / 19)
//You are given an array of uppercase letters, in which the letter S appears even number of times. 
//After each S the letter Т appears one or more times. Using a stack, check if after each S (until the next S), 
//if Т appears the same number of times. Input: First line, array of characters (string).
//Output: Print 1 if the condition is fulfilled, otherwise print 0.

Class name: StackBukvi

package Letters;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.Arrays;
        import java.util.NoSuchElementException;
        import java.util.Scanner;

interface Stack<E> {

    // Elementi na stekot se objekti od proizvolen tip.

    // Metodi za pristap:

    public boolean isEmpty ();
    // Vrakja true ako i samo ako stekot e prazen.

    public E peek ();
    // Go vrakja elementot na vrvot od stekot.

    // Metodi za transformacija:

    public void clear ();
    // Go prazni stekot.

    public void push (E x);
    // Go dodava x na vrvot na stekot.

    public E pop ();
    // Go otstranuva i vrakja elementot shto e na vrvot na stekot.
}

class ArrayStack<E> implements Stack<E> {
    private E[] elems;
    private int depth;

    @SuppressWarnings("unchecked")
    public ArrayStack (int maxDepth) {
        // Konstrukcija na nov, prazen stek.
        elems = (E[]) new Object[maxDepth];
        depth = 0;
    }


    public boolean isEmpty () {
        // Vrakja true ako i samo ako stekot e prazen.
        return (depth == 0);
    }


    public E peek () {
        // Go vrakja elementot na vrvot od stekot.
        if (depth == 0)
            throw new NoSuchElementException();
        return elems[depth-1];
    }


    public void clear () {
        // Go prazni stekot.
        for (int i = 0; i < depth; i++)  elems[i] = null;
        depth = 0;
    }


    public void push (E x) {
        // Go dodava x na vrvot na stekot.
        elems[depth++] = x;
    }


    public E pop () {
        // Go otstranuva i vrakja elementot shto e na vrvot na stekot.
        if (depth == 0)
            throw new NoSuchElementException();
        E topmost = elems[--depth];
        elems[depth] = null;
        return topmost;
    }
}



public class StackBukvi {

    public static String[] makeArray(String letters){
        String []splitted;

        splitted = letters.split("");
        return splitted;
    }

    public static int valid(String[] letters){
        boolean oddS = false;
        boolean valid = false;
        ArrayStack Stack;
        Stack = new ArrayStack(100);

        for (int i = 0; i < letters.length; i++) {
            if(letters[i].equals("S")){
                oddS = !oddS;
                valid = true;
            }
            if(valid){
                if(oddS){
                    if (letters[i].equals("T")) {
                        Stack.push(letters[i]);
                    }
                }else{
                    if(letters[i].equals("T")){
                        Stack.pop();
                    }
                }
            }
        }
        if(Stack.isEmpty()) return 1;
        return 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        System.out.println(valid(makeArray(str)));
    }
}

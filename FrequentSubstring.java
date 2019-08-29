/* For a given string, find the most frequent substring. If two substrings have the same frequency, then print the longer one, and if they are the same, print the one higher lexicographically.

Example: For the string "abc" substrings are "a", "b", "c", "ab", "bc", "abc". All have the same frequency, but "abc" is the longest.

Name of the class (Java): MostFrequentSubstring.
*/

package ExercisesForSecondPartialExam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "(" + key + "," + value + ")";
    }
}

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class CBHT<K extends Comparable<K>, E> {

    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {		// Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                curr.element = newEntry;
                return;
            }
        }
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K,E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}

public class MostFrequentSubstring {
    public static void main (String[] args) throws IOException {
        CBHT<String,Integer> tabela = new CBHT<String,Integer>(300);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String word = br.readLine().trim();

        int mostFrequent = 0;
        String mostFrequentSubstring = "";
        String lexi = "";
        int occurrences = 1;
        String substring = "";
        for(int i=0; i<word.length()+1; i++) {
            for (int j = i + 1; j < word.length()+1; j++) {
                substring = word.substring(i, j);
//                System.out.println(substring);
                SLLNode<MapEntry<String, Integer>> temp = tabela.search(substring);

                if(temp != null){
                    tabela.insert(substring, temp.element.value += 1);
                    if(temp.element.value > mostFrequent){
                        mostFrequentSubstring = temp.element.key;
                        mostFrequent = temp.element.value;
                    }
                    if(temp.element.value == mostFrequent){
                        if(temp.element.key.length() > mostFrequentSubstring.length()){
                            mostFrequentSubstring = temp.element.key;
                        }
                        if(temp.element.key.length() == mostFrequentSubstring.length()){
                            if(temp.element.key.compareTo(mostFrequentSubstring) < 0){
                                mostFrequentSubstring = temp.element.key;
                            }
                        }

                    }
                }else{
                    tabela.insert(substring, occurrences);
                }
            }
        }

        String a = "a";
        String b = "b";

//        System.out.println(a.compareTo(b));

//        System.out.println(tabela);
//        System.out.println(mostFrequent);

        System.out.println(mostFrequentSubstring);

    }
}

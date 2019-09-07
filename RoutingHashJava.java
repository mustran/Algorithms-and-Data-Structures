/* 
Stating routing Problem 2 (0 / 0)
You should simulate routing by using a hash table. Every router is one bucket in the hash table and it receives the input packets only through one interface. Since the router performs the routing of the packet by using the static routes it knows, when it receives a packet it should tell if it is possible to route the packet in the network (postoi or nepostoi). It is important that all addresses have network mask /24 which means that the last 8 bits are allocated for addressing. We assume that all addresses are busy and the packet can be transferred to any device in a network if the network exists in the routing table. For example, if the address 10.10.10.0 can be found in the routing table, it means that the router can transfer the packet to all devices in that network (10.10.10.1- 10.10.10.254).

The number of the routers is given in the first input line. In the next lines for each router the IP address of the interface and IP addresses of the networks to which it has static routes are given. Then the number of routing attempts are entered. In the next lines for each attempt an input interface and device IP address are given. The router should answer if it knows the address or not.

Class Name :RoutingHashJava
*/


package Lab06_Hashing.Staticko_rutiranje_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CBHT<K extends Comparable<K>, E> {

    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is
        // equal
        // to targetKey. Return a link to that node (or null if there is none).
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
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
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

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public SLLNode<E> getSucc() {
        return succ;
    }

    public void setSucc(SLLNode<E> succ) {
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}


class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

        // Each MapEntry object is a pair consisting of a key (a Comparable
        // object) and a value (an arbitrary object).
        K key;
        E value;

public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
        }

public int compareTo (K that) {
// Compare this map entry to that map entry.
@SuppressWarnings("unchecked")
		MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
        }

public String toString () {
        return "<" + key + "," + value + ">";
        }
        }

public class RoutingHashJava {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String n = br.readLine();
        int number = Integer.parseInt(n);
        CBHT<String, List<String>> table = new CBHT<>(number * 2);

        for (int i=0; i<number; i++){
            String router = br.readLine();
            String interfejs = br.readLine();
            String interfejsSplitted[] = interfejs.split(",");
            List<String> lista = new ArrayList<>();

            for (int j = 0; j < interfejsSplitted.length; j++) {
                lista.add(interfejsSplitted[j]);
            }
            table.insert(router, lista);
        }


        String n1 = br.readLine();
        int number1 = Integer.parseInt(n1);

        for (int i = 0; i < number1; i++) {
            String router1 = br.readLine();
            String interfejs2 = br.readLine();

            SLLNode<MapEntry<String, List<String>>> temp = table.search(router1);

            if(temp == null){
                System.out.println("ne postoi");
            }else{
                List value = temp.element.value;
                String merged= "";
                String[] splitted = new String[value.size()];
                for (int j=0; j<value.size(); j++){
                    splitted[j] = String.valueOf(value.get(j));
                    String splitThatShit[] = splitted[j].split("\\.");
                    for (int m=0; m<splitThatShit.length-1; m++){
                        merged += splitThatShit[m];
                    }
                    splitted[j] = merged;
                    merged = "";
                }

                String interfejs2Splitted[] = interfejs2.split("\\.");
                String interjefsMerged = "";
                int subnetMask = Integer.parseInt(interfejs2Splitted[interfejs2Splitted.length-1]);
                for(int in=0; in<interfejs2Splitted.length-1; in++){
                    interjefsMerged += interfejs2Splitted[in];
                }

                if(Arrays.asList(splitted).contains(interjefsMerged) && (subnetMask >= 0 && subnetMask <=255)) {
                    System.out.println("postoi");
                }else{
                    System.out.println("ne postoi");
                }
            }
        }
    }
}

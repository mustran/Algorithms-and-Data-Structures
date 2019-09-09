/*

Counter of the Ministry of Internal Affairs Problem 3 (0 / 0)
Every morning people come to the Ministry of Internal Affairs to apply for one or more documents.
The document can be one of the following:
1. ID card
2. Passport
3. Driving license
People waiting for ID card are served first when the counter opens, then those for passport and finally those waiting for driving license. Every person waits in the queue for the appropriate document that he/she applies for (i.e. either in the queue for ID cards or in the queue for passports in the queue or for driving licenses). If a person has to apply for more documents, first he/she applies for ID card, then for passport and finally for driving license. So if a person applies for ID card and driving license he/she first waits in the queue for ID cards and when he/she finishes, he/she goes at the end of queue for driving licenses.

The first input line indicates how many people came to MIA. Then, four lines are entered for every person (first name and surname are entered in the first line, and the three other lines are for the specifying the appropriate document (identity card, passport and driving license) that a person applies for. 1 means that the person applies for the document and 0 means that he/she does not apply.

For example:

Aleksandar Aleksandrovski
1
0
1
means that Alexander Aleksandrovski will apply for ID card and driving license, but not for passport.
Output should print the names of people in order they finish with applying.

Class name (Java): MVR

*/


import java.util.PriorityQueue;
import java.util.Scanner;

class ArrayQueue<E>{
    E[] elems;
    int length, front, rear;
    @SuppressWarnings("unchecked")
    public ArrayQueue (int maxlength) {
        elems = (E[]) new Object[maxlength];
        clear();
    }
    public boolean isEmpty () {
        return (length == 0);
    }
    public int size () {
        return length;
    }
    public E peek () {
        if (length > 0)
            return elems[front];
        else{
            System.out.println("Queue is empty");
            return null;
        }
    }
    public void clear () {
        length = 0;
        front = rear = 0;
    }
    public void enqueue (E x) {
        elems[rear++] = x;
        if (rear == elems.length)  rear = 0;
        length++;
    }
    public E dequeue () {
        if (length > 0) {
            E frontmost = elems[front];
            elems[front++] = null;
            if (front == elems.length)  front = 0;
            length--;
            return frontmost;
        } else{
            System.out.println("Queue is empty");
            return null;
        }
    }
}

class Gragjanin{
    String imePrezime;
    int lKarta, pasos, vozacka;
    public Gragjanin(String imePrezime, int lKarta, int pasos, int vozacka) {
        super();
        this.imePrezime = imePrezime;
        this.lKarta = lKarta;
        this.pasos = pasos;
        this.vozacka = vozacka;
    }
}

public class MVR {
    public static void otvoriShalter(ArrayQueue<Gragjanin> redicaLkarti,
                                     ArrayQueue<Gragjanin> redicaPasos, ArrayQueue<Gragjanin> redicaVozacka){
        // Your code here
        while(!redicaLkarti.isEmpty()){
            Gragjanin temp = redicaLkarti.dequeue();
            if(temp.pasos == 1){
                redicaPasos.enqueue(temp);
            }else if(temp.vozacka == 1){
                redicaVozacka.enqueue(temp);
            }else{
                System.out.println(temp.imePrezime);
            }
        }

        while(!redicaPasos.isEmpty()){
            Gragjanin temp = redicaPasos.dequeue();
            if(temp.vozacka == 1){
                redicaVozacka.enqueue(temp);
            }else{
                System.out.println(temp.imePrezime);
            }
        }

        while(!redicaVozacka.isEmpty()){
            System.out.println(redicaVozacka.dequeue().imePrezime);
        }
    }

    public static void main(String[] args) {

        PriorityQueue<Gragjanin> queue = new PriorityQueue<>();

        Scanner br = new Scanner(System.in);
        ArrayQueue<Gragjanin> redicaLkarti = new ArrayQueue<Gragjanin>(10);
        ArrayQueue<Gragjanin> redicaPasos = new ArrayQueue<Gragjanin>(10);
        ArrayQueue<Gragjanin> redicaVozacka = new ArrayQueue<Gragjanin>(10);

        int N = Integer.parseInt(br.nextLine());
        for(int i=1;i<=N;i++){
            String imePrezime = br.nextLine();
            int lKarta = Integer.parseInt(br.nextLine());
            int pasos = Integer.parseInt(br.nextLine());
            int vozacka = Integer.parseInt(br.nextLine());
            Gragjanin covek = new Gragjanin(imePrezime,lKarta,pasos,vozacka);
            if(lKarta==1) {
                redicaLkarti.enqueue(covek);
            }
            else if(pasos==1) {
                redicaPasos.enqueue(covek);
            }
            else{
                redicaVozacka.enqueue(covek);
            }
        }
        otvoriShalter(redicaLkarti, redicaPasos, redicaVozacka);
    }
}

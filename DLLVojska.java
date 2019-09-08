/*
Army Problem 2 (2 / 6)
Before the commander are lined up all the soliders, and in a doubly linked list are given their IDs. The commander doesn't like how the soliders are lined up so he decides to choose two sub-intervals and swap them, i.e. the soliders in the first sub-interval will now be in the second, and vice versa.

Input: In the first line you are given the number of soliders. In the second line you are given the IDs of each solider. In the third line you are given two numbers, IDs of the first and last solider of the first sub-interval. In the fourth line you are given two numbers, IDs of the first and last solider of the second sub-interval.

Output: Print the new solider line-up (i.e. their IDs)

NOTE 1: The sub-intervals will never overlap, and will have at least one solider. The whole array will contain at least two soliders. NOTE 2: Pay special attention to intervals which are next to eachother, contain the first or last solider.

Class name: DLLVojska
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DLLNode<E> {
	protected E element;
	protected DLLNode<E> pred, succ;

	public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
		this.element = elem;
		this.pred = pred;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return "<-"+element.toString()+"->";
	}
}


class DLL<E> {
	private DLLNode<E> first, last;

	public DLL() {
		// Construct an empty SLL
		this.first = null;
		this.last = null;
	}

	public void deleteList() {
		first = null;
		last = null;
	}
	
	public int length() {
		int ret;
		if (first != null) {
			DLLNode<E> tmp = first;
			ret = 1;
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret++;
			}
			return ret;
		} else
			return 0;

	}
	
	public void insertFirst(E o) {
		DLLNode<E> ins = new DLLNode<E>(o, null, first);
		if (first == null)
			last = ins;
		else
			first.pred = ins;
		first = ins;
	}

	public void insertLast(E o) {
		if (first == null)
			insertFirst(o);
		else {
			DLLNode<E> ins = new DLLNode<E>(o, last, null);
			last.succ = ins;
			last = ins;
		}
	}

	public void insertAfter(E o, DLLNode<E> after) {
		if(after==last){
			insertLast(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
		after.succ.pred = ins;
		after.succ = ins;
	}

	public void insertBefore(E o, DLLNode<E> before) {
		if(before == first){
			insertFirst(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
		before.pred.succ = ins;
		before.pred = ins;
	}

	public E deleteFirst() {
		if (first != null) {
			DLLNode<E> tmp = first;
			first = first.succ;
			if (first != null) first.pred = null;
			if (first == null)
				last = null;
			return tmp.element;
		} else
			return null;
	}

	public E deleteLast() {
		if (first != null) {
			if (first.succ == null)
				return deleteFirst();
			else {
				DLLNode<E> tmp = last;
				last = last.pred;
				last.succ = null;
				return tmp.element;
			}
		}
		// else throw Exception
		return null;
	}


	@Override
	public String toString() {
		String ret = new String();
		if (first != null) {
			DLLNode<E> tmp = first;
			ret += tmp + "<->";
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret += tmp + "<->";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}
	
	public DLLNode<E> getFirst() {
		return first;
	}

	public DLLNode<E> getLast() {

		return last;
	}
	
}


public class DLLVojska {
    public static void main(String[] args) throws IOException {
        DLL<Integer> lista = new DLL<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        @SuppressWarnings("Duplicates")
        String arr[] = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            lista.insertLast(Integer.parseInt(arr[i]));
        }

        String firstTwoNumbers[] = br.readLine().split(" ");
        int a = Integer.parseInt(firstTwoNumbers[0]);
        int b = Integer.parseInt(firstTwoNumbers[1]);
        String secondTwoNumbers[] = br.readLine().split(" ");
        int c = Integer.parseInt(secondTwoNumbers[0]);
        int d = Integer.parseInt(secondTwoNumbers[1]);

        DLL<Integer> result = vojska(lista, a, b, c, d);

        DLLNode<Integer> node = result.getFirst();
        System.out.print(node.element + " ");
        node = node.succ;
        while(node != null){
            System.out.print(node.element + " ");
            node = node.succ;
        }
    }

    private static DLL<Integer> vojska(DLL<Integer> lista, int a, int b, int c, int d){

        DLLNode<Integer> aNode = null;
        DLLNode<Integer> bNode = null;
        DLLNode<Integer> cNode = null;
        DLLNode<Integer> dNode = null;

        DLLNode<Integer> curr = lista.getFirst();

        while (curr != null){
            if(curr.element == a){
                aNode = curr;
            }if(curr.element == b){
                bNode = curr;
            }if(curr.element == c){
                cNode = curr;
            }if(curr.element == d){
                dNode = curr;
            }
            curr = curr.succ;
        }

        DLL<Integer> ab = new DLL<>();
        DLL<Integer> cd = new DLL<>();

        curr = lista.getFirst();

        while (curr != null){
            if (curr == aNode){
                while (curr != bNode){
                    ab.insertLast(curr.element);
                    curr = curr.succ;
                }
            }if (curr == cNode){
                while (curr != dNode){
                    cd.insertLast(curr.element);
                    curr = curr.succ;
                }
            }
            curr = curr.succ;
        }
        ab.insertLast(bNode.element);
        cd.insertLast(dNode.element);

        DLLNode<Integer> cdPred = cNode.pred;
        DLLNode<Integer> cdLast = dNode.succ;
        DLLNode<Integer> abPred = aNode.pred;
        DLLNode<Integer> abLast = bNode.succ;

        if(aNode.pred == null){
            lista = cd;
        }
        else{
            abPred.succ = cd.getFirst();
        }

        if(cdPred == bNode){
            cd.getLast().succ = ab.getFirst();
            ab.getLast().succ = cdLast;
            return lista;
        }

        cd.getLast().succ = abLast;
        cdPred.succ = ab.getFirst();
        ab.getLast().succ = cdLast;

        return lista;
    }
}


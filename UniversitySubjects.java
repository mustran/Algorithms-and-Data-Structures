/*
University Subjects Problem 1 (0 / 0)
On one university there are mandatory and elective subjects. For each subject you are given, a list of subjects that need to be passed in order to be eligible for that subject is given. Find the next available subject for you, if you know what is the last subject that you have passed.

Input: In the first line you are given a single integer N representing the number of elements. In the following N lines you are given the IDs of the subjects. In the following line you are given a single integer M representing the number of dependences between the subjects. In the following M lines you are given a list of IDs separated with a single white space. Starting from the second ID to the last ID you have the subjects that you need to pass in order to be able to take the subjected defined with the first ID in this line. In the last line you are given the last subject that you have passed.

Output: The output should contain a single ID representing the next available subject.
*/

package Lab10_Graphs.UniversitySubjects_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
class GraphNode<E> {
    private int index;//index (reden broj) na temeto vo grafot
    private E info;
    private LinkedList<GraphNode<E>> neighbors;

    public GraphNode(int index, E info) {
        this.index = index;
        this.info = info;
        neighbors = new LinkedList<GraphNode<E>>();
    }

    boolean containsNeighbor(GraphNode<E> o){
        return neighbors.contains(o);
    }

    void addNeighbor(GraphNode<E> o){
        neighbors.add(o);
    }


    void removeNeighbor(GraphNode<E> o){
        if(neighbors.contains(o))
            neighbors.remove(o);
    }


    @Override
    public String toString() {
        String ret= "INFO:"+info+" SOSEDI:";
        for(int i=0;i<neighbors.size();i++)
            ret+=neighbors.get(i).info+" ";
        return ret;

    }

    @Override
    public boolean equals(Object obj) {
        @SuppressWarnings("unchecked")
        GraphNode<E> pom = (GraphNode<E>)obj;
        return (pom.info.equals(this.info));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public LinkedList<GraphNode<E>> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<GraphNode<E>> neighbors) {
        this.neighbors = neighbors;
    }
}


class Graph<E> {

    int num_nodes;
    GraphNode<E> adjList[];

    @SuppressWarnings("unchecked")
    public Graph(int num_nodes, E[] list) {
        this.num_nodes = num_nodes;
        adjList = (GraphNode<E>[]) new GraphNode[num_nodes];
        for (int i = 0; i < num_nodes; i++)
            adjList[i] = new GraphNode<E>(i, list[i]);
    }

    @SuppressWarnings("unchecked")
    public Graph(int num_nodes) {
        this.num_nodes = num_nodes;
        adjList = (GraphNode<E>[]) new GraphNode[num_nodes];
        for (int i = 0; i < num_nodes; i++)
            adjList[i] = new GraphNode<E>(i, null);
    }

    int adjacent(int x, int y) {
        // proveruva dali ima vrska od jazelot so
        // indeks x do jazelot so indeks y
        return (adjList[x].containsNeighbor(adjList[y])) ? 1 : 0;
    }

    void addEdge(int x, int y) {
        // dodava vrska od jazelot so indeks x do jazelot so indeks y
        if (!adjList[x].containsNeighbor(adjList[y])) {
            adjList[x].addNeighbor(adjList[y]);
        }
    }

    void deleteEdge(int x, int y) {
        adjList[x].removeNeighbor(adjList[y]);
    }

    /************************TOPOLOGICAL SORT*******************************************************************/

    void dfsVisit(Stack<Integer> s, int i, boolean[] visited){
        if(!visited[i]){
            visited[i] = true;
            Iterator<GraphNode<E>> it = adjList[i].getNeighbors().iterator();
            System.out.println("dfsVisit: "+i+" Stack="+s);
            while(it.hasNext()){
                dfsVisit(s, it.next().getIndex(), visited);
            }
            s.push(i);
            System.out.println("--dfsVisit: "+i+" Stack="+s);
        }
    }

    void topological_sort_dfs(){
        boolean visited[] = new boolean[num_nodes];
        for(int i=0;i<num_nodes;i++){
            visited[i] = false;
        }

        Stack<Integer> s = new Stack<Integer>();

        for(int i=0;i<num_nodes;i++){
            dfsVisit(s,i,visited);
        }
        System.out.println("----Stack="+s);
        while(!s.isEmpty()){
            System.out.println(adjList[s.pop()]);
        }
    }

    /***********************************************************************************************************/

    @Override
    public String toString() {
        String ret = new String();
        for (int i = 0; i < this.num_nodes; i++)
            ret += i + ": " + adjList[i] + "\n";
        return ret;
    }

}


public class UniversitySubjects {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n_nodes = Integer.parseInt(br.readLine());
        Map<String, Integer> hashmap = new HashMap<>();
        
        Graph<String> graph = new Graph<>(n_nodes);

        String courseName;
        for (int i = 0; i < n_nodes; i++) {
            courseName = br.readLine();
            graph.adjList[i].setInfo(courseName);
            hashmap.put(courseName, i);
        }

        int courseDependencies = Integer.parseInt(br.readLine());

        String courses[];
        for (int i = 0; i < courseDependencies; i++) {
            courses = br.readLine().split(" ");
            int courseDependent = hashmap.get(courses[0]);
            int dependencies = hashmap.get(courses[courses.length-1]);
            graph.addEdge(dependencies, courseDependent);
        }

        String lastCoursePassed = br.readLine();

        int lastCoursePassedIndex = hashmap.get(lastCoursePassed);

        System.out.println(graph.adjList[lastCoursePassedIndex].getNeighbors().getFirst().getInfo());

    }
}

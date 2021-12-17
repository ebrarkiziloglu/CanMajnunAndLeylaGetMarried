import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.out;
// This is the implementation for weighted and directed graph of the country of Leyla and Mecnun.
// The data given in the input file will be stored in an adjacency list field.
// In the main class, two different objects of this class will be created.
// First object, graph1, is used for the one side of the country -for 'c' cities-.
// Second object, graph2, is used for the other side of the country -for Leyla's home town and all 'd' cities- and it will be undirected.

public class MyGraph {

    private final int numOfVertices;
    private ArrayList<HashMap<Integer, Integer>> adjList;
    // each node will have its Hash map to store the edges.
    // if Node A will have an edge to Node B, with the weight c, in the Ath index of the adjList has a data with the key B and value c.
    // next two fields will be used in the first part of the Project, where we need the shortest path from Mecnun's city to Leyla's city.
    private int[] prevNodes;
    private int[] shortDistance;
    int leylasHometown;
    int mecnunsHometown;
    // Constructors:
    public MyGraph(int v, int mecnun, int leyla) {
        this.numOfVertices = v;
        adjList = new ArrayList<HashMap<Integer, Integer>>();
        // now v+1 empty hashmap will be created in adjList.
        // Since our nodes' index starts with 1, 0th element of the adjList will not be used, yet created to protect the indexing.
        for(int i = 0; i <=v; i++){
            adjList.add(new HashMap<Integer, Integer>());
        }
        this.mecnunsHometown = mecnun;
        this.leylasHometown = leyla;
    }
    // the Constructor below will be used for the second part of the country: 
    		// there is no specific index for Mecnun's home town. 
    		// And for Leyla's home town, index 0 will be used.
    public MyGraph(int v){
        this.numOfVertices = v;
        adjList = new ArrayList<HashMap<Integer, Integer>>();
        // Since 0th index of the adjList will be used by Leyla's home town city, so the indexing will be fixed.
        for(int i = 0; i <=v; i++){
            adjList.add(new HashMap<Integer, Integer>());
        }
    }
    // Getters & Setters:
    public int getNumOfVertices() {  return numOfVertices;  }
    public int[] getPreviousNodes() {   return this.prevNodes;  }
    public int[] getShortDistanceToMecnun() {    return this.shortDistance;  }
    public int getLeylasHometown() {  return leylasHometown;    }
    public int getMecnunsHometown(){  return mecnunsHometown;   }

    // add new weighted edges:
    public void addEdge(int source, int sink, int weight){
        if(this.adjList.get(source).containsKey(sink)){
            // if an edge between source and sink already exists, the shortest weight will be stored.
            if(this.adjList.get(source).get(sink) > weight)
                this.adjList.get(source).put(sink, weight);
        } else { // the edge between the nodes does not exist yet:
            this.adjList.get(source).put(sink, weight);
        }
    }
    // get all neighbors and weights of the edges,given the specific index of a node
    public HashMap<Integer, Integer> getAllNeighbors(int source){
        return this.adjList.get(source);
    }

    // Dijkstra's Algorithm:
    // Finding whether Mecnun will arrive in time:  	 //	takes the index of the starting city, i.e. Mecnun's hometown, as a parameter.
    													 // returns the list of previous nodes of each node.
    public void findShortestPaths(int source){
        int initial = Integer.MAX_VALUE;
        this.prevNodes = new int[this.numOfVertices + 1];
        this.shortDistance = new int[this.numOfVertices + 1];
        this.prevNodes[0] = -2;
        this.shortDistance[0] = -2;
        for(int i = 1; i <= this.numOfVertices; i++){
            this.prevNodes[i] = -1;
            this.shortDistance[i] = initial;
        }
        prevNodes[source] = 0;
        shortDistance[source] = 0;

        boolean[] visited = new boolean[this.numOfVertices+1];
        for(int i = 0; i <= this.numOfVertices; i++) visited[i] = false;
        visited[0] = true;
        for(int i = 1; i <= this.numOfVertices; i++){
            int current = nextNode(visited);
            if(this.shortDistance[current] != initial){
                HashMap<Integer, Integer> edges = this.getAllNeighbors(current);
                // int numOfEdges = edges.size();
                for(int neighbor : edges.keySet()){
                    if(!visited[neighbor]){         // means this neighbor node is not visited yet
                        int weight = edges.get(neighbor);
                        if(shortDistance[current] + weight < shortDistance[neighbor]){
                            shortDistance[neighbor] = shortDistance[current] + weight;
                            this.prevNodes[neighbor] = current;
                        }
                    }
                }
            }
            visited[current] = true;
        }
    }

    // To find which node to visit next, we need to determine the unvisited node with the shortest distance to the source node (Mecnun's city).
    public int nextNode(boolean[] visited){
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;
        boolean notVisited = true;
        for(int i = 1; i <= this.numOfVertices; i++){
            if(!visited[i]){        // returns true if the node is not visited yet.
                if(notVisited) 
                    minIndex = i; 
                notVisited = false;
                if(this.shortDistance[i] < minDistance){
                    minDistance = this.shortDistance[i];
                    minIndex = i;
                }
            }
        }
        return minIndex;
    }


    public String mecnunsPathToLeyla(){
        String path = "";
        int parent = this.prevNodes[leylasHometown];
        if(parent == -1) return "-1";
        
        ArrayList<Integer> pathIndex = new ArrayList<>();
        pathIndex.add(leylasHometown);
        
        while(parent != this.mecnunsHometown){
        	pathIndex.add(parent);
            parent = this.prevNodes[parent];
        }
        pathIndex.add(parent);
        int length = pathIndex.size();
        for(int i = length-1; i > -1; i--){
            path += "c" + pathIndex.get(i) + " ";
        }
        return path;
    }

    // Prim's algorithm:
    // Determining a honeymoon route, if possible, for Leyla and Mecnun:
    // returns the cost of the honeymoon
    public int findHoneymoonRoute(){
    	int source = 0;		// Leyla's home town index in graph2.
        int cost = 0;




        // main implementation


        // check edge cases

        // if Mecnun cannot make it in time, return -1  ??
        // if no route is possible, return -2   ??

        return cost;
    }

    @Override
    public String toString(){
        String text = "";
        for(int i = 0; i <= numOfVertices; i++){
            text += "source: " + i + ":  ";
            HashMap<Integer,Integer> edges = this.adjList.get(i);
            for(int neighbor : edges.keySet())
                text+= " -> " + neighbor + ": " + edges.get(neighbor) + " || ";
            text += "\n";
        }
        return text;
    }


}

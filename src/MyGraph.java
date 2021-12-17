import java.util.ArrayList;
import java.util.HashMap;
//This is the implementation for weighted and directed graph of the country of Leyla and Mecnun.
//The data given in the input file will be stored in an adjacency list field.
//In the main class, two different objects of this class will be created.
//First object, graph1, is used for the one side of the country -for 'c' cities-.
//Second object, graph2, is used for the other side of the country -for Leyla's home town and all 'd' cities- and it will be undirected.

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
        // now (Leyla's home id + 1) empty hash map will be created in adjList.
        // Since our nodes' index starts with 1, 0th element of the adjList will not be used, yet created to protect the indexing.
        for(int i = 0; i <=leyla; i++){
            adjList.add(new HashMap<Integer, Integer>());
        }
        this.mecnunsHometown = mecnun;
        this.leylasHometown = leyla;
    }
    // the Constructor below will be used for the second part of the country:
    // there is no index for Mecnun's home town, since it is irrelevant.
    // And for Leyla's home town, index 0 will be used.
    public MyGraph(int v){
        this.numOfVertices = v;		// v equals the number of d cities + 1 (Leyla's home town)
        adjList = new ArrayList<HashMap<Integer, Integer>>();
        // Since 0th index of the adjList will be used by Leyla's home town city, the indexing will be fixed.
        // (dI)th city will owns the Ith Hash map in the adjacency list.
        for(int i = 0; i <v; i++){
            adjList.add(new HashMap<Integer, Integer>());
        }
    }
    
    // Getters & Setters:
    public int[] getPreviousNodes() {   return this.prevNodes;  }
    public int[] getShortDistanceToMecnun() {    return this.shortDistance;  }
    public ArrayList<HashMap<Integer, Integer>> getAndjList()	{ return this.adjList;	  }
    // get all neighbors and weights of the edges, given the specific index of a node
    public HashMap<Integer, Integer> getAllNeighbors(int source){   return this.adjList.get(source); }

    // add new weighted edges:
    public void addEdge(int source, int sink, int weight){
        if(this.adjList.get(source).containsKey(sink)){
            // if an edge between source and sink already exists, the shortest weight will be stored.
            if(this.adjList.get(source).get(sink) > weight)
                this.adjList.get(source).put(sink, weight);
        } else { // the edge between the nodes does not exist yet:
            this.adjList.get(source).put(sink, weight);
        }  }

    // PART 1: Dijkstra's Algorithm:
    // Finding whether Mecnun will arrive in time:  	 //	takes the index of the starting city, i.e. Mecnun's home town, as a parameter.
    // returns the list of previous nodes of each node.
    public void findShortestPaths(int source){
        int initial = Integer.MAX_VALUE;
        this.prevNodes = new int[this.numOfVertices + 1];
        this.shortDistance = new int[this.numOfVertices + 1];
        this.prevNodes[0] = -2;
        this.shortDistance[0] = -2;
        for(int i = 1; i <= this.numOfVertices; i++){
            this.prevNodes[i] = -1;
            this.shortDistance[i] = initial;       }
        prevNodes[source] = 0;
        shortDistance[source] = 0;
        boolean[] visited = new boolean[this.numOfVertices+1];
        for(int i = 0; i <= this.numOfVertices; i++) visited[i] = false;
        visited[0] = true;
        // for(int i = 1; i <= this.numOfVertices; i++){
        for(int i = 0; i <= this.numOfVertices; i++){
            int current = nextNode(visited, shortDistance, this.numOfVertices);
            if(this.shortDistance[current] != initial){
                HashMap<Integer, Integer> edges = this.getAllNeighbors(current);
                // int numOfEdges = edges.size();
                for(int neighbor : edges.keySet()){
                    if(!visited[neighbor]){         // means this neighbor node is not visited yet
                        int weight = edges.get(neighbor);
                        if(shortDistance[current] + weight < shortDistance[neighbor]){
                            shortDistance[neighbor] = shortDistance[current] + weight;
                            this.prevNodes[neighbor] = current;
                        }   }   }
                visited[current] = true;
            }   }   }

    // To find which node to visit next:
    // In Part 1, we need to determine the unvisited node with the shortest distance to the source node (Mecnun's city).
    // In Part 2, we need to determine the unvisited node with the least current cost.
    public int nextNode(boolean[] visited, int[] distance, int count){
    	// In order to use the same "find the Node with the min cost/distance that is not yet visited" function 
    	// for both part 1 and 2 of the Project, 3 parameters are needed.
    	// For Part 1, visited boolean list, distances to the source node list, and number of vertices are given as parameters.
    	// For Part 2, visited boolean list, cost of each node list, and number of vertices are given as parameters.
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;
        boolean notVisited = true;
        for(int i = 0; i <= count; i++){
            if(!visited[i]){        // returns true if the node is not visited yet.
                if(notVisited)
                    minIndex = i;
                notVisited = false;
                if(distance[i] < minDistance){
                    minDistance = distance[i];
                    minIndex = i;
                }   }   }
        return minIndex;
    }

    // After the Dijkstra's Algorithm, this function returns the shortest path String in the right form, to be directly printed to the output file.
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
        for(int i = length-1; i > 0; i--){
            path += "c" + pathIndex.get(i) + " ";
        }
        path += "c" + pathIndex.get(0);
        return path;
    }

    // PART 2: Prim's algorithm:
    // Determining a honeymoon route, if possible, for Leyla and Mecnun:
    // returns the cost of the honeymoon
    public int findHoneymoonRoute(){
        // Leyla's home town index, which is the source index, is 0 in graph2.
        int totalCost = 0;
        int initial = Integer.MAX_VALUE;
        boolean[] visited = new boolean[this.numOfVertices];
        int[] parent = new int[this.numOfVertices];
        int[] cost = new int[this.numOfVertices];
        for(int i = 0; i <this.numOfVertices; i++) {
            visited[i] = false;
            parent[i] = -1;
            cost[i] = initial;
        }
        cost[0] = 0;
        for(int i = 0; i < this.numOfVertices; i++){
            int current = nextNode(visited, cost, this.numOfVertices-1);
            if(cost[current] != initial){
                HashMap<Integer, Integer> edges = this.getAllNeighbors(current);
                for(int neighbor : edges.keySet()){
                    if(!visited[neighbor]){         // means this neighbor node is not visited yet
                        int weight = edges.get(neighbor);
                        if(weight < cost[neighbor]){
                            cost[neighbor] = weight;
                            parent[neighbor] = current;
                        }   }   }
                visited[current] = true;
            }   }
        // check whether all the edges are visited:
        boolean allNodesVisited = true;
        for(boolean b : visited) if(!b) allNodesVisited = false;

        // Calculate the cost:
        if(allNodesVisited) {
            for (int edgeCost : cost) totalCost += edgeCost;
            totalCost *= 2;
        } else{
            totalCost = -2;
        }

        return totalCost;
    }

    // Following method is put for design and debugging purposes. 
    @Override
    public String toString(){
        String text = "";
        int length = this.adjList.size();
        for(int i = 0; i < length; i++){
            text += "source: " + i + ":  ";
            HashMap<Integer,Integer> edges = this.adjList.get(i);
            for(int neighbor : edges.keySet())
                text+= " -> " + neighbor + ": " + edges.get(neighbor) + " || ";
            text += "\n";
        }
        return text;
    }


}

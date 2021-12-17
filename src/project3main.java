import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
// import static java.lang.System.out;
public class project3main {


// Project3/src/*.java -d Project3/bin --release 16
// java -cp Project3/bin project3main test_cases/input_2.txt myoutput.txt

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();

        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        Scanner sc = new Scanner(inputFile);
        PrintStream print = new PrintStream(outputFile);

        int time = Integer.parseInt(sc.nextLine());
        int vertices = sc.nextInt();
        sc.nextLine();
        String[] hometowns = sc.nextLine().split(" ");
        int mecnunsHome = Integer.parseInt(hometowns[0].substring(1));
        int leylasHome = Integer.parseInt(hometowns[1].substring(1));
        
        // For two sides of the country, two different MyGraph objects will be created.
        // Since we don't know the exact number of c cities and d cities, for the first part of the country, number of vertices will be the total number of vertices of the country.
        // For the second part of the country, number of the vertices will be (vertices-leylasHome), since this can be the maximum number of d cities.
        // While creating a graph1 object, only adjacency hash maps for edges until the number of Leyla's Home town index will be created.
        MyGraph graph1 = new MyGraph(vertices, mecnunsHome, leylasHome);

        // For the second part of the country, undirected graph will be used.
        // Therefore, each edge will be added to the both nodes.
        MyGraph graph2 = new MyGraph(vertices - leylasHome + 1);
        int count = leylasHome-1;
        // for all nodes before Leyla's home town, the code below will handle the edges:
        while(count > 0){
            String[] input = sc.nextLine().split(" ");
            int source = Integer.parseInt(input[0].substring(1));
            int length = input.length;
            for(int i = 1; i < length; i+=2){
                int sink = Integer.parseInt(input[i].substring(1));
                int weight = Integer.parseInt(input[i + 1]);
                if(sink != source)
                	graph1.addEdge(source, sink, weight);
            }
            count--;
        }
        // Now it is Leyla's hometown's turn:
        String[] input = sc.nextLine().split(" ");
        int length = input.length;
        for(int i = 1; i < length; i+=2){
        	String type = input[i].substring(0,1);
        	// From Leyla's city, only edges to 'd' cities are important, so only they will be stored.
        	if(type.equals("d")) {
        		int sink = Integer.parseInt(input[i].substring(1));
                int weight = Integer.parseInt(input[i + 1]);
                graph2.addEdge(0, sink, weight);
                graph2.addEdge(sink, 0, weight); 		// this graph is undirected
        	}
        }
        count = leylasHome;
        // At this point, there may be still some c cities, that come after Leyla's home town, left.
        String inputLine = sc.nextLine();
        while(inputLine.charAt(0) == 99){         // means this line is still edges for a 'c' city.
            String[] edges = inputLine.split(" ");
            // If there are any 'c' cities after Leyla's home, first, an adjacency hash map will be created for them. Then, edges will be added:
            graph1.getAndjList().add(new HashMap<Integer, Integer>());
            int sourceNode = Integer.parseInt(edges[0].substring(1));
            int len = edges.length;
            for(int i = 1; i < len; i+=2){
                int sink = Integer.parseInt(edges[i].substring(1));
                int weight = Integer.parseInt(edges[i + 1]);
                if(sink != sourceNode)
                	graph1.addEdge(sourceNode, sink, weight);
            }
            count ++;
            inputLine = sc.nextLine();
        }
        // when city d1's line is read, while loop above will terminate.
        // there are only d cities left, and their number is exactly equals to vertices-count
        count = vertices - count;

        for(int i = 0; i < count; i++){
            String[] edges = inputLine.split(" ");
            int sourceNode = Integer.parseInt(edges[0].substring(1));
            int len = edges.length;
            for(int j = 1; j < len; j+=2){
                int sink = Integer.parseInt(edges[j].substring(1));
                int weight = Integer.parseInt(edges[j + 1]);
                if(sink != sourceNode) {
                	graph2.addEdge(sourceNode, sink, weight);
                    graph2.addEdge(sink, sourceNode, weight); 		// this graph is undirected
                }
            }
            if(i == count - 1) break;
            inputLine = sc.nextLine();
        }

        graph1.findShortestPaths(mecnunsHome);
        int[] shortestDistances = graph1.getShortDistanceToMecnun();
        int mecnunsTime = shortestDistances[leylasHome];
        boolean canMecnunMakeIt = true;         // stores if Mecnun arrives to the city at time.

        // print.println("shortest path length: " + mecnunsTime);
        String shortestPath = graph1.mecnunsPathToLeyla();
        if(shortestPath.equals("-1") || mecnunsTime > time) canMecnunMakeIt = false;
        print.println(shortestPath);
        int honeymoonCost = graph2.findHoneymoonRoute();
        if(! canMecnunMakeIt) honeymoonCost = -1;
        print.print(honeymoonCost);

        long end = System.currentTimeMillis();
        long elapsedTime = end - start;

        // print.println(elapsedTime + " milliseconds.");

        sc.close();
        print.close();
    }
}

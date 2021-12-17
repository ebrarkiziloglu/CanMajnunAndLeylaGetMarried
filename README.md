<br /><br />
<div align="center">
<h1 align="center">Can Majnun and Leyla Get Married?<img src = "https://i.pinimg.com/originals/ae/d4/a5/aed4a561e70f530eda0f7261017b6cf2.gif" width = 50px>
</h1>
</div>

<br />
<br />

Majnun and Leyla are hapless lovers. However, Leyla's father does not want them to marry. 
Therefore, he puts a condition on Majnun before giving his consent. 
If Majnun manages to come from his hometown to Leyla's hometown before the time determined by the father, 
he will consent to their marriage. The father shows no mercy and hides the appointed time from Majnun. 
Thus, Majnun has to find the shortest route from his hometown to Leyla. To do so, we will use Dijkstra's Algorithm.

Their country is divided into two regions by a river. Leyla and Majnun live in the same region of the country. 
Leyla's city is the only city in this region that has bridges to cross to the other region.
If Majnun arrives at Leyla's hometown on time, they will get married and will go on a honeymoon on foot in the other part of the country. 
Roads are undirected for walking. But each pedestrian has to pay a sidewalk tax that is equal to the length of the road. 
If a tax is paid for a road, they can use that road as much as they want.
They want to visit all cities in the other region, at the least cost, if this is not possible they won't be able to go on their honeymoon. 
To determine this, we will use Prim's Algorithm. 

The total number of cities in the country, as well as the time limit Leyla's father puts, are given in the input file.
Majnun and Leyla's hometowns' ids are also given.
Then, for each city, the cities that it has a road to and the length of the road are given.


## Classes

There are two classes in this project.
### MyGraph Class:
  - To represent the graph, an adjacency list is used.
  - There are two Constructors in this class. 
  - Since the first region of the country is directed and the second region is undirected, 
  two different instances of this class with two different constructors are used.
  - findShortestPaths method uses Dijkstra's Algorithm.
  - findHoneymoonRoute method uses Prim's Algorithm. 

### project3main Class:
  - In this main class, firstly, input is read.
  - Then, Majnun's route to Leyla is calculated.
  - If Majnun arrives on time, his route is printed and the Honeymoon route is determined.
  - If such a route exists, the total cost of their Honeymoon is printed.
  
  
<div>
<h2>Installation <img src = "https://media2.giphy.com/media/QssGEmpkyEOhBCb7e1/giphy.gif?cid=ecf05e47a0n3gi1bfqntqmob8g9aid1oyj2wr3ds3mg700bl&rid=giphy.gif" width = 28px></h2>
</div>

In this project, **project3main.java** class takes the input commmands from an input file given. 
To read from the file, the class uses **Scanner** and **File** built in classes, and implements the following code:
```java
File inputFile = new File(args[0]);
Scanner sc = new Scanner(inputFile);
```
The output is also printed to an output file. For this, I used **PrintStream** and **File** built in classes, and implemented the following code:
```java
File outputFile = new File(args[1]);
PrintStream print = new PrintStream(outputFile);
```

**Command to cocmpile the project:**
```sh
CanMajnunAndLeylaGetMarried/src/*.java -d CanMajnunAndLeylaGetMarried/bin --release 16
```

**Command to run the project:**
```sh
java -cp CanMajnunAndLeylaGetMarried/bin project3main CanMajnunAndLeylaGetMarried/src/testCases/inputFiles/input_0.txt CanMajnunAndLeylaGetMarried/src/testCases/outputFiles/myoutput.txt
```


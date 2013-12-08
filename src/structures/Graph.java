package structures;

import java.util.*;

public class Graph {
	
	protected ArrayList<Vertex> adjacencyLists; 
	
	HashMap<String,LinkedList<Integer>> studentsInSchool = new HashMap<String,LinkedList<Integer>>();
	HashMap<String,Integer> nameHash = new HashMap<String,Integer>();

	public int addVertex(Vertex vert) {
		if(!containsV(vert)) { 
			adjacencyLists.add(vert);
			nameHash.put(vert.name, adjacencyLists.size()-1);
			if(!vert.school.equals("")) {
				if(!studentsInSchool.containsKey(vert.school)) {
					LinkedList<Integer> stu = new LinkedList<Integer>();
					stu.add(adjacencyLists.size()-1);
					studentsInSchool.put(vert.school, stu);
				}else{
					studentsInSchool.get(vert.school).add(adjacencyLists.size()-1);
				}
			}
		}
		return adjacencyLists.size()-1;
	}

	public String shortestPath(String introName, String endName) {
		Queue<Integer> q = new LinkedList<Integer>();
		boolean[] visited = new boolean[adjacencyLists.size()];
		Arrays.fill(visited, false);
		HashMap<Integer,Integer> previous = new HashMap<Integer,Integer>();
		
		int start = indexOfName(introName);
		int finish = indexOfName(endName);
		int curr = start;
		
		previous.put(curr,start);
		q.add(curr);
		visited[curr] = true;
		
		while(!q.isEmpty()) {
			curr = (int)q.remove(); 
			if(curr == finish) {
				break;
			} else {
				for(int nbr : adjacencyLists.get(curr).neighbors) {
					if(!visited[nbr]) {
						q.add(nbr);
						visited[nbr] = true;
						previous.put(nbr,curr);
					}
				}
			}
		}
		
		if(curr != finish) {
			return "There is no path for "+introName+"";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(nameOfIndex(finish));
		for(int i=finish; i != start; i=previous.get(i)) { 
			sb.insert(0, nameOfIndex(previous.get(i)) + " --> ");
		}
		return sb.toString();
	}
	
	public Graph subgraph(String school) {
	    //still needs to be completed
		return null;
	}
	
	public void printSchools() {
		for(Map.Entry entry : studentsInSchool.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
	public ArrayList<Graph> cliques() {
		ArrayList<Graph> cliques = new ArrayList<Graph>();
		for(String school : studentsInSchool.keySet()) {
			cliques.add(subgraph(school));
		}
		return cliques; 
	}
	
	ArrayList<String> connectors = new ArrayList<String>();
	int num = 0;
	int start = 0;
	
	private void depthFirstSearch(Vertex v) {
		num++;
		v.num = num;
		v.backwards = num;
		v.visited = true;

		for(int wnum : v.neighbors) {
			Vertex w = adjacencyLists.get(wnum);
			if(!w.visited) {
				depthFirstSearch(w);
				if(indexOfName(v.name) != start && v.num <= w.backwards) {
					if(!connectors.contains(v.name)) connectors.add(v.name);
				}
				if(v.num > w.backwards) v.backwards = Math.min(v.backwards, w.backwards);
			} else v.backwards = Math.min(v.backwards, w.num);
		}		
	}
	
	public String connectors() {
		for(int i = 0; i < adjacencyLists.size(); i++) {
			Vertex v = adjacencyLists.get(i);
			if(!v.visited && v.neighbors.size() == 1) {
				num = 0;
				start = i;
				depthFirstSearch(v);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(connectors.get(0));
		for(int i = 1; i < connectors.size(); i++) sb.insert(0, connectors.get(i)+", ");
		sb.insert(0, "Connectors: ");
		return sb.toString();
	}

	public Neighbor firstNeighbor(int sourceNum) {
		return null;
	}	
	public class shPath {
		
		int numverts;
		int sourceNum;
		int destNum;
		LinkedList<Neighbor> fringe;
		int[] Distance;
		int[] Previous;
		boolean[] Done;
		Graph G;
		
		public shPath(Graph G, String name1, String name2) {
			this.G = G;
			numverts = G.numberOfVertices();
			
			this.sourceNum = G.indexOfName(name1);
			this.destNum = G.indexOfName(name2);
			
			fringe = new LinkedList<Neighbor>();
			Distance = new int[numverts];
			Previous = new int[numverts];
			Done = new boolean[numverts];
			

			for(int i=0; i < numverts; i++) {
				Done[i] = false;
				Distance[i] = Integer.MAX_VALUE;
				Previous[i] = i;
			}
			fringe.clear();
			

			Done[sourceNum] = true;
			Distance[sourceNum] = 0;
			
			Neighbor nbr = G.firstNeighbor(sourceNum);
			while(nbr != null) {
				int w = nbr.vnum;
				Distance[w] = 1;
				Previous[w] = sourceNum;
				fringe.add(nbr);
				nbr = nbr.next;
			}
		}
		
		public void runSome(int howManySteps) {
			int step = 0;
			while(step < howManySteps) {
				if(fringe.isEmpty()) break;

			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(adjacencyLists.size()+"\n");
		
		for(int v = 0; v < adjacencyLists.size(); v++) {
			sb.append(nameOfIndex(v) + 
					(adjacencyLists.get(v).school.equals("") ? "|n" : "|y|" + adjacencyLists.get(v).school) + "\n");
		}
		boolean[][] appended = new boolean[adjacencyLists.size()][adjacencyLists.size()];
		for(int i = 0; i < adjacencyLists.size(); i++) {
			for(int j = 0; j < adjacencyLists.size(); j++) {
				appended[i][j] = false;
			}
		}
		for(int v = 0; v < adjacencyLists.size(); v++) {
			for(int nbr : adjacencyLists.get(v).neighbors) {
				if(!appended[v][nbr]) {
					sb.append(nameOfIndex(v) +"|"+nameOfIndex(nbr) + "\n");
					appended[v][nbr] = true; appended[nbr][v] = true;
				}
			}
		}
		return sb.toString();
	}
	// a whole slew of methods
	public Graph(){ 
		
		adjacencyLists = new ArrayList<Vertex>(); 
		
		}
	public Graph(int vertexCap){ 
		
		adjacencyLists = new ArrayList<Vertex>(vertexCap); 
		
		}
	public int numberOfVertices() { 
		
		return adjacencyLists.size(); 
		
		}
	
	public boolean containsV(Vertex v){ 
		
		return adjacencyLists.indexOf(v) != -1; 
		
		}
	public int vertexNumberOf(Vertex v){ 
		
		return adjacencyLists.indexOf(v); 
		
		}
	public boolean containsEdge(int vnum, int nbr){ 
		
		return adjacencyLists.get(vnum).neighbors.contains(nbr); 
		
		}
	public void addEdge(int vnum, int nbr) {
		Vertex v = adjacencyLists.get(vnum);
		if(!v.neighbors.contains(nbr)) {
			v.neighbors.add(nbr);
			adjacencyLists.get(nbr).neighbors.add(vnum);
		}
	}
	
	public int indexOfName(String name){ 
		
		return nameHash.containsKey(name) ? nameHash.get(name) : -1; 
		
		}
	public String nameOfIndex(int vnum){ 
		
		return adjacencyLists.get(vnum).name; 
		
		}
	

}

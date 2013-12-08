// Tanzil Ansari, Brett Bertoni

package apps;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import structures.Graph;
import structures.Vertex;

public class Friends {

	static Scanner stdin = new Scanner(System.in);
	
	static char option() {
		System.out.print("Choose action: ");
		System.out.print("(s) - Students at a school, ");
		System.out.print("(i) - Intro chain, ");
		System.out.print("(c) - Cliques, ");
		System.out.print("(o) - Connectors, or ");
		System.out.print("(q) - Quit? => ");
		char choice = stdin.next().toLowerCase().charAt(0);
		while (choice != 's' && choice != 'i' && choice != 'c' && choice != 'o' && choice != 'q') {
			System.out.print("Please enter the correct letter => ");
			choice = stdin.next().toLowerCase().charAt(0);
		}
		return choice;
	}
	
	static Graph build(String graphFile) 
	throws IOException {
		Scanner sc = new Scanner(new File(graphFile));
		int verts = sc.nextInt();
		Graph gr = new Graph(verts);
		
		sc.useDelimiter("\n");
		for(int v = 0; v < verts; v++) {	
			String[] lineBuild = sc.next().split("\\|");
			String nameBiuld = lineBuild[0];
			gr.addVertex(new Vertex(nameBiuld, (lineBuild[1].equals("y") ? lineBuild[2] : "")));
		}
		while(sc.hasNext()) {
			String[] lineBuild = sc.next().split("\\|");
			gr.addEdge(gr.indexOfName(lineBuild[0]),gr.indexOfName(lineBuild[1]));
		}
		
		return gr;
	}
	
	public static void main(String[] args)
	throws IOException {
		System.out.print("Enter graph file name => ");
		String graphFile = stdin.next();
		Graph graph = build(graphFile);
		char choices;
		while((choices = option()) != 'q') {
			switch (choices) {
				case 's':
					System.out.print("Please enter a school name => ");
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					String school = in.readLine();
					System.out.println(graph.subgraph(school));
					break;
				case 'i':
					System.out.print("Please enter name of person who wants introduction => ");
					String introName = stdin.next().toLowerCase();
					System.out.print("Please enter who "+ introName + " wants to meet => ");
					String endName = stdin.next().toLowerCase();
					System.out.println(graph.shortestPath(introName, endName));
					break;
				case 'c':
					ArrayList<Graph> cliques = graph.cliques();
					for(int i=0; i < cliques.size(); i++) {
						System.out.println("Clique " + (i+1) + ":");
						System.out.println(cliques.get(i));
					}
					break;
				case 'o':
					System.out.println(graph.connectors());
					break; 
			}
		}
	}

}

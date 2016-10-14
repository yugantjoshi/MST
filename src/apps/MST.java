package apps;

import structures.*;
import java.util.ArrayList;

public class MST {

	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		/* COMPLETE THIS METHOD */
		PartialTreeList ptL = new PartialTreeList();

		for(int i=0; i<graph.vertices.length; i++)
		{
			Vertex v = graph.vertices[i];
			PartialTree pt = new PartialTree(v);
			Vertex.Neighbor nbr = v.neighbors;
			while(nbr!=null)
			{
				PartialTree.Arc arc= new PartialTree.Arc(v, nbr.vertex, nbr.weight) ;
				pt.getArcs().insert(arc);
				nbr = nbr.next;
			}
			ptL.append(pt);
		}
		return ptL;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

		/* COMPLETE THIS METHOD */
		
		ArrayList<PartialTree.Arc> arcL = new ArrayList<PartialTree.Arc>();
		if(ptlist.size()<=1)
		{
			return arcL;
		}
		while(ptlist.size()>1)
		{
			PartialTree ptx = ptlist.remove();
			MinHeap<PartialTree.Arc> pqx = ptx.getArcs();
			
			PartialTree.Arc hpa = pqx.deleteMin();
			Vertex v2 = hpa.v2;
			
			while(v2.getRoot()==ptx.getRoot())
			{
				hpa = pqx.deleteMin();
				v2 = hpa.v2;
			}
			arcL.add(hpa);
			PartialTree pty = ptlist.removeTreeContaining(v2);
			ptx.merge(pty);
			ptlist.append(ptx);
			
		}

		return arcL;
	}
}

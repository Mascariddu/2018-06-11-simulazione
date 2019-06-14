package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import com.mysql.jdbc.BestResponseTimeBalanceStrategy;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	SightingsDAO dao;
	List<String> vertex;
	SimpleDirectedGraph<String, DefaultEdge> grafo;
	List<String> best;
	
	public Model() {
		
		dao = new SightingsDAO();
		
	}

	public List<String> getAnni() {
		// TODO Auto-generated method stub
		return dao.getAnni();
	}

	public void creaGrafo(int anno) {
		// TODO Auto-generated method stub
		grafo = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		vertex = dao.getStati(anno);
		
		Graphs.addAllVertices(grafo, vertex);
		
		List<Adiacenze> adiacenze = new ArrayList<Adiacenze>();
		
		adiacenze = dao.getEdges(anno);
		
		for(Adiacenze a : adiacenze) {
			
			String source = a.getState1();
			String target = a.getState2();
			grafo.addEdge(source, target);
			System.out.println("aggiunta edge");
			
		}
		
		System.out.println("#vertici: "+grafo.vertexSet().size());
		System.out.println("#archi: "+grafo.edgeSet().size());
	}

	public List<String> getPredecessori(String stato) {
		// TODO Auto-generated method stub
		
		return Graphs.predecessorListOf(grafo, stato);
	}

	public List<String> getVertex() {
		// TODO Auto-generated method stub
		return vertex;
	}

	public List<String> getSuccessori(String value) {
		// TODO Auto-generated method stub
		return Graphs.successorListOf(grafo, value);
	}

	public List<String> getRaggiungibili(String value) {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		DepthFirstIterator<String, DefaultEdge> iterator = new DepthFirstIterator<String, DefaultEdge>(grafo);
		
		while(iterator.hasNext())
			result.add(iterator.next());
		
		return result;
	}

	public int ottieniCammino(String value) {
		// TODO Auto-generated method stub

		return this.getRaggiungibili(value).size();
	}

	public List<String> trovaCammino(int anno,String stato) {
		// TODO Auto-generated method stub
		List<String> parziale = new ArrayList<String>();
		parziale.add(stato);
		
		best = new ArrayList<String>();
		best.add(stato);
		
		cerca(parziale);
		
		return best;
	}

	private void cerca(List<String> parziale) {
		// TODO Auto-generated method stub
		System.out.println("CERCO");
		
		//TERMINAZIONE
		if(parziale.size() > best.size()) {
			best = new ArrayList<String>(parziale);
			System.out.println("NEW BEST");
		}
		
		String last = parziale.get(parziale.size()-1);
		
		for(String s : Graphs.successorListOf(grafo, last)) {
			
			if(!parziale.contains(s)) {
				
				parziale.add(s);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			} 
			
		}
		
	}


}
		


package aed.find;

import es.upm.aedlib.tree.Tree;
import es.upm.aedlib.Position;
import java.util.Iterator;
import es.upm.aedlib.positionlist.*;


public class Find {

  /**
   * Busca ficheros con nombre igual que fileName dentro el arbol directory,
   * y devuelve un PositionList con el nombre completo de los ficheros
   * (incluyendo el camino).
   */
	public static void find(String fileName, Tree<String> directory) {
		if(!directory.isEmpty()){			
			Position<String> pos = directory.root();			
			PositionList<String> location = new NodePositionList<String>();
			location = search(directory, pos, fileName);
			for(String s: location){
				Printer.println(s);
			}
		}
	}
	
	private static PositionList<String> search(Tree<String> directory, Position<String> root, String item){
		PositionList<String> results = new NodePositionList<String>();;
		if(root.element().equals(item)){
			String address = new String("/" + root.element()); 
			results.addLast(address);
		}
		Iterable<Position<String>> children = directory.children(root);
		Iterator<Position<String>> it = children.iterator();
		while(it.hasNext()){
			Position<String> next = it.next();
			Iterable<String> deepSearch = search(directory, next, item);
			Iterator<String> itDS = deepSearch.iterator();
			while(itDS.hasNext()){
				String result = itDS.next();
				results.addLast("/" + root.element() + result);
			}
	
		}
		return results;
	}
}

package aed.cnossos;

import es.upm.aedlib.Pair;
import es.upm.aedlib.positionlist.*;
import es.upm.aedlib.Position;
import java.util.Hashtable;
import java.util.Iterator;

public class TeseoExplorador {
	
	public static Pair<Object,PositionList<PuntoCardinal>> explora(Laberinto cnossos) {
		PositionList<PuntoCardinal> path = new NodePositionList<PuntoCardinal>();
		Hashtable<String, PositionList<PuntoCardinal>> table = new Hashtable<String, PositionList<PuntoCardinal>>();		
		return search(cnossos, table, path);		
	}
	
	private static Pair<Object,PositionList<PuntoCardinal>> search(Laberinto cnossos, 
			Hashtable<String, PositionList<PuntoCardinal>> table, PositionList<PuntoCardinal> path){
		if(cnossos.tieneTesoro()) return new Pair<Object, PositionList<PuntoCardinal>>(cnossos.getTesoro(), path);
		if(!cnossos.sueloMarcadoConTiza()) cnossos.marcaSueloConTiza();
		PositionList<PuntoCardinal> toExplore;
		toExplore = table.get(cnossos.printLocation());
		if (toExplore == null){
			toExplore = new NodePositionList<PuntoCardinal>();
			Iterable<PuntoCardinal> caminos = cnossos.caminos();
			Iterator<PuntoCardinal> it = caminos.iterator();
			while(it.hasNext()){
				toExplore.addLast(it.next());
			}		
		}
		backtrack(toExplore, path);
		if(toExplore.isEmpty()){
			if(path.isEmpty()) return null;
			cnossos.ir(PuntoCardinal.opuesto(path.remove(path.last())));
		}else{
			PuntoCardinal next = toExplore.remove(toExplore.first());
			table.put(cnossos.printLocation(), toExplore);
			path.addLast(next);
			cnossos.ir(next);
		}	
		return search(cnossos, table, path);
	}
	
	private static void backtrack(PositionList<PuntoCardinal> toExplore, PositionList<PuntoCardinal> path){
		if(path.isEmpty() || toExplore.isEmpty()) return;
		Position<PuntoCardinal> pc = toExplore.first();
		PuntoCardinal last = path.last().element();
		for(int i = 0; i < toExplore.size(); i++){
			if(pc.element() == PuntoCardinal.opuesto(last)){
				toExplore.remove(pc);
				return;
			}
			pc = toExplore.next(pc);
		}
	}	
}




package aed.cache;

import es.upm.aedlib.Position;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;



public class Cache<Key,Value> {
	private int maxCacheSize;
	private Storage<Key,Value> storage;
	private Map<Key,CacheCell<Key,Value>> map;
	private PositionList<Key> lru;

	public Cache(int maxCacheSize, Storage<Key,Value> storage) {
		this.storage = storage;
		this.map = new HashTableMap<Key,CacheCell<Key,Value>>();
		this.lru = new NodePositionList<Key>();
		this.maxCacheSize = maxCacheSize;
	}

	public Value get(Key key) {		
		return checkCache(key, null).getValue();	
	}

	public void put(Key key, Value value) {
		checkCache(key, value);		
	}
	
	private CacheCell<Key,Value> checkCache(Key key, Value value){
		CacheCell<Key,Value> item = map.get(key);
		if (item != null){
			item = inCache(key, value, item);			
		}else{
			item = updateCache(key, value);
		}		
		return item;
	}
	
	private CacheCell<Key,Value> updateCache(Key key, Value value){
		lru.addFirst(key);
		CacheCell<Key,Value> item;
		if(value != null){
			item = new CacheCell<Key,Value>(value, true, lru.first());
		}else{
			item = new CacheCell<Key,Value>(storage.read(key), false, lru.first());	
		}
		map.put(key, item);
		if(lru.size() > maxCacheSize){
			Key k = lru.remove(lru.last());
			CacheCell<Key,Value> aux = map.get(k);
			map.remove(k);
			if(aux.getDirty()) storage.write(k, aux.getValue());
		}
		return item;
	}
	
	private CacheCell<Key,Value> inCache(Key key, Value value, CacheCell<Key,Value> item){
		Position<Key> pos = lru.first();
		do{
			if(pos.element() == key) break;
			pos = lru.next(pos);
		}while(lru.next(pos) != null);			
		lru.addFirst(key);
		lru.remove(pos);
		item.setPos(lru.first());
		if(value != null){
			item.setValue(value);
			item.setDirty(true);
		}
		map.put(key, item);
		return item;		
	}
}


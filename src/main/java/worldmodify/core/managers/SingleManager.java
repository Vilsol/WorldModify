package worldmodify.core.managers;

import java.util.concurrent.ConcurrentHashMap;

public class SingleManager<T, M> {
	
	private ConcurrentHashMap<T, M> objects = new ConcurrentHashMap<T, M>();
	
	public void registerObject(T key, M value) {
		if(!objects.containsKey(key)) objects.put(key, value);
	}

	public void deleteObject(T key) {
		if(objects.containsKey(key)) objects.remove(key);
	}
	
	public M getObject(T key){
		if(objects.containsKey(key)) return objects.get(key);
		return null;
	}

	public ConcurrentHashMap<T, M> getObjects() {
		return objects;
	}
	
}

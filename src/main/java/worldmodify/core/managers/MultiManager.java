package worldmodify.core.managers;

import java.util.concurrent.CopyOnWriteArrayList;

public class MultiManager<T> {
	
	private CopyOnWriteArrayList<T> objects = new CopyOnWriteArrayList<T>();
	
	public void registerObject(T object) {
		if(!objects.contains(object)) objects.add(object);
	}

	public void deleteObject(T object) {
		if(objects.contains(object)) objects.remove(object);
	}

	public CopyOnWriteArrayList<T> getObjects() {
		return objects;
	}
	
}

package cl.security.observer.listeners;

import cl.security.observer.publisher.EventManager;

public class ListenerManager {
	
	public EventManager events;
	
	public ListenerManager() {
		this.events = new EventManager("verify_db");
	}
	
	public void verifyContentTable() {
		events.notifySubscriber("verify_db");
	}

}

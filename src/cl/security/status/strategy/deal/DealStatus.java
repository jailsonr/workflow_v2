package cl.security.status.strategy.deal;

import cl.security.status.strategy.StatusStrategy;

public class DealStatus implements Runnable{
	
	private StatusStrategy strategy = null;
	
	public DealStatus process(StatusStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	@Override
	public void run() {
		strategy.status();
		System.out.println("Ejecutando " + strategy.toString());
	}

}

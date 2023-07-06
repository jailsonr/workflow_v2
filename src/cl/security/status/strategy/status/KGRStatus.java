package cl.security.status.strategy.status;

import cl.security.status.strategy.StatusStrategy;

public class KGRStatus implements StatusStrategy{

	@Override
	public int status() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String statusFromCustomWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void acceptanceLogger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void overdraftLogger() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

}

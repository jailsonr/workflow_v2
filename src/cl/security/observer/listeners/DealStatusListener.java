package cl.security.observer.listeners;

public class DealStatusListener implements EventListener{
	
	private boolean isTimeToExecute = false;

	@Override
	public void setIfIsTimeToExecute() {
		// Hardcode - Aqui de sebe ir a la BD a revisar si hay data en la tabla y de ser asi se setea a true
		
		try {
			setTimeToExecute(true);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean isTimeToExecute() {
		return isTimeToExecute;
	}

	private void setTimeToExecute(boolean isTimeToExecute) {
		this.isTimeToExecute = isTimeToExecute;
	}
	
	

}

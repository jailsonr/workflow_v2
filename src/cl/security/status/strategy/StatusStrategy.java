package cl.security.status.strategy;

public interface StatusStrategy {
	
	public int status();
	public String statusFromCustomWindow();
	public void acceptanceLogger();
	public void overdraftLogger();

}

package cl.security.mdd.retries;

@FunctionalInterface
public interface RetryImplementation {
	
	void run(RetryLogic r);

}

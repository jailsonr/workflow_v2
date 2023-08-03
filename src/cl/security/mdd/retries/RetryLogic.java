package cl.security.mdd.retries;

public class RetryLogic {

	private int retryAttempts;
	final long TIME_TO_WAIT;
	private int dealReties;

	public RetryLogic(int retryAttempts, long timeToWait, int dealReties) {
		this.retryAttempts = retryAttempts;
		this.TIME_TO_WAIT = timeToWait;
		this.dealReties = dealReties;
	}

	public void retryImpl(RetryImplementation retryImplementation) {
		if (shouldRetry()) {
			retryAttempts--;
			dealReties++;
			retryImplementation.run(this);
			waitBeforeNextRetry();
		} else {

		}
	}

	public boolean shouldRetry() {
		return retryAttempts > 0;
	}
	
	public void stopExecution() {
		retryAttempts = 0;
	}

	public void waitBeforeNextRetry() {
		try {
			Thread.sleep(dealReties * TIME_TO_WAIT);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public int getRetryAttempts() {
		return retryAttempts;
	}

	public void setRetryAttempts(int retryAttempts) {
		this.retryAttempts = retryAttempts;
	}
	
	public int getDealReties() {
		return dealReties;
	}
	
	public void setDealReties(int dealReties) {
		this.dealReties = dealReties;
	}
	
}

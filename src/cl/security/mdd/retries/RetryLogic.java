package cl.security.mdd.retries;

public class RetryLogic {

	int retryAttempts;
	final long TIME_TO_WAIT;
	public int dealReties = 0;

	public RetryLogic(int retryAttempts, long timeToWait) {
		this.retryAttempts = retryAttempts;
		this.TIME_TO_WAIT = timeToWait;
	}

	public void retryImpl(RetryImplementation retryImplementation) {
		if (shouldRetry()) {
			retryAttempts--;
			retryImplementation.run();
			waitBeforeNextRetry();
		} else {

		}

	}

	public boolean shouldRetry() {
		return retryAttempts > 0;
	}

	public void waitBeforeNextRetry() {
		try {
			Thread.sleep(dealReties * TIME_TO_WAIT);
		} catch (Exception e) {
		}
	}

}

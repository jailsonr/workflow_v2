package cl.security.mdd.retries;

public class RetryLogic {

	int dealRetries;
	int retryAttempts;
	final long TIME_TO_WAIT;

	public RetryLogic(int retryAttempts, long timeToWait, int dealRetries) {
		this.retryAttempts = retryAttempts;
		this.TIME_TO_WAIT = timeToWait;
		this.dealRetries = dealRetries;
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
		return dealRetries < retryAttempts;
	}

	public void waitBeforeNextRetry() {
		try {
			Thread.sleep(TIME_TO_WAIT);
		} catch (Exception e) {
		}
	}

}

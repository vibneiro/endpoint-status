package com.ivan.endpointstats.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.LongAdder;

//@ThreadSafe
public class StatusHolder {

	private final LongAdder requestCount = new LongAdder();
	private final LongAdder successCount = new LongAdder();

	public void addRequestCount(long value) {
		requestCount.add(value);
	}

	public void addSuccessCount(long value) {
		successCount.add(value);
	}

	public long getRequestCount() {
		return requestCount.longValue();
	}

	public long getSuccessCount() {
		return successCount.longValue();
	}

	public double getSuccessRate() {
		Double rate = new Double(successCount.longValue() / requestCount.longValue());
		return new BigDecimal(rate.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

}

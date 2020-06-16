package instrumental;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 

Please write a small library which helps to track the number of events that happened during a specified window of time. As an example, 
it might be used to track how often a particular webpage is served. The library should support two operations:
1. Client should be able to signal that a single event happened (e.g. a page was served)
2. Client should be able to request the number of events that happened over a user-specified amount of time until current time. 
You may limit the supported timespan to a reasonable upper bound, like 5 minutes. 

Please only track counts, you do not have to track any data payload associated with an event. 

Since the main use for a component like this is presenting diagnostic information on demand, it doesn’t have to persist data. Additionally, 
please do not spend time writing an example application that integrates the library, because we will not base our assessment on it. 

The event count tracks past event for at most 300s(5min) and return the number of event within a specfic time range. It can handle 1M requests

 *
 */
public class EventCounter implements Runnable {		
	private int windowTime;
	private int startIndex;
	private int currentIndex;
	private List<AtomicInteger> counters;
	private ScheduledFuture<?> task;
	private AtomicBoolean atomicBoolean; 	
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	public EventCounter(int windowTime) {
		if (windowTime <= 0) {
			throw new IllegalArgumentException("Illegal input. The given specified window of time should be larger than 0");
		}
		this.startIndex = -1;
		this.currentIndex = -1;
		this.windowTime = windowTime;
		counters = new ArrayList<AtomicInteger>();
		atomicBoolean = new AtomicBoolean(false); 		
		for(int i = 0; i <= windowTime; i++) {
			counters.add(new AtomicInteger(0));
		}
	}
		
	public void open() {
		startIndex = (int)(System.currentTimeMillis() / 1000) % windowTime;
		currentIndex = startIndex;
		task = scheduler.scheduleAtFixedRate(this, 1000, 1000, TimeUnit.MILLISECONDS);
	}
			
	public void logEvent() {
		int currentIndexCopy = currentIndex;
		counters.get(currentIndexCopy).incrementAndGet();
	}
		
	public long countEvents(int startTime) {
		long count = 0;
		if (startTime > 0) {
			int updatedStartTime = Math.min(startTime, windowTime);
			int currentIndexCopy = currentIndex;
			int index = 0;
			for(int i = 0; i < updatedStartTime ; i++) {
				index = (currentIndexCopy - i) % windowTime;
				index = index < 0 ? windowTime + index : index;
				count += counters.get(index).get();
			}
		}
		return count;
	}
	
	public void close() {
		startIndex = -1;
		currentIndex = -1;
		task.cancel(true);
	}
	
	public final void run() {
		currentIndex = (int)(System.currentTimeMillis() / 1000) % windowTime;
		if(currentIndex == startIndex) {
			atomicBoolean.compareAndSet(false, true);
		}
		if(atomicBoolean.get()) {
			counters.get(currentIndex).set(0);
		}		
	}
}

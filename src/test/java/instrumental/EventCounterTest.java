package instrumental;

import org.junit.Assert;
import org.junit.Test;


public class EventCounterTest {

	@Test
	public void testLogEvent() throws InterruptedException {
		EventCounter counter = new EventCounter(300);
		counter.open();
		for(int i = 0; i < 10; i++) {
			counter.logEvent();
			Thread.sleep(100);
		}
		Assert.assertEquals(10, counter.countEvents(10));
		Thread.sleep(2000);
		for(int i = 0; i < 10; i++) {
			counter.logEvent();
			Thread.sleep(100);
		}
		Assert.assertEquals(20, counter.countEvents(25));
		Assert.assertEquals(20, counter.countEvents(300));
		Thread.sleep(1000);
		Assert.assertEquals(0, counter.countEvents(-1));						
		Assert.assertEquals(0, counter.countEvents(0));		
		counter.close();
	}
		
}

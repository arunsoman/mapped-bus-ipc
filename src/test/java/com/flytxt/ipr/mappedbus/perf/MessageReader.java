package com.flytxt.ipr.mappedbus.perf;
import org.junit.Test;

import com.flytxt.ipc.mappedbus.MappedBusMessage;
import com.flytxt.ipc.mappedbus.MappedBusReader;

public class MessageReader {

	@Test
	public void run(String fileName) {
		try {
			MappedBusReader reader = new MappedBusReader("/tmp/fileName", 20000000000L, 12);
			reader.open();

			PriceUpdate priceUpdate = new PriceUpdate();

			MappedBusMessage message = null;

			long start = System.nanoTime();
			for (int i = 0; i < 80000000; i++) {
				while (true) {
					if (reader.next()) {
						int type = reader.readType();
						switch (type) {
						case PriceUpdate.TYPE:
							message = priceUpdate;
							break;
						default:
							throw new RuntimeException("Unknown type: " + type);
						}
						reader.readMessage(message);
						break;
					}
				}
			}
			long stop = System.nanoTime();
			System.out.println("Elapsed: " + ((stop - start) / 1000000 ) + " ms");
			System.out.println("Per op: " + ((stop - start) / 80000000 ) + " ns");
			System.out.println("Op/s: " + (long)(80000000/((stop-start)/(float)1000000000)));

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
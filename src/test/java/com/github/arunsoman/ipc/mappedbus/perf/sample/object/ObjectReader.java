package com.github.arunsoman.ipc.mappedbus.perf.sample.object;
import com.github.arunsoman.ipc.mappedbus.MappedBusMessage;
import com.github.arunsoman.ipc.mappedbus.MappedBusReader;

public class ObjectReader {

	public static void main(String[] args) {
		ObjectReader reader = new ObjectReader();
		reader.run();	
	}

	public void run() {
		try {
			MappedBusReader reader = new MappedBusReader("/tmp/test-message", 2000000L, 12);
			reader.open();

			PriceUpdate priceUpdate = new PriceUpdate();
			
			MappedBusMessage message = null;

			while (true) {
				if (reader.next()) {
					boolean recovered = reader.hasRecovered();
					int type = reader.readType();
					switch (type) {
					case PriceUpdate.TYPE:
						message = priceUpdate;
						break;
					default:
						throw new RuntimeException("Unknown type: " + type);
					}
					reader.readMessage(message);					
					System.out.println("Read: " + message + ", hasRecovered=" + recovered);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
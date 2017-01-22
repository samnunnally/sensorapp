/**
 * 
 */
package sensorapp.controller;

import java.util.Collection;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author samuelnunnally
 *
 */
@RestController
public class LEDController {

	private static final Logger logger = LoggerFactory.getLogger(LEDController.class);

	@RequestMapping("/toggle-led")
	public String handleLED() throws InterruptedException {
		System.out.println("<--Pi4J--> GPIO Control Example ... started.");

		// create gpio controller
		GpioController gpio = GpioFactory.getInstance();

		Collection<GpioPin> pins = gpio.getProvisionedPins();
		GpioPinDigitalOutput pin = null;
		for (GpioPin proPin : pins) {
			if ("MyLED".equals(proPin.getName())) {
				pin = (GpioPinDigitalOutput) proPin;
			}
		}

		// provision gpio pin #01 as an output pin and turn on
		if (pin == null) {
			pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
		} else {
			pin.high();
		}
		// set shutdown state for this pin
		pin.setShutdownOptions(true, PinState.LOW);

		System.out.println("--> GPIO state should be: ON");

		Thread.sleep(1000);

		// turn off gpio pin #01
		pin.low();
		System.out.println("--> GPIO state should be: OFF");

		Thread.sleep(2000);

		// toggle the current state of gpio pin #01 (should turn on)
		pin.toggle();
		System.out.println("--> GPIO state should be: ON");

		Thread.sleep(3000);

		// toggle the current state of gpio pin #01 (should turn off)
		pin.toggle();
		System.out.println("--> GPIO state should be: OFF");

		Thread.sleep(4000);

		// turn on gpio pin #01 for 1 second and then off
		System.out.println("--> GPIO state should be: ON for only 1 second");
		pin.pulse(1000, true); // set second argument to 'true' use a blocking
								// call

		Future<?> f = pin.blink(500, 5000);

		if (f != null) {
			try {
				f.get();
			} catch (CancellationException | ExecutionException expected) {
				logger.warn(expected.toString(), expected);
			}
		}
		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and
		// scheduled tasks)
		// if (gpio != null) {
		// gpio.shutdown();
		// }

		return "success updated";
	}
}

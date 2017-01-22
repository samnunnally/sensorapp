/**
 * 
 */
package sensorapp.component;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import sensorapp.controller.LEDController;

/**
 * @author samuelnunnally
 *
 */
@Component
public class SwitchListener {

	private GpioPinDigitalInput myButton = null;

	@PostConstruct
	public void registerListener() throws InterruptedException {

		try {
			// create gpio controller
			GpioController gpio = GpioFactory.getInstance();

			// provision gpio pin #02 as an input pin with its internal pull
			// down
			// resistor enabled
			myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);

			// create and register gpio pin listener
			myButton.addListener(new GpioPinListenerDigital() {
				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
					// display pin state on console
					// System.out.println(" --> GPIO PIN STATE CHANGE: " +
					// event.getPin() + " = " + event.getState());
					try {
						new LEDController().handleLED();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
			System.out.println("Registered myButton");
		} catch (UnsatisfiedLinkError expected) {
			System.out.println("not running on pi");

		}
	}
}

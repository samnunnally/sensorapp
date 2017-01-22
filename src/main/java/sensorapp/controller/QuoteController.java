/**
 * 
 */
package sensorapp.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sensorapp.domain.Quote;

/**
 * @author samuelnunnally
 *
 */

@RestController
public class QuoteController {

	@RequestMapping("/")
	String home() throws InterruptedException, ExecutionException {


		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		return quote.toString();
	}

}

/**
 * 
 */
package sensorapp.controller;

import java.net.SocketException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;

import sensorapp.component.IPAddressComponent;

/**
 * @author samuelnunnally
 *
 */

@RestController
@JsonAutoDetect(getterVisibility=Visibility.NONE)
public class IPAddressController {

	@RequestMapping(value="/ip-addresses",produces = "application/json")
	@ResponseBody
	String getIPAddresses() throws SocketException, JsonProcessingException{
		return new IPAddressComponent().getIPAddresses();
	}
}

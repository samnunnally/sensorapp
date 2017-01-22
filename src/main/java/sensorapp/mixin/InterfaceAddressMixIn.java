/**
 * 
 */
package sensorapp.mixin;

import java.net.InetAddress;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author samuelnunnally
 *
 */
public abstract class InterfaceAddressMixIn {

	@JsonProperty("address")
	abstract InetAddress getAddress();

	@JsonProperty("broadcast")
	abstract InetAddress getBroadcast();

}

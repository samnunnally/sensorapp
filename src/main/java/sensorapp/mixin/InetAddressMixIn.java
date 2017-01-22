/**
 * 
 */
package sensorapp.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author samuelnunnally
 *
 */
public abstract class InetAddressMixIn {

	@JsonProperty("hostAddress")
	abstract String getHostAddress();

	@JsonProperty("canonicalHostName")
	abstract String getCanonicalHostName();

	@JsonProperty("hostName")
	abstract String getHostName();

	@JsonProperty("address")
	abstract byte[] getAddress();

}

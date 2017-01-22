/**
 * 
 */
package sensorapp.mixin;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author samuelnunnally
 *
 */
public abstract class NetworkInterfaceMixIn {

	@JsonProperty("displayName")
	abstract String getDisplayName();

	@JsonProperty("hardwareAddress")
	abstract byte[] getHardwareAddress();

	@JsonProperty("mtu")
	abstract int getMTU();

	@JsonProperty("name")
	abstract String getName();

	@JsonProperty("interfaceAddresses")
	abstract List<InterfaceAddress> getInterfaceAddresses();

	@JsonProperty("parent")
	abstract NetworkInterface getParent();
}

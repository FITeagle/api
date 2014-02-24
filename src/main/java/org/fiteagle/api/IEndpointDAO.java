package org.fiteagle.api;

import java.util.List;


public interface IEndpointDAO {

	public List<? extends Endpoint> findEndpoints(String serviceId, String regionId,
			String interfaceType);

	public Endpoint addEndpoint(Endpoint endpoint);

	public Endpoint findEndpoint(long endpointId);

	public Endpoint updateEndpoint(long endpointId, Endpoint endpoint);

	public void deleteEndpoint(long endpointId);

	public void deleteEndpointsForRegion(long regionid);

	public void deleteEndpointForServiceId(long serviceid);

}
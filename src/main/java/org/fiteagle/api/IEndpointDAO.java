package org.fiteagle.api;

import java.util.List;

public interface IEndpointDAO {

	public List<? extends IEndpoint> findEndpoints(String serviceId, String regionId,
			String interfaceType);

	public IEndpoint addEndpoint(IEndpoint endpoint);

	public IEndpoint findEndpoint(long endpointId);

	public IEndpoint updateEndpoint(long endpointId, IEndpoint endpoint);

	public void deleteEndpoint(long endpointId);

	public void deleteEndpointsForRegion(long regionid);

	public void deleteEndpointForServiceId(long serviceid);

}
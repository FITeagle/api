package org.fiteagle.api;

import java.util.List;

public interface IServiceDAO {

	public IService createService(IService service);

	public List<? extends IService> findServices(String type);

	public IService findService(long serviceid);

	public IService updateService(IService service);

	public void deleteService(long serviceid);

}
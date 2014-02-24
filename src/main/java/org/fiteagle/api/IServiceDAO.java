package org.fiteagle.api;

import java.util.List;


public interface IServiceDAO {

	public Service createService(Service service);

	public List<? extends Service> findServices(String type);

	public Service findService(long serviceid);

	public Service updateService(Service service);

	public void deleteService(long serviceid);

}
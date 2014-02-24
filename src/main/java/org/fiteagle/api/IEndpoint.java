package org.fiteagle.api;

import java.util.Map;

public interface IEndpoint {

	public long getId();

	public void setId(long id);

	public String getInterfaceType();

	public void setInterfaceType(String interfaceType);

	public long getService_id();

	public void setService_id(long serviceId);

	public String getName();

	public void setName(String name);

	public Map<String, ILinkInfo> get_links();

	public long getRegionId();

	public void setRegionId(long regionId);

	public String getUrl();

	public void setUrl(String url);

	public void addLink(String name, String string);

}
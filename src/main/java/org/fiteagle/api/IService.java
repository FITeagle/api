package org.fiteagle.api;

import java.util.Map;

public interface IService {

	public Map<String, ILinkInfo> get_links();

	public void set_links(Map<String, ILinkInfo> links);

	public void addLink(String name, ILinkInfo l);

	public String getType();

	public void setType(String type);

	public long getId();

	public void setId(long serviceId);

	public void addLinksWithId(String uriInfo);

	public void addLinksWithoutId(String uriInfo);

}
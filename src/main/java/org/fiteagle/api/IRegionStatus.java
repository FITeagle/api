package org.fiteagle.api;

import java.util.List;
import java.util.Map;

public interface IRegionStatus {

	public long getTimestamp();

	public void setTimestamp(long timestamp);

	public String getStatus();

	public void setStatus(String status);

	public long getRegion();

	public void setRegion(long id);

	public Map<String, List<ILinkInfo>> get_links();

	public void set_links(Map<String, List<ILinkInfo>> links);

	public void addLink(String name, ILinkInfo l);

	public boolean equals(Object obj);

	public void addLinksWithId(String uriInfo);

	public void addLinksWithoutId(String uriInfo);

}
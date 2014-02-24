package org.fiteagle.api;

import java.util.List;
import java.util.Map;

public interface IRegion {

	public Map<String, List<ILinkInfo>> get_links();

	public void set_links(Map<String, List<ILinkInfo>> links);

	public void addLink(String name, ILinkInfo l);

	public List<IContactInformation> getContacts();

	public void setContacts(List<IContactInformation> contacts);

	public long getId();

	public String getCountry();

	public void setCountry(String country);

	public String getLatitude();

	public void setLatitude(String latitude);

	public String getLongitude();

	public void setLongitude(String longitude);

	public String getAdminUsername();

	public void setAdminUsername(String adminUsername);

	public IRegionStatus getRegionStatus();

	public void setRegionStatus(IRegionStatus registrationStatus);

	public String getNodeType();

	public void setNodeType(String nodeType);

	public void setId(long id);

	public void addContact(IContactInformation contactInfo);

	public void addLinksWithId(String uriInfo);

	public void addLinksWithoutId(String uriInfo);

}
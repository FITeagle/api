package org.fiteagle.api;

import java.util.List;
import java.util.Map;

public interface IContactInformation {

	public long getId();

	public String getType();

	public void setType(String type);

	public String getName();

	public void setName(String name);

	public String getCountry();

	public void setCountry(String country);

	public String getFax();

	public void setFax(String fax);

	public String getPhone();

	public void setPhone(String phone);

	public String getEmail();

	public void setEmail(String email);

	public String getAddress();

	public void setAddress(String address);

	public boolean equals(Object obj);

	public Map<String, List<ILinkInfo>> get_links();

	public void set_links(Map<String, List<ILinkInfo>> links);

	public void addLink(String name, ILinkInfo l);

	public void addLinksWithId(String uriInfo);

	public void addLinksWithoutId(String uriInfo);

}
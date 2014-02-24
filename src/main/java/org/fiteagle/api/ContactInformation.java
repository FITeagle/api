package org.fiteagle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.swing.plaf.synth.Region;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
public class ContactInformation extends LinkableEntity {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getId()
	 */
	
	public long getId() {
		return id;
	}


	String name;
	String country;
	String fax;
	String phone;
	String email;
	String type;
	@ManyToOne(targetEntity=Region.class)
	@XmlTransient
	Region region;
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getType()
	 */
	
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setType(java.lang.String)
	 */
	
	public void setType(String type) {
		this.type = type;
	}

	String address;

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getName()
	 */
	
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setName(java.lang.String)
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getCountry()
	 */
	
	public String getCountry() {
		return country;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setCountry(java.lang.String)
	 */
	
	public void setCountry(String country) {
		this.country = country;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getFax()
	 */
	
	public String getFax() {
		return fax;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setFax(java.lang.String)
	 */
	
	public void setFax(String fax) {
		this.fax = fax;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getPhone()
	 */
	
	public String getPhone() {
		return phone;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setPhone(java.lang.String)
	 */
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getEmail()
	 */
	
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setEmail(java.lang.String)
	 */
	
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#getAddress()
	 */
	
	public String getAddress() {
		return address;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#setAddress(java.lang.String)
	 */
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#equals(java.lang.Object)
	 */
	
	public boolean equals(Object obj) {
		boolean ret =false;
		if(obj.getClass().equals(this.getClass())){
			ContactInformation toCompare  = (ContactInformation) obj;
			if(this.getAddress().equals(toCompare.getAddress())&&
			   this.getCountry().equals(toCompare.getCountry())&&
			   this.getEmail().equals(toCompare.getEmail())&&
			   this.getFax().equals(toCompare.getFax())&&
			   this.getName().equals(toCompare.getName())&&
			   this.getPhone().equals(toCompare.getPhone())&&
			   this.getType().equals(toCompare.getType())){
				ret = true;
			}
		}
		return ret;
	}
	

	@Transient
	Map<String, List<LinkInfo>> _links;
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#get_links()
	 */
	
	public Map<String,List<LinkInfo>> get_links() {
		return _links;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#set_links(java.util.Map)
	 */
	
	public void set_links(Map<String, List<LinkInfo>> links) {
		this._links = links;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#addLink(java.lang.String, org.fiteagle.xifi.api.model.LinkInfo)
	 */
	
	public void addLink(String name,LinkInfo l){
		List<LinkInfo> list;
		if(this._links == null){
			_links = new HashMap<>();
			list = new ArrayList<>();
			list.add(l);
		}else{
			list = _links.get(name);
			if(list == null)
				list = new ArrayList<>();
			list.add(l);
		}
		_links.put(name,list);
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#addLinksWithId(java.lang.String)
	 */
	
	public void addLinksWithId(String uriInfo) {
		this.addLink("self",  new LinkInfo(trimURI(uriInfo)+ "/" + this.getId()));
		
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IContactInformation#addLinksWithoutId(java.lang.String)
	 */
	
	public void addLinksWithoutId(String uriInfo) {
	
		
	}
	
}

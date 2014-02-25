package org.fiteagle.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
public class RegionStatus extends LinkableEntity implements Serializable{

	/**
	 * 
	 */
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 6096080413211248518L;


	@Id
	long region;
	
	
	long timestamp;
	
	String status;

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#getTimestamp()
	 */
	
	public long getTimestamp() {
		return timestamp;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#setTimestamp(long)
	 */
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#getStatus()
	 */
	
	public String getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#setStatus(java.lang.String)
	 */
	
	public void setStatus(String status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#getRegion()
	 */
	
	public long getRegion() {
		return region;
	}
	


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#setRegion(long)
	 */
	
	public void setRegion(long id) {
		this.region = id;
	}
	@Transient
	Map<String, List<LinkInfo>> _links;
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#get_links()
	 */
	
	public Map<String,List<LinkInfo>> get_links() {
		return _links;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#set_links(java.util.Map)
	 */
	
	public void set_links(Map<String, List<LinkInfo>> links) {
		this._links = links;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#addLink(java.lang.String, org.fiteagle.xifi.api.model.LinkInfo)
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
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#equals(java.lang.Object)
	 */
	
	
	public boolean equals(Object obj) {
		boolean ret = false;
		if(this.getClass().equals(obj.getClass())){
			RegionStatus toCompare = (RegionStatus) obj;
			if(this.region == toCompare.getRegion() && this.getStatus().equals(toCompare.getStatus()) && this.getTimestamp() == toCompare.getTimestamp()){
				ret = true;
			}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#addLinksWithId(java.lang.String)
	 */
	
	
	public void addLinksWithId(String uriInfo) {
		
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegionStatus#addLinksWithoutId(java.lang.String)
	 */
	
	
	public void addLinksWithoutId(String uriInfo) {
		String trimmedUri = trimURI(uriInfo);
		this.addLink("self",  new LinkInfo(trimmedUri));
		this.addLink("parent", new LinkInfo(trimmedUri.subSequence(0, trimmedUri.lastIndexOf("/")).toString()));
		
	}
}

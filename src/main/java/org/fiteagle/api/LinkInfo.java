package org.fiteagle.api;


public class LinkInfo {

	private String href;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fiteagle.xifi.api.model.ILinkInfo#getHref()
	 */
	
	public String getHref() {
		return href;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fiteagle.xifi.api.model.ILinkInfo#setHref(java.lang.String)
	 */
	
	public void setHref(String href) {
		this.href = href;
	}

	public LinkInfo(String href) {
		this.href = href;
	}

}

package com.dac.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "catalog")
public class Catalog {
	
	private int catid;
	private String name;
	private String region;
	private String assetname;
	private String assetdesc;
	private String cid;
	private String cname;
	private String sid;
	private String sname;
	private String model;
	private String tags;
	private boolean isrequired;
		
	public Catalog(int catid, String name, String region, String assetname, String assetdesc, String cid, String cname,
			String sid, String sname, String model, String tags, boolean isrequired) {
		super();
		this.catid = catid;
		this.name = name;
		this.region = region;
		this.assetname = assetname;
		this.assetdesc = assetdesc;
		this.cid = cid;
		this.cname = cname;
		this.sid = sid;
		this.sname = sname;
		this.model = model;
		this.tags = tags;
		this.isrequired = isrequired;
	}
	
	@XmlElement
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@XmlElement
	public String getAssetname() {
		return assetname;
	}
	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}
	@XmlElement
	public String getAssetdesc() {
		return assetdesc;
	}
	public void setAssetdesc(String assetdesc) {
		this.assetdesc = assetdesc;
	}
	@XmlElement
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	@XmlElement
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	@XmlElement
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	@XmlElement
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	@XmlElement
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@XmlElement
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	@XmlElement
	public boolean isIsrequired() {
		return isrequired;
	}
	public void setIsrequired(boolean isrequired) {
		this.isrequired = isrequired;
	}
	
	public Catalog() {
		
	}

	@Override
	public String toString() {
		return "Catalog [catid=" + catid + ", name=" + name + ", region=" + region + ", assetname=" + assetname
				+ ", assetdesc=" + assetdesc + ", cid=" + cid + ", cname=" + cname + ", sid=" + sid + ", sname=" + sname
				+ ", model=" + model + ", tags=" + tags + ", isrequired=" + isrequired + "]";
	}


}

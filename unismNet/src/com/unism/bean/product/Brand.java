package com.unism.bean.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Brand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -849073827216090698L;
	/**
	 * ID
	 */
	private String code;
	/**
	 * 
	 */
	private String name;
	/**
	 * 路径
	 */
	private String logopath;

	private Boolean visible = true;

	public Brand() {

	}

	public Brand(String name) {
		this.name = name;
	}

	public Brand(String name, String logopath) {
		this.name = name;
		this.logopath = logopath;
	}

	@Id
	@Column(length = 50, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// /images/brand/2012/04/25/12/brand.jpg
	@Column(length = 80, nullable = true)
	public String getLogopath() {
		return logopath;
	}

	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}

	@Column(nullable = false)
	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((logopath == null) ? 0 : logopath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((visible == null) ? 0 : visible.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brand other = (Brand) obj;
		if (logopath == null) {
			if (other.logopath != null)
				return false;
		} else if (!logopath.equals(other.logopath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (visible == null) {
			if (other.visible != null)
				return false;
		} else if (!visible.equals(other.visible))
			return false;
		return true;
	}

}

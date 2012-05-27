package com.unism.bean.product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 实体产品Bean对象
 * 
 * @author pdf
 * 
 */
@Entity
public class ProductType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3950084459529872195L;
	/** 类别id **/
	private Integer typeid;
	/**
	 * 类别名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 是否可见
	 */
	private Boolean visible = true;

	/**
	 * 子类
	 */
	private Set<ProductType> childType = new HashSet<ProductType>();

	/**
	 * 父类
	 */
	private ProductType parent;

	public ProductType() {

	}

	public ProductType(Integer typeid) {
		this.typeid = typeid;
	}

	public ProductType(String name, String note) {
		this.name = name;
		this.note = note;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "parent", fetch = FetchType.EAGER)
	public Set<ProductType> getChildType() {
		return childType;
	}

	public void setChildType(Set<ProductType> childType) {
		this.childType = childType;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "parentid")
	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}

	@Column(length = 36, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 200)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(nullable = false)
	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeid == null) ? 0 : typeid.hashCode());
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
		ProductType other = (ProductType) obj;
		if (typeid == null) {
			if (other.typeid != null)
				return false;
		} else if (!typeid.equals(other.typeid))
			return false;
		return true;
	}

}

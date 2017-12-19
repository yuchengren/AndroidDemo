package com.yuchengren.mvp.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yuchengren on 2017/12/19.
 */
@Entity
public class MenuEntity {
	@Id(autoincrement = true)
	@Property(nameInDb = "id")
	private Long id;
	@NotNull
	@Property(nameInDb = "code")
	private String code;
	@Property(nameInDb = "parentCode")
	private String parentCode;
	@Property(nameInDb = "comment")
	private String comment;
	@Property(nameInDb = "created")
	private Date created;
	@Property(nameInDb = "modified")
	private Date modified;

	@Transient
	private boolean isLeafed;//是否有子节点

	@Generated(hash = 1185431099)
	public MenuEntity(Long id, @NotNull String code, String parentCode,
			String comment, Date created, Date modified) {
		this.id = id;
		this.code = code;
		this.parentCode = parentCode;
		this.comment = comment;
		this.created = created;
		this.modified = modified;
	}

	@Generated(hash = 1441921350)
	public MenuEntity() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}



}

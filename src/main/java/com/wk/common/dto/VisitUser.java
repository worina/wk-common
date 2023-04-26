package com.wk.common.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VisitUser implements Serializable {

	private Integer id;
	private String name;
	
	private String mobile;
	
	private String password;
	
	private String dingUserId;
	private String extension;
	
	private String deptIds;
	
	private Integer status;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date loginTime;
	
	private Integer admin;

	private String client;


}

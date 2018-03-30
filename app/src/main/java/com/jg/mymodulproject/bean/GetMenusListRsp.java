package com.jg.mymodulproject.bean;

import java.io.Serializable;
import java.util.List;

public class GetMenusListRsp implements Serializable {

	public String customerId; // 登录用户ID
	public String menuCode; // 功能编号
	public String menuNameShow; // 功能名称
	public String menuUrl;
	public int menulocUrl;


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuNameShow() {
		return menuNameShow;
	}

	public void setMenuNameShow(String menuNameShow) {
		this.menuNameShow = menuNameShow;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public int getMenulocUrl() {
		return menulocUrl;
	}

	public void setMenulocUrl(int menulocUrl) {
		this.menulocUrl = menulocUrl;
	}
}

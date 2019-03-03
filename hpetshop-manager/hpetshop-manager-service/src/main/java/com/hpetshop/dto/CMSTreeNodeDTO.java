package com.hpetshop.dto;

/**
 * 
 * <ul>
 * <li>文件名称: CMSTreeNodeDTO</li>
 * <li>文件描述: 用于CMS首页栏目的异步树dto</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月7日</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 * 
 * @author wushaochuan
 * @version 1.0.0
 */
public class CMSTreeNodeDTO {
	// easyui异步树的json
	/**
	 * [{ "id": 1, "text": "Node 1", "state": "closed", "children": [{ "id": 11, "text": "Node 11" },{ "id": 12, "text": "Node 12" }] },{ "id": 2, "text": "Node 2", "state": "closed" }]
	 */
	private Long id;
	private String text;
	private String state;// 1是closed 2是open

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

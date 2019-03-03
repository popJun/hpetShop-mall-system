package com.hpetshop.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * <ul>
 * <li>文件名称: CatNodeDTO</li>
 * <li>文件描述:商品分类节点格式</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月6日</li>
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
public class CatNodeDTO {
	/*
	 * json数据格式data: [ { u: "/products/1.html", n: "<a href='/products/1.html'>犬类</a>", i: [ { u: "/products/2.html", n: "贵宾犬", i: [ "/products/3.html|黑色", "/products/4.html|黄色", "/products/5.html|白色", "/products/6.html|紫色" ] },
	 */
	@JsonProperty("n")
	private String name;// 拼装json数据中的n
	@JsonProperty("u")
	private String url;// 拼装json数据中的u
	@JsonProperty("i")
	private List<?> item;// 拼装json数据中的list

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<?> getItem() {
		return item;
	}

	public void setItem(List<?> item) {
		this.item = item;
	}

}

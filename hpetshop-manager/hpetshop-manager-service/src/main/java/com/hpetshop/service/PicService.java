package com.hpetshop.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * <ul>
 * <li>文件名称: PicService</li>
 * <li>文件描述: 用于图片上传到FTP的service</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月4日</li>
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
public interface PicService {
	/**
	 * 用于将将图片上传的FTP
	 * 
	 * @param multipartFile
	 * @return
	 */
	public Map<String, Object> picUpload(MultipartFile multipartFile);
}
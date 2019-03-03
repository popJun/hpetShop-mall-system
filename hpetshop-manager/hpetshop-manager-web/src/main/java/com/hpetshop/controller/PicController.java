package com.hpetshop.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hpetshop.service.PicService;

/**
 * 
 * <ul>
 * <li>文件名称: PicController</li>
 * <li>文件描述:用于图片上传到ftp</li>
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
@Controller
public class PicController {
	@Autowired
	private PicService picService;

	@RequestMapping("pic/upload")
	@ResponseBody
	public String PicUpdate(MultipartFile uploadFile) {
		Map<String, Object> resultMap = picService.picUpload(uploadFile);
		// 为了兼容性（火狐无法自动转化）
		String json = JSONObject.toJSONString(resultMap);
		return json;

	}
}

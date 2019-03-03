package com.hpetshop.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hpetshop.service.PicService;
import com.hpetshop.utils.FTPUtil;
import com.hpetshop.utils.IDUtils;

/**
 * 
 * <ul>
 * <li>文件名称: PicServiceImpl</li>
 * <li>文件描述:用于图片上传到FTP</li>
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
@Service
public class PicServiceImpl implements PicService {

	@Value("${FTP.HOST}")
	private String FTPHost;// ftp主机名

	@Value("${FTP.PORT}")
	private Integer FTPPort;// ftp端口号

	@Value("${FTP.USERNAME}")
	private String ftpUserName;// 登录用户名

	@Value("${FTP.USERPASSWOERD}")
	private String FTPUserPassword;// 登录密码

	@Value("${FTP.BASEPATH}")
	private String FTPBasePath;// 保存图片的基础路径

	@Value("${FTP.PICBASEPATH}")
	private String FTPPicBashPth;// 图片回显时候的文件夹路径

	/**
	 * 用于照片的上传
	 * 
	 * @param multipartFile
	 * @return
	 */
	@Override
	public Map<String, Object> picUpload(MultipartFile multipartFile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 取原始文件名
			String fileOrgName = multipartFile.getOriginalFilename();
			String ext = fileOrgName.substring(fileOrgName.lastIndexOf("."));
			// 文件上传图片的衍生文件夹
			String filePath = new DateTime().toString("/");
			// 生成图片名字
			String fileName = IDUtils.genImageName() + ext;
			boolean result = FTPUtil.uploadFile(FTPHost, FTPPort, ftpUserName, FTPUserPassword, FTPBasePath, filePath, fileName, multipartFile.getInputStream());

			if (result) {
				resultMap.put("error", 0);
				resultMap.put("url", FTPPicBashPth + fileName);
			} else {
				resultMap.put("error", 1);
				resultMap.put("message", "图片上传失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}

package com.platform.sign;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.base.BaseController;
import com.framework.utils.FileWriter;
import com.framework.utils.ResponseUtil;

/**
 * 
* @ClassName: PDFController  
* @Description: TODO 生成PDF
* @author cjwei7  
* @date 2018年7月19日  
*
 */
@Controller
@RequestMapping("pdf")
public class PDFController extends BaseController {
	
	/**
	 * 跳转到在线签名页面
	 */
	@RequestMapping("toPDFPage")
	public String toSignPage(HttpServletRequest request){
		return ("/web/pdf/pdf_page");
	}
	
	/**
	 * 
	* @Title: saveSignPhoto  
	* @Description: TODO 将在线签名保存为图片
	* @param @param request
	* @param @param response    参数  
	* @return void    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/generatePDF")
	@ResponseBody
	public String generatePDF(HttpServletRequest request,HttpServletResponse response,String imageData) {
		    String filePath = "";
			try {
				// 签名图片信息（不含前缀data:image/jpeg;base64）
				String newFileName = new DateTime(new Date()).toString("yyyyMMddHHmmssSSSS")+".jpeg";
				
				String rootPath = request.getSession().getServletContext().getRealPath("/upload");
				
				//上传签名图片至服务器
				filePath = FileWriter.uploadBase64Img(imageData,newFileName);  //重名名图片上传
				if(filePath.indexOf(":") > -1){ //windows
					filePath = filePath.replaceAll("\\\\",   "/");
				}
	
				if(StringUtils.isBlank(filePath)){
					return ResponseUtil.renderFailure("在线签名图片上传失败。");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseUtil.renderFailure("操作失败。");
			}
			return ResponseUtil.renderSuccess(filePath);
	}
	
}

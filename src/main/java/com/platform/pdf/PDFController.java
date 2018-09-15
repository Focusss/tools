package com.platform.pdf;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.base.BaseController;
import com.framework.core.common.Constant;
import com.framework.utils.ResponseUtil;
import com.framework.utils.pdf.PDFUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
				String saleManName = request.getParameter("saleManName");
				String saleLegalMan = request.getParameter("saleLegalMan");
				String saleLegalManTel = request.getParameter("saleLegalManTel");
				String saleRegisterAdr = request.getParameter("saleRegisterAdr");
				String salePostalCode = request.getParameter("salePostalCode");
				String businessLicense = request.getParameter("businessLicense");
				String bank = request.getParameter("bank");
				String account = request.getParameter("account");
				String fileNames = request.getParameter("fileNames");
				
				JSONObject data = new JSONObject();
				data.element("saleManName", saleManName);
				data.element("saleLegalMan", saleLegalMan);
				data.element("saleLegalManTel", saleLegalManTel);
				data.element("saleRegisterAdr", saleRegisterAdr);
				data.element("salePostalCode", salePostalCode);
				data.element("businessLicense", businessLicense);
				data.element("bank", bank);
				data.element("account", account);
				JSONArray jsonArray = new JSONArray();
				if(StringUtils.isNotBlank(fileNames)){
					for(String fileName : fileNames.split(",")){
						JSONObject jsonObject = new JSONObject();
						jsonObject.element("url", Constant.UPLOAD_PATH + File.separator + fileName);
						jsonArray.add(jsonObject);
					}
				}
				data.element("images", jsonArray);
				
				filePath = PDFUtil.createPDF(Constant.UPLOAD_PATH, "demo.pdf", data);
			
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseUtil.renderFailure("操作失败。");
			}
			return ResponseUtil.renderSuccess("demo.pdf");
	}
	
}

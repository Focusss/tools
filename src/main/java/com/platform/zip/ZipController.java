package com.platform.zip;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.framework.core.base.BaseController;
import com.framework.core.common.Constant;
import com.framework.utils.AjaxUtils;
import com.framework.utils.Decompression;
import com.framework.utils.FileDownloadHandle;
import com.framework.utils.FileWriter;
import com.framework.utils.ZipByAntUtils;

import net.sf.json.JSONObject;

/**
 * 
* @ClassName: SignatureController  
* @Description: TODO 在线签名  
* @author cjwei7  
* @date 2018年7月16日  
*
 */
@Controller
@RequestMapping("zip")
public class ZipController extends BaseController {
	
	public static  Integer checkStatus=null;//数据校验准确性校验状态，0 正在进行， 1 成功
	public static  Integer currentCount=null;//当前下载个数
	public static  Integer totalCount=null;//总行数

	
	/**
	 * 跳转到批量下载页面
	 */
	@RequestMapping("toBatchPage")
	public String toBatchPage(HttpServletRequest request){
		List<String> list = new ArrayList<String>();
		try {
			String imagesPath = request.getSession().getServletContext().getRealPath("/images");
			File imgDirFile = new File(imagesPath);
			getChildFile(imgDirFile, list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		return ("/web/zip/zip_page");
	}
	
	
	
	@RequestMapping(value = "/download")
	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,String imageData) {
		    String tempDirectory = Constant.UPLOAD_PATH + File.separator+"temp";
			try {
				checkStatus = 0 ;
				// 签名图片信息
			   //查找images目录下的图片
				List<String> list = new ArrayList<String>();
				String imagesPath = request.getSession().getServletContext().getRealPath("/images");
				File imgDirFile = new File(imagesPath);
				if(imgDirFile.exists()){
					getChildFile(imgDirFile, list);
					if(list.size() > 0){
						totalCount = list.size();
						currentCount = 0; //初始话导入的数量为0
						checkStatus=1;//校验已经结束
						for(String fileName : list){
							File file = new File(imagesPath + File.separator + fileName);
							InputStream is = new FileInputStream(file);
							ZipByAntUtils.updateFile(is,file.getName(), tempDirectory+File.separator); //上传文件到该目录下,在根目录下创建文件，有可能因为权限不足而报错
							currentCount ++;
						}
					}
				}
				
				//压缩
				String zipName = "download.zip";
				String zipPath = tempDirectory+File.separator+zipName;
				ZipByAntUtils.compress(tempDirectory, new File(zipPath));   //对改目录下的文件进行压缩打包
				//下载压缩包
				File file=new File(zipPath);
				FileDownloadHandle.downloadFile(file, zipName, response);
							
			} catch (Exception e) {
				AjaxUtils.renderFailure(e.getMessage(), response);
				e.printStackTrace();
			}finally{
				ZipByAntUtils.deleteFile(new File(tempDirectory+File.separator));   //删除临时目录下载的文件
			}
	}
	
	/**
	 * 查询压缩的状态
	 */
	@RequestMapping(value="/getStatus", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getStatus() {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer status = null;// 0正在进行 ,1成功
		Integer hasCompressCount = null;// 已压缩个数
		
		if (checkStatus != null) {
			status = checkStatus;
			if (status == 0) {
				map.put("stage", "1");
				map.put("info", "正在校验压缩数据");
			} else {
				if (currentCount !=null && currentCount > 0) {
					hasCompressCount = currentCount;
					if (hasCompressCount < totalCount) {
						map.put("stage", "2");
						map.put("info", "校验成功开始,已成功压缩个数为：" + hasCompressCount);
						map.put("number",hasCompressCount);
						map.put("allNumber",totalCount);
					} else {
						map.put("allNumber",totalCount);
						map.put("stage", "3");
						map.put("info", "导入成功！");
						
						//重新初始化
						checkStatus= null;
						currentCount=null;
						totalCount=null;
						
					}
				}
			}
		}else {
			map.put("info", "读取校验失败");
		}
		return map;
	}
	
	/**
	 * 跳转到导入Zip页面
	 */
	@RequestMapping("toImportPage")
	public String toImportPage(HttpServletRequest request){
		
		return ("/web/zip/import_zip_page");
	}
	
	/**
	 * 
	* @Title: toImportPage  
	* @Description: TODO  导入压缩文件并解压
	* @param @param bean
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("importZip")
	public String importZip(HttpServletRequest request){
		String errormsg=null;
		String importstatus="false";
		File file = null ; 
		
	    String  dirPath = Constant.UPLOAD_PATH  +File.separator
	    		         + "zip"+File.separator+ new DateTime(new Date()).toString("yyyyMMddHHmmssSSSS"); //输出路径（文件夹目录）  
		//错误文件名
	    String fileName = "error_msg.txt";
		String path = request.getSession().getServletContext().getRealPath("/upload") + File.separator + fileName;
		//新建日志文件
		FileOutputStream fos = null;
	    BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		String importErrorMsg = null;
	    try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile upfile = multipartRequest.getFile("file"); 
			String uploadFileName =  upfile.getOriginalFilename();
			if(!(uploadFileName.endsWith(".zip") ||uploadFileName.endsWith(".rar"))){
				throw new ServiceException("只能上传.zip或.rar文件！");
			}
			
			File directoryFile = new File(dirPath);
    		// 创建文件夹
    		if (!directoryFile.isDirectory()) {
    			directoryFile.mkdirs();
    		}
			file = new File(dirPath + File.separator +uploadFileName );
			upfile.transferTo(file);
		    
			if(uploadFileName.endsWith(".zip")){
				Decompression.unzip(file, dirPath);
			}else if(uploadFileName.endsWith(".rar")){
				Decompression.unrar(file, dirPath);
			}
			importstatus = "true";
		}catch (Exception e) {
			importstatus = "false";
			fileName= "";
			errormsg= e.getMessage();
			e.printStackTrace();
		}finally {
		   
		    //有错误时才执行
			if(StringUtils.isNotBlank(errormsg)){
				
				FileWriter.deleteAll(new File(dirPath));
			}
			
			if (StringUtils.isNotBlank(importErrorMsg)) {
				errormsg = "导入文件存在错误，详细内容请看错误日志！";
				importstatus = "false";
				try {
					File errorFile = new File(path);
					if(!errorFile.exists()){
						errorFile.getParentFile().mkdirs();          
					}
				    errorFile.createNewFile();
				    fos = new FileOutputStream(errorFile);
				    osw = new OutputStreamWriter(fos);
					bw = new BufferedWriter(osw);
				    bw.write(importErrorMsg);
				    bw.newLine();
					bw.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(bw != null){
							bw.close();
						}
						if(osw != null){
							osw.close();
						}
						if(fos != null){
							fos.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
			//导入成功删除错误日志文件
			if("true".equals(importstatus)){
				File oldFile = new File(path);
				if(oldFile.exists()){
					oldFile.delete();
				}
				fileName = "";
			}
			if(file != null){
				file.delete();
			}
		}
		request.setAttribute("fileName", fileName);
		request.setAttribute("importstatus", importstatus);
		if("true".equals(importstatus)){
			List<JSONObject> list = getDecompressionFiles(dirPath);
			request.setAttribute("list", list);
		}
		return ("/web/zip/import_zip_page");
	}
	
	/**
	 * 
	* @Title: getDecompressionFiles  
	* @Description: TODO 获取解压后的文件
	* @param @param dirPath
	* @param @return    参数  
	* @return List<JSONObject>    返回类型  
	* @throws
	 */
	private List<JSONObject>  getDecompressionFiles(String dirPath){
		File file = new File(dirPath);
		List<JSONObject> list = new ArrayList<JSONObject>();
		File[] files= file.listFiles();
		for(File childFile : files){
			String fileName = childFile.getName();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			JSONObject json = new JSONObject();
			json.element("name",fileName);
			String path = childFile.getAbsolutePath();
			if(isWindows()){
				json.element("url", path.replaceAll("\\\\", "/"));
			}
			
			if(ext.equals("doc") || ext.equals("docx")){
				ext = "doc-32.gif";
			}
			else if(ext.equals("ppt") || ext.equals("pptx")){
				ext = "ppt-32.gif";
			}else if(ext.equals("txt")){
				ext = "txt-32.gif";
			}else if(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")
					|| ext.equals("bmp") || ext.equals("gif")){
				ext = "jpg-32.gif";
			}else if(ext.equals("xls") || ext.equals("xlsx")){
				ext = "xls-32.gif";
			}else if(ext.equals("pdf")){
				ext = "pdf-32.gif";
			}else if(ext.equals("exe") || ext.equals("msi")){
				ext = "exe-32.gif";
			}else if(ext.equals("rar") || ext.equals("zip") || ext.equals("gz")){
				ext = "rar-32.gif";
			}else if(ext.equals("vsd")){
				ext = "vsd-32.gif";
			}else{
				ext = "unknow.gif";
			}
			json.element("ext", ext);
			list.add(json);
		}
		
		return list;
	}
	

	/**
	 * 
	* @Title: downloadErrorFile  
	* @Description: TODO 下载错误日志
	* @param @param request    参数  
	* @return void    返回类型  
	* @throws
	 */
	@RequestMapping("downloadErrorFile")
	public void downloadErrorFile(HttpServletRequest request){
		String fileName = "error_msg.txt";
		String path = request.getSession().getServletContext().getRealPath("/upload/"+fileName);
		File file = new File(path);
        FileDownloadHandle.downloadFile(file, fileName,response);
	}
	
	/**
	 * 
	 * @Title: downloadFile  
	 * @Description: TODO 下载错误日志
	 * @param @param request    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request){
		String url = request.getParameter("url");
		url = URLDecoder.decode(url);
		if(isWindows()){
			url = url.replaceAll("/","\\\\");
			
		}
		String[] fileNameArr = url.split("\\\\");
		File file = new File(url);
		FileDownloadHandle.downloadFile(file, fileNameArr[fileNameArr.length-1],response);
	}
	
	public boolean isWindows() {
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}
	
	private void getChildFile(File file,List<String> list){
		if(file.exists()){
			if (file.isDirectory()) {
				for(File childFile : file.listFiles()){
					getChildFile(childFile, list);
				}
			}else{
				String pFileName = file.getParentFile().getName();
				String fileName = file.getName();
				if(!"images".equals(pFileName)){
					fileName = pFileName + File.separator + fileName;
				}
				list.add(fileName);
			}
		}
	}
	
	
}

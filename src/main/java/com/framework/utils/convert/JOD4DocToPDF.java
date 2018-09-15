package com.framework.utils.convert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/*import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.JComException;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;*/

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

/**
 * 文档转换工具
 * 
 */
public class JOD4DocToPDF {
	/**
	 * openoffice 文件转换服务端口
	 */
	private final static int OPENOFFICE_SERVICE_PORT = 8100;
	/**
	 * swftools工具目录
	 */
	private final static String SWFTOOL_DIR = "F:\\swftools";
	/**
	 * pdf转swf语言包路径
	 */
	private final static String PDF2SWF_LANG = "F:\\xpdf-chinese-simplified";
	
	public String sourcePath;
	public String destPath;
	
	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	/**
	 * 利用OpenOffice 转换office to pdf
	 * @param fileName
	 * @param documentPath
	 * @param pdfDocumentPath
	 * @param swfDocumentPath
	 * @param jpgDocumentPath
	 * @param ext
	 */
	public void docToPdfByOpenOffice(String fileName,String documentPath, String pdfDocumentPath,String swfDocumentPath,String jpgDocumentPath,String ext){
		 // connect to an OpenOffice.org instance running on port 8100
		String imagepath = jpgDocumentPath;
		
		if(ext.equals("pdf")){
			//生成pdf第一页缩略图
		    try {
				tranfer(pdfDocumentPath,imagepath,0.3f);
			} catch (PDFException e1) {
				e1.printStackTrace();
			} catch (PDFSecurityException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				PDF2SWF(pdfDocumentPath,swfDocumentPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(ext.equals("gif") || ext.equals("jpg") || ext.equals("gpeg") || ext.equals("png") || ext.equals("bmp")){
			try {
				Picture2SWF(documentPath,swfDocumentPath,ext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPENOFFICE_SERVICE_PORT);
		    try{
		    	connection.connect();
			    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
			    //生成pdf
			    converter.convert(new File(documentPath), new File(pdfDocumentPath));
			    //生成pdf第一页缩略图
			    try {
					tranfer(pdfDocumentPath,imagepath,0.3f);
				} catch (PDFException e1) {
					e1.printStackTrace();
				} catch (PDFSecurityException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			    try {
			    	PDF2SWF(pdfDocumentPath,swfDocumentPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }catch(ConnectException cex){
		    	cex.printStackTrace();
		    }finally{
		    	//File pdfFile = new File(pdfDocumentPath);
	    		//pdfFile.delete();
		    	if(connection!=null){
		    		connection.disconnect();
		    		connection = null;
		    	}
		    }
		}
	}
	
	
	private String Picture2SWF(String documentPath, String swfDocumentPath,String ext) throws Exception{
		File source = new File(documentPath);
		if (!source.exists()) {
			throw new Exception();
		}
		File dest=new File(new File(swfDocumentPath).getPath().substring(0, new File(swfDocumentPath).getPath().lastIndexOf("\\")+1));
		if (!dest.exists()) {
			dest.mkdirs();
		}
		source.setReadable(false);
		String outputFile =swfDocumentPath;//只生成一个swf文件
		String command = "";
		if(ext.equals("jpg") || ext.equals("jpeg")){
			command = SWFTOOL_DIR +  File.separator + "jpeg2swf \""+documentPath+"\" -o \""+swfDocumentPath+"\"";	
		}else if(ext.equals("png")){
			command = SWFTOOL_DIR +  File.separator +"png2swf \""+documentPath+"\" -o \""+swfDocumentPath+"\"";
		}else if(ext.equals("gif")){
			command = SWFTOOL_DIR +  File.separator + "gif2swf \""+documentPath+"\" -o \""+swfDocumentPath+"\"";
		}
		  
		Process process = Runtime.getRuntime().exec(command);

		InputStreamWathThread inputWathThread = new InputStreamWathThread(process);
		inputWathThread.start();
		ErrorInputStreamWathThread errorInputWathThread = new ErrorInputStreamWathThread(process);
		errorInputWathThread.start();
		
		try {
			process.waitFor();//等待子进程的结束，子进程就是系统调用文件转换这个新进程
			inputWathThread.setOver(true);//转换完，停止流的处理
			errorInputWathThread.setOver(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println(e);
		}
		return outputFile;
		
	}

	public String PDF2SWF(String sourcePath,String destPath) throws Exception {
		File source = new File(sourcePath);
		
		if (!source.exists()) {
			throw new Exception();
		}
		File dest=(new File(destPath)).getParentFile();
		if (!dest.exists()) {
			dest.mkdirs();
		}
		source.setReadable(false);
	
		String outputFile =destPath;//只生成一个swf文件
		//String command = "c:\\Program Files\\SWFTools\\pdf2swf \""+sourcePath+"\" -o \""+outputFile+"\" -s languagedir=\"c:\\xpdf-chinese-simplified\" -T 9";  
		Process process = Runtime.getRuntime().exec(new String[]{SWFTOOL_DIR + File.separator + "pdf2swf",sourcePath,"-o",outputFile,"-s","languagedir="+PDF2SWF_LANG , "-T", "9"});

		InputStreamWathThread inputWathThread = new InputStreamWathThread(process);
		inputWathThread.start();
		ErrorInputStreamWathThread errorInputWathThread = new ErrorInputStreamWathThread(process);
		errorInputWathThread.start();
		
		try {
			process.waitFor();//等待子进程的结束，子进程就是系统调用文件转换这个新进程
			inputWathThread.setOver(true);//转换完，停止流的处理
			errorInputWathThread.setOver(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println(e);
		}
		return outputFile;
	}
	
	
	
	private void tranfer(String filepath, String imagepath, float zoom)	throws PDFException, PDFSecurityException, IOException {
		// ICEpdf document class
		Document document = new Document();
		float rotation = 0f;
		document.setFile(filepath);
		//int maxPages = document.getPageTree().getNumberOfPages();
		//System.out.println("page counts="+maxPages);
		BufferedImage img = (BufferedImage) document.getPageImage(0,GraphicsRenderingHints.SCREEN,Page.BOUNDARY_CROPBOX, rotation,zoom);
		Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("jpg");
		ImageWriter writer = (ImageWriter) iter.next();
		File dest= (new File(imagepath)).getParentFile();
		if (!dest.exists()) {
			dest.mkdirs();
		}
		File outFile = new File(imagepath);
		FileOutputStream out = new FileOutputStream(outFile);
		ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
		writer.setOutput(outImage);
		writer.write(new IIOImage(img, null, null));
		outImage.close();
		out.close();
		document.dispose();
		writer.dispose();
	}
	
}

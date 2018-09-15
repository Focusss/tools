package com.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * 压缩zip类
 * 
 * @author Administrator
 * 
 */
public class ZipByAntUtils {
	/*public static void main(String[] args) {
		compress("G:\\sign\\201807", new File("G:\\sign\\201807.zip"));
		
	}
*/
	/**
	 * 打zip压缩包 src要压缩的路径 压缩文件file
	 */
	public static void compress(String srcPathName, File zipFile) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists()) {
			throw new RuntimeException(srcPathName + "不存在！");
		}

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setEncoding("GBK");// 设置编码，防止压缩文件名字乱码，还有被压缩文件的乱码

		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		// fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); 排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute(); // 执行生成
	}

	public static void updateFile(InputStream is, String fileName, String savePath) {
		OutputStream ops = null;
		try {
			File realPath = new File(savePath);
			if (!realPath.exists()) {
				realPath.mkdirs();
			}
			File destFile = new File(savePath, fileName);
			ops = new FileOutputStream(destFile);
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = is.read(b)) > 0) {
				ops.write(b, 0, length);
			}
			ops.close();
		} catch (Exception e) {
			System.out.println(fileName);
			e.printStackTrace();
		} finally {
			if (null != ops) {
				try {
					ops.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteFile(File file) {
		if(!file.exists()){
			return;
		}
		File[] fileArray = file.listFiles();
		if (fileArray.length > 0) {
			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isFile()) {
					if (fileArray[i].delete()) {
					} else {
						System.out.println("删除不成功");
					}
				} else {
					deleteFile(fileArray[i]);
				}
			}
		}
		if (file.delete()) {
		} else {
			System.out.println("删除不成功");
		}
	}
}
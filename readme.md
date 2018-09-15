## 功能点
1. 在线签名
2. 导出数据到PDF文件
3. 批量下载文件 
4. 压缩包导入
5. 文档上传、转换及预览

## 依赖及原理

1. 在线签名使用**jsignature(js插件)**生成签名图片数据(*base64格式*)，在后台Tom过**FileWriter**类中的**uploadBase64Img()**方法保存到磁盘上。
2. 生成电子工单pdf需要使用包**iText-2.0.8.jar**、**iTextAsian.jar**、**itext-rtf-2.1.7.jar**
3. 批量下载需要使用包**ant-1.6.5.jar**
4. 压缩包导入需要使用**junrar-0.7.jar**(解压rar文件)、**ant-1.6.5.jar**(解压zip文件)
5. 文件上传、转换及生成pdf、swf、缩略图文件  
   1. 原理
   转换过程：文档-->pdf-->swf。这里需要两个工具的支持，一个是利用OpenOffice将上传文档转换为pdf,一个是利用swftools将上一步的pdf转换为swf，该文件用于预览功能。至于文档的缩略图是利用icepdf包生成的。
   2. 环境准备
   安装好Apache OpenOffice、swftools软件,并配置好相关环境，启动openoffice才能正常使用。**本项目使用的OpenOffice、swftools环境是Windows**
   安装方式、环境配置及启动过程见[openOffice、swftools安装及环境配置过程](https://focusss.github.io/2018/09/14/openOffice和swftools安装及环境配置过程)
   3. 前端使用uploadify插件进行多文件上传，同时需要浏览器开启**允许flash运行**。	
![前端正常上传页面](https://github.com/Focusss/tools/blob/master/src/main/webapp/images/screen/upload.png)	
   4. 后台主要用到的jar包**jodconverter-2.2.2.jar**(生成pdf文件)、**icepdf-core-6.1.2.jar**(缩略图)

6. 文件预览需要先将文件转换为swf格式，然后利用**flexpaper_flash,js**、**FlexPaperViewer.swf**插件进行预览。  
注：保存路径及swf文件命名不能存在中文。

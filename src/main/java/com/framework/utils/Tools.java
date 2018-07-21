package com.framework.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Tools {
	private static int BUFFER_SIZE = 1024;

	// 判断对象是否为空
	public static boolean isBlank(Object value) {
		if (value instanceof String) {
			if ("".equals(((String) value).trim())) {
				return true;
			}
		}
		if (value == null)
			return true;
		if (value instanceof CharSequence)
			return ((CharSequence) value).length() == 0;
		else if (value instanceof Number)
			return ((Number) value).doubleValue() == 0;
		else if (value.getClass().isArray())
			return ((Object[]) value).length == 0;
		else if (value instanceof Collection)
			return ((Collection) value).isEmpty();
		else
			return value.toString().length() == 0;
	}

	// 判断对象是否为空
	public static boolean isValid(Object v) {
		return !isBlank(v);
	}

	// 空格和null都被判定为空
	public static boolean isNullOrEmpty(Object s) {
		return s == null || s.toString().trim().equals("") || "null".equals(s.toString().trim());
	}

	// 将JSON字符串转化为JSON
	public static JSONObject conertToJSONObject(String jsonString) {
		if (isBlank(jsonString)) {
			return null;
		}
		JSONObject jsonobject = JSONObject.fromObject(jsonString);
		return jsonobject;
	}

	// 将JSON字符串转化为JSON数组
	public static JSONArray conertToJSONArray(String jsonString) {
		if (isBlank(jsonString)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray;
	}

	// 将JSONArray转化为字符串
	public static String conertToString(JSONArray jsonArray) {
		if (isBlank(jsonArray)) {
			return "";
		}
		return jsonArray.toString();
	}

	// 成功响应
	public static JSONObject successResponse(String msg, JSONObject data) {
		JSONObject object = new JSONObject();
		object.put("code", "0000");
		object.put("msg", msg);
		object.put("data", data);
		return object;
	}

	// 成功响应 重载
	public static JSONObject successResponse(String msg) {
		JSONObject object = new JSONObject();
		object.put("code", "0000");
		object.put("msg", msg);
		return object;
	}

	// 失败响应
	public static JSONObject failureResponse(String msg) {
		JSONObject object = new JSONObject();
		object.put("code", "9999");
		object.put("msg", msg);
		return object;
	}

	// 格式化Date
	public static String formatDate(Date date, String rule) {
		SimpleDateFormat ft = new SimpleDateFormat(rule);
		return ft.format(date);
	}

	// 将字符串2014/12/01 转换为日期
	public static Date stringToDate(String dateString) throws ParseException {
		DateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		return ft.parse(dateString);
	}

	// 将字符串2014/12/01 转换为日期
	public static Date stringToDate(String dateString, String rule) throws ParseException {
		DateFormat ft = new SimpleDateFormat(rule);
		return ft.parse(dateString);
	}

	public static String decode(String encryedData, String keystring) throws Exception {
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(keystring.getBytes());
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, key);

		// 现在，获取数据并加密
		byte[] data = Base64.decodeBase64(encryedData);

		// 正式执行加密操作
		byte[] encryptedData = cipher.doFinal(data);
		return new String(encryptedData, "gbk");
	}

	/**
	 * <p>
	 * Description: 电子锁加密方法
	 * </p>
	 * 
	 * @param 需要加密的明文
	 * @param 密钥
	 * @return 密文
	 */
	public static String EncryptByLock(String str, String keystring) {
		if (isBlank(str))
			return str;
		if (keystring.length() < 6)
			return str;
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String strOut = "";
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(keystring.getBytes());

			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);

			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);

			// 现在，获取数据并加密
			byte[] data = str.getBytes("gbk"); /* 用某种方法获取数据 */

			// 正式执行加密操作
			byte[] encryptedData = cipher.doFinal(data);

			strOut = Base64.encodeBase64String(encryptedData);

			strOut = strOut.replaceAll("\r\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strOut;
	}

	// MD5算法加密
	public static String md5(byte[] source) {
		String s = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte[] tmp = md.digest();
			StringBuffer buf = new StringBuffer(tmp.length * 2);
			for (int i = 0; i < tmp.length; i++) {
				if (((int) tmp[i] & 0xff) < 0x10) {
					buf.append("0");
				}
				buf.append(Long.toString((int) tmp[i] & 0xff, 16));
			}
			s = buf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static Integer[] toIntArray(String value) {
		return toIntArray(split(value));
	}

	public static String[] split(String s) {
		if (isBlank(s))
			return new String[0];
		else if (s.indexOf("\r\n") >= 0)
			return s.split("\r\n");
		else if (s.indexOf("\n") >= 0)
			return s.split("\n");
		else if (s.indexOf(";") >= 0)
			return s.split(";");
		else if (s.indexOf(",") >= 0)
			return s.split(",");
		else if (s.indexOf("；") >= 0)
			return s.split("；");
		else if (s.indexOf("，") >= 0)
			return s.split("，");
		else
			return new String[] { s };
	}

	public static Integer[] toIntArray(String[] arr) {
		if (arr == null)
			return null;
		Integer[] result = new Integer[arr.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Integer(arr[i].trim());
		}
		return result;
	}

	public static Long[] toLongArray(String[] arr) {
		if (arr == null)
			return null;
		Long[] result = new Long[arr.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Long(arr[i].trim());
		}
		return result;
	}

	public static int toInt(Object obj) {
		if (obj == null || obj.toString().length() == 0)
			return 0;
		else if (obj instanceof Number)
			return ((Number) obj).intValue();
		else if (isNumeric(obj))
			return Double.valueOf(obj.toString()).intValue();
		else
			throw new NumberFormatException(obj.toString());
	}

	public static boolean isNumeric(Object value) {
		if (value != null && value instanceof Number)
			return true;
		else
			return isNumeric((String) value);
	}

	public static boolean isNumeric(String value) {
		if (value == null)
			return false;
		try {
			return new Double(value) != null;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean existOf(Object[] array, Object obj) {
		return indexOf(array, obj) >= 0;
	}

	public static int indexOf(Object[] array, Object obj) {
		for (int i = 0; i < array.length; i++) {
			if (isEqual(array[i], obj))
				return i;
		}
		return -1;
	}

	public static boolean isEqual(Object o1, Object o2) {
		if (o1 == o2)
			return true;
		if (o1 != null)
			return o1.equals(o2);
		if (o2 != null)
			return o2.equals(o1);
		return false;
	}

	//
	public static String toXlsString(String[][] csv) {
		String templet = loadfromfile(Tools.class, "export.html", "utf8");
		StringBuffer content = new StringBuffer();
		if (csv == null)
			return templet;
		for (int i = 0; i < csv.length; i++) {
			content.append("<tr>");
			for (int j = 0; j < csv[i].length; j++) {
				content.append("<td>").append(csv[i][j]).append("</td>");
			}
			content.append("</tr>\n");
		}
		return templet.replace("${content}", content);
	}

	public static String formatObjectToString(Object object) {
		if (object == null) {
			return "";
		}
		if (object instanceof Date) {
			return formatDate((Date) object, "yyyy/MM/dd");
		}
		return object.toString();
	}

	// 将一个字符串转化为集合例如"12;13"
	public static List<Long> convertStringToList(String numberString, String split) {
		List<Long> list = new ArrayList<Long>();
		String[] arry = numberString.split(split);
		for (int i = 0; i < arry.length; i++) {
			list.add(Long.parseLong(arry[i]));
		}
		return list;
	}

	public static String loadfromfile(Class cls, String name, String charset) {
		StringBuffer result = new StringBuffer();
		char[] buf = new char[BUFFER_SIZE];
		int readlen = 0;
		Reader in = null;
		try {
			in = new InputStreamReader(cls.getResourceAsStream(name), charset);
			while ((readlen = in.read(buf)) > 0) {
				result = result.append(buf, 0, readlen);
			}
		} catch (Exception e) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result.toString();
	}

	public static void main(String[] args) throws Exception {
		String decrypt = "大家好";
		String key = "12345678";
		System.out.println(decode(EncryptByLock(decrypt, key), key).equals(decrypt));
		decrypt = "发送序号1,线路编号：PABFS01-20141211-007密码个人码：874271";
		String encrypt = EncryptByLock(decrypt, key);
		String result = decode(encrypt, key);
		System.out.println("结果：" + result.equals(decrypt));
		System.out.println("短信已加密内容字节长度：" + encrypt.getBytes().length + "，短信内容：" + encrypt);
		System.out.println("短信未加密内容字节长度：" + decrypt.getBytes().length + "，短信内容：" + decrypt);
		System.out.println(new String(
				decode("8sJsGsw54k9eHpleTvW06Y8sBzdCYFgky7MDGu5gmzcz3lhfOpT+DTQ2upNZt4NYHcQArCCGHxb4xIHu8rlr+WQeK09nyqCPg6AtgJul6q0=",
						"12345678").getBytes(),
				"utf8"));
		System.out.println(new String(
				decode("pzmKVYN5yNfibOa4geMmLn60FaMk1CAE2mGmXSzbWNvPpUY8GJUVn/WVLjQGtmTM5odHa5MiPQGNCjahPqtDeg==",
						"12345678").getBytes(),
				"utf8"));

		System.out.println("finished");
		// JSONObject data = new JSONObject();
		// data.put("a", "1");
		// data.put("b", "2");
		// System.out.println(successResponse("好",data).toString());

		// for(int i=1;i<10;i++)
		// {
		// for(int j=1;j<10;j++)
		// {
		// if(j>i)
		// System.out.print(" "+j+"X"+i+"="+i*j);
		// }
		// }
		// Date processDate = new Date();
		// String temp = Tools.formatDate(processDate, "yyyy/MM/dd")+"
		// "+"14:30"+":00";
		// Date tempDate =null;
		// try {
		// tempDate = Tools.stringToDate(temp);
		// System.out.println(tempDate.getTime());
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Calendar c = Calendar.getInstance();
		// c.setTime(tempDate);
		// c.add(Calendar.MINUTE, -30);
		// Date processDateTemp = c.getTime();
		// Long longProcessDateTemp = processDateTemp.getTime();
		// System.out.println(longProcessDateTemp);
		// //Date processDate = new Date();
		// Long longNowDate = tempDate.getTime();
		// System.out.println(longNowDate);
		// System.out.println((longNowDate-longProcessDateTemp)/1000/60);
	}
}

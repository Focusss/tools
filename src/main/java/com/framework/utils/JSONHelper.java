package com.framework.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.framework.core.common.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * JSON和JAVA的POJO相互转换工具类
 */
public final class JSONHelper {

	/**
	 * 将数组转换成JSON
	 * 
	 * @param object
	 * @return JSON
	 */
	public static String array2json(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将JSON转换成数组
	 * 
	 * @param json
	 *            JSON对象
	 * @param valueClz
	 *            数组中存放的对象的Class
	 * @return 数组
	 */
	public static Object json2Array(String json, Class valueClz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toArray(jsonArray, valueClz);
	}

	/**
	 * 将Collection转换成JSON
	 * 
	 * @param object
	 * @return JSON
	 */
	public static String collection2json(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将Map转换成JSON
	 * 
	 * @param object
	 *            map
	 * @return JSON
	 */
	public static String map2json(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成Map
	 * 
	 * @param keyArray
	 *            Map的key
	 * @param json
	 *            JSON
	 * @param valueClz
	 *            Map中value的Class
	 * @return map
	 */
	public static Map json2Map(Object[] keyArray, String json, Class valueClz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map classMap = new HashMap();

		for (int i = 0; i < keyArray.length; i++) {
			classMap.put(keyArray[i], valueClz);
		}

		return (Map) JSONObject.toBean(jsonObject, Map.class, classMap);
	}

	/**
	 * 将POJO转换成JSON
	 * 
	 * @param object
	 *            POJO
	 * @return JSON
	 */
	public static String bean2json(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成POJO
	 * 
	 * @param json
	 *            JSON
	 * @param beanClz
	 *            POJO的Class
	 * @return POJO
	 */
	public static Object json2Object(String json, Class beanClz) {
		return JSONObject.toBean(JSONObject.fromObject(json), beanClz);
	}

	/**
	 * 将String转换成JSON
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return JSON
	 */
	public static String string2json(String key, String value) {
		JSONObject object = new JSONObject();
		object.put(key, value);
		return object.toString();
	}

	/**
	 * 将JSON转换成String
	 * 
	 * @param key
	 *            key
	 * @param json
	 *            JSON
	 * @return string
	 */
	public static String json2String(String json, String key) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject.get(key).toString();
	}

	/**
	 * 将List对象序列化为JSON文本
	 * 
	 * @param list
	 *            对象集合
	 * @return JSON文本
	 */
	public static <T> String toJSONString(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/**
	 * 将对象序列化为JSON文本
	 * 
	 * @param object
	 *            对象
	 * @return JSON文本
	 */
	public static String toJSONString(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将JSON对象数组序列化为JSON文本
	 * 
	 * @param jsonArray
	 *            JSON对象数组
	 * @return JSON文本
	 */
	public static String toJSONString(JSONArray jsonArray) {
		return jsonArray.toString();
	}

	/**
	 * 将JSON对象序列化为JSON文本
	 * 
	 * @param jsonObject
	 *            JSON对象
	 * @return JSON文本
	 */
	public static String toJSONString(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	/**
	 * 将对象转换为List对象
	 * 
	 * @param object
	 *            JSON文本
	 * @return List对象
	 */
	public static List toArrayList(Object object) {
		List arrayList = new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(object);

		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();

			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}

		return arrayList;
	}

	/**
	 * 将对象转换为JSON对象数组
	 * 
	 * @param object
	 *            对象
	 * @return JSON对象数组
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/**
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 *            对象
	 * @return JSON对象
	 */
	public static JSONObject toJSONObject(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		return JSONObject.fromObject(object, jsonConfig);
	}

	/**
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 *            对象
	 * @param excludes
	 *            过滤不需要转换的字段属性
	 * @return JSON对象
	 */
	public static JSONObject toJSONObject(Object object, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		return JSONObject.fromObject(object, jsonConfig);
	}

	/**
	 * 将对象转换为HashMap
	 * 
	 * @param object
	 *            对象
	 * @return HashMap
	 */
	public static HashMap toHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = JSONHelper.toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}

		return data;
	}

	/**
	 * 将对象转换为非实体类型(Map<String,Object>)的List
	 * 
	 * @param object
	 *            对象
	 * @return List
	 */
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 将JSON对象数组转换为传入类型的List
	 * 
	 * @param <T>
	 *            泛型T代表实体类型
	 * @param jsonArray
	 *            JSON数组
	 * @param objectClass
	 *            对象的Class
	 * @return List
	 */
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
		return JSONArray.toList(jsonArray, objectClass);
	}

	/**
	 * 将对象转换为传入类型的List
	 * 
	 * @param <T>
	 *            泛型T代表实体类型
	 * @param object
	 *            对象
	 * @param objectClass
	 *            对象的Class
	 * @return List
	 */
	public static <T> List<T> toList(Object object, Class<T> objectClass) {
		JSONArray jsonArray = JSONArray.fromObject(object);

		return JSONArray.toList(jsonArray, objectClass);
	}

	/**
	 * 将JSON对象转换为传入类型的对象
	 * 
	 * @param <T>
	 *            泛型T代表实体类型
	 * @param jsonObject
	 *            JSON对象
	 * @param beanClass
	 *            对象的Class
	 * @return 传入类型的对象
	 */
	public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * 将对象转换为传入类型的对象
	 * 
	 * @param <T>
	 *            泛型T代表实体类型
	 * @param object
	 *            对象
	 * @param beanClass
	 *            对象的Class
	 * @return 传入类型的对象
	 */
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(object);

		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            泛型T代表主实体类型
	 * @param <D>
	 *            泛型D代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName
	 *            从实体类在主实体类中的属性名称
	 * @param detailClass
	 *            从实体类型
	 * @return 主从关系的实体
	 */
	public static <T, D> T toBean(String jsonString, Class<T> mainClass, String detailName, Class<D> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);

		T mainEntity = JSONHelper.toBean(jsonObject, mainClass);
		List<D> detailList = JSONHelper.toList(jsonArray, detailClass);

		try {
			BeanUtils.setProperty(mainEntity, detailName, detailList);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            泛型T代表主实体类型
	 * @param <D1>
	 *            泛型D1 代表从实体类型
	 * @param <D2>
	 *            泛型D2 代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @return 主从关系的实体
	 */
	public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass, String detailName1, Class<D1> detailClass1, String detailName2,
			Class<D2> detailClass2) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);

		T mainEntity = JSONHelper.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JSONHelper.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JSONHelper.toList(jsonArray2, detailClass2);

		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            泛型T代表主实体类型
	 * @param <D1>
	 *            泛型D1 代表从实体类型
	 * @param <D2>
	 *            泛型D2 代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @param detailName3
	 *            从实体类在主实体类中的属性
	 * @param detailClass3
	 *            从实体类型
	 * @return 主从关系的实体
	 */
	public static <T, D1, D2, D3> T toBean(String jsonString, Class<T> mainClass, String detailName1, Class<D1> detailClass1, String detailName2,
			Class<D2> detailClass2, String detailName3, Class<D3> detailClass3) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);

		T mainEntity = JSONHelper.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JSONHelper.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JSONHelper.toList(jsonArray2, detailClass2);
		List<D3> detailList3 = JSONHelper.toList(jsonArray3, detailClass3);

		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
			BeanUtils.setProperty(mainEntity, detailName3, detailList3);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            主实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailClass
	 *            存放了多个从实体在主实体中属性名称和类型
	 * @return 主从关系的实体
	 */
	public static <T> T toBean(String jsonString, Class<T> mainClass, HashMap<String, Class> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T mainEntity = JSONHelper.toBean(jsonObject, mainClass);
		for (Object key : detailClass.keySet()) {
			try {
				Class value = (Class) detailClass.get(key);
				BeanUtils.setProperty(mainEntity, key.toString(), value);
			} catch (Exception ex) {
				throw new RuntimeException("主从关系JSON反序列化实体失败！");
			}
		}
		return mainEntity;
	}

	/**
	 * 创建一颗树，以json字符串形式返回
	 * 
	 * @param list
	 *            原始数据列表
	 * @return 树
	 */
	public static String createTreeJson(List<JSONObject> list, String text) {
		JSONArray rootArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject resource = list.get(i);
			// resource.put("iconCls","icon-blank");
			resource.put("text", resource.getString(text));
			if (resource.get("pid").toString().equals("0")) {
				JSONObject rootObj = createBranch(list, resource, text);
				rootArray.add(rootObj);
			}
		}
		return rootArray.toString();
	}

	/**
	 * 实现模糊查询
	 * 
	 * @param list
	 *            原始数据列表
	 * @return 树
	 */
	public static String createSearchTreeJson(List<JSONObject> list, String text) {
		JSONArray rootArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			boolean flag = true;
			JSONObject resource_i = list.get(i);
			for (int j = 0; j < list.size(); j++) {
				JSONObject resource_j = list.get(j);
				if (resource_i.get("pid").toString().equals(resource_j.get("id").toString())) {
					flag = false;
					break;
				}
			}
			// 不存在父节点的，则可以直接创建树
			if (flag) {
				JSONObject rootObj = createBranch(list, resource_i, text);
				rootArray.add(rootObj);
			}
		}
		return rootArray.toString();
	}

	/**
	 * 递归创建分支节点Json对象
	 * 
	 * @param list
	 *            创建树的原始数据
	 * @param currentNode
	 *            当前节点
	 * @return 当前节点与该节点的子节点json对象
	 */
	public static JSONObject createBranch(List<JSONObject> list, JSONObject currentNode, String text) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		JSONObject currentObj = JSONObject.fromObject(currentNode);
		JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			JSONObject newNode = list.get(i);
			// newNode.put("iconCls","icon-blank");
			newNode.put("text", newNode.getString(text));
			if (newNode.getString("pid") != null && newNode.getString("pid").compareTo(currentNode.getString("id")) == 0) {
				JSONObject childObj = createBranch(list, newNode, text);
				childArray.add(childObj);
			}
		}
		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}
		return currentObj;
	}

	/**
	 * tree
	 * 
	 * @param list
	 * @param text
	 * @return
	 */
	public static List<JSONObject> deptTreeJson(List<JSONObject> list, String text) {
		List<JSONObject> arrayList = new ArrayList<JSONObject>();
		// 获取集合中所有的根目录
		List<JSONObject> rootJson = getRootJson(list);
		if (rootJson.size() > 0) {
			for (JSONObject jsonObject : rootJson) {
				JSONObject deptBranch = createDeptBranch(list, jsonObject, text);
				arrayList.add(deptBranch);
			}
		}
		return arrayList;
	}

	/**
	 * 递归
	 * 
	 * @param list
	 * @param currentNode
	 * @param text
	 * @return
	 */
	private static JSONObject createDeptBranch(List<JSONObject> list, JSONObject currentNode, String text) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		JSONObject currentObj = JSONObject.fromObject(currentNode);
		JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			JSONObject newNode = list.get(i);
			newNode.put("text", newNode.getString(text));
			if (newNode.getString("pid") != null && newNode.getString("pid").equals(currentNode.getString("id"))) {
				JSONObject childObj = createBranch(list, newNode, text);
				childArray.add(childObj);
			}
		}
		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}
		return currentObj;

	}

	/**
	 * 获取集合中的所有的根目录
	 * 
	 * @param list
	 * @return
	 */
	private static List<JSONObject> getRootJson(List<JSONObject> list) {
		ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
		ArrayList<String> idList2 = new ArrayList<String>();
		// 把父Id添加到集合中
		for (JSONObject jsonObject : list) {
			idList2.add(jsonObject.getString("id"));
		}
		for (JSONObject jsonObject : list) {
			if (!idList2.contains(jsonObject.getString("pid"))) {
				arrayList.add(jsonObject);
			}
		}
		return arrayList;
	}

	/** bean 转换为Map **/
	public static <K, V> Map<K, V> Bean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					ret.put((K) field, (V) (null == value ? "" : String.valueOf(value)));
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}
}

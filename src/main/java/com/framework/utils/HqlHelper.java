package com.framework.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.framework.core.common.Page;

/**
 * HQL生成工具
 */
public class HqlHelper {
	private String entityName;
	private String alias="a";//默认别名是a
	private List<Object> params=new ArrayList<Object>();//存放查询条件值
	private StringBuffer whereCondition=new StringBuffer("");
	private StringBuffer orderBy=new StringBuffer("");
	private Page queryPage;//分页查询属性
	
	public HqlHelper(){
		
	}
	public HqlHelper(Class clazz){
		this.entityName=clazz.getSimpleName();
	}
	/**
	 * 添加等于比较条件
	 * @param property 实体类属性名
	 * @param value  Double,String,Integer,Long,Float
	 * @return HqlHelper
	 */
	public HqlHelper addEqual(String property,Object value){
		
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value);
		whereCondition.append(alias+"."+property+" =?");
		return this;
	}
	
	/**
	 * 添加等于比较条件,不区域大小写
	 * @param property 实体类属性名
	 * @param value Double,String,Integer,Long,Float
	 * @return HqlHelper
	 */
	public HqlHelper addEqualIgnoreCase(String property,Object value){
		
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value.toString().trim().toUpperCase());
		whereCondition.append("upper("+alias+"."+property+") =?");
		return this;
	}
	
	/**
	 * 添加不等于比较条件
	 * @param property 实体类属性名
	 * @param value  Double,String,Integer,Long,Float
	 * @return HqlHelper
	 */
	public HqlHelper addNotEqual(String property,Object value){
		
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		
		params.add(value);
		whereCondition.append(alias+"."+property+" !=? ");
		
		return this;
	}
	
	/**
	 * 添加不等于比较条件,不区域大小写
	 * @param property 实体类属性名
	 * @param value Double,String,Integer,Long,Float
	 * @return HqlHelper
	 */
	public HqlHelper addNotEqualIgnoreCase(String property,Object value){
		
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value.toString().trim().toUpperCase());
		whereCondition.append(alias+"."+property+" !=? ");
		whereCondition.append("upper("+alias+"."+property+") != ?");
		return this;
	}
	
	/**
	 * 添加LIKE模糊查询
	 * @param property 实体类属性名
	 * @param value String
	 * @return HqlHelper
	 */
	public HqlHelper addLike(String property,String value){
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add("%"+value+"%");
		whereCondition.append(alias+"."+property+" like ? ");
		return this;
	}
	
	/**
	 * 添加LIKE模糊查询,不区分大小写
	 * @param property 实体类属性名
	 * @param value String
	 * @return HqlHelper
	 */
	public HqlHelper addLikeIgnoreCase(String property,String value){
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add("%"+value.trim().toUpperCase()+"%");
		whereCondition.append("upper("+alias+"."+property+") like ?");
		return this;
	}
	
	
	
	/**
	 * 添加以指定字符结尾的模糊查询,不区分大小写
	 * @param property 实体类属性名
	 * @param value String
	 * @return HqlHelper
	 */
	public HqlHelper addEndWith(String property,String value){
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add("%"+value.trim().toUpperCase());
		whereCondition.append(alias+"."+property+" like ?");
		return this;
	}
	
	/**
	 * 添加以指定字符开始的模糊查询,不区分大小写
	 * @param property 实体类属性名
	 * @param value String
	 * @return HqlHelper
	 */
	public HqlHelper addStartWith(String property,String value){
		if(StringUtils.isEmpty(property)||value==null||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value.trim().toUpperCase()+"%");
		whereCondition.append(alias+"."+property+" like ?");
		return this;
	}
	
	/**
	 * 添加IN查询 
	 * @param property 实体类属性
	 * @param list 集合
	 * @return HqlHelper
	 */
	public HqlHelper addIn(String property,List list){
		if(list==null||list.size()==0){
			if(!"".equals(whereCondition.toString())){
				whereCondition.append(" and ");
			}
			whereCondition.append("0=1");
			return this;
		}
		return addIn(property,list.toArray());
	}
	
	
	/**
	 * 添加IN查询 
	 * @param property 实体类属性
	 * @param list 集合
	 * @return HqlHelper
	 */
	public HqlHelper addNotIn(String property,List list){
		if(list==null||list.size()==0){
			return this;
		}
		return addNotIn(property,list.toArray());
	}
	
	/**
	 * 添加IN查询  
	 * @param property 实体类属性
	 * @param array 对象数组
	 * @return HqlHelper
	 */
	public HqlHelper addIn(String property,Object[] array){
		if(array==null||array.length==0){
			return this;
		}
		StringBuffer sb=new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			Object obj = array[i];
			if(obj==null||"".equals(obj.toString().trim()) ){
				continue;
			}
			if(!"".equals(sb.toString().trim())){
				sb.append(",");
			}
			if(obj instanceof String){
				params.add(obj.toString().trim());
			}else{
				params.add(obj);
			}
			
			sb.append("?");
		}
		if("".equals(sb.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		List<String> inList=new ArrayList<String>();
		String sbString=sb.toString();//sbString邮","和"？"组成。
		if(sbString.length()>1800){//如果问号超过900个。也就是IN里面有900个，则拆分
			int i=0;
			while(true){
				if((i+1800)>=(sbString.length()-1)){
					String in=sbString.substring(i, sbString.length());
					inList.add(in);
					break;
				}else{
					String in=sbString.substring(i, i+1800);
					i=i+1800;
					inList.add(in);
				}
			}
		}else{
			inList.add(sbString);
		}
		
		StringBuffer childOr=new StringBuffer("");
		
		for (String in : inList) {
			if(!childOr.toString().equals("")){
				childOr.append(" or ");
			}
			if(in.startsWith(",")){
				in=in.substring(1);
			}
			if(in.endsWith(",")){
				in=in.substring(0,in.length()-1);
			}
			childOr.append(alias+"."+property+" in ("+in+")");
		}
		whereCondition.append("( "+childOr.toString()+" )");
		return this;
	}
	
	/**
	 * 添加NOT IN查询  
	 * @param property 实体类属性
	 * @param array 对象数组
	 * @return HqlHelper
	 */
	public HqlHelper addNotIn(String property,Object[] array){
		if(array==null||array.length==0){
			return this;
		}
		StringBuffer sb=new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			Object obj = array[i];
			if(obj==null||"".equals(obj.toString().trim()) ){
				continue;
			}
			if(!"".equals(sb.toString().trim())){
				sb.append(",");
			}
			params.add(obj);
			sb.append("?");
		}
		if("".equals(sb.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		whereCondition.append(alias+"."+property+" not in ("+sb.toString()+")");
		
		return this;
	}
	
	/**
	 * 添加IS NULL查询
	 * @param property
	 * @return HqlHelper
	 */
	public HqlHelper addIsNull(String property){
		if(StringUtils.isBlank(property)){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		
		whereCondition.append(alias+"."+property+" is null ");
		return this;
	}
	
	/**
	 * 添加IS NOT NULL查询
	 * @param property
	 * @return HqlHelper
	 */
	public HqlHelper addIsNotNull(String property){
		if(StringUtils.isBlank(property)){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		
		whereCondition.append(alias+"."+property+" is not null ");
		return this;
	}
	
	/**
	 * 添加小于查询条件
	 * @param property
	 * @return HqlHelper
	 */
	public HqlHelper addLessThan(String property,Object value){
		if(value==null||StringUtils.isEmpty(property)||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		
		params.add(value);
		whereCondition.append(alias+"."+property+" < ?");
		return this;
	}
	
	/**
	 * 添加小于等于查询条件
	 * @param property
	 * @return HqlHelper
	 */
	public HqlHelper addLessEqualThan(String property,Object value){
		if(value==null||StringUtils.isEmpty(property)||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value);
		whereCondition.append(alias+"."+property+" <= ?");
		
		return this;
	}
	
	/**
	 * 添加大于查询条件
	 * @param property
	 * @return HqlHelper
	 */
	public HqlHelper addGreatThan(String property,Object value){
		if(value==null||StringUtils.isEmpty(property)||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value);
		whereCondition.append(alias+"."+property+" >?");
		
		return this;
	}

	
	/**
	 * 添加大于等于查询条件
	 * @param property
	 * @return HqlHelper
	 */
	public HqlHelper addGreatEqualThan(String property,Object value){
		if(value==null||StringUtils.isEmpty(property)||StringUtils.isEmpty(value.toString())){
			return this;
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		params.add(value);
		whereCondition.append(alias+"."+property+" >=?");
		return this;
	}
	
	/**
	 * 添加日期范围查询（yyyy-MM-dd）
	 * @param property 实体类属性
	 * @param fromDate 开始日期
	 * @param toDate 结束日期
	 * @return HqlHelper
	 */
	public HqlHelper addDateBetween(String property,String fromDate,String toDate){
		return addDateBetween(property,fromDate,toDate,"yyyy-MM-dd");
	}
	
	/**
	 * 添加日期范围查询
	 * @param property 实体类属性
	 * @param fromDate 开始日期
	 * @param toDate 结束日期
	 * @param formater 日期类型模式，例如：yyyy-MM表示只比较到月份，yyyy-MM-dd比较到具体的一天，以此类推
	 * @return HqlHelper
	 */
	public HqlHelper addDateBetween(String property,String fromDate,String toDate,String formater){
		
		if(StringUtils.isEmpty(fromDate) && StringUtils.isEmpty(toDate)){
			return this;
		}
		
		if(StringUtils.isNotBlank(fromDate)){
			if(!"".equals(whereCondition.toString())){
				whereCondition.append(" and ");
			}
			params.add(fromDate);
			whereCondition.append("to_char("+alias+"."+property+",'"+formater+"')>=?");
		}
		if(StringUtils.isNotBlank(toDate)){
			if(!"".equals(whereCondition.toString())){
				whereCondition.append(" and ");
			}
			params.add(toDate);
			whereCondition.append("to_char("+alias+"."+property+",'"+formater+"')<=?");
		}
		return this;
	}
	
	/**
	 * 添加数值范围查询
	 * @param property 实体类属性
	 * @param from 开始数值
	 * @param to 结束数值
	 * @return HqlHelper
	 */
	public HqlHelper addBetween(String property,Integer from,Integer to){
		
		if(from==null&&to==null){
			return this;
		}
		
		if(from!=null){
			if(!"".equals(whereCondition.toString())){
				whereCondition.append(" and ");
			}
			params.add(from);
			whereCondition.append(alias+"."+property+">=?");
		}
		if(to!=null){
			if(!"".equals(whereCondition.toString())){
				whereCondition.append(" and ");
			}
			params.add(to);
			whereCondition.append(alias+"."+property+"<=?");
		}
		return this;
	}
	
	/**
	 * 添加或条件查询
	 * @param or
	 * @return HqlHelper
	 */
	public HqlHelper addOr(HqlHelper or){
		if(!"".equals(or.getWhereCondition().toString())){
			if(!"".equals(whereCondition.toString())){
				whereCondition.append(" and ");
			}
			params.addAll(or.getParams());
			whereCondition.append("("+or.getWhereCondition().toString().replace(" and ", " or ")+")");
		}
		return this;
	}
	
	/**
	 * OR查询
	 * @param or
	 * @return HqlHelper
	 */
	public HqlHelper addArrayOR(HqlHelper ... or){
		StringBuffer sb=new StringBuffer("");
		for (HqlHelper hqlHelper : or) {
			if (StringUtils.isEmpty(hqlHelper.getWhereCondition().toString())) {
				continue;
			}
			if(!sb.toString().equals("")){
				sb.append(" or ");
			}
			params.addAll(hqlHelper.getParams());
			sb.append("("+hqlHelper.getWhereCondition().toString()+")");
		}
		if(!"".equals(whereCondition.toString())){
			whereCondition.append(" and ");
		}
		whereCondition.append("("+sb.toString()+")");
		return this;
	}
	
	/**
	 * 添加排序字段
	 * @param property 实体属性名
	 * @param type 排序方式 asc desc
	 * @return HqlHelper
	 */
	public HqlHelper addOrderBy(String property,String type){
		if(StringUtils.isNotBlank(property)&&StringUtils.isNotBlank(type)){
			if(orderBy.toString().indexOf(" order by")!=-1){
				orderBy.append(","+alias+"."+property+" "+type + " nulls last");
			}else{
				orderBy.append(" order by "+alias+"."+property+" "+type + " nulls last");
			}
			
		}
		return this;
	}
	
	/**
	 * 生成HQL查询语句
	 * @return hql
	 */
	public String getHQL(){
		String hql=" from "+this.entityName+" "+this.alias;
		if(!"".equals(whereCondition.toString())){
			hql+=" where "+whereCondition.toString();
		}
		
		return hql+" "+orderBy.toString();
	}

	public StringBuffer getWhereCondition() {
		return whereCondition;
	}
	
	
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
	public Page getQueryPage() {
		return queryPage;
	}
	public void setQueryPage(Page queryPage) {
		this.queryPage = queryPage;
		if(queryPage!=null){
			if("null".equals(queryPage.getSort())){
				queryPage.setSort(null);
			}
		}
		if(queryPage!=null&&queryPage.getSort()!=null&&queryPage.getOrder()!=null){
			orderBy=new StringBuffer(" order by a."+queryPage.getSort()+" "+queryPage.getOrder());
		}
	}
	
	/**
	 * 封装list查询的表单条件
	 * @param queryObj 封装的查询表单
	 * @param notQueryObj  表单中例外的属性值
	 * @return
	 */
	public HqlHelper installHqlQuery(Object queryObj, Map<?,?> notQueryObj) {
		// 获取queryObj属性值
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(queryObj);
		String name;
		String type;
		boolean  notQueryisEmpty= false;
//		判断例外的表单属性值是否都不为空
		notQueryisEmpty = HqlQueryUtils.isNotAllEmpty(notQueryObj);
		
		for (int i = 0; i < origDescriptors.length; i++) {
//			获取属性所属的类型
			type = origDescriptors[i].getPropertyType().toString();
//			获取属性的名称
			name = origDescriptors[i].getName();
			if (HqlQueryUtils.judgedIsUselessField(name)) {
				continue;
			}
			if(notQueryisEmpty&&HqlQueryUtils.ifRightType(type)){
				Object value;
				try {
//					判断属性字段不为空，且不在例外的字段中允许封装
					if(!StringUtil.isEmpty(name)&&(!name.equals(notQueryObj.get(name)))){
						value = PropertyUtils.getSimpleProperty(queryObj, name);
						addEqual(name, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return this;
	}
}

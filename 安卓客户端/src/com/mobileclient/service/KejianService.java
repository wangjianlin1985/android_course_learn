package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Kejian;
import com.mobileclient.util.HttpUtil;

/*课件信息管理业务逻辑层*/
public class KejianService {
	/* 添加课件信息 */
	public String AddKejian(Kejian kejian) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", kejian.getId() + "");
		params.put("title", kejian.getTitle());
		params.put("path", kejian.getPath());
		params.put("addTime", kejian.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "KejianServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询课件信息 */
	public List<Kejian> QueryKejian(Kejian queryConditionKejian) throws Exception {
		String urlString = HttpUtil.BASE_URL + "KejianServlet?action=query";
		if(queryConditionKejian != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionKejian.getTitle(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		KejianListHandler kejianListHander = new KejianListHandler();
		xr.setContentHandler(kejianListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Kejian> kejianList = kejianListHander.getKejianList();
		return kejianList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Kejian> kejianList = new ArrayList<Kejian>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Kejian kejian = new Kejian();
				kejian.setId(object.getInt("id"));
				kejian.setTitle(object.getString("title"));
				kejian.setPath(object.getString("path"));
				kejian.setAddTime(object.getString("addTime"));
				kejianList.add(kejian);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kejianList;
	}

	/* 更新课件信息 */
	public String UpdateKejian(Kejian kejian) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", kejian.getId() + "");
		params.put("title", kejian.getTitle());
		params.put("path", kejian.getPath());
		params.put("addTime", kejian.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "KejianServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除课件信息 */
	public String DeleteKejian(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "KejianServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "课件信息信息删除失败!";
		}
	}

	/* 根据记录编号获取课件信息对象 */
	public Kejian GetKejian(int id)  {
		List<Kejian> kejianList = new ArrayList<Kejian>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "KejianServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Kejian kejian = new Kejian();
				kejian.setId(object.getInt("id"));
				kejian.setTitle(object.getString("title"));
				kejian.setPath(object.getString("path"));
				kejian.setAddTime(object.getString("addTime"));
				kejianList.add(kejian);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = kejianList.size();
		if(size>0) return kejianList.get(0); 
		else return null; 
	}
}

package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Chapter;
import com.mobileclient.util.HttpUtil;

/*章信息管理业务逻辑层*/
public class ChapterService {
	/* 添加章信息 */
	public String AddChapter(Chapter chapter) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", chapter.getId() + "");
		params.put("title", chapter.getTitle());
		params.put("addTime", chapter.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ChapterServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询章信息 */
	public List<Chapter> QueryChapter(Chapter queryConditionChapter) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ChapterServlet?action=query";
		if(queryConditionChapter != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ChapterListHandler chapterListHander = new ChapterListHandler();
		xr.setContentHandler(chapterListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Chapter> chapterList = chapterListHander.getChapterList();
		return chapterList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Chapter> chapterList = new ArrayList<Chapter>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Chapter chapter = new Chapter();
				chapter.setId(object.getInt("id"));
				chapter.setTitle(object.getString("title"));
				chapter.setAddTime(object.getString("addTime"));
				chapterList.add(chapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chapterList;
	}

	/* 更新章信息 */
	public String UpdateChapter(Chapter chapter) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", chapter.getId() + "");
		params.put("title", chapter.getTitle());
		params.put("addTime", chapter.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ChapterServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除章信息 */
	public String DeleteChapter(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ChapterServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "章信息信息删除失败!";
		}
	}

	/* 根据记录编号获取章信息对象 */
	public Chapter GetChapter(int id)  {
		List<Chapter> chapterList = new ArrayList<Chapter>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ChapterServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Chapter chapter = new Chapter();
				chapter.setId(object.getInt("id"));
				chapter.setTitle(object.getString("title"));
				chapter.setAddTime(object.getString("addTime"));
				chapterList.add(chapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = chapterList.size();
		if(size>0) return chapterList.get(0); 
		else return null; 
	}
}

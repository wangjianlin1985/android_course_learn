package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.HomeworkUpload;
import com.mobileclient.util.HttpUtil;

/*上传的作业管理业务逻辑层*/
public class HomeworkUploadService {
	/* 添加上传的作业 */
	public String AddHomeworkUpload(HomeworkUpload homeworkUpload) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uploadId", homeworkUpload.getUploadId() + "");
		params.put("homeworkTaskObj", homeworkUpload.getHomeworkTaskObj() + "");
		params.put("studentObj", homeworkUpload.getStudentObj());
		params.put("homeworkFile", homeworkUpload.getHomeworkFile());
		params.put("uploadTime", homeworkUpload.getUploadTime());
		params.put("resultFile", homeworkUpload.getResultFile());
		params.put("pigaiTime", homeworkUpload.getPigaiTime());
		params.put("pigaiFlag", homeworkUpload.getPigaiFlag() + "");
		params.put("pingyu", homeworkUpload.getPingyu());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkUploadServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询上传的作业 */
	public List<HomeworkUpload> QueryHomeworkUpload(HomeworkUpload queryConditionHomeworkUpload) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HomeworkUploadServlet?action=query";
		if(queryConditionHomeworkUpload != null) {
			urlString += "&homeworkTaskObj=" + queryConditionHomeworkUpload.getHomeworkTaskObj();
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionHomeworkUpload.getStudentObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HomeworkUploadListHandler homeworkUploadListHander = new HomeworkUploadListHandler();
		xr.setContentHandler(homeworkUploadListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<HomeworkUpload> homeworkUploadList = homeworkUploadListHander.getHomeworkUploadList();
		return homeworkUploadList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<HomeworkUpload> homeworkUploadList = new ArrayList<HomeworkUpload>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HomeworkUpload homeworkUpload = new HomeworkUpload();
				homeworkUpload.setUploadId(object.getInt("uploadId"));
				homeworkUpload.setHomeworkTaskObj(object.getInt("homeworkTaskObj"));
				homeworkUpload.setStudentObj(object.getString("studentObj"));
				homeworkUpload.setHomeworkFile(object.getString("homeworkFile"));
				homeworkUpload.setUploadTime(object.getString("uploadTime"));
				homeworkUpload.setResultFile(object.getString("resultFile"));
				homeworkUpload.setPigaiTime(object.getString("pigaiTime"));
				homeworkUpload.setPigaiFlag(object.getInt("pigaiFlag"));
				homeworkUpload.setPingyu(object.getString("pingyu"));
				homeworkUploadList.add(homeworkUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return homeworkUploadList;
	}

	/* 更新上传的作业 */
	public String UpdateHomeworkUpload(HomeworkUpload homeworkUpload) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uploadId", homeworkUpload.getUploadId() + "");
		params.put("homeworkTaskObj", homeworkUpload.getHomeworkTaskObj() + "");
		params.put("studentObj", homeworkUpload.getStudentObj());
		params.put("homeworkFile", homeworkUpload.getHomeworkFile());
		params.put("uploadTime", homeworkUpload.getUploadTime());
		params.put("resultFile", homeworkUpload.getResultFile());
		params.put("pigaiTime", homeworkUpload.getPigaiTime());
		params.put("pigaiFlag", homeworkUpload.getPigaiFlag() + "");
		params.put("pingyu", homeworkUpload.getPingyu());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkUploadServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除上传的作业 */
	public String DeleteHomeworkUpload(int uploadId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uploadId", uploadId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkUploadServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "上传的作业信息删除失败!";
		}
	}

	/* 根据记录编号获取上传的作业对象 */
	public HomeworkUpload GetHomeworkUpload(int uploadId)  {
		List<HomeworkUpload> homeworkUploadList = new ArrayList<HomeworkUpload>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uploadId", uploadId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkUploadServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HomeworkUpload homeworkUpload = new HomeworkUpload();
				homeworkUpload.setUploadId(object.getInt("uploadId"));
				homeworkUpload.setHomeworkTaskObj(object.getInt("homeworkTaskObj"));
				homeworkUpload.setStudentObj(object.getString("studentObj"));
				homeworkUpload.setHomeworkFile(object.getString("homeworkFile"));
				homeworkUpload.setUploadTime(object.getString("uploadTime"));
				homeworkUpload.setResultFile(object.getString("resultFile"));
				homeworkUpload.setPigaiTime(object.getString("pigaiTime"));
				homeworkUpload.setPigaiFlag(object.getInt("pigaiFlag"));
				homeworkUpload.setPingyu(object.getString("pingyu"));
				homeworkUploadList.add(homeworkUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = homeworkUploadList.size();
		if(size>0) return homeworkUploadList.get(0); 
		else return null; 
	}
}

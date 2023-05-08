package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.HomeworkTask;
import com.mobileclient.util.HttpUtil;

/*作业任务管理业务逻辑层*/
public class HomeworkTaskService {
	/* 添加作业任务 */
	public String AddHomeworkTask(HomeworkTask homeworkTask) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homeworkTask.getHomeworkId() + "");
		params.put("teacherObj", homeworkTask.getTeacherObj() + "");
		params.put("title", homeworkTask.getTitle());
		params.put("content", homeworkTask.getContent());
		params.put("addTime", homeworkTask.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询作业任务 */
	public List<HomeworkTask> QueryHomeworkTask(HomeworkTask queryConditionHomeworkTask) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HomeworkTaskServlet?action=query";
		if(queryConditionHomeworkTask != null) {
			urlString += "&teacherObj=" + queryConditionHomeworkTask.getTeacherObj();
			urlString += "&title=" + URLEncoder.encode(queryConditionHomeworkTask.getTitle(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HomeworkTaskListHandler homeworkTaskListHander = new HomeworkTaskListHandler();
		xr.setContentHandler(homeworkTaskListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<HomeworkTask> homeworkTaskList = homeworkTaskListHander.getHomeworkTaskList();
		return homeworkTaskList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<HomeworkTask> homeworkTaskList = new ArrayList<HomeworkTask>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HomeworkTask homeworkTask = new HomeworkTask();
				homeworkTask.setHomeworkId(object.getInt("homeworkId"));
				homeworkTask.setTeacherObj(object.getInt("teacherObj"));
				homeworkTask.setTitle(object.getString("title"));
				homeworkTask.setContent(object.getString("content"));
				homeworkTask.setAddTime(object.getString("addTime"));
				homeworkTaskList.add(homeworkTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return homeworkTaskList;
	}

	/* 更新作业任务 */
	public String UpdateHomeworkTask(HomeworkTask homeworkTask) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homeworkTask.getHomeworkId() + "");
		params.put("teacherObj", homeworkTask.getTeacherObj() + "");
		params.put("title", homeworkTask.getTitle());
		params.put("content", homeworkTask.getContent());
		params.put("addTime", homeworkTask.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除作业任务 */
	public String DeleteHomeworkTask(int homeworkId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homeworkId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "作业任务信息删除失败!";
		}
	}

	/* 根据记录编号获取作业任务对象 */
	public HomeworkTask GetHomeworkTask(int homeworkId)  {
		List<HomeworkTask> homeworkTaskList = new ArrayList<HomeworkTask>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homeworkId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HomeworkTask homeworkTask = new HomeworkTask();
				homeworkTask.setHomeworkId(object.getInt("homeworkId"));
				homeworkTask.setTeacherObj(object.getInt("teacherObj"));
				homeworkTask.setTitle(object.getString("title"));
				homeworkTask.setContent(object.getString("content"));
				homeworkTask.setAddTime(object.getString("addTime"));
				homeworkTaskList.add(homeworkTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = homeworkTaskList.size();
		if(size>0) return homeworkTaskList.get(0); 
		else return null; 
	}
}

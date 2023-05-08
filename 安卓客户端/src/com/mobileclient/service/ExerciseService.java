package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Exercise;
import com.mobileclient.util.HttpUtil;

/*习题信息管理业务逻辑层*/
public class ExerciseService {
	/* 添加习题信息 */
	public String AddExercise(Exercise exercise) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", exercise.getId() + "");
		params.put("title", exercise.getTitle());
		params.put("chapterId", exercise.getChapterId() + "");
		params.put("content", exercise.getContent());
		params.put("addTime", exercise.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询习题信息 */
	public List<Exercise> QueryExercise(Exercise queryConditionExercise) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ExerciseServlet?action=query";
		if(queryConditionExercise != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionExercise.getTitle(), "UTF-8") + "";
			urlString += "&chapterId=" + queryConditionExercise.getChapterId();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ExerciseListHandler exerciseListHander = new ExerciseListHandler();
		xr.setContentHandler(exerciseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Exercise> exerciseList = exerciseListHander.getExerciseList();
		return exerciseList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Exercise exercise = new Exercise();
				exercise.setId(object.getInt("id"));
				exercise.setTitle(object.getString("title"));
				exercise.setChapterId(object.getInt("chapterId"));
				exercise.setContent(object.getString("content"));
				exercise.setAddTime(object.getString("addTime"));
				exerciseList.add(exercise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exerciseList;
	}

	/* 更新习题信息 */
	public String UpdateExercise(Exercise exercise) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", exercise.getId() + "");
		params.put("title", exercise.getTitle());
		params.put("chapterId", exercise.getChapterId() + "");
		params.put("content", exercise.getContent());
		params.put("addTime", exercise.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除习题信息 */
	public String DeleteExercise(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "习题信息信息删除失败!";
		}
	}

	/* 根据记录编号获取习题信息对象 */
	public Exercise GetExercise(int id)  {
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Exercise exercise = new Exercise();
				exercise.setId(object.getInt("id"));
				exercise.setTitle(object.getString("title"));
				exercise.setChapterId(object.getInt("chapterId"));
				exercise.setContent(object.getString("content"));
				exercise.setAddTime(object.getString("addTime"));
				exerciseList.add(exercise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = exerciseList.size();
		if(size>0) return exerciseList.get(0); 
		else return null; 
	}
}

package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Question;
import com.mobileclient.util.HttpUtil;

/*在线问答管理业务逻辑层*/
public class QuestionService {
	/* 添加在线问答 */
	public String AddQuestion(Question question) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", question.getId() + "");
		params.put("teacherId", question.getTeacherId() + "");
		params.put("questioner", question.getQuestioner());
		params.put("content", question.getContent());
		params.put("reply", question.getReply());
		params.put("addTime", question.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询在线问答 */
	public List<Question> QueryQuestion(Question queryConditionQuestion) throws Exception {
		String urlString = HttpUtil.BASE_URL + "QuestionServlet?action=query";
		if(queryConditionQuestion != null) {
			urlString += "&teacherId=" + queryConditionQuestion.getTeacherId();
			urlString += "&questioner=" + URLEncoder.encode(queryConditionQuestion.getQuestioner(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		QuestionListHandler questionListHander = new QuestionListHandler();
		xr.setContentHandler(questionListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Question> questionList = questionListHander.getQuestionList();
		return questionList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Question> questionList = new ArrayList<Question>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Question question = new Question();
				question.setId(object.getInt("id"));
				question.setTeacherId(object.getInt("teacherId"));
				question.setQuestioner(object.getString("questioner"));
				question.setContent(object.getString("content"));
				question.setReply(object.getString("reply"));
				question.setAddTime(object.getString("addTime"));
				questionList.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questionList;
	}

	/* 更新在线问答 */
	public String UpdateQuestion(Question question) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", question.getId() + "");
		params.put("teacherId", question.getTeacherId() + "");
		params.put("questioner", question.getQuestioner());
		params.put("content", question.getContent());
		params.put("reply", question.getReply());
		params.put("addTime", question.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除在线问答 */
	public String DeleteQuestion(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "在线问答信息删除失败!";
		}
	}

	/* 根据记录编号获取在线问答对象 */
	public Question GetQuestion(int id)  {
		List<Question> questionList = new ArrayList<Question>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Question question = new Question();
				question.setId(object.getInt("id"));
				question.setTeacherId(object.getInt("teacherId"));
				question.setQuestioner(object.getString("questioner"));
				question.setContent(object.getString("content"));
				question.setReply(object.getString("reply"));
				question.setAddTime(object.getString("addTime"));
				questionList.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = questionList.size();
		if(size>0) return questionList.get(0); 
		else return null; 
	}
}

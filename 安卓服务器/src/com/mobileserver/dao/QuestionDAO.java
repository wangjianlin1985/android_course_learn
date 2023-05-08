package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Question;
import com.mobileserver.util.DB;

public class QuestionDAO {

	public List<Question> QueryQuestion(int teacherId,String questioner) {
		List<Question> questionList = new ArrayList<Question>();
		DB db = new DB();
		String sql = "select * from Question where 1=1";
		if (teacherId != 0)
			sql += " and teacherId=" + teacherId;
		if (!questioner.equals(""))
			sql += " and questioner like '%" + questioner + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setTeacherId(rs.getInt("teacherId"));
				question.setQuestioner(rs.getString("questioner"));
				question.setContent(rs.getString("content"));
				question.setReply(rs.getString("reply"));
				question.setAddTime(rs.getString("addTime"));
				questionList.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return questionList;
	}
	/* 传入在线问答对象，进行在线问答的添加业务 */
	public String AddQuestion(Question question) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新在线问答 */
			String sqlString = "insert into Question(teacherId,questioner,content,reply,addTime) values (";
			sqlString += question.getTeacherId() + ",";
			sqlString += "'" + question.getQuestioner() + "',";
			sqlString += "'" + question.getContent() + "',";
			sqlString += "'" + question.getReply() + "',";
			sqlString += "'" + question.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "在线问答添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "在线问答添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除在线问答 */
	public String DeleteQuestion(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Question where id=" + id;
			db.executeUpdate(sqlString);
			result = "在线问答删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "在线问答删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到在线问答 */
	public Question GetQuestion(int id) {
		Question question = null;
		DB db = new DB();
		String sql = "select * from Question where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				question = new Question();
				question.setId(rs.getInt("id"));
				question.setTeacherId(rs.getInt("teacherId"));
				question.setQuestioner(rs.getString("questioner"));
				question.setContent(rs.getString("content"));
				question.setReply(rs.getString("reply"));
				question.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return question;
	}
	/* 更新在线问答 */
	public String UpdateQuestion(Question question) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Question set ";
			sql += "teacherId=" + question.getTeacherId() + ",";
			sql += "questioner='" + question.getQuestioner() + "',";
			sql += "content='" + question.getContent() + "',";
			sql += "reply='" + question.getReply() + "',";
			sql += "addTime='" + question.getAddTime() + "'";
			sql += " where id=" + question.getId();
			db.executeUpdate(sql);
			result = "在线问答更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "在线问答更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}

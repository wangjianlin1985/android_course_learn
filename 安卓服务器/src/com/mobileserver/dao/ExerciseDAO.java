package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Exercise;
import com.mobileserver.util.DB;

public class ExerciseDAO {

	public List<Exercise> QueryExercise(String title,int chapterId) {
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		DB db = new DB();
		String sql = "select * from Exercise where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (chapterId != 0)
			sql += " and chapterId=" + chapterId;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Exercise exercise = new Exercise();
				exercise.setId(rs.getInt("id"));
				exercise.setTitle(rs.getString("title"));
				exercise.setChapterId(rs.getInt("chapterId"));
				exercise.setContent(rs.getString("content"));
				exercise.setAddTime(rs.getString("addTime"));
				exerciseList.add(exercise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return exerciseList;
	}
	/* 传入习题信息对象，进行习题信息的添加业务 */
	public String AddExercise(Exercise exercise) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新习题信息 */
			String sqlString = "insert into Exercise(title,chapterId,content,addTime) values (";
			sqlString += "'" + exercise.getTitle() + "',";
			sqlString += exercise.getChapterId() + ",";
			sqlString += "'" + exercise.getContent() + "',";
			sqlString += "'" + exercise.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "习题信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "习题信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除习题信息 */
	public String DeleteExercise(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Exercise where id=" + id;
			db.executeUpdate(sqlString);
			result = "习题信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "习题信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到习题信息 */
	public Exercise GetExercise(int id) {
		Exercise exercise = null;
		DB db = new DB();
		String sql = "select * from Exercise where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				exercise = new Exercise();
				exercise.setId(rs.getInt("id"));
				exercise.setTitle(rs.getString("title"));
				exercise.setChapterId(rs.getInt("chapterId"));
				exercise.setContent(rs.getString("content"));
				exercise.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return exercise;
	}
	/* 更新习题信息 */
	public String UpdateExercise(Exercise exercise) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Exercise set ";
			sql += "title='" + exercise.getTitle() + "',";
			sql += "chapterId=" + exercise.getChapterId() + ",";
			sql += "content='" + exercise.getContent() + "',";
			sql += "addTime='" + exercise.getAddTime() + "'";
			sql += " where id=" + exercise.getId();
			db.executeUpdate(sql);
			result = "习题信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "习题信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}

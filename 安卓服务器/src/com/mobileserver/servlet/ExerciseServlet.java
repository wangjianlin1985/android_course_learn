package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ExerciseDAO;
import com.mobileserver.domain.Exercise;

import org.json.JSONStringer;

public class ExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ϰ����Ϣҵ������*/
	private ExerciseDAO exerciseDAO = new ExerciseDAO();

	/*Ĭ�Ϲ��캯��*/
	public ExerciseServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯϰ����Ϣ�Ĳ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			int chapterId = 0;
			if (request.getParameter("chapterId") != null)
				chapterId = Integer.parseInt(request.getParameter("chapterId"));

			/*����ҵ���߼���ִ��ϰ����Ϣ��ѯ*/
			List<Exercise> exerciseList = exerciseDAO.QueryExercise(title,chapterId);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Exercises>").append("\r\n");
			for (int i = 0; i < exerciseList.size(); i++) {
				sb.append("	<Exercise>").append("\r\n")
				.append("		<id>")
				.append(exerciseList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<title>")
				.append(exerciseList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<chapterId>")
				.append(exerciseList.get(i).getChapterId())
				.append("</chapterId>").append("\r\n")
				.append("		<content>")
				.append(exerciseList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<addTime>")
				.append(exerciseList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Exercise>").append("\r\n");
			}
			sb.append("</Exercises>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Exercise exercise: exerciseList) {
				  stringer.object();
			  stringer.key("id").value(exercise.getId());
			  stringer.key("title").value(exercise.getTitle());
			  stringer.key("chapterId").value(exercise.getChapterId());
			  stringer.key("content").value(exercise.getContent());
			  stringer.key("addTime").value(exercise.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ϰ����Ϣ����ȡϰ����Ϣ�������������浽�½���ϰ����Ϣ���� */ 
			Exercise exercise = new Exercise();
			int id = Integer.parseInt(request.getParameter("id"));
			exercise.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			exercise.setTitle(title);
			int chapterId = Integer.parseInt(request.getParameter("chapterId"));
			exercise.setChapterId(chapterId);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			exercise.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			exercise.setAddTime(addTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = exerciseDAO.AddExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ϰ����Ϣ����ȡϰ����Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = exerciseDAO.DeleteExercise(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ϰ����Ϣ֮ǰ�ȸ���id��ѯĳ��ϰ����Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			Exercise exercise = exerciseDAO.GetExercise(id);

			// �ͻ��˲�ѯ��ϰ����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(exercise.getId());
			  stringer.key("title").value(exercise.getTitle());
			  stringer.key("chapterId").value(exercise.getChapterId());
			  stringer.key("content").value(exercise.getContent());
			  stringer.key("addTime").value(exercise.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ϰ����Ϣ����ȡϰ����Ϣ�������������浽�½���ϰ����Ϣ���� */ 
			Exercise exercise = new Exercise();
			int id = Integer.parseInt(request.getParameter("id"));
			exercise.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			exercise.setTitle(title);
			int chapterId = Integer.parseInt(request.getParameter("chapterId"));
			exercise.setChapterId(chapterId);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			exercise.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			exercise.setAddTime(addTime);

			/* ����ҵ���ִ�и��²��� */
			String result = exerciseDAO.UpdateExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

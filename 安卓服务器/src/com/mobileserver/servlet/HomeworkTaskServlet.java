package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HomeworkTaskDAO;
import com.mobileserver.domain.HomeworkTask;

import org.json.JSONStringer;

public class HomeworkTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������ҵ����ҵ������*/
	private HomeworkTaskDAO homeworkTaskDAO = new HomeworkTaskDAO();

	/*Ĭ�Ϲ��캯��*/
	public HomeworkTaskServlet() {
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
			/*��ȡ��ѯ��ҵ����Ĳ�����Ϣ*/
			int teacherObj = 0;
			if (request.getParameter("teacherObj") != null)
				teacherObj = Integer.parseInt(request.getParameter("teacherObj"));
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����ҵ�����ѯ*/
			List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTask(teacherObj,title);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<HomeworkTasks>").append("\r\n");
			for (int i = 0; i < homeworkTaskList.size(); i++) {
				sb.append("	<HomeworkTask>").append("\r\n")
				.append("		<homeworkId>")
				.append(homeworkTaskList.get(i).getHomeworkId())
				.append("</homeworkId>").append("\r\n")
				.append("		<teacherObj>")
				.append(homeworkTaskList.get(i).getTeacherObj())
				.append("</teacherObj>").append("\r\n")
				.append("		<title>")
				.append(homeworkTaskList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(homeworkTaskList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<addTime>")
				.append(homeworkTaskList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</HomeworkTask>").append("\r\n");
			}
			sb.append("</HomeworkTasks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(HomeworkTask homeworkTask: homeworkTaskList) {
				  stringer.object();
			  stringer.key("homeworkId").value(homeworkTask.getHomeworkId());
			  stringer.key("teacherObj").value(homeworkTask.getTeacherObj());
			  stringer.key("title").value(homeworkTask.getTitle());
			  stringer.key("content").value(homeworkTask.getContent());
			  stringer.key("addTime").value(homeworkTask.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ҵ���񣺻�ȡ��ҵ����������������浽�½�����ҵ������� */ 
			HomeworkTask homeworkTask = new HomeworkTask();
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			homeworkTask.setHomeworkId(homeworkId);
			int teacherObj = Integer.parseInt(request.getParameter("teacherObj"));
			homeworkTask.setTeacherObj(teacherObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setAddTime(addTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = homeworkTaskDAO.AddHomeworkTask(homeworkTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ҵ���񣺻�ȡ��ҵ����ļ�¼���*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = homeworkTaskDAO.DeleteHomeworkTask(homeworkId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ҵ����֮ǰ�ȸ���homeworkId��ѯĳ����ҵ����*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			HomeworkTask homeworkTask = homeworkTaskDAO.GetHomeworkTask(homeworkId);

			// �ͻ��˲�ѯ����ҵ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("homeworkId").value(homeworkTask.getHomeworkId());
			  stringer.key("teacherObj").value(homeworkTask.getTeacherObj());
			  stringer.key("title").value(homeworkTask.getTitle());
			  stringer.key("content").value(homeworkTask.getContent());
			  stringer.key("addTime").value(homeworkTask.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ҵ���񣺻�ȡ��ҵ����������������浽�½�����ҵ������� */ 
			HomeworkTask homeworkTask = new HomeworkTask();
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			homeworkTask.setHomeworkId(homeworkId);
			int teacherObj = Integer.parseInt(request.getParameter("teacherObj"));
			homeworkTask.setTeacherObj(teacherObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setAddTime(addTime);

			/* ����ҵ���ִ�и��²��� */
			String result = homeworkTaskDAO.UpdateHomeworkTask(homeworkTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

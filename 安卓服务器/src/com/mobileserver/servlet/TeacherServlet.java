package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TeacherDAO;
import com.mobileserver.domain.Teacher;

import org.json.JSONStringer;

public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ʦ��Ϣҵ������*/
	private TeacherDAO teacherDAO = new TeacherDAO();

	/*Ĭ�Ϲ��캯��*/
	public TeacherServlet() {
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
			/*��ȡ��ѯ��ʦ��Ϣ�Ĳ�����Ϣ*/
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			String position = request.getParameter("position");
			position = position == null ? "" : new String(request.getParameter(
					"position").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�н�ʦ��Ϣ��ѯ*/
			List<Teacher> teacherList = teacherDAO.QueryTeacher(name,position);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Teachers>").append("\r\n");
			for (int i = 0; i < teacherList.size(); i++) {
				sb.append("	<Teacher>").append("\r\n")
				.append("		<id>")
				.append(teacherList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<name>")
				.append(teacherList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<position>")
				.append(teacherList.get(i).getPosition())
				.append("</position>").append("\r\n")
				.append("		<password>")
				.append(teacherList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<introduction>")
				.append(teacherList.get(i).getIntroduction())
				.append("</introduction>").append("\r\n")
				.append("	</Teacher>").append("\r\n");
			}
			sb.append("</Teachers>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Teacher teacher: teacherList) {
				  stringer.object();
			  stringer.key("id").value(teacher.getId());
			  stringer.key("name").value(teacher.getName());
			  stringer.key("position").value(teacher.getPosition());
			  stringer.key("password").value(teacher.getPassword());
			  stringer.key("introduction").value(teacher.getIntroduction());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӽ�ʦ��Ϣ����ȡ��ʦ��Ϣ�������������浽�½��Ľ�ʦ��Ϣ���� */ 
			Teacher teacher = new Teacher();
			int id = Integer.parseInt(request.getParameter("id"));
			teacher.setId(id);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			teacher.setName(name);
			String position = new String(request.getParameter("position").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPosition(position);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPassword(password);
			String introduction = new String(request.getParameter("introduction").getBytes("iso-8859-1"), "UTF-8");
			teacher.setIntroduction(introduction);

			/* ����ҵ���ִ����Ӳ��� */
			String result = teacherDAO.AddTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ʦ��Ϣ����ȡ��ʦ��Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = teacherDAO.DeleteTeacher(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���½�ʦ��Ϣ֮ǰ�ȸ���id��ѯĳ����ʦ��Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			Teacher teacher = teacherDAO.GetTeacher(id);

			// �ͻ��˲�ѯ�Ľ�ʦ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(teacher.getId());
			  stringer.key("name").value(teacher.getName());
			  stringer.key("position").value(teacher.getPosition());
			  stringer.key("password").value(teacher.getPassword());
			  stringer.key("introduction").value(teacher.getIntroduction());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���½�ʦ��Ϣ����ȡ��ʦ��Ϣ�������������浽�½��Ľ�ʦ��Ϣ���� */ 
			Teacher teacher = new Teacher();
			int id = Integer.parseInt(request.getParameter("id"));
			teacher.setId(id);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			teacher.setName(name);
			String position = new String(request.getParameter("position").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPosition(position);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPassword(password);
			String introduction = new String(request.getParameter("introduction").getBytes("iso-8859-1"), "UTF-8");
			teacher.setIntroduction(introduction);

			/* ����ҵ���ִ�и��²��� */
			String result = teacherDAO.UpdateTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

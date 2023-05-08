package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StudentDAO;
import com.mobileserver.domain.Student;

import org.json.JSONStringer;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ѧ����Ϣҵ������*/
	private StudentDAO studentDAO = new StudentDAO();

	/*Ĭ�Ϲ��캯��*/
	public StudentServlet() {
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
			/*��ȡ��ѯѧ����Ϣ�Ĳ�����Ϣ*/
			String studentNumber = request.getParameter("studentNumber");
			studentNumber = studentNumber == null ? "" : new String(request.getParameter(
					"studentNumber").getBytes("iso-8859-1"), "UTF-8");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			String className = request.getParameter("className");
			className = className == null ? "" : new String(request.getParameter(
					"className").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��ѧ����Ϣ��ѯ*/
			List<Student> studentList = studentDAO.QueryStudent(studentNumber,name,birthday,className,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Students>").append("\r\n");
			for (int i = 0; i < studentList.size(); i++) {
				sb.append("	<Student>").append("\r\n")
				.append("		<studentNumber>")
				.append(studentList.get(i).getStudentNumber())
				.append("</studentNumber>").append("\r\n")
				.append("		<password>")
				.append(studentList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<name>")
				.append(studentList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(studentList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<birthday>")
				.append(studentList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<zzmm>")
				.append(studentList.get(i).getZzmm())
				.append("</zzmm>").append("\r\n")
				.append("		<className>")
				.append(studentList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("		<telephone>")
				.append(studentList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<photo>")
				.append(studentList.get(i).getPhoto())
				.append("</photo>").append("\r\n")
				.append("		<address>")
				.append(studentList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</Student>").append("\r\n");
			}
			sb.append("</Students>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Student student: studentList) {
				  stringer.object();
			  stringer.key("studentNumber").value(student.getStudentNumber());
			  stringer.key("password").value(student.getPassword());
			  stringer.key("name").value(student.getName());
			  stringer.key("sex").value(student.getSex());
			  stringer.key("birthday").value(student.getBirthday());
			  stringer.key("zzmm").value(student.getZzmm());
			  stringer.key("className").value(student.getClassName());
			  stringer.key("telephone").value(student.getTelephone());
			  stringer.key("photo").value(student.getPhoto());
			  stringer.key("address").value(student.getAddress());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѧ����Ϣ����ȡѧ����Ϣ�������������浽�½���ѧ����Ϣ���� */ 
			Student student = new Student();
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNumber(studentNumber);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			student.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			student.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			student.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			student.setBirthday(birthday);
			String zzmm = new String(request.getParameter("zzmm").getBytes("iso-8859-1"), "UTF-8");
			student.setZzmm(zzmm);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			student.setClassName(className);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			student.setTelephone(telephone);
			String photo = new String(request.getParameter("photo").getBytes("iso-8859-1"), "UTF-8");
			student.setPhoto(photo);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			student.setAddress(address);

			/* ����ҵ���ִ����Ӳ��� */
			String result = studentDAO.AddStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѧ����Ϣ����ȡѧ����Ϣ��ѧ��*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = studentDAO.DeleteStudent(studentNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѧ����Ϣ֮ǰ�ȸ���studentNumber��ѯĳ��ѧ����Ϣ*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			Student student = studentDAO.GetStudent(studentNumber);

			// �ͻ��˲�ѯ��ѧ����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("studentNumber").value(student.getStudentNumber());
			  stringer.key("password").value(student.getPassword());
			  stringer.key("name").value(student.getName());
			  stringer.key("sex").value(student.getSex());
			  stringer.key("birthday").value(student.getBirthday());
			  stringer.key("zzmm").value(student.getZzmm());
			  stringer.key("className").value(student.getClassName());
			  stringer.key("telephone").value(student.getTelephone());
			  stringer.key("photo").value(student.getPhoto());
			  stringer.key("address").value(student.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѧ����Ϣ����ȡѧ����Ϣ�������������浽�½���ѧ����Ϣ���� */ 
			Student student = new Student();
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNumber(studentNumber);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			student.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			student.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			student.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			student.setBirthday(birthday);
			String zzmm = new String(request.getParameter("zzmm").getBytes("iso-8859-1"), "UTF-8");
			student.setZzmm(zzmm);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			student.setClassName(className);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			student.setTelephone(telephone);
			String photo = new String(request.getParameter("photo").getBytes("iso-8859-1"), "UTF-8");
			student.setPhoto(photo);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			student.setAddress(address);

			/* ����ҵ���ִ�и��²��� */
			String result = studentDAO.UpdateStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

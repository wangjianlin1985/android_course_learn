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

	/*构造教师信息业务层对象*/
	private TeacherDAO teacherDAO = new TeacherDAO();

	/*默认构造函数*/
	public TeacherServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询教师信息的参数信息*/
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			String position = request.getParameter("position");
			position = position == null ? "" : new String(request.getParameter(
					"position").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行教师信息查询*/
			List<Teacher> teacherList = teacherDAO.QueryTeacher(name,position);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加教师信息：获取教师信息参数，参数保存到新建的教师信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = teacherDAO.AddTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除教师信息：获取教师信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = teacherDAO.DeleteTeacher(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新教师信息之前先根据id查询某个教师信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			Teacher teacher = teacherDAO.GetTeacher(id);

			// 客户端查询的教师信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新教师信息：获取教师信息参数，参数保存到新建的教师信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = teacherDAO.UpdateTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

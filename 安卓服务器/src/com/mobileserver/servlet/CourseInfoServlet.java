package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CourseInfoDAO;
import com.mobileserver.domain.CourseInfo;

import org.json.JSONStringer;

public class CourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造课程信息业务层对象*/
	private CourseInfoDAO courseInfoDAO = new CourseInfoDAO();

	/*默认构造函数*/
	public CourseInfoServlet() {
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
			/*获取查询课程信息的参数信息*/

			/*调用业务逻辑层执行课程信息查询*/
			List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfo();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<CourseInfos>").append("\r\n");
			for (int i = 0; i < courseInfoList.size(); i++) {
				sb.append("	<CourseInfo>").append("\r\n")
				.append("		<id>")
				.append(courseInfoList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<jianjie>")
				.append(courseInfoList.get(i).getJianjie())
				.append("</jianjie>").append("\r\n")
				.append("		<dagan>")
				.append(courseInfoList.get(i).getDagan())
				.append("</dagan>").append("\r\n")
				.append("	</CourseInfo>").append("\r\n");
			}
			sb.append("</CourseInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(CourseInfo courseInfo: courseInfoList) {
				  stringer.object();
			  stringer.key("id").value(courseInfo.getId());
			  stringer.key("jianjie").value(courseInfo.getJianjie());
			  stringer.key("dagan").value(courseInfo.getDagan());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加课程信息：获取课程信息参数，参数保存到新建的课程信息对象 */ 
			CourseInfo courseInfo = new CourseInfo();
			int id = Integer.parseInt(request.getParameter("id"));
			courseInfo.setId(id);
			String jianjie = new String(request.getParameter("jianjie").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setJianjie(jianjie);
			String dagan = new String(request.getParameter("dagan").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setDagan(dagan);

			/* 调用业务层执行添加操作 */
			String result = courseInfoDAO.AddCourseInfo(courseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除课程信息：获取课程信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = courseInfoDAO.DeleteCourseInfo(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新课程信息之前先根据id查询某个课程信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			CourseInfo courseInfo = courseInfoDAO.GetCourseInfo(id);

			// 客户端查询的课程信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(courseInfo.getId());
			  stringer.key("jianjie").value(courseInfo.getJianjie());
			  stringer.key("dagan").value(courseInfo.getDagan());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新课程信息：获取课程信息参数，参数保存到新建的课程信息对象 */ 
			CourseInfo courseInfo = new CourseInfo();
			int id = Integer.parseInt(request.getParameter("id"));
			courseInfo.setId(id);
			String jianjie = new String(request.getParameter("jianjie").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setJianjie(jianjie);
			String dagan = new String(request.getParameter("dagan").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setDagan(dagan);

			/* 调用业务层执行更新操作 */
			String result = courseInfoDAO.UpdateCourseInfo(courseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

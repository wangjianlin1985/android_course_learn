package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.KejianDAO;
import com.mobileserver.domain.Kejian;

import org.json.JSONStringer;

public class KejianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造课件信息业务层对象*/
	private KejianDAO kejianDAO = new KejianDAO();

	/*默认构造函数*/
	public KejianServlet() {
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
			/*获取查询课件信息的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行课件信息查询*/
			List<Kejian> kejianList = kejianDAO.QueryKejian(title);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Kejians>").append("\r\n");
			for (int i = 0; i < kejianList.size(); i++) {
				sb.append("	<Kejian>").append("\r\n")
				.append("		<id>")
				.append(kejianList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<title>")
				.append(kejianList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<path>")
				.append(kejianList.get(i).getPath())
				.append("</path>").append("\r\n")
				.append("		<addTime>")
				.append(kejianList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Kejian>").append("\r\n");
			}
			sb.append("</Kejians>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Kejian kejian: kejianList) {
				  stringer.object();
			  stringer.key("id").value(kejian.getId());
			  stringer.key("title").value(kejian.getTitle());
			  stringer.key("path").value(kejian.getPath());
			  stringer.key("addTime").value(kejian.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加课件信息：获取课件信息参数，参数保存到新建的课件信息对象 */ 
			Kejian kejian = new Kejian();
			int id = Integer.parseInt(request.getParameter("id"));
			kejian.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			kejian.setTitle(title);
			String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "UTF-8");
			kejian.setPath(path);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			kejian.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = kejianDAO.AddKejian(kejian);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除课件信息：获取课件信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = kejianDAO.DeleteKejian(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新课件信息之前先根据id查询某个课件信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			Kejian kejian = kejianDAO.GetKejian(id);

			// 客户端查询的课件信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(kejian.getId());
			  stringer.key("title").value(kejian.getTitle());
			  stringer.key("path").value(kejian.getPath());
			  stringer.key("addTime").value(kejian.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新课件信息：获取课件信息参数，参数保存到新建的课件信息对象 */ 
			Kejian kejian = new Kejian();
			int id = Integer.parseInt(request.getParameter("id"));
			kejian.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			kejian.setTitle(title);
			String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "UTF-8");
			kejian.setPath(path);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			kejian.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = kejianDAO.UpdateKejian(kejian);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ChapterDAO;
import com.mobileserver.domain.Chapter;

import org.json.JSONStringer;

public class ChapterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造章信息业务层对象*/
	private ChapterDAO chapterDAO = new ChapterDAO();

	/*默认构造函数*/
	public ChapterServlet() {
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
			/*获取查询章信息的参数信息*/

			/*调用业务逻辑层执行章信息查询*/
			List<Chapter> chapterList = chapterDAO.QueryChapter();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Chapters>").append("\r\n");
			for (int i = 0; i < chapterList.size(); i++) {
				sb.append("	<Chapter>").append("\r\n")
				.append("		<id>")
				.append(chapterList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<title>")
				.append(chapterList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<addTime>")
				.append(chapterList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Chapter>").append("\r\n");
			}
			sb.append("</Chapters>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Chapter chapter: chapterList) {
				  stringer.object();
			  stringer.key("id").value(chapter.getId());
			  stringer.key("title").value(chapter.getTitle());
			  stringer.key("addTime").value(chapter.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加章信息：获取章信息参数，参数保存到新建的章信息对象 */ 
			Chapter chapter = new Chapter();
			int id = Integer.parseInt(request.getParameter("id"));
			chapter.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			chapter.setTitle(title);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			chapter.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = chapterDAO.AddChapter(chapter);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除章信息：获取章信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = chapterDAO.DeleteChapter(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新章信息之前先根据id查询某个章信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			Chapter chapter = chapterDAO.GetChapter(id);

			// 客户端查询的章信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(chapter.getId());
			  stringer.key("title").value(chapter.getTitle());
			  stringer.key("addTime").value(chapter.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新章信息：获取章信息参数，参数保存到新建的章信息对象 */ 
			Chapter chapter = new Chapter();
			int id = Integer.parseInt(request.getParameter("id"));
			chapter.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			chapter.setTitle(title);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			chapter.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = chapterDAO.UpdateChapter(chapter);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

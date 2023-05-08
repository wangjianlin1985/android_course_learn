package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.VideoDAO;
import com.mobileserver.domain.Video;

import org.json.JSONStringer;

public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造视频信息业务层对象*/
	private VideoDAO videoDAO = new VideoDAO();

	/*默认构造函数*/
	public VideoServlet() {
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
			/*获取查询视频信息的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			int chapterId = 0;
			if (request.getParameter("chapterId") != null)
				chapterId = Integer.parseInt(request.getParameter("chapterId"));

			/*调用业务逻辑层执行视频信息查询*/
			List<Video> videoList = videoDAO.QueryVideo(title,chapterId);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Videos>").append("\r\n");
			for (int i = 0; i < videoList.size(); i++) {
				sb.append("	<Video>").append("\r\n")
				.append("		<id>")
				.append(videoList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<title>")
				.append(videoList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<chapterId>")
				.append(videoList.get(i).getChapterId())
				.append("</chapterId>").append("\r\n")
				.append("		<path>")
				.append(videoList.get(i).getPath())
				.append("</path>").append("\r\n")
				.append("		<addTime>")
				.append(videoList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Video>").append("\r\n");
			}
			sb.append("</Videos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Video video: videoList) {
				  stringer.object();
			  stringer.key("id").value(video.getId());
			  stringer.key("title").value(video.getTitle());
			  stringer.key("chapterId").value(video.getChapterId());
			  stringer.key("path").value(video.getPath());
			  stringer.key("addTime").value(video.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加视频信息：获取视频信息参数，参数保存到新建的视频信息对象 */ 
			Video video = new Video();
			int id = Integer.parseInt(request.getParameter("id"));
			video.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			video.setTitle(title);
			int chapterId = Integer.parseInt(request.getParameter("chapterId"));
			video.setChapterId(chapterId);
			String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "UTF-8");
			video.setPath(path);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			video.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = videoDAO.AddVideo(video);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除视频信息：获取视频信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = videoDAO.DeleteVideo(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新视频信息之前先根据id查询某个视频信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			Video video = videoDAO.GetVideo(id);

			// 客户端查询的视频信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(video.getId());
			  stringer.key("title").value(video.getTitle());
			  stringer.key("chapterId").value(video.getChapterId());
			  stringer.key("path").value(video.getPath());
			  stringer.key("addTime").value(video.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新视频信息：获取视频信息参数，参数保存到新建的视频信息对象 */ 
			Video video = new Video();
			int id = Integer.parseInt(request.getParameter("id"));
			video.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			video.setTitle(title);
			int chapterId = Integer.parseInt(request.getParameter("chapterId"));
			video.setChapterId(chapterId);
			String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "UTF-8");
			video.setPath(path);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			video.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = videoDAO.UpdateVideo(video);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

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

	/*������Ƶ��Ϣҵ������*/
	private VideoDAO videoDAO = new VideoDAO();

	/*Ĭ�Ϲ��캯��*/
	public VideoServlet() {
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
			/*��ȡ��ѯ��Ƶ��Ϣ�Ĳ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			int chapterId = 0;
			if (request.getParameter("chapterId") != null)
				chapterId = Integer.parseInt(request.getParameter("chapterId"));

			/*����ҵ���߼���ִ����Ƶ��Ϣ��ѯ*/
			List<Video> videoList = videoDAO.QueryVideo(title,chapterId);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ƶ��Ϣ����ȡ��Ƶ��Ϣ�������������浽�½�����Ƶ��Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = videoDAO.AddVideo(video);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ƶ��Ϣ����ȡ��Ƶ��Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = videoDAO.DeleteVideo(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ƶ��Ϣ֮ǰ�ȸ���id��ѯĳ����Ƶ��Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			Video video = videoDAO.GetVideo(id);

			// �ͻ��˲�ѯ����Ƶ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ƶ��Ϣ����ȡ��Ƶ��Ϣ�������������浽�½�����Ƶ��Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = videoDAO.UpdateVideo(video);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

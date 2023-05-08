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

	/*��������Ϣҵ������*/
	private ChapterDAO chapterDAO = new ChapterDAO();

	/*Ĭ�Ϲ��캯��*/
	public ChapterServlet() {
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
			/*��ȡ��ѯ����Ϣ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ������Ϣ��ѯ*/
			List<Chapter> chapterList = chapterDAO.QueryChapter();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �������Ϣ����ȡ����Ϣ�������������浽�½�������Ϣ���� */ 
			Chapter chapter = new Chapter();
			int id = Integer.parseInt(request.getParameter("id"));
			chapter.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			chapter.setTitle(title);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			chapter.setAddTime(addTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = chapterDAO.AddChapter(chapter);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������Ϣ����ȡ����Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = chapterDAO.DeleteChapter(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������Ϣ֮ǰ�ȸ���id��ѯĳ������Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			Chapter chapter = chapterDAO.GetChapter(id);

			// �ͻ��˲�ѯ������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ��������Ϣ����ȡ����Ϣ�������������浽�½�������Ϣ���� */ 
			Chapter chapter = new Chapter();
			int id = Integer.parseInt(request.getParameter("id"));
			chapter.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			chapter.setTitle(title);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			chapter.setAddTime(addTime);

			/* ����ҵ���ִ�и��²��� */
			String result = chapterDAO.UpdateChapter(chapter);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

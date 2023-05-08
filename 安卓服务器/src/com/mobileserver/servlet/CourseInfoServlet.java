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

	/*����γ���Ϣҵ������*/
	private CourseInfoDAO courseInfoDAO = new CourseInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public CourseInfoServlet() {
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
			/*��ȡ��ѯ�γ���Ϣ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�пγ���Ϣ��ѯ*/
			List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfo();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӿγ���Ϣ����ȡ�γ���Ϣ�������������浽�½��Ŀγ���Ϣ���� */ 
			CourseInfo courseInfo = new CourseInfo();
			int id = Integer.parseInt(request.getParameter("id"));
			courseInfo.setId(id);
			String jianjie = new String(request.getParameter("jianjie").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setJianjie(jianjie);
			String dagan = new String(request.getParameter("dagan").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setDagan(dagan);

			/* ����ҵ���ִ����Ӳ��� */
			String result = courseInfoDAO.AddCourseInfo(courseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���γ���Ϣ����ȡ�γ���Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = courseInfoDAO.DeleteCourseInfo(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¿γ���Ϣ֮ǰ�ȸ���id��ѯĳ���γ���Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			CourseInfo courseInfo = courseInfoDAO.GetCourseInfo(id);

			// �ͻ��˲�ѯ�Ŀγ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¿γ���Ϣ����ȡ�γ���Ϣ�������������浽�½��Ŀγ���Ϣ���� */ 
			CourseInfo courseInfo = new CourseInfo();
			int id = Integer.parseInt(request.getParameter("id"));
			courseInfo.setId(id);
			String jianjie = new String(request.getParameter("jianjie").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setJianjie(jianjie);
			String dagan = new String(request.getParameter("dagan").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setDagan(dagan);

			/* ����ҵ���ִ�и��²��� */
			String result = courseInfoDAO.UpdateCourseInfo(courseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

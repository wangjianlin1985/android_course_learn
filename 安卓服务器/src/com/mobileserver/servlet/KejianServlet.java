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

	/*����μ���Ϣҵ������*/
	private KejianDAO kejianDAO = new KejianDAO();

	/*Ĭ�Ϲ��캯��*/
	public KejianServlet() {
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
			/*��ȡ��ѯ�μ���Ϣ�Ĳ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�пμ���Ϣ��ѯ*/
			List<Kejian> kejianList = kejianDAO.QueryKejian(title);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӿμ���Ϣ����ȡ�μ���Ϣ�������������浽�½��Ŀμ���Ϣ���� */ 
			Kejian kejian = new Kejian();
			int id = Integer.parseInt(request.getParameter("id"));
			kejian.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			kejian.setTitle(title);
			String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "UTF-8");
			kejian.setPath(path);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			kejian.setAddTime(addTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = kejianDAO.AddKejian(kejian);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���μ���Ϣ����ȡ�μ���Ϣ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = kejianDAO.DeleteKejian(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¿μ���Ϣ֮ǰ�ȸ���id��ѯĳ���μ���Ϣ*/
			int id = Integer.parseInt(request.getParameter("id"));
			Kejian kejian = kejianDAO.GetKejian(id);

			// �ͻ��˲�ѯ�Ŀμ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¿μ���Ϣ����ȡ�μ���Ϣ�������������浽�½��Ŀμ���Ϣ���� */ 
			Kejian kejian = new Kejian();
			int id = Integer.parseInt(request.getParameter("id"));
			kejian.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			kejian.setTitle(title);
			String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "UTF-8");
			kejian.setPath(path);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			kejian.setAddTime(addTime);

			/* ����ҵ���ִ�и��²��� */
			String result = kejianDAO.UpdateKejian(kejian);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

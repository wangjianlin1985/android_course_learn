package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HomeworkUploadDAO;
import com.mobileserver.domain.HomeworkUpload;

import org.json.JSONStringer;

public class HomeworkUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ϴ�����ҵҵ������*/
	private HomeworkUploadDAO homeworkUploadDAO = new HomeworkUploadDAO();

	/*Ĭ�Ϲ��캯��*/
	public HomeworkUploadServlet() {
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
			/*��ȡ��ѯ�ϴ�����ҵ�Ĳ�����Ϣ*/
			int homeworkTaskObj = 0;
			if (request.getParameter("homeworkTaskObj") != null)
				homeworkTaskObj = Integer.parseInt(request.getParameter("homeworkTaskObj"));
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");

			/*����ҵ���߼���ִ���ϴ�����ҵ��ѯ*/
			List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUpload(homeworkTaskObj,studentObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<HomeworkUploads>").append("\r\n");
			for (int i = 0; i < homeworkUploadList.size(); i++) {
				sb.append("	<HomeworkUpload>").append("\r\n")
				.append("		<uploadId>")
				.append(homeworkUploadList.get(i).getUploadId())
				.append("</uploadId>").append("\r\n")
				.append("		<homeworkTaskObj>")
				.append(homeworkUploadList.get(i).getHomeworkTaskObj())
				.append("</homeworkTaskObj>").append("\r\n")
				.append("		<studentObj>")
				.append(homeworkUploadList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<homeworkFile>")
				.append(homeworkUploadList.get(i).getHomeworkFile())
				.append("</homeworkFile>").append("\r\n")
				.append("		<uploadTime>")
				.append(homeworkUploadList.get(i).getUploadTime())
				.append("</uploadTime>").append("\r\n")
				.append("		<resultFile>")
				.append(homeworkUploadList.get(i).getResultFile())
				.append("</resultFile>").append("\r\n")
				.append("		<pigaiTime>")
				.append(homeworkUploadList.get(i).getPigaiTime())
				.append("</pigaiTime>").append("\r\n")
				.append("		<pigaiFlag>")
				.append(homeworkUploadList.get(i).getPigaiFlag())
				.append("</pigaiFlag>").append("\r\n")
				.append("		<pingyu>")
				.append(homeworkUploadList.get(i).getPingyu())
				.append("</pingyu>").append("\r\n")
				.append("	</HomeworkUpload>").append("\r\n");
			}
			sb.append("</HomeworkUploads>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(HomeworkUpload homeworkUpload: homeworkUploadList) {
				  stringer.object();
			  stringer.key("uploadId").value(homeworkUpload.getUploadId());
			  stringer.key("homeworkTaskObj").value(homeworkUpload.getHomeworkTaskObj());
			  stringer.key("studentObj").value(homeworkUpload.getStudentObj());
			  stringer.key("homeworkFile").value(homeworkUpload.getHomeworkFile());
			  stringer.key("uploadTime").value(homeworkUpload.getUploadTime());
			  stringer.key("resultFile").value(homeworkUpload.getResultFile());
			  stringer.key("pigaiTime").value(homeworkUpload.getPigaiTime());
			  stringer.key("pigaiFlag").value(homeworkUpload.getPigaiFlag());
			  stringer.key("pingyu").value(homeworkUpload.getPingyu());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����ϴ�����ҵ����ȡ�ϴ�����ҵ�������������浽�½����ϴ�����ҵ���� */ 
			HomeworkUpload homeworkUpload = new HomeworkUpload();
			int uploadId = Integer.parseInt(request.getParameter("uploadId"));
			homeworkUpload.setUploadId(uploadId);
			int homeworkTaskObj = Integer.parseInt(request.getParameter("homeworkTaskObj"));
			homeworkUpload.setHomeworkTaskObj(homeworkTaskObj);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setStudentObj(studentObj);
			String homeworkFile = new String(request.getParameter("homeworkFile").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setHomeworkFile(homeworkFile);
			String uploadTime = new String(request.getParameter("uploadTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setUploadTime(uploadTime);
			String resultFile = new String(request.getParameter("resultFile").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setResultFile(resultFile);
			String pigaiTime = new String(request.getParameter("pigaiTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setPigaiTime(pigaiTime);
			int pigaiFlag = Integer.parseInt(request.getParameter("pigaiFlag"));
			homeworkUpload.setPigaiFlag(pigaiFlag);
			String pingyu = new String(request.getParameter("pingyu").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setPingyu(pingyu);

			/* ����ҵ���ִ����Ӳ��� */
			String result = homeworkUploadDAO.AddHomeworkUpload(homeworkUpload);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ϴ�����ҵ����ȡ�ϴ�����ҵ�ļ�¼���*/
			int uploadId = Integer.parseInt(request.getParameter("uploadId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = homeworkUploadDAO.DeleteHomeworkUpload(uploadId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����ϴ�����ҵ֮ǰ�ȸ���uploadId��ѯĳ���ϴ�����ҵ*/
			int uploadId = Integer.parseInt(request.getParameter("uploadId"));
			HomeworkUpload homeworkUpload = homeworkUploadDAO.GetHomeworkUpload(uploadId);

			// �ͻ��˲�ѯ���ϴ�����ҵ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("uploadId").value(homeworkUpload.getUploadId());
			  stringer.key("homeworkTaskObj").value(homeworkUpload.getHomeworkTaskObj());
			  stringer.key("studentObj").value(homeworkUpload.getStudentObj());
			  stringer.key("homeworkFile").value(homeworkUpload.getHomeworkFile());
			  stringer.key("uploadTime").value(homeworkUpload.getUploadTime());
			  stringer.key("resultFile").value(homeworkUpload.getResultFile());
			  stringer.key("pigaiTime").value(homeworkUpload.getPigaiTime());
			  stringer.key("pigaiFlag").value(homeworkUpload.getPigaiFlag());
			  stringer.key("pingyu").value(homeworkUpload.getPingyu());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����ϴ�����ҵ����ȡ�ϴ�����ҵ�������������浽�½����ϴ�����ҵ���� */ 
			HomeworkUpload homeworkUpload = new HomeworkUpload();
			int uploadId = Integer.parseInt(request.getParameter("uploadId"));
			homeworkUpload.setUploadId(uploadId);
			int homeworkTaskObj = Integer.parseInt(request.getParameter("homeworkTaskObj"));
			homeworkUpload.setHomeworkTaskObj(homeworkTaskObj);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setStudentObj(studentObj);
			String homeworkFile = new String(request.getParameter("homeworkFile").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setHomeworkFile(homeworkFile);
			String uploadTime = new String(request.getParameter("uploadTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setUploadTime(uploadTime);
			String resultFile = new String(request.getParameter("resultFile").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setResultFile(resultFile);
			String pigaiTime = new String(request.getParameter("pigaiTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setPigaiTime(pigaiTime);
			int pigaiFlag = Integer.parseInt(request.getParameter("pigaiFlag"));
			homeworkUpload.setPigaiFlag(pigaiFlag);
			String pingyu = new String(request.getParameter("pingyu").getBytes("iso-8859-1"), "UTF-8");
			homeworkUpload.setPingyu(pingyu);

			/* ����ҵ���ִ�и��²��� */
			String result = homeworkUploadDAO.UpdateHomeworkUpload(homeworkUpload);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

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

	/*构造上传的作业业务层对象*/
	private HomeworkUploadDAO homeworkUploadDAO = new HomeworkUploadDAO();

	/*默认构造函数*/
	public HomeworkUploadServlet() {
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
			/*获取查询上传的作业的参数信息*/
			int homeworkTaskObj = 0;
			if (request.getParameter("homeworkTaskObj") != null)
				homeworkTaskObj = Integer.parseInt(request.getParameter("homeworkTaskObj"));
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");

			/*调用业务逻辑层执行上传的作业查询*/
			List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUpload(homeworkTaskObj,studentObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加上传的作业：获取上传的作业参数，参数保存到新建的上传的作业对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = homeworkUploadDAO.AddHomeworkUpload(homeworkUpload);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除上传的作业：获取上传的作业的记录编号*/
			int uploadId = Integer.parseInt(request.getParameter("uploadId"));
			/*调用业务逻辑层执行删除操作*/
			String result = homeworkUploadDAO.DeleteHomeworkUpload(uploadId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新上传的作业之前先根据uploadId查询某个上传的作业*/
			int uploadId = Integer.parseInt(request.getParameter("uploadId"));
			HomeworkUpload homeworkUpload = homeworkUploadDAO.GetHomeworkUpload(uploadId);

			// 客户端查询的上传的作业对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新上传的作业：获取上传的作业参数，参数保存到新建的上传的作业对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = homeworkUploadDAO.UpdateHomeworkUpload(homeworkUpload);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

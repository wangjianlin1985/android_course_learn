package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HomeworkTaskDAO;
import com.mobileserver.domain.HomeworkTask;

import org.json.JSONStringer;

public class HomeworkTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造作业任务业务层对象*/
	private HomeworkTaskDAO homeworkTaskDAO = new HomeworkTaskDAO();

	/*默认构造函数*/
	public HomeworkTaskServlet() {
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
			/*获取查询作业任务的参数信息*/
			int teacherObj = 0;
			if (request.getParameter("teacherObj") != null)
				teacherObj = Integer.parseInt(request.getParameter("teacherObj"));
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行作业任务查询*/
			List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTask(teacherObj,title);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<HomeworkTasks>").append("\r\n");
			for (int i = 0; i < homeworkTaskList.size(); i++) {
				sb.append("	<HomeworkTask>").append("\r\n")
				.append("		<homeworkId>")
				.append(homeworkTaskList.get(i).getHomeworkId())
				.append("</homeworkId>").append("\r\n")
				.append("		<teacherObj>")
				.append(homeworkTaskList.get(i).getTeacherObj())
				.append("</teacherObj>").append("\r\n")
				.append("		<title>")
				.append(homeworkTaskList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(homeworkTaskList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<addTime>")
				.append(homeworkTaskList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</HomeworkTask>").append("\r\n");
			}
			sb.append("</HomeworkTasks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(HomeworkTask homeworkTask: homeworkTaskList) {
				  stringer.object();
			  stringer.key("homeworkId").value(homeworkTask.getHomeworkId());
			  stringer.key("teacherObj").value(homeworkTask.getTeacherObj());
			  stringer.key("title").value(homeworkTask.getTitle());
			  stringer.key("content").value(homeworkTask.getContent());
			  stringer.key("addTime").value(homeworkTask.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加作业任务：获取作业任务参数，参数保存到新建的作业任务对象 */ 
			HomeworkTask homeworkTask = new HomeworkTask();
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			homeworkTask.setHomeworkId(homeworkId);
			int teacherObj = Integer.parseInt(request.getParameter("teacherObj"));
			homeworkTask.setTeacherObj(teacherObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = homeworkTaskDAO.AddHomeworkTask(homeworkTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除作业任务：获取作业任务的记录编号*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			/*调用业务逻辑层执行删除操作*/
			String result = homeworkTaskDAO.DeleteHomeworkTask(homeworkId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新作业任务之前先根据homeworkId查询某个作业任务*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			HomeworkTask homeworkTask = homeworkTaskDAO.GetHomeworkTask(homeworkId);

			// 客户端查询的作业任务对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("homeworkId").value(homeworkTask.getHomeworkId());
			  stringer.key("teacherObj").value(homeworkTask.getTeacherObj());
			  stringer.key("title").value(homeworkTask.getTitle());
			  stringer.key("content").value(homeworkTask.getContent());
			  stringer.key("addTime").value(homeworkTask.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新作业任务：获取作业任务参数，参数保存到新建的作业任务对象 */ 
			HomeworkTask homeworkTask = new HomeworkTask();
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			homeworkTask.setHomeworkId(homeworkId);
			int teacherObj = Integer.parseInt(request.getParameter("teacherObj"));
			homeworkTask.setTeacherObj(teacherObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			homeworkTask.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = homeworkTaskDAO.UpdateHomeworkTask(homeworkTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

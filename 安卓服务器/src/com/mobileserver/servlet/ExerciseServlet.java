package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ExerciseDAO;
import com.mobileserver.domain.Exercise;

import org.json.JSONStringer;

public class ExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造习题信息业务层对象*/
	private ExerciseDAO exerciseDAO = new ExerciseDAO();

	/*默认构造函数*/
	public ExerciseServlet() {
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
			/*获取查询习题信息的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			int chapterId = 0;
			if (request.getParameter("chapterId") != null)
				chapterId = Integer.parseInt(request.getParameter("chapterId"));

			/*调用业务逻辑层执行习题信息查询*/
			List<Exercise> exerciseList = exerciseDAO.QueryExercise(title,chapterId);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Exercises>").append("\r\n");
			for (int i = 0; i < exerciseList.size(); i++) {
				sb.append("	<Exercise>").append("\r\n")
				.append("		<id>")
				.append(exerciseList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<title>")
				.append(exerciseList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<chapterId>")
				.append(exerciseList.get(i).getChapterId())
				.append("</chapterId>").append("\r\n")
				.append("		<content>")
				.append(exerciseList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<addTime>")
				.append(exerciseList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Exercise>").append("\r\n");
			}
			sb.append("</Exercises>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Exercise exercise: exerciseList) {
				  stringer.object();
			  stringer.key("id").value(exercise.getId());
			  stringer.key("title").value(exercise.getTitle());
			  stringer.key("chapterId").value(exercise.getChapterId());
			  stringer.key("content").value(exercise.getContent());
			  stringer.key("addTime").value(exercise.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加习题信息：获取习题信息参数，参数保存到新建的习题信息对象 */ 
			Exercise exercise = new Exercise();
			int id = Integer.parseInt(request.getParameter("id"));
			exercise.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			exercise.setTitle(title);
			int chapterId = Integer.parseInt(request.getParameter("chapterId"));
			exercise.setChapterId(chapterId);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			exercise.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			exercise.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = exerciseDAO.AddExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除习题信息：获取习题信息的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = exerciseDAO.DeleteExercise(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新习题信息之前先根据id查询某个习题信息*/
			int id = Integer.parseInt(request.getParameter("id"));
			Exercise exercise = exerciseDAO.GetExercise(id);

			// 客户端查询的习题信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(exercise.getId());
			  stringer.key("title").value(exercise.getTitle());
			  stringer.key("chapterId").value(exercise.getChapterId());
			  stringer.key("content").value(exercise.getContent());
			  stringer.key("addTime").value(exercise.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新习题信息：获取习题信息参数，参数保存到新建的习题信息对象 */ 
			Exercise exercise = new Exercise();
			int id = Integer.parseInt(request.getParameter("id"));
			exercise.setId(id);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			exercise.setTitle(title);
			int chapterId = Integer.parseInt(request.getParameter("chapterId"));
			exercise.setChapterId(chapterId);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			exercise.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			exercise.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = exerciseDAO.UpdateExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QuestionDAO;
import com.mobileserver.domain.Question;

import org.json.JSONStringer;

public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造在线问答业务层对象*/
	private QuestionDAO questionDAO = new QuestionDAO();

	/*默认构造函数*/
	public QuestionServlet() {
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
			/*获取查询在线问答的参数信息*/
			int teacherId = 0;
			if (request.getParameter("teacherId") != null)
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			String questioner = request.getParameter("questioner");
			questioner = questioner == null ? "" : new String(request.getParameter(
					"questioner").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行在线问答查询*/
			List<Question> questionList = questionDAO.QueryQuestion(teacherId,questioner);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Questions>").append("\r\n");
			for (int i = 0; i < questionList.size(); i++) {
				sb.append("	<Question>").append("\r\n")
				.append("		<id>")
				.append(questionList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<teacherId>")
				.append(questionList.get(i).getTeacherId())
				.append("</teacherId>").append("\r\n")
				.append("		<questioner>")
				.append(questionList.get(i).getQuestioner())
				.append("</questioner>").append("\r\n")
				.append("		<content>")
				.append(questionList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<reply>")
				.append(questionList.get(i).getReply())
				.append("</reply>").append("\r\n")
				.append("		<addTime>")
				.append(questionList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Question>").append("\r\n");
			}
			sb.append("</Questions>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Question question: questionList) {
				  stringer.object();
			  stringer.key("id").value(question.getId());
			  stringer.key("teacherId").value(question.getTeacherId());
			  stringer.key("questioner").value(question.getQuestioner());
			  stringer.key("content").value(question.getContent());
			  stringer.key("reply").value(question.getReply());
			  stringer.key("addTime").value(question.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加在线问答：获取在线问答参数，参数保存到新建的在线问答对象 */ 
			Question question = new Question();
			int id = Integer.parseInt(request.getParameter("id"));
			question.setId(id);
			int teacherId = Integer.parseInt(request.getParameter("teacherId"));
			question.setTeacherId(teacherId);
			String questioner = new String(request.getParameter("questioner").getBytes("iso-8859-1"), "UTF-8");
			question.setQuestioner(questioner);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			question.setContent(content);
			String reply = new String(request.getParameter("reply").getBytes("iso-8859-1"), "UTF-8");
			question.setReply(reply);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			question.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = questionDAO.AddQuestion(question);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除在线问答：获取在线问答的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = questionDAO.DeleteQuestion(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新在线问答之前先根据id查询某个在线问答*/
			int id = Integer.parseInt(request.getParameter("id"));
			Question question = questionDAO.GetQuestion(id);

			// 客户端查询的在线问答对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(question.getId());
			  stringer.key("teacherId").value(question.getTeacherId());
			  stringer.key("questioner").value(question.getQuestioner());
			  stringer.key("content").value(question.getContent());
			  stringer.key("reply").value(question.getReply());
			  stringer.key("addTime").value(question.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新在线问答：获取在线问答参数，参数保存到新建的在线问答对象 */ 
			Question question = new Question();
			int id = Integer.parseInt(request.getParameter("id"));
			question.setId(id);
			int teacherId = Integer.parseInt(request.getParameter("teacherId"));
			question.setTeacherId(teacherId);
			String questioner = new String(request.getParameter("questioner").getBytes("iso-8859-1"), "UTF-8");
			question.setQuestioner(questioner);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			question.setContent(content);
			String reply = new String(request.getParameter("reply").getBytes("iso-8859-1"), "UTF-8");
			question.setReply(reply);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			question.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = questionDAO.UpdateQuestion(question);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

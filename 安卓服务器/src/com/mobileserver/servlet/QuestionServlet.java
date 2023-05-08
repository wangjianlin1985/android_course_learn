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

	/*���������ʴ�ҵ������*/
	private QuestionDAO questionDAO = new QuestionDAO();

	/*Ĭ�Ϲ��캯��*/
	public QuestionServlet() {
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
			/*��ȡ��ѯ�����ʴ�Ĳ�����Ϣ*/
			int teacherId = 0;
			if (request.getParameter("teacherId") != null)
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			String questioner = request.getParameter("questioner");
			questioner = questioner == null ? "" : new String(request.getParameter(
					"questioner").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�������ʴ��ѯ*/
			List<Question> questionList = questionDAO.QueryQuestion(teacherId,questioner);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��������ʴ𣺻�ȡ�����ʴ�������������浽�½��������ʴ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = questionDAO.AddQuestion(question);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�������ʴ𣺻�ȡ�����ʴ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = questionDAO.DeleteQuestion(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���������ʴ�֮ǰ�ȸ���id��ѯĳ�������ʴ�*/
			int id = Integer.parseInt(request.getParameter("id"));
			Question question = questionDAO.GetQuestion(id);

			// �ͻ��˲�ѯ�������ʴ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���������ʴ𣺻�ȡ�����ʴ�������������浽�½��������ʴ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = questionDAO.UpdateQuestion(question);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

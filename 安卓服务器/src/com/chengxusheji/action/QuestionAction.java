package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.QuestionDAO;
import com.chengxusheji.domain.Question;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class QuestionAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���ʵ���ʦ*/
    private Teacher teacherId;
    public void setTeacherId(Teacher teacherId) {
        this.teacherId = teacherId;
    }
    public Teacher getTeacherId() {
        return this.teacherId;
    }

    /*�������Ҫ��ѯ������: ������*/
    private String questioner;
    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }
    public String getQuestioner() {
        return this.questioner;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource TeacherDAO teacherDAO;
    @Resource QuestionDAO questionDAO;

    /*��������Question����*/
    private Question question;
    public void setQuestion(Question question) {
        this.question = question;
    }
    public Question getQuestion() {
        return this.question;
    }

    /*��ת�����Question��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Teacher��Ϣ*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*���Question��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddQuestion() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherId = teacherDAO.GetTeacherById(question.getTeacherId().getId());
            question.setTeacherId(teacherId);
            questionDAO.AddQuestion(question);
            ctx.put("message",  java.net.URLEncoder.encode("Question��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Question���ʧ��!"));
            return "error";
        }
    }

    /*��ѯQuestion��Ϣ*/
    public String QueryQuestion() {
        if(currentPage == 0) currentPage = 1;
        if(questioner == null) questioner = "";
        List<Question> questionList = questionDAO.QueryQuestionInfo(teacherId, questioner, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        questionDAO.CalculateTotalPageAndRecordNumber(teacherId, questioner);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = questionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = questionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("questionList",  questionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherId", teacherId);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("questioner", questioner);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryQuestionOutputToExcel() { 
        if(questioner == null) questioner = "";
        List<Question> questionList = questionDAO.QueryQuestionInfo(teacherId,questioner);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Question��Ϣ��¼"; 
        String[] headers = { "��¼���","���ʵ���ʦ","������","��������","�ظ�����","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<questionList.size();i++) {
        	Question question = questionList.get(i); 
        	dataset.add(new String[]{question.getId() + "",question.getTeacherId().getName(),
question.getQuestioner(),question.getContent(),question.getReply(),question.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Question.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯQuestion��Ϣ*/
    public String FrontQueryQuestion() {
        if(currentPage == 0) currentPage = 1;
        if(questioner == null) questioner = "";
        List<Question> questionList = questionDAO.QueryQuestionInfo(teacherId, questioner, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        questionDAO.CalculateTotalPageAndRecordNumber(teacherId, questioner);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = questionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = questionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("questionList",  questionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherId", teacherId);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("questioner", questioner);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Question��Ϣ*/
    public String ModifyQuestionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡQuestion����*/
        Question question = questionDAO.GetQuestionById(id);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("question",  question);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Question��Ϣ*/
    public String FrontShowQuestionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡQuestion����*/
        Question question = questionDAO.GetQuestionById(id);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("question",  question);
        return "front_show_view";
    }

    /*�����޸�Question��Ϣ*/
    public String ModifyQuestion() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherId = teacherDAO.GetTeacherById(question.getTeacherId().getId());
            question.setTeacherId(teacherId);
            questionDAO.UpdateQuestion(question);
            ctx.put("message",  java.net.URLEncoder.encode("Question��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Question��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Question��Ϣ*/
    public String DeleteQuestion() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            questionDAO.DeleteQuestion(id);
            ctx.put("message",  java.net.URLEncoder.encode("Questionɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Questionɾ��ʧ��!"));
            return "error";
        }
    }

}

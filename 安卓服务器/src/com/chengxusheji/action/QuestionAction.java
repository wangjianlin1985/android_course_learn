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

    /*界面层需要查询的属性: 提问的老师*/
    private Teacher teacherId;
    public void setTeacherId(Teacher teacherId) {
        this.teacherId = teacherId;
    }
    public Teacher getTeacherId() {
        return this.teacherId;
    }

    /*界面层需要查询的属性: 提问者*/
    private String questioner;
    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }
    public String getQuestioner() {
        return this.questioner;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource TeacherDAO teacherDAO;
    @Resource QuestionDAO questionDAO;

    /*待操作的Question对象*/
    private Question question;
    public void setQuestion(Question question) {
        this.question = question;
    }
    public Question getQuestion() {
        return this.question;
    }

    /*跳转到添加Question视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加Question信息*/
    @SuppressWarnings("deprecation")
    public String AddQuestion() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherId = teacherDAO.GetTeacherById(question.getTeacherId().getId());
            question.setTeacherId(teacherId);
            questionDAO.AddQuestion(question);
            ctx.put("message",  java.net.URLEncoder.encode("Question添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Question添加失败!"));
            return "error";
        }
    }

    /*查询Question信息*/
    public String QueryQuestion() {
        if(currentPage == 0) currentPage = 1;
        if(questioner == null) questioner = "";
        List<Question> questionList = questionDAO.QueryQuestionInfo(teacherId, questioner, currentPage);
        /*计算总的页数和总的记录数*/
        questionDAO.CalculateTotalPageAndRecordNumber(teacherId, questioner);
        /*获取到总的页码数目*/
        totalPage = questionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryQuestionOutputToExcel() { 
        if(questioner == null) questioner = "";
        List<Question> questionList = questionDAO.QueryQuestionInfo(teacherId,questioner);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Question信息记录"; 
        String[] headers = { "记录编号","提问的老师","提问者","提问内容","回复内容","提问时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Question.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Question信息*/
    public String FrontQueryQuestion() {
        if(currentPage == 0) currentPage = 1;
        if(questioner == null) questioner = "";
        List<Question> questionList = questionDAO.QueryQuestionInfo(teacherId, questioner, currentPage);
        /*计算总的页数和总的记录数*/
        questionDAO.CalculateTotalPageAndRecordNumber(teacherId, questioner);
        /*获取到总的页码数目*/
        totalPage = questionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Question信息*/
    public String ModifyQuestionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Question对象*/
        Question question = questionDAO.GetQuestionById(id);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("question",  question);
        return "modify_view";
    }

    /*查询要修改的Question信息*/
    public String FrontShowQuestionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Question对象*/
        Question question = questionDAO.GetQuestionById(id);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("question",  question);
        return "front_show_view";
    }

    /*更新修改Question信息*/
    public String ModifyQuestion() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherId = teacherDAO.GetTeacherById(question.getTeacherId().getId());
            question.setTeacherId(teacherId);
            questionDAO.UpdateQuestion(question);
            ctx.put("message",  java.net.URLEncoder.encode("Question信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Question信息更新失败!"));
            return "error";
       }
   }

    /*删除Question信息*/
    public String DeleteQuestion() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            questionDAO.DeleteQuestion(id);
            ctx.put("message",  java.net.URLEncoder.encode("Question删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Question删除失败!"));
            return "error";
        }
    }

}

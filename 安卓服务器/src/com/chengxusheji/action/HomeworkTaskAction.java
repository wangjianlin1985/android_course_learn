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
import com.chengxusheji.dao.HomeworkTaskDAO;
import com.chengxusheji.domain.HomeworkTask;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HomeworkTaskAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��ʦ*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
    }

    /*�������Ҫ��ѯ������: ��ҵ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
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

    private int homeworkId;
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }
    public int getHomeworkId() {
        return homeworkId;
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
    @Resource HomeworkTaskDAO homeworkTaskDAO;

    /*��������HomeworkTask����*/
    private HomeworkTask homeworkTask;
    public void setHomeworkTask(HomeworkTask homeworkTask) {
        this.homeworkTask = homeworkTask;
    }
    public HomeworkTask getHomeworkTask() {
        return this.homeworkTask;
    }

    /*��ת�����HomeworkTask��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Teacher��Ϣ*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*���HomeworkTask��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHomeworkTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherObj = teacherDAO.GetTeacherById(homeworkTask.getTeacherObj().getId());
            homeworkTask.setTeacherObj(teacherObj);
            homeworkTaskDAO.AddHomeworkTask(homeworkTask);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkTask��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkTask���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHomeworkTask��Ϣ*/
    public String QueryHomeworkTask() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTaskInfo(teacherObj, title, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        homeworkTaskDAO.CalculateTotalPageAndRecordNumber(teacherObj, title);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = homeworkTaskDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = homeworkTaskDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("homeworkTaskList",  homeworkTaskList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("title", title);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryHomeworkTaskOutputToExcel() { 
        if(title == null) title = "";
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTaskInfo(teacherObj,title);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HomeworkTask��Ϣ��¼"; 
        String[] headers = { "��¼���","��ʦ","��ҵ����","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<homeworkTaskList.size();i++) {
        	HomeworkTask homeworkTask = homeworkTaskList.get(i); 
        	dataset.add(new String[]{homeworkTask.getHomeworkId() + "",homeworkTask.getTeacherObj().getName(),
homeworkTask.getTitle(),homeworkTask.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"HomeworkTask.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯHomeworkTask��Ϣ*/
    public String FrontQueryHomeworkTask() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTaskInfo(teacherObj, title, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        homeworkTaskDAO.CalculateTotalPageAndRecordNumber(teacherObj, title);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = homeworkTaskDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = homeworkTaskDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("homeworkTaskList",  homeworkTaskList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("title", title);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�HomeworkTask��Ϣ*/
    public String ModifyHomeworkTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������homeworkId��ȡHomeworkTask����*/
        HomeworkTask homeworkTask = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkId);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("homeworkTask",  homeworkTask);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�HomeworkTask��Ϣ*/
    public String FrontShowHomeworkTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������homeworkId��ȡHomeworkTask����*/
        HomeworkTask homeworkTask = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkId);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("homeworkTask",  homeworkTask);
        return "front_show_view";
    }

    /*�����޸�HomeworkTask��Ϣ*/
    public String ModifyHomeworkTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherObj = teacherDAO.GetTeacherById(homeworkTask.getTeacherObj().getId());
            homeworkTask.setTeacherObj(teacherObj);
            homeworkTaskDAO.UpdateHomeworkTask(homeworkTask);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkTask��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkTask��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��HomeworkTask��Ϣ*/
    public String DeleteHomeworkTask() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            homeworkTaskDAO.DeleteHomeworkTask(homeworkId);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkTaskɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkTaskɾ��ʧ��!"));
            return "error";
        }
    }

}

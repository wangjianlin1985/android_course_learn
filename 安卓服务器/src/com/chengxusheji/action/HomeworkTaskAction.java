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

    /*界面层需要查询的属性: 老师*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
    }

    /*界面层需要查询的属性: 作业标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
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

    private int homeworkId;
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }
    public int getHomeworkId() {
        return homeworkId;
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
    @Resource HomeworkTaskDAO homeworkTaskDAO;

    /*待操作的HomeworkTask对象*/
    private HomeworkTask homeworkTask;
    public void setHomeworkTask(HomeworkTask homeworkTask) {
        this.homeworkTask = homeworkTask;
    }
    public HomeworkTask getHomeworkTask() {
        return this.homeworkTask;
    }

    /*跳转到添加HomeworkTask视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加HomeworkTask信息*/
    @SuppressWarnings("deprecation")
    public String AddHomeworkTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherObj = teacherDAO.GetTeacherById(homeworkTask.getTeacherObj().getId());
            homeworkTask.setTeacherObj(teacherObj);
            homeworkTaskDAO.AddHomeworkTask(homeworkTask);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkTask添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkTask添加失败!"));
            return "error";
        }
    }

    /*查询HomeworkTask信息*/
    public String QueryHomeworkTask() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTaskInfo(teacherObj, title, currentPage);
        /*计算总的页数和总的记录数*/
        homeworkTaskDAO.CalculateTotalPageAndRecordNumber(teacherObj, title);
        /*获取到总的页码数目*/
        totalPage = homeworkTaskDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryHomeworkTaskOutputToExcel() { 
        if(title == null) title = "";
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTaskInfo(teacherObj,title);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HomeworkTask信息记录"; 
        String[] headers = { "记录编号","老师","作业标题","发布时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"HomeworkTask.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询HomeworkTask信息*/
    public String FrontQueryHomeworkTask() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryHomeworkTaskInfo(teacherObj, title, currentPage);
        /*计算总的页数和总的记录数*/
        homeworkTaskDAO.CalculateTotalPageAndRecordNumber(teacherObj, title);
        /*获取到总的页码数目*/
        totalPage = homeworkTaskDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的HomeworkTask信息*/
    public String ModifyHomeworkTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键homeworkId获取HomeworkTask对象*/
        HomeworkTask homeworkTask = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkId);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("homeworkTask",  homeworkTask);
        return "modify_view";
    }

    /*查询要修改的HomeworkTask信息*/
    public String FrontShowHomeworkTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键homeworkId获取HomeworkTask对象*/
        HomeworkTask homeworkTask = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkId);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("homeworkTask",  homeworkTask);
        return "front_show_view";
    }

    /*更新修改HomeworkTask信息*/
    public String ModifyHomeworkTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher teacherObj = teacherDAO.GetTeacherById(homeworkTask.getTeacherObj().getId());
            homeworkTask.setTeacherObj(teacherObj);
            homeworkTaskDAO.UpdateHomeworkTask(homeworkTask);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkTask信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkTask信息更新失败!"));
            return "error";
       }
   }

    /*删除HomeworkTask信息*/
    public String DeleteHomeworkTask() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            homeworkTaskDAO.DeleteHomeworkTask(homeworkId);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkTask删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkTask删除失败!"));
            return "error";
        }
    }

}

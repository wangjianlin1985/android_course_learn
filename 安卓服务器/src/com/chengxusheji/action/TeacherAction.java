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
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TeacherAction extends BaseAction {

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 职称*/
    private String position;
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
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

    /*待操作的Teacher对象*/
    private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return this.teacher;
    }

    /*跳转到添加Teacher视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Teacher信息*/
    @SuppressWarnings("deprecation")
    public String AddTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            teacherDAO.AddTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher添加失败!"));
            return "error";
        }
    }

    /*查询Teacher信息*/
    public String QueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(position == null) position = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(name, position, currentPage);
        /*计算总的页数和总的记录数*/
        teacherDAO.CalculateTotalPageAndRecordNumber(name, position);
        /*获取到总的页码数目*/
        totalPage = teacherDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("name", name);
        ctx.put("position", position);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryTeacherOutputToExcel() { 
        if(name == null) name = "";
        if(position == null) position = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(name,position);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Teacher信息记录"; 
        String[] headers = { "记录编号","姓名","职称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<teacherList.size();i++) {
        	Teacher teacher = teacherList.get(i); 
        	dataset.add(new String[]{teacher.getId() + "",teacher.getName(),teacher.getPosition()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Teacher.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Teacher信息*/
    public String FrontQueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(position == null) position = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(name, position, currentPage);
        /*计算总的页数和总的记录数*/
        teacherDAO.CalculateTotalPageAndRecordNumber(name, position);
        /*获取到总的页码数目*/
        totalPage = teacherDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("name", name);
        ctx.put("position", position);
        return "front_query_view";
    }

    /*查询要修改的Teacher信息*/
    public String ModifyTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Teacher对象*/
        Teacher teacher = teacherDAO.GetTeacherById(id);

        ctx.put("teacher",  teacher);
        return "modify_view";
    }

    /*查询要修改的Teacher信息*/
    public String FrontShowTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Teacher对象*/
        Teacher teacher = teacherDAO.GetTeacherById(id);

        ctx.put("teacher",  teacher);
        return "front_show_view";
    }

    /*更新修改Teacher信息*/
    public String ModifyTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            teacherDAO.UpdateTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher信息更新失败!"));
            return "error";
       }
   }

    /*删除Teacher信息*/
    public String DeleteTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teacherDAO.DeleteTeacher(id);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher删除失败!"));
            return "error";
        }
    }

}

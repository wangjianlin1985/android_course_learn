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

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ְ��*/
    private String position;
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
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

    /*��������Teacher����*/
    private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return this.teacher;
    }

    /*��ת�����Teacher��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Teacher��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            teacherDAO.AddTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTeacher��Ϣ*/
    public String QueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(position == null) position = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(name, position, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(name, position);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryTeacherOutputToExcel() { 
        if(name == null) name = "";
        if(position == null) position = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(name,position);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Teacher��Ϣ��¼"; 
        String[] headers = { "��¼���","����","ְ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Teacher.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTeacher��Ϣ*/
    public String FrontQueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(position == null) position = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(name, position, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(name, position);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String ModifyTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherById(id);

        ctx.put("teacher",  teacher);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String FrontShowTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherById(id);

        ctx.put("teacher",  teacher);
        return "front_show_view";
    }

    /*�����޸�Teacher��Ϣ*/
    public String ModifyTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            teacherDAO.UpdateTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Teacher��Ϣ*/
    public String DeleteTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teacherDAO.DeleteTeacher(id);
            ctx.put("message",  java.net.URLEncoder.encode("Teacherɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacherɾ��ʧ��!"));
            return "error";
        }
    }

}

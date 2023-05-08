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
import com.chengxusheji.dao.CourseInfoDAO;
import com.chengxusheji.domain.CourseInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CourseInfoAction extends BaseAction {

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
    @Resource CourseInfoDAO courseInfoDAO;

    /*��������CourseInfo����*/
    private CourseInfo courseInfo;
    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
    public CourseInfo getCourseInfo() {
        return this.courseInfo;
    }

    /*��ת�����CourseInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���CourseInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            courseInfoDAO.AddCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCourseInfo��Ϣ*/
    public String QueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCourseInfoOutputToExcel() { 
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CourseInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","�γ̼��","�γ̴��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<courseInfoList.size();i++) {
        	CourseInfo courseInfo = courseInfoList.get(i); 
        	dataset.add(new String[]{courseInfo.getId() + "",courseInfo.getJianjie(),courseInfo.getDagan()});
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
			response.setHeader("Content-disposition","attachment; filename="+"CourseInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCourseInfo��Ϣ*/
    public String FrontQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CourseInfo��Ϣ*/
    public String ModifyCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡCourseInfo����*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoById(id);

        ctx.put("courseInfo",  courseInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CourseInfo��Ϣ*/
    public String FrontShowCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡCourseInfo����*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoById(id);

        ctx.put("courseInfo",  courseInfo);
        return "front_show_view";
    }

    /*�����޸�CourseInfo��Ϣ*/
    public String ModifyCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            courseInfoDAO.UpdateCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CourseInfo��Ϣ*/
    public String DeleteCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseInfoDAO.DeleteCourseInfo(id);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

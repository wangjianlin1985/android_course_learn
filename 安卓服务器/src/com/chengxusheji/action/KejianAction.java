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
import com.chengxusheji.dao.KejianDAO;
import com.chengxusheji.domain.Kejian;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class KejianAction extends BaseAction {

    /*界面层需要查询的属性: 课件标题*/
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
    @Resource KejianDAO kejianDAO;

    /*待操作的Kejian对象*/
    private Kejian kejian;
    public void setKejian(Kejian kejian) {
        this.kejian = kejian;
    }
    public Kejian getKejian() {
        return this.kejian;
    }

    /*跳转到添加Kejian视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Kejian信息*/
    @SuppressWarnings("deprecation")
    public String AddKejian() {
        ActionContext ctx = ActionContext.getContext();
        try {
            kejianDAO.AddKejian(kejian);
            ctx.put("message",  java.net.URLEncoder.encode("Kejian添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Kejian添加失败!"));
            return "error";
        }
    }

    /*查询Kejian信息*/
    public String QueryKejian() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Kejian> kejianList = kejianDAO.QueryKejianInfo(title, currentPage);
        /*计算总的页数和总的记录数*/
        kejianDAO.CalculateTotalPageAndRecordNumber(title);
        /*获取到总的页码数目*/
        totalPage = kejianDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = kejianDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("kejianList",  kejianList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryKejianOutputToExcel() { 
        if(title == null) title = "";
        List<Kejian> kejianList = kejianDAO.QueryKejianInfo(title);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Kejian信息记录"; 
        String[] headers = { "记录编号","课件标题","文件路径","添加时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<kejianList.size();i++) {
        	Kejian kejian = kejianList.get(i); 
        	dataset.add(new String[]{kejian.getId() + "",kejian.getTitle(),kejian.getPath(),kejian.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Kejian.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Kejian信息*/
    public String FrontQueryKejian() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Kejian> kejianList = kejianDAO.QueryKejianInfo(title, currentPage);
        /*计算总的页数和总的记录数*/
        kejianDAO.CalculateTotalPageAndRecordNumber(title);
        /*获取到总的页码数目*/
        totalPage = kejianDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = kejianDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("kejianList",  kejianList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        return "front_query_view";
    }

    /*查询要修改的Kejian信息*/
    public String ModifyKejianQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Kejian对象*/
        Kejian kejian = kejianDAO.GetKejianById(id);

        ctx.put("kejian",  kejian);
        return "modify_view";
    }

    /*查询要修改的Kejian信息*/
    public String FrontShowKejianQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Kejian对象*/
        Kejian kejian = kejianDAO.GetKejianById(id);

        ctx.put("kejian",  kejian);
        return "front_show_view";
    }

    /*更新修改Kejian信息*/
    public String ModifyKejian() {
        ActionContext ctx = ActionContext.getContext();
        try {
            kejianDAO.UpdateKejian(kejian);
            ctx.put("message",  java.net.URLEncoder.encode("Kejian信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Kejian信息更新失败!"));
            return "error";
       }
   }

    /*删除Kejian信息*/
    public String DeleteKejian() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            kejianDAO.DeleteKejian(id);
            ctx.put("message",  java.net.URLEncoder.encode("Kejian删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Kejian删除失败!"));
            return "error";
        }
    }

}

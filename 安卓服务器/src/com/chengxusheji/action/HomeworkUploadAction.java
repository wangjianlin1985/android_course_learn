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
import com.chengxusheji.dao.HomeworkUploadDAO;
import com.chengxusheji.domain.HomeworkUpload;
import com.chengxusheji.dao.HomeworkTaskDAO;
import com.chengxusheji.domain.HomeworkTask;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HomeworkUploadAction extends BaseAction {

	/*图片或文件字段homeworkFile参数接收*/
	private File homeworkFileFile;
	private String homeworkFileFileFileName;
	private String homeworkFileFileContentType;
	public File getHomeworkFileFile() {
		return homeworkFileFile;
	}
	public void setHomeworkFileFile(File homeworkFileFile) {
		this.homeworkFileFile = homeworkFileFile;
	}
	public String getHomeworkFileFileFileName() {
		return homeworkFileFileFileName;
	}
	public void setHomeworkFileFileFileName(String homeworkFileFileFileName) {
		this.homeworkFileFileFileName = homeworkFileFileFileName;
	}
	public String getHomeworkFileFileContentType() {
		return homeworkFileFileContentType;
	}
	public void setHomeworkFileFileContentType(String homeworkFileFileContentType) {
		this.homeworkFileFileContentType = homeworkFileFileContentType;
	}
	/*图片或文件字段resultFile参数接收*/
	private File resultFileFile;
	private String resultFileFileFileName;
	private String resultFileFileContentType;
	public File getResultFileFile() {
		return resultFileFile;
	}
	public void setResultFileFile(File resultFileFile) {
		this.resultFileFile = resultFileFile;
	}
	public String getResultFileFileFileName() {
		return resultFileFileFileName;
	}
	public void setResultFileFileFileName(String resultFileFileFileName) {
		this.resultFileFileFileName = resultFileFileFileName;
	}
	public String getResultFileFileContentType() {
		return resultFileFileContentType;
	}
	public void setResultFileFileContentType(String resultFileFileContentType) {
		this.resultFileFileContentType = resultFileFileContentType;
	}
    /*界面层需要查询的属性: 作业标题*/
    private HomeworkTask homeworkTaskObj;
    public void setHomeworkTaskObj(HomeworkTask homeworkTaskObj) {
        this.homeworkTaskObj = homeworkTaskObj;
    }
    public HomeworkTask getHomeworkTaskObj() {
        return this.homeworkTaskObj;
    }

    /*界面层需要查询的属性: 提交的学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
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

    private int uploadId;
    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }
    public int getUploadId() {
        return uploadId;
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
    @Resource HomeworkTaskDAO homeworkTaskDAO;
    @Resource StudentDAO studentDAO;
    @Resource HomeworkUploadDAO homeworkUploadDAO;

    /*待操作的HomeworkUpload对象*/
    private HomeworkUpload homeworkUpload;
    public void setHomeworkUpload(HomeworkUpload homeworkUpload) {
        this.homeworkUpload = homeworkUpload;
    }
    public HomeworkUpload getHomeworkUpload() {
        return this.homeworkUpload;
    }

    /*跳转到添加HomeworkUpload视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的HomeworkTask信息*/
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*添加HomeworkUpload信息*/
    @SuppressWarnings("deprecation")
    public String AddHomeworkUpload() {
        ActionContext ctx = ActionContext.getContext();
        try {
            HomeworkTask homeworkTaskObj = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkUpload.getHomeworkTaskObj().getHomeworkId());
            homeworkUpload.setHomeworkTaskObj(homeworkTaskObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(homeworkUpload.getStudentObj().getStudentNumber());
            homeworkUpload.setStudentObj(studentObj);
            /*处理作业文件上传*/
            String homeworkFilePath = "upload/noimage.jpg"; 
       	 	if(homeworkFileFile != null)
       	 		homeworkFilePath = photoUpload(homeworkFileFile,homeworkFileFileContentType);
       	 	homeworkUpload.setHomeworkFile(homeworkFilePath);
            /*处理批改结果文件上传*/
            String resultFilePath = "upload/noimage.jpg"; 
       	 	if(resultFileFile != null)
       	 		resultFilePath = photoUpload(resultFileFile,resultFileFileContentType);
       	 	homeworkUpload.setResultFile(resultFilePath);
            homeworkUploadDAO.AddHomeworkUpload(homeworkUpload);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkUpload添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkUpload添加失败!"));
            return "error";
        }
    }

    /*查询HomeworkUpload信息*/
    public String QueryHomeworkUpload() {
        if(currentPage == 0) currentPage = 1;
        List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUploadInfo(homeworkTaskObj, studentObj, currentPage);
        /*计算总的页数和总的记录数*/
        homeworkUploadDAO.CalculateTotalPageAndRecordNumber(homeworkTaskObj, studentObj);
        /*获取到总的页码数目*/
        totalPage = homeworkUploadDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = homeworkUploadDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("homeworkUploadList",  homeworkUploadList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("homeworkTaskObj", homeworkTaskObj);
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryHomeworkUploadOutputToExcel() { 
        List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUploadInfo(homeworkTaskObj,studentObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HomeworkUpload信息记录"; 
        String[] headers = { "记录编号","作业标题","提交的学生","作业文件","提交时间","批改结果文件","批改时间","是否批改","评语"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<homeworkUploadList.size();i++) {
        	HomeworkUpload homeworkUpload = homeworkUploadList.get(i); 
        	dataset.add(new String[]{homeworkUpload.getUploadId() + "",homeworkUpload.getHomeworkTaskObj().getTitle(),
homeworkUpload.getStudentObj().getName(),
homeworkUpload.getHomeworkFile(),homeworkUpload.getUploadTime(),homeworkUpload.getResultFile(),homeworkUpload.getPigaiTime(),homeworkUpload.getPigaiFlag() + "",homeworkUpload.getPingyu()});
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
			response.setHeader("Content-disposition","attachment; filename="+"HomeworkUpload.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询HomeworkUpload信息*/
    public String FrontQueryHomeworkUpload() {
        if(currentPage == 0) currentPage = 1;
        List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUploadInfo(homeworkTaskObj, studentObj, currentPage);
        /*计算总的页数和总的记录数*/
        homeworkUploadDAO.CalculateTotalPageAndRecordNumber(homeworkTaskObj, studentObj);
        /*获取到总的页码数目*/
        totalPage = homeworkUploadDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = homeworkUploadDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("homeworkUploadList",  homeworkUploadList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("homeworkTaskObj", homeworkTaskObj);
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "front_query_view";
    }

    /*查询要修改的HomeworkUpload信息*/
    public String ModifyHomeworkUploadQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键uploadId获取HomeworkUpload对象*/
        HomeworkUpload homeworkUpload = homeworkUploadDAO.GetHomeworkUploadByUploadId(uploadId);

        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("homeworkUpload",  homeworkUpload);
        return "modify_view";
    }

    /*查询要修改的HomeworkUpload信息*/
    public String FrontShowHomeworkUploadQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键uploadId获取HomeworkUpload对象*/
        HomeworkUpload homeworkUpload = homeworkUploadDAO.GetHomeworkUploadByUploadId(uploadId);

        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("homeworkUpload",  homeworkUpload);
        return "front_show_view";
    }

    /*更新修改HomeworkUpload信息*/
    public String ModifyHomeworkUpload() {
        ActionContext ctx = ActionContext.getContext();
        try {
            HomeworkTask homeworkTaskObj = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkUpload.getHomeworkTaskObj().getHomeworkId());
            homeworkUpload.setHomeworkTaskObj(homeworkTaskObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(homeworkUpload.getStudentObj().getStudentNumber());
            homeworkUpload.setStudentObj(studentObj);
            /*处理作业文件上传*/
            if(homeworkFileFile != null) {
            	String homeworkFilePath = photoUpload(homeworkFileFile,homeworkFileFileContentType);
            	homeworkUpload.setHomeworkFile(homeworkFilePath);
            }
            /*处理批改结果文件上传*/
            if(resultFileFile != null) {
            	String resultFilePath = photoUpload(resultFileFile,resultFileFileContentType);
            	homeworkUpload.setResultFile(resultFilePath);
            }
            homeworkUploadDAO.UpdateHomeworkUpload(homeworkUpload);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkUpload信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkUpload信息更新失败!"));
            return "error";
       }
   }

    /*删除HomeworkUpload信息*/
    public String DeleteHomeworkUpload() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            homeworkUploadDAO.DeleteHomeworkUpload(uploadId);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkUpload删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkUpload删除失败!"));
            return "error";
        }
    }

}

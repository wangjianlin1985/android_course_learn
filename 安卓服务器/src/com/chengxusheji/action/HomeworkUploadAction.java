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

	/*ͼƬ���ļ��ֶ�homeworkFile��������*/
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
	/*ͼƬ���ļ��ֶ�resultFile��������*/
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
    /*�������Ҫ��ѯ������: ��ҵ����*/
    private HomeworkTask homeworkTaskObj;
    public void setHomeworkTaskObj(HomeworkTask homeworkTaskObj) {
        this.homeworkTaskObj = homeworkTaskObj;
    }
    public HomeworkTask getHomeworkTaskObj() {
        return this.homeworkTaskObj;
    }

    /*�������Ҫ��ѯ������: �ύ��ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
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

    private int uploadId;
    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }
    public int getUploadId() {
        return uploadId;
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
    @Resource HomeworkTaskDAO homeworkTaskDAO;
    @Resource StudentDAO studentDAO;
    @Resource HomeworkUploadDAO homeworkUploadDAO;

    /*��������HomeworkUpload����*/
    private HomeworkUpload homeworkUpload;
    public void setHomeworkUpload(HomeworkUpload homeworkUpload) {
        this.homeworkUpload = homeworkUpload;
    }
    public HomeworkUpload getHomeworkUpload() {
        return this.homeworkUpload;
    }

    /*��ת�����HomeworkUpload��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�HomeworkTask��Ϣ*/
        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*���HomeworkUpload��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHomeworkUpload() {
        ActionContext ctx = ActionContext.getContext();
        try {
            HomeworkTask homeworkTaskObj = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkUpload.getHomeworkTaskObj().getHomeworkId());
            homeworkUpload.setHomeworkTaskObj(homeworkTaskObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(homeworkUpload.getStudentObj().getStudentNumber());
            homeworkUpload.setStudentObj(studentObj);
            /*������ҵ�ļ��ϴ�*/
            String homeworkFilePath = "upload/noimage.jpg"; 
       	 	if(homeworkFileFile != null)
       	 		homeworkFilePath = photoUpload(homeworkFileFile,homeworkFileFileContentType);
       	 	homeworkUpload.setHomeworkFile(homeworkFilePath);
            /*�������Ľ���ļ��ϴ�*/
            String resultFilePath = "upload/noimage.jpg"; 
       	 	if(resultFileFile != null)
       	 		resultFilePath = photoUpload(resultFileFile,resultFileFileContentType);
       	 	homeworkUpload.setResultFile(resultFilePath);
            homeworkUploadDAO.AddHomeworkUpload(homeworkUpload);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkUpload��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkUpload���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHomeworkUpload��Ϣ*/
    public String QueryHomeworkUpload() {
        if(currentPage == 0) currentPage = 1;
        List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUploadInfo(homeworkTaskObj, studentObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        homeworkUploadDAO.CalculateTotalPageAndRecordNumber(homeworkTaskObj, studentObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = homeworkUploadDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryHomeworkUploadOutputToExcel() { 
        List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUploadInfo(homeworkTaskObj,studentObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HomeworkUpload��Ϣ��¼"; 
        String[] headers = { "��¼���","��ҵ����","�ύ��ѧ��","��ҵ�ļ�","�ύʱ��","���Ľ���ļ�","����ʱ��","�Ƿ�����","����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"HomeworkUpload.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯHomeworkUpload��Ϣ*/
    public String FrontQueryHomeworkUpload() {
        if(currentPage == 0) currentPage = 1;
        List<HomeworkUpload> homeworkUploadList = homeworkUploadDAO.QueryHomeworkUploadInfo(homeworkTaskObj, studentObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        homeworkUploadDAO.CalculateTotalPageAndRecordNumber(homeworkTaskObj, studentObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = homeworkUploadDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�HomeworkUpload��Ϣ*/
    public String ModifyHomeworkUploadQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������uploadId��ȡHomeworkUpload����*/
        HomeworkUpload homeworkUpload = homeworkUploadDAO.GetHomeworkUploadByUploadId(uploadId);

        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("homeworkUpload",  homeworkUpload);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�HomeworkUpload��Ϣ*/
    public String FrontShowHomeworkUploadQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������uploadId��ȡHomeworkUpload����*/
        HomeworkUpload homeworkUpload = homeworkUploadDAO.GetHomeworkUploadByUploadId(uploadId);

        List<HomeworkTask> homeworkTaskList = homeworkTaskDAO.QueryAllHomeworkTaskInfo();
        ctx.put("homeworkTaskList", homeworkTaskList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("homeworkUpload",  homeworkUpload);
        return "front_show_view";
    }

    /*�����޸�HomeworkUpload��Ϣ*/
    public String ModifyHomeworkUpload() {
        ActionContext ctx = ActionContext.getContext();
        try {
            HomeworkTask homeworkTaskObj = homeworkTaskDAO.GetHomeworkTaskByHomeworkId(homeworkUpload.getHomeworkTaskObj().getHomeworkId());
            homeworkUpload.setHomeworkTaskObj(homeworkTaskObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(homeworkUpload.getStudentObj().getStudentNumber());
            homeworkUpload.setStudentObj(studentObj);
            /*������ҵ�ļ��ϴ�*/
            if(homeworkFileFile != null) {
            	String homeworkFilePath = photoUpload(homeworkFileFile,homeworkFileFileContentType);
            	homeworkUpload.setHomeworkFile(homeworkFilePath);
            }
            /*�������Ľ���ļ��ϴ�*/
            if(resultFileFile != null) {
            	String resultFilePath = photoUpload(resultFileFile,resultFileFileContentType);
            	homeworkUpload.setResultFile(resultFilePath);
            }
            homeworkUploadDAO.UpdateHomeworkUpload(homeworkUpload);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkUpload��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkUpload��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��HomeworkUpload��Ϣ*/
    public String DeleteHomeworkUpload() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            homeworkUploadDAO.DeleteHomeworkUpload(uploadId);
            ctx.put("message",  java.net.URLEncoder.encode("HomeworkUploadɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HomeworkUploadɾ��ʧ��!"));
            return "error";
        }
    }

}

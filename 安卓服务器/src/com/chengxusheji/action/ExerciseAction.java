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
import com.chengxusheji.dao.ExerciseDAO;
import com.chengxusheji.domain.Exercise;
import com.chengxusheji.dao.ChapterDAO;
import com.chengxusheji.domain.Chapter;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ExerciseAction extends BaseAction {

    /*�������Ҫ��ѯ������: ϰ������*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ������*/
    private Chapter chapterId;
    public void setChapterId(Chapter chapterId) {
        this.chapterId = chapterId;
    }
    public Chapter getChapterId() {
        return this.chapterId;
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
    @Resource ChapterDAO chapterDAO;
    @Resource ExerciseDAO exerciseDAO;

    /*��������Exercise����*/
    private Exercise exercise;
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public Exercise getExercise() {
        return this.exercise;
    }

    /*��ת�����Exercise��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Chapter��Ϣ*/
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "add_view";
    }

    /*���Exercise��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddExercise() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(exercise.getChapterId().getId());
            exercise.setChapterId(chapterId);
            exerciseDAO.AddExercise(exercise);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise���ʧ��!"));
            return "error";
        }
    }

    /*��ѯExercise��Ϣ*/
    public String QueryExercise() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(title, chapterId, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        exerciseDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = exerciseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = exerciseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("exerciseList",  exerciseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("chapterId", chapterId);
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryExerciseOutputToExcel() { 
        if(title == null) title = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(title,chapterId);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Exercise��Ϣ��¼"; 
        String[] headers = { "��¼���","ϰ������","������","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<exerciseList.size();i++) {
        	Exercise exercise = exerciseList.get(i); 
        	dataset.add(new String[]{exercise.getId() + "",exercise.getTitle(),exercise.getChapterId().getTitle(),
exercise.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Exercise.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯExercise��Ϣ*/
    public String FrontQueryExercise() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(title, chapterId, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        exerciseDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = exerciseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = exerciseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("exerciseList",  exerciseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("chapterId", chapterId);
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Exercise��Ϣ*/
    public String ModifyExerciseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡExercise����*/
        Exercise exercise = exerciseDAO.GetExerciseById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("exercise",  exercise);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Exercise��Ϣ*/
    public String FrontShowExerciseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡExercise����*/
        Exercise exercise = exerciseDAO.GetExerciseById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("exercise",  exercise);
        return "front_show_view";
    }

    /*�����޸�Exercise��Ϣ*/
    public String ModifyExercise() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(exercise.getChapterId().getId());
            exercise.setChapterId(chapterId);
            exerciseDAO.UpdateExercise(exercise);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Exercise��Ϣ*/
    public String DeleteExercise() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            exerciseDAO.DeleteExercise(id);
            ctx.put("message",  java.net.URLEncoder.encode("Exerciseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exerciseɾ��ʧ��!"));
            return "error";
        }
    }

}

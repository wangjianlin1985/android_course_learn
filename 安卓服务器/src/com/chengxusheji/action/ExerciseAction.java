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

    /*界面层需要查询的属性: 习题名称*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 所在章*/
    private Chapter chapterId;
    public void setChapterId(Chapter chapterId) {
        this.chapterId = chapterId;
    }
    public Chapter getChapterId() {
        return this.chapterId;
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
    @Resource ChapterDAO chapterDAO;
    @Resource ExerciseDAO exerciseDAO;

    /*待操作的Exercise对象*/
    private Exercise exercise;
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public Exercise getExercise() {
        return this.exercise;
    }

    /*跳转到添加Exercise视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Chapter信息*/
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "add_view";
    }

    /*添加Exercise信息*/
    @SuppressWarnings("deprecation")
    public String AddExercise() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(exercise.getChapterId().getId());
            exercise.setChapterId(chapterId);
            exerciseDAO.AddExercise(exercise);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise添加失败!"));
            return "error";
        }
    }

    /*查询Exercise信息*/
    public String QueryExercise() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(title, chapterId, currentPage);
        /*计算总的页数和总的记录数*/
        exerciseDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*获取到总的页码数目*/
        totalPage = exerciseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryExerciseOutputToExcel() { 
        if(title == null) title = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(title,chapterId);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Exercise信息记录"; 
        String[] headers = { "记录编号","习题名称","所在章","加入时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Exercise.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Exercise信息*/
    public String FrontQueryExercise() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(title, chapterId, currentPage);
        /*计算总的页数和总的记录数*/
        exerciseDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*获取到总的页码数目*/
        totalPage = exerciseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Exercise信息*/
    public String ModifyExerciseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Exercise对象*/
        Exercise exercise = exerciseDAO.GetExerciseById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("exercise",  exercise);
        return "modify_view";
    }

    /*查询要修改的Exercise信息*/
    public String FrontShowExerciseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Exercise对象*/
        Exercise exercise = exerciseDAO.GetExerciseById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("exercise",  exercise);
        return "front_show_view";
    }

    /*更新修改Exercise信息*/
    public String ModifyExercise() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(exercise.getChapterId().getId());
            exercise.setChapterId(chapterId);
            exerciseDAO.UpdateExercise(exercise);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise信息更新失败!"));
            return "error";
       }
   }

    /*删除Exercise信息*/
    public String DeleteExercise() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            exerciseDAO.DeleteExercise(id);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise删除失败!"));
            return "error";
        }
    }

}

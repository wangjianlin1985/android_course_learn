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
import com.chengxusheji.dao.VideoDAO;
import com.chengxusheji.domain.Video;
import com.chengxusheji.dao.ChapterDAO;
import com.chengxusheji.domain.Chapter;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class VideoAction extends BaseAction {

    /*界面层需要查询的属性: 视频资料标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 所属章*/
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
    @Resource VideoDAO videoDAO;

    /*待操作的Video对象*/
    private Video video;
    public void setVideo(Video video) {
        this.video = video;
    }
    public Video getVideo() {
        return this.video;
    }

    /*跳转到添加Video视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Chapter信息*/
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "add_view";
    }

    /*添加Video信息*/
    @SuppressWarnings("deprecation")
    public String AddVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(video.getChapterId().getId());
            video.setChapterId(chapterId);
            videoDAO.AddVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video添加失败!"));
            return "error";
        }
    }

    /*查询Video信息*/
    public String QueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, chapterId, currentPage);
        /*计算总的页数和总的记录数*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*获取到总的页码数目*/
        totalPage = videoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = videoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("videoList",  videoList);
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
    public String QueryVideoOutputToExcel() { 
        if(title == null) title = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title,chapterId);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Video信息记录"; 
        String[] headers = { "记录编号","视频资料标题","所属章","文件路径","添加时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<videoList.size();i++) {
        	Video video = videoList.get(i); 
        	dataset.add(new String[]{video.getId() + "",video.getTitle(),video.getChapterId().getTitle(),
video.getPath(),video.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Video.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Video信息*/
    public String FrontQueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, chapterId, currentPage);
        /*计算总的页数和总的记录数*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*获取到总的页码数目*/
        totalPage = videoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = videoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("videoList",  videoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("chapterId", chapterId);
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "front_query_view";
    }

    /*查询要修改的Video信息*/
    public String ModifyVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Video对象*/
        Video video = videoDAO.GetVideoById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("video",  video);
        return "modify_view";
    }

    /*查询要修改的Video信息*/
    public String FrontShowVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取Video对象*/
        Video video = videoDAO.GetVideoById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("video",  video);
        return "front_show_view";
    }

    /*更新修改Video信息*/
    public String ModifyVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(video.getChapterId().getId());
            video.setChapterId(chapterId);
            videoDAO.UpdateVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video信息更新失败!"));
            return "error";
       }
   }

    /*删除Video信息*/
    public String DeleteVideo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            videoDAO.DeleteVideo(id);
            ctx.put("message",  java.net.URLEncoder.encode("Video删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video删除失败!"));
            return "error";
        }
    }

}

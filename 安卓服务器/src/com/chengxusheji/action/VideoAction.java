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

    /*�������Ҫ��ѯ������: ��Ƶ���ϱ���*/
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
    @Resource VideoDAO videoDAO;

    /*��������Video����*/
    private Video video;
    public void setVideo(Video video) {
        this.video = video;
    }
    public Video getVideo() {
        return this.video;
    }

    /*��ת�����Video��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Chapter��Ϣ*/
        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        return "add_view";
    }

    /*���Video��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(video.getChapterId().getId());
            video.setChapterId(chapterId);
            videoDAO.AddVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video���ʧ��!"));
            return "error";
        }
    }

    /*��ѯVideo��Ϣ*/
    public String QueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, chapterId, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = videoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryVideoOutputToExcel() { 
        if(title == null) title = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title,chapterId);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Video��Ϣ��¼"; 
        String[] headers = { "��¼���","��Ƶ���ϱ���","������","�ļ�·��","���ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Video.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯVideo��Ϣ*/
    public String FrontQueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, chapterId, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, chapterId);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = videoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Video��Ϣ*/
    public String ModifyVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡVideo����*/
        Video video = videoDAO.GetVideoById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("video",  video);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Video��Ϣ*/
    public String FrontShowVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡVideo����*/
        Video video = videoDAO.GetVideoById(id);

        List<Chapter> chapterList = chapterDAO.QueryAllChapterInfo();
        ctx.put("chapterList", chapterList);
        ctx.put("video",  video);
        return "front_show_view";
    }

    /*�����޸�Video��Ϣ*/
    public String ModifyVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Chapter chapterId = chapterDAO.GetChapterById(video.getChapterId().getId());
            video.setChapterId(chapterId);
            videoDAO.UpdateVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Video��Ϣ*/
    public String DeleteVideo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            videoDAO.DeleteVideo(id);
            ctx.put("message",  java.net.URLEncoder.encode("Videoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Videoɾ��ʧ��!"));
            return "error";
        }
    }

}

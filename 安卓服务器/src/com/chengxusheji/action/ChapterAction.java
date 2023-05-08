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
import com.chengxusheji.dao.ChapterDAO;
import com.chengxusheji.domain.Chapter;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ChapterAction extends BaseAction {

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

    /*��������Chapter����*/
    private Chapter chapter;
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    public Chapter getChapter() {
        return this.chapter;
    }

    /*��ת�����Chapter��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Chapter��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddChapter() {
        ActionContext ctx = ActionContext.getContext();
        try {
            chapterDAO.AddChapter(chapter);
            ctx.put("message",  java.net.URLEncoder.encode("Chapter��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Chapter���ʧ��!"));
            return "error";
        }
    }

    /*��ѯChapter��Ϣ*/
    public String QueryChapter() {
        if(currentPage == 0) currentPage = 1;
        List<Chapter> chapterList = chapterDAO.QueryChapterInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        chapterDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = chapterDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = chapterDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("chapterList",  chapterList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryChapterOutputToExcel() { 
        List<Chapter> chapterList = chapterDAO.QueryChapterInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Chapter��Ϣ��¼"; 
        String[] headers = { "��¼���","�±���","���ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<chapterList.size();i++) {
        	Chapter chapter = chapterList.get(i); 
        	dataset.add(new String[]{chapter.getId() + "",chapter.getTitle(),chapter.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Chapter.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯChapter��Ϣ*/
    public String FrontQueryChapter() {
        if(currentPage == 0) currentPage = 1;
        List<Chapter> chapterList = chapterDAO.QueryChapterInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        chapterDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = chapterDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = chapterDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("chapterList",  chapterList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Chapter��Ϣ*/
    public String ModifyChapterQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡChapter����*/
        Chapter chapter = chapterDAO.GetChapterById(id);

        ctx.put("chapter",  chapter);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Chapter��Ϣ*/
    public String FrontShowChapterQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡChapter����*/
        Chapter chapter = chapterDAO.GetChapterById(id);

        ctx.put("chapter",  chapter);
        return "front_show_view";
    }

    /*�����޸�Chapter��Ϣ*/
    public String ModifyChapter() {
        ActionContext ctx = ActionContext.getContext();
        try {
            chapterDAO.UpdateChapter(chapter);
            ctx.put("message",  java.net.URLEncoder.encode("Chapter��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Chapter��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Chapter��Ϣ*/
    public String DeleteChapter() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            chapterDAO.DeleteChapter(id);
            ctx.put("message",  java.net.URLEncoder.encode("Chapterɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Chapterɾ��ʧ��!"));
            return "error";
        }
    }

}

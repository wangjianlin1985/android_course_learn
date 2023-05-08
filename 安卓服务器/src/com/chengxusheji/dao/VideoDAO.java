package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Chapter;
import com.chengxusheji.domain.Video;

@Service @Transactional
public class VideoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddVideo(Video video) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(video);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Video> QueryVideoInfo(String title,Chapter chapterId,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Video video where 1=1";
    	if(!title.equals("")) hql = hql + " and video.title like '%" + title + "%'";
    	if(null != chapterId && chapterId.getId()!=0) hql += " and video.chapterId.id=" + chapterId.getId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List videoList = q.list();
    	return (ArrayList<Video>) videoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Video> QueryVideoInfo(String title,Chapter chapterId) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Video video where 1=1";
    	if(!title.equals("")) hql = hql + " and video.title like '%" + title + "%'";
    	if(null != chapterId && chapterId.getId()!=0) hql += " and video.chapterId.id=" + chapterId.getId();
    	Query q = s.createQuery(hql);
    	List videoList = q.list();
    	return (ArrayList<Video>) videoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Video> QueryAllVideoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Video";
        Query q = s.createQuery(hql);
        List videoList = q.list();
        return (ArrayList<Video>) videoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,Chapter chapterId) {
        Session s = factory.getCurrentSession();
        String hql = "From Video video where 1=1";
        if(!title.equals("")) hql = hql + " and video.title like '%" + title + "%'";
        if(null != chapterId && chapterId.getId()!=0) hql += " and video.chapterId.id=" + chapterId.getId();
        Query q = s.createQuery(hql);
        List videoList = q.list();
        recordNumber = videoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Video GetVideoById(int id) {
        Session s = factory.getCurrentSession();
        Video video = (Video)s.get(Video.class, id);
        return video;
    }

    /*更新Video信息*/
    public void UpdateVideo(Video video) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(video);
    }

    /*删除Video信息*/
    public void DeleteVideo (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object video = s.load(Video.class, id);
        s.delete(video);
    }

}

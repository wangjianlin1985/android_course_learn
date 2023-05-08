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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Video GetVideoById(int id) {
        Session s = factory.getCurrentSession();
        Video video = (Video)s.get(Video.class, id);
        return video;
    }

    /*����Video��Ϣ*/
    public void UpdateVideo(Video video) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(video);
    }

    /*ɾ��Video��Ϣ*/
    public void DeleteVideo (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object video = s.load(Video.class, id);
        s.delete(video);
    }

}

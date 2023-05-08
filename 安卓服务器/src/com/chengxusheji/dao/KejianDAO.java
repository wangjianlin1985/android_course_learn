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
import com.chengxusheji.domain.Kejian;

@Service @Transactional
public class KejianDAO {

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
    public void AddKejian(Kejian kejian) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(kejian);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Kejian> QueryKejianInfo(String title,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Kejian kejian where 1=1";
    	if(!title.equals("")) hql = hql + " and kejian.title like '%" + title + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List kejianList = q.list();
    	return (ArrayList<Kejian>) kejianList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Kejian> QueryKejianInfo(String title) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Kejian kejian where 1=1";
    	if(!title.equals("")) hql = hql + " and kejian.title like '%" + title + "%'";
    	Query q = s.createQuery(hql);
    	List kejianList = q.list();
    	return (ArrayList<Kejian>) kejianList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Kejian> QueryAllKejianInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Kejian";
        Query q = s.createQuery(hql);
        List kejianList = q.list();
        return (ArrayList<Kejian>) kejianList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title) {
        Session s = factory.getCurrentSession();
        String hql = "From Kejian kejian where 1=1";
        if(!title.equals("")) hql = hql + " and kejian.title like '%" + title + "%'";
        Query q = s.createQuery(hql);
        List kejianList = q.list();
        recordNumber = kejianList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Kejian GetKejianById(int id) {
        Session s = factory.getCurrentSession();
        Kejian kejian = (Kejian)s.get(Kejian.class, id);
        return kejian;
    }

    /*����Kejian��Ϣ*/
    public void UpdateKejian(Kejian kejian) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(kejian);
    }

    /*ɾ��Kejian��Ϣ*/
    public void DeleteKejian (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object kejian = s.load(Kejian.class, id);
        s.delete(kejian);
    }

}

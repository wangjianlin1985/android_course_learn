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
import com.chengxusheji.domain.HomeworkTask;
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.HomeworkUpload;

@Service @Transactional
public class HomeworkUploadDAO {

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
    public void AddHomeworkUpload(HomeworkUpload homeworkUpload) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(homeworkUpload);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HomeworkUpload> QueryHomeworkUploadInfo(HomeworkTask homeworkTaskObj,Student studentObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HomeworkUpload homeworkUpload where 1=1";
    	if(null != homeworkTaskObj && homeworkTaskObj.getHomeworkId()!=0) hql += " and homeworkUpload.homeworkTaskObj.homeworkId=" + homeworkTaskObj.getHomeworkId();
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and homeworkUpload.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List homeworkUploadList = q.list();
    	return (ArrayList<HomeworkUpload>) homeworkUploadList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HomeworkUpload> QueryHomeworkUploadInfo(HomeworkTask homeworkTaskObj,Student studentObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HomeworkUpload homeworkUpload where 1=1";
    	if(null != homeworkTaskObj && homeworkTaskObj.getHomeworkId()!=0) hql += " and homeworkUpload.homeworkTaskObj.homeworkId=" + homeworkTaskObj.getHomeworkId();
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and homeworkUpload.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	Query q = s.createQuery(hql);
    	List homeworkUploadList = q.list();
    	return (ArrayList<HomeworkUpload>) homeworkUploadList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HomeworkUpload> QueryAllHomeworkUploadInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From HomeworkUpload";
        Query q = s.createQuery(hql);
        List homeworkUploadList = q.list();
        return (ArrayList<HomeworkUpload>) homeworkUploadList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(HomeworkTask homeworkTaskObj,Student studentObj) {
        Session s = factory.getCurrentSession();
        String hql = "From HomeworkUpload homeworkUpload where 1=1";
        if(null != homeworkTaskObj && homeworkTaskObj.getHomeworkId()!=0) hql += " and homeworkUpload.homeworkTaskObj.homeworkId=" + homeworkTaskObj.getHomeworkId();
        if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and homeworkUpload.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
        Query q = s.createQuery(hql);
        List homeworkUploadList = q.list();
        recordNumber = homeworkUploadList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public HomeworkUpload GetHomeworkUploadByUploadId(int uploadId) {
        Session s = factory.getCurrentSession();
        HomeworkUpload homeworkUpload = (HomeworkUpload)s.get(HomeworkUpload.class, uploadId);
        return homeworkUpload;
    }

    /*更新HomeworkUpload信息*/
    public void UpdateHomeworkUpload(HomeworkUpload homeworkUpload) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(homeworkUpload);
    }

    /*删除HomeworkUpload信息*/
    public void DeleteHomeworkUpload (int uploadId) throws Exception {
        Session s = factory.getCurrentSession();
        Object homeworkUpload = s.load(HomeworkUpload.class, uploadId);
        s.delete(homeworkUpload);
    }

}

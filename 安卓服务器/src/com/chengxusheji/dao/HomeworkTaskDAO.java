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
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.domain.HomeworkTask;

@Service @Transactional
public class HomeworkTaskDAO {

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
    public void AddHomeworkTask(HomeworkTask homeworkTask) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(homeworkTask);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HomeworkTask> QueryHomeworkTaskInfo(Teacher teacherObj,String title,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HomeworkTask homeworkTask where 1=1";
    	if(null != teacherObj && teacherObj.getId()!=0) hql += " and homeworkTask.teacherObj.id=" + teacherObj.getId();
    	if(!title.equals("")) hql = hql + " and homeworkTask.title like '%" + title + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List homeworkTaskList = q.list();
    	return (ArrayList<HomeworkTask>) homeworkTaskList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HomeworkTask> QueryHomeworkTaskInfo(Teacher teacherObj,String title) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HomeworkTask homeworkTask where 1=1";
    	if(null != teacherObj && teacherObj.getId()!=0) hql += " and homeworkTask.teacherObj.id=" + teacherObj.getId();
    	if(!title.equals("")) hql = hql + " and homeworkTask.title like '%" + title + "%'";
    	Query q = s.createQuery(hql);
    	List homeworkTaskList = q.list();
    	return (ArrayList<HomeworkTask>) homeworkTaskList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HomeworkTask> QueryAllHomeworkTaskInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From HomeworkTask";
        Query q = s.createQuery(hql);
        List homeworkTaskList = q.list();
        return (ArrayList<HomeworkTask>) homeworkTaskList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Teacher teacherObj,String title) {
        Session s = factory.getCurrentSession();
        String hql = "From HomeworkTask homeworkTask where 1=1";
        if(null != teacherObj && teacherObj.getId()!=0) hql += " and homeworkTask.teacherObj.id=" + teacherObj.getId();
        if(!title.equals("")) hql = hql + " and homeworkTask.title like '%" + title + "%'";
        Query q = s.createQuery(hql);
        List homeworkTaskList = q.list();
        recordNumber = homeworkTaskList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public HomeworkTask GetHomeworkTaskByHomeworkId(int homeworkId) {
        Session s = factory.getCurrentSession();
        HomeworkTask homeworkTask = (HomeworkTask)s.get(HomeworkTask.class, homeworkId);
        return homeworkTask;
    }

    /*更新HomeworkTask信息*/
    public void UpdateHomeworkTask(HomeworkTask homeworkTask) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(homeworkTask);
    }

    /*删除HomeworkTask信息*/
    public void DeleteHomeworkTask (int homeworkId) throws Exception {
        Session s = factory.getCurrentSession();
        Object homeworkTask = s.load(HomeworkTask.class, homeworkId);
        s.delete(homeworkTask);
    }

}

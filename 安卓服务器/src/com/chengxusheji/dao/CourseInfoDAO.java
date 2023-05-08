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
import com.chengxusheji.domain.CourseInfo;

@Service @Transactional
public class CourseInfoDAO {

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
    public void AddCourseInfo(CourseInfo courseInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(courseInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CourseInfo> QueryCourseInfoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CourseInfo courseInfo where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List courseInfoList = q.list();
    	return (ArrayList<CourseInfo>) courseInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CourseInfo> QueryCourseInfoInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CourseInfo courseInfo where 1=1";
    	Query q = s.createQuery(hql);
    	List courseInfoList = q.list();
    	return (ArrayList<CourseInfo>) courseInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CourseInfo> QueryAllCourseInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From CourseInfo";
        Query q = s.createQuery(hql);
        List courseInfoList = q.list();
        return (ArrayList<CourseInfo>) courseInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From CourseInfo courseInfo where 1=1";
        Query q = s.createQuery(hql);
        List courseInfoList = q.list();
        recordNumber = courseInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public CourseInfo GetCourseInfoById(int id) {
        Session s = factory.getCurrentSession();
        CourseInfo courseInfo = (CourseInfo)s.get(CourseInfo.class, id);
        return courseInfo;
    }

    /*更新CourseInfo信息*/
    public void UpdateCourseInfo(CourseInfo courseInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(courseInfo);
    }

    /*删除CourseInfo信息*/
    public void DeleteCourseInfo (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object courseInfo = s.load(CourseInfo.class, id);
        s.delete(courseInfo);
    }

}

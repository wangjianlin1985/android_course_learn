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

@Service @Transactional
public class TeacherDAO {

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
    public void AddTeacher(Teacher teacher) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(teacher);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Teacher> QueryTeacherInfo(String name,String position,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Teacher teacher where 1=1";
    	if(!name.equals("")) hql = hql + " and teacher.name like '%" + name + "%'";
    	if(!position.equals("")) hql = hql + " and teacher.position like '%" + position + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List teacherList = q.list();
    	return (ArrayList<Teacher>) teacherList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Teacher> QueryTeacherInfo(String name,String position) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Teacher teacher where 1=1";
    	if(!name.equals("")) hql = hql + " and teacher.name like '%" + name + "%'";
    	if(!position.equals("")) hql = hql + " and teacher.position like '%" + position + "%'";
    	Query q = s.createQuery(hql);
    	List teacherList = q.list();
    	return (ArrayList<Teacher>) teacherList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Teacher> QueryAllTeacherInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Teacher";
        Query q = s.createQuery(hql);
        List teacherList = q.list();
        return (ArrayList<Teacher>) teacherList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String name,String position) {
        Session s = factory.getCurrentSession();
        String hql = "From Teacher teacher where 1=1";
        if(!name.equals("")) hql = hql + " and teacher.name like '%" + name + "%'";
        if(!position.equals("")) hql = hql + " and teacher.position like '%" + position + "%'";
        Query q = s.createQuery(hql);
        List teacherList = q.list();
        recordNumber = teacherList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Teacher GetTeacherById(int id) {
        Session s = factory.getCurrentSession();
        Teacher teacher = (Teacher)s.get(Teacher.class, id);
        return teacher;
    }

    /*更新Teacher信息*/
    public void UpdateTeacher(Teacher teacher) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(teacher);
    }

    /*删除Teacher信息*/
    public void DeleteTeacher (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object teacher = s.load(Teacher.class, id);
        s.delete(teacher);
    }

}

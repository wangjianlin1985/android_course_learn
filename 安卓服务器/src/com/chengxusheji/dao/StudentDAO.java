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
import com.chengxusheji.domain.Student;

@Service @Transactional
public class StudentDAO {

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
    public void AddStudent(Student student) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(student);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Student> QueryStudentInfo(String studentNumber,String name,String birthday,String className,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Student student where 1=1";
    	if(!studentNumber.equals("")) hql = hql + " and student.studentNumber like '%" + studentNumber + "%'";
    	if(!name.equals("")) hql = hql + " and student.name like '%" + name + "%'";
    	if(!birthday.equals("")) hql = hql + " and student.birthday like '%" + birthday + "%'";
    	if(!className.equals("")) hql = hql + " and student.className like '%" + className + "%'";
    	if(!telephone.equals("")) hql = hql + " and student.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List studentList = q.list();
    	return (ArrayList<Student>) studentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Student> QueryStudentInfo(String studentNumber,String name,String birthday,String className,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Student student where 1=1";
    	if(!studentNumber.equals("")) hql = hql + " and student.studentNumber like '%" + studentNumber + "%'";
    	if(!name.equals("")) hql = hql + " and student.name like '%" + name + "%'";
    	if(!birthday.equals("")) hql = hql + " and student.birthday like '%" + birthday + "%'";
    	if(!className.equals("")) hql = hql + " and student.className like '%" + className + "%'";
    	if(!telephone.equals("")) hql = hql + " and student.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List studentList = q.list();
    	return (ArrayList<Student>) studentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Student> QueryAllStudentInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Student";
        Query q = s.createQuery(hql);
        List studentList = q.list();
        return (ArrayList<Student>) studentList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String studentNumber,String name,String birthday,String className,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Student student where 1=1";
        if(!studentNumber.equals("")) hql = hql + " and student.studentNumber like '%" + studentNumber + "%'";
        if(!name.equals("")) hql = hql + " and student.name like '%" + name + "%'";
        if(!birthday.equals("")) hql = hql + " and student.birthday like '%" + birthday + "%'";
        if(!className.equals("")) hql = hql + " and student.className like '%" + className + "%'";
        if(!telephone.equals("")) hql = hql + " and student.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List studentList = q.list();
        recordNumber = studentList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Student GetStudentByStudentNumber(String studentNumber) {
        Session s = factory.getCurrentSession();
        Student student = (Student)s.get(Student.class, studentNumber);
        return student;
    }

    /*更新Student信息*/
    public void UpdateStudent(Student student) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(student);
    }

    /*删除Student信息*/
    public void DeleteStudent (String studentNumber) throws Exception {
        Session s = factory.getCurrentSession();
        Object student = s.load(Student.class, studentNumber);
        s.delete(student);
    }

}

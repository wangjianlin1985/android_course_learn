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
import com.chengxusheji.domain.Question;

@Service @Transactional
public class QuestionDAO {

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
    public void AddQuestion(Question question) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(question);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Question> QueryQuestionInfo(Teacher teacherId,String questioner,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Question question where 1=1";
    	if(null != teacherId && teacherId.getId()!=0) hql += " and question.teacherId.id=" + teacherId.getId();
    	if(!questioner.equals("")) hql = hql + " and question.questioner like '%" + questioner + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List questionList = q.list();
    	return (ArrayList<Question>) questionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Question> QueryQuestionInfo(Teacher teacherId,String questioner) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Question question where 1=1";
    	if(null != teacherId && teacherId.getId()!=0) hql += " and question.teacherId.id=" + teacherId.getId();
    	if(!questioner.equals("")) hql = hql + " and question.questioner like '%" + questioner + "%'";
    	Query q = s.createQuery(hql);
    	List questionList = q.list();
    	return (ArrayList<Question>) questionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Question> QueryAllQuestionInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Question";
        Query q = s.createQuery(hql);
        List questionList = q.list();
        return (ArrayList<Question>) questionList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Teacher teacherId,String questioner) {
        Session s = factory.getCurrentSession();
        String hql = "From Question question where 1=1";
        if(null != teacherId && teacherId.getId()!=0) hql += " and question.teacherId.id=" + teacherId.getId();
        if(!questioner.equals("")) hql = hql + " and question.questioner like '%" + questioner + "%'";
        Query q = s.createQuery(hql);
        List questionList = q.list();
        recordNumber = questionList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Question GetQuestionById(int id) {
        Session s = factory.getCurrentSession();
        Question question = (Question)s.get(Question.class, id);
        return question;
    }

    /*更新Question信息*/
    public void UpdateQuestion(Question question) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(question);
    }

    /*删除Question信息*/
    public void DeleteQuestion (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object question = s.load(Question.class, id);
        s.delete(question);
    }

}

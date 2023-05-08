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
import com.chengxusheji.domain.Exercise;

@Service @Transactional
public class ExerciseDAO {

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
    public void AddExercise(Exercise exercise) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(exercise);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exercise> QueryExerciseInfo(String title,Chapter chapterId,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Exercise exercise where 1=1";
    	if(!title.equals("")) hql = hql + " and exercise.title like '%" + title + "%'";
    	if(null != chapterId && chapterId.getId()!=0) hql += " and exercise.chapterId.id=" + chapterId.getId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List exerciseList = q.list();
    	return (ArrayList<Exercise>) exerciseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exercise> QueryExerciseInfo(String title,Chapter chapterId) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Exercise exercise where 1=1";
    	if(!title.equals("")) hql = hql + " and exercise.title like '%" + title + "%'";
    	if(null != chapterId && chapterId.getId()!=0) hql += " and exercise.chapterId.id=" + chapterId.getId();
    	Query q = s.createQuery(hql);
    	List exerciseList = q.list();
    	return (ArrayList<Exercise>) exerciseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exercise> QueryAllExerciseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Exercise";
        Query q = s.createQuery(hql);
        List exerciseList = q.list();
        return (ArrayList<Exercise>) exerciseList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,Chapter chapterId) {
        Session s = factory.getCurrentSession();
        String hql = "From Exercise exercise where 1=1";
        if(!title.equals("")) hql = hql + " and exercise.title like '%" + title + "%'";
        if(null != chapterId && chapterId.getId()!=0) hql += " and exercise.chapterId.id=" + chapterId.getId();
        Query q = s.createQuery(hql);
        List exerciseList = q.list();
        recordNumber = exerciseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Exercise GetExerciseById(int id) {
        Session s = factory.getCurrentSession();
        Exercise exercise = (Exercise)s.get(Exercise.class, id);
        return exercise;
    }

    /*����Exercise��Ϣ*/
    public void UpdateExercise(Exercise exercise) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(exercise);
    }

    /*ɾ��Exercise��Ϣ*/
    public void DeleteExercise (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object exercise = s.load(Exercise.class, id);
        s.delete(exercise);
    }

}

package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Student;
import com.mobileserver.util.DB;

public class StudentDAO {

	public List<Student> QueryStudent(String studentNumber,String name,Timestamp birthday,String className,String telephone) {
		List<Student> studentList = new ArrayList<Student>();
		DB db = new DB();
		String sql = "select * from Student where 1=1";
		if (!studentNumber.equals(""))
			sql += " and studentNumber like '%" + studentNumber + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		if (!className.equals(""))
			sql += " and className like '%" + className + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setBirthday(rs.getTimestamp("birthday"));
				student.setZzmm(rs.getString("zzmm"));
				student.setClassName(rs.getString("className"));
				student.setTelephone(rs.getString("telephone"));
				student.setPhoto(rs.getString("photo"));
				student.setAddress(rs.getString("address"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return studentList;
	}
	/* ����ѧ����Ϣ���󣬽���ѧ����Ϣ�����ҵ�� */
	public String AddStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧ����Ϣ */
			String sqlString = "insert into Student(studentNumber,password,name,sex,birthday,zzmm,className,telephone,photo,address) values (";
			sqlString += "'" + student.getStudentNumber() + "',";
			sqlString += "'" + student.getPassword() + "',";
			sqlString += "'" + student.getName() + "',";
			sqlString += "'" + student.getSex() + "',";
			sqlString += "'" + student.getBirthday() + "',";
			sqlString += "'" + student.getZzmm() + "',";
			sqlString += "'" + student.getClassName() + "',";
			sqlString += "'" + student.getTelephone() + "',";
			sqlString += "'" + student.getPhoto() + "',";
			sqlString += "'" + student.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧ����Ϣ */
	public String DeleteStudent(String studentNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Student where studentNumber='" + studentNumber + "'";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѧ�Ż�ȡ��ѧ����Ϣ */
	public Student GetStudent(String studentNumber) {
		Student student = null;
		DB db = new DB();
		String sql = "select * from Student where studentNumber='" + studentNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setBirthday(rs.getTimestamp("birthday"));
				student.setZzmm(rs.getString("zzmm"));
				student.setClassName(rs.getString("className"));
				student.setTelephone(rs.getString("telephone"));
				student.setPhoto(rs.getString("photo"));
				student.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return student;
	}
	/* ����ѧ����Ϣ */
	public String UpdateStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Student set ";
			sql += "password='" + student.getPassword() + "',";
			sql += "name='" + student.getName() + "',";
			sql += "sex='" + student.getSex() + "',";
			sql += "birthday='" + student.getBirthday() + "',";
			sql += "zzmm='" + student.getZzmm() + "',";
			sql += "className='" + student.getClassName() + "',";
			sql += "telephone='" + student.getTelephone() + "',";
			sql += "photo='" + student.getPhoto() + "',";
			sql += "address='" + student.getAddress() + "'";
			sql += " where studentNumber='" + student.getStudentNumber() + "'";
			db.executeUpdate(sql);
			result = "ѧ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}

package com.klu.JFSD_LAB;



import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
   int id;
   String name;
   String gender;
   String dept;
   String program;
   String dob;
   long contactno;
   String graduationstatus;
   double cgpa;
   int backlogs;
  

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getDept() {
	return dept;
}
public void setDept(String dept) {
	this.dept = dept;
}
public String getProgram() {
	return program;
}
public void setProgram(String program) {
	this.program = program;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public long getContactno() {
	return contactno;
}
public void setContactno(long contactno) {
	this.contactno = contactno;
}
public String getGraduationstatus() {
	return graduationstatus;
}
public void setGraduationstatus(String graduationstatus) {
	this.graduationstatus = graduationstatus;
}
public double getCgpa() {
	return cgpa;
}
public void setCgpa(double cgpa) {
	this.cgpa = cgpa;
}
public int getBacklogs() {
	return backlogs;
}
public void setBacklogs(int backlogs) {
	this.backlogs = backlogs;
}
@Override
public String toString() {
    return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", dept=" + dept
            + ", program=" + program + ", dob=" + dob + ", graduationstatus=" + graduationstatus
            + ", contactno=" + contactno + ", cgpa=" + cgpa + ", backlogs=" + backlogs + "]";
}


   
   
   
}


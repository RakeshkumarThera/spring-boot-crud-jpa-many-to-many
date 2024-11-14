package com.rakesh.cruddemo.dao;

import com.rakesh.cruddemo.entity.Course;
import com.rakesh.cruddemo.entity.Instructor;
import com.rakesh.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{


    //define field for entity manager
    private EntityManager entityManager;

    //inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        //retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);

        //get the courses
        List<Course> courses = tempInstructor.getCourses();

        //break the association of all courses for the instructor
        for(Course tempCourse: courses) {
            tempCourse.setInstructor(null);
        }

        //delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class, theId);

        //remove the associated object reference
        //break bi-directional link

        tempInstructorDetail.getInstructor().setInstructorDetail(null);

        entityManager.remove(tempInstructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {

        //create Query
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data", Course.class);
        query.setParameter("data", theId);

        //execute query
        List<Course> courses = query.getResultList();

        return  courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId){

        //create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                                        "select i from Instructor i"
                                            + " JOIN FETCH i.courses"
                                            + " JOIN FETCH i.instructorDetail"
                                            + " where i.id = :data", Instructor.class);
        query.setParameter("data", theId);

        //execute query
        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor);
    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class ,theId);
    }

    @Override
    @Transactional
    public void deleteCourse(int theId) {

        //retrieve the course
        Course tempCourse = entityManager.find(Course.class, theId);

        //delete the course
        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
        //create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c " +
                        "JOIN FETCH c.reviews " +
                        "where c.id = :data", Course.class);

        query.setParameter("data", theId);

        //execute query
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {
        //create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c " +
                        "JOIN FETCH c.students " +
                        "where c.id = :data", Course.class);

        query.setParameter("data", theId);

        //execute query
        Course course = query.getSingleResult();

        return course;
    }
}

package com.rakesh.cruddemo;

import com.rakesh.cruddemo.dao.AppDAO;
import com.rakesh.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){
		return runner -> {
//			createCourseAndStudents(appDAO);
			findCourseAndStudents(appDAO);

		};
	}

	private void findCourseAndStudents(AppDAO appDAO) {
		int theId = 10;

		Course tempCourse = appDAO.findCourseAndStudentsByCourseId(theId);
		System.out.println("Loaded Courses: " + tempCourse );
		System.out.println("Students: " +tempCourse.getStudents());

		System.out.println("Done");
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		 Course tempCourse = new Course("Exodus");
		 Student tempStudent1 = new Student("Rakesh", "Thera", "rakeshkumarthera@gmail.com");
		 Student tempStudent2 = new Student("Reshma", "Thera", "reshma94penumudi@gmail.com");

		 tempCourse.addStudent(tempStudent1);
		 tempCourse.addStudent(tempStudent2);

		System.out.println("Saving the course: " + tempCourse);
		System.out.println("Associated Students" + tempCourse.getStudents());

		appDAO.save(tempCourse);
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int theId = 10;
		System.out.println("Deleting Course with id : " +theId);
		appDAO.deleteCourse(theId);
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
		int theId = 10;

		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		//create a course
		Course tempCourse = new Course("Genesis");

		//add some reviews
		tempCourse.addReview(new Review("First Book of Bible"));
		tempCourse.addReview(new Review("Book of Origins"));
		tempCourse.addReview(new Review("Author is Moses breathed out by God"));

		System.out.println("Saving the course");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());
		//save the course
		appDAO.save(tempCourse);

		System.out.println("Done!!");
	}

	private void deleteCourse(AppDAO appDAO) {
		int theId=10;
		System.out.println("Deleting the course with id: " + theId);
		appDAO.deleteCourse(theId);
		System.out.println("Deleted");
	}

	private void updateCourse(AppDAO appDAO) {
		int theId = 10;

		//find course
		System.out.println("Updating the course id with: " + theId);
		Course tempCourse = appDAO.findCourseById(theId);

		//update Course
		System.out.println("Updating the course: " + tempCourse);
		tempCourse.setTitle("In the Beginning God created the Heavens and the Earth");
		appDAO.update(tempCourse);
	}

	private void updateInstructor(AppDAO appDAO) {
		int theId = 1;

		//find the instructor;
		System.out.println("Finding the instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		//update instructor
		System.out.println("Updating instructor : " + tempInstructor);
		tempInstructor.setLastName("Penumudi");

		appDAO.update(tempInstructor);

		System.out.println("Done!");
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding the instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);
		System.out.println("Instructor: " + tempInstructor);
		System.out.println("the asociated course" + tempInstructor.getCourses());
		System.out.println("Done!!");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {
		int theId=1;
		System.out.println("Finding the instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("Instructor: " + tempInstructor);

		// find courses for instructor
		System.out.println("Finding courses for instructor id: " + theId);
		List<Course> courses = appDAO.findCoursesByInstructorId(theId);

		//associate the objects
		tempInstructor.setCourses(courses);

		System.out.println("the associated courses : " + tempInstructor.getCourses());
		System.out.println("Done!!");
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int theId=1;
		System.out.println("Finding the instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("Instructor: " + tempInstructor);
		System.out.println("the associated courses" + tempInstructor.getCourses());
		System.out.println("Done!!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		//create the instructor
		Instructor tempInstructor =
				new Instructor("rakesh", "thera", "rakeshkumarthera@gmail.com");

		InstructorDetail tempInstructorDetail =
				new InstructorDetail("https://www.youtube.com/@godswordministries.",
						"Travelling");

		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//Create some courses

		Course tempCourse1 = new Course("Genesis");
		Course tempCourse2 = new Course("Exodus");

		//Add Courses to Instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);


		//Save the instructors
		//
		//NOTE: this will also save the courses
		//because of CascadeType.PERSIST
		//
		System.out.println("Saving Instructor: " + tempInstructor);
		System.out.println("The Courses: " +tempInstructor.getCourses());

		appDAO.save(tempInstructor);

		System.out.println("DONE!!!");
	}

	private void deleteInstructorDetail(AppDAO appDAO) {
		int theId = 3;
		System.out.println("Deleting instructor detail with id: " + theId);
		appDAO.deleteInstructorById(theId);
		System.out.println("Delete Complete!!");
	}

	private void findInstructorDetail(AppDAO appDAO) {
		int theId = 2;

		//get the instructor detail
		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

		// print the instructor detail
		System.out.println("Instructor detials: " + tempInstructorDetail);

		// print the associated instructor
		System.out.println("The associated instructor: " + tempInstructorDetail.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting the instructor id: " + theId);
		appDAO.deleteInstructorById(theId);
		System.out.println("Deleted!!");
	}

	private void findInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("Instructor: " + tempInstructor);
		System.out.println("the associated instructor details only: " + tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {
		//create the instructor
		Instructor tempInstructor =
				new Instructor("rakesh", "thera", "rakeshkumarthera@gmail.com");

		InstructorDetail tempInstructorDetail =
				new InstructorDetail("https://www.youtube.com/@godswordministries.",
						"Travelling");

		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//save the instructors

		//
		//NOTE: this will save the details object
		//because of CascadeType:ALL
		//
		System.out.println("Saving Instructors: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}
}
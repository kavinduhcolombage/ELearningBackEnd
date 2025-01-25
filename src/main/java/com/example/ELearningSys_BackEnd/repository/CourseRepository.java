package com.example.ELearningSys_BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ELearningSys_BackEnd.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}

package com;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/8
 */
public class Student {

    private String id;

    private Course course;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", course=" + course +
                ", name='" + name + '\'' +
                '}';
    }
}

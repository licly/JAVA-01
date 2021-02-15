package com;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/8
 */
@ConfigurationProperties("user.config.course")
public class Course {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

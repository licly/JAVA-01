package com.charlie.course10.jdbc;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/6
 */
public class User {

    private Long id;

    private String name;

    private String address;

    private LocalDateTime arriveTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                '}';
    }
}

package com.magent.domain;

import com.magent.domain.enums.TimeIntervalConstants;
import com.magent.domain.interfaces.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created  on 02.08.2016.
 */
@Entity
@Table(name = "ma_time_config")
public class TimeInterval implements Identifiable<Integer> {

    @Id
    @Column(name = "tconf_pk")
    private Integer id;

    @Column(name = "tconf_name",nullable = false,unique = true)
    private String name;

    @Column(name = "tconf_interval_minutes",nullable = false)
    private String timeInterval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeInterval() {

    }

    public TimeInterval(Integer id, String name, String timeInterval) {
        this.id = id;
        this.name = name;
        this.timeInterval = timeInterval;
    }

    public TimeInterval(TimeIntervalConstants constants) {
        this.id=constants.getId();
        this.name=constants.toString();
        this.timeInterval=constants.getDefaultTimeInterval();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeInterval that = (TimeInterval) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

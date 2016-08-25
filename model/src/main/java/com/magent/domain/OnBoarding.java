package com.magent.domain;

import com.magent.domain.interfaces.Identifiable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.*;

/**
 * Created  on 22.06.2016.
 */
@SuppressFBWarnings("EI_EXPOSE_REP2")
@Entity
@Table(name = "ma_onboards")
public class OnBoarding implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "onboard_id_pk")
    private Long id;

    @Column(name = "onboard_img", nullable = false)
    private byte[] image;

    @Column(name = "onboard_contents", length = 250)
    private String content;

    @Column(name = "onboard_filename",nullable = false)
    private String fullFileName;

    @Column(name = "onboard_desc", length = 50)
    private String description;



    public OnBoarding() {
    }

    public OnBoarding(byte[] image, String content, String fullFileName, String description) {
        this.image = image;
        this.content = content;
        this.fullFileName = fullFileName;
        this.description = description;
    }

    public OnBoarding(byte[] image, String fullFileName) {
        this.image = image;
        this.fullFileName = fullFileName;
        this.content=null;
        this.description=null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullFileName() {
        return fullFileName;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnBoarding that = (OnBoarding) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "OnBoarding{" +
                "content='" + content + '\'' +
                '}';
    }
}

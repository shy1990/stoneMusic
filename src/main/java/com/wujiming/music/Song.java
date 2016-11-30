package com.wujiming.music;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

/**
 * @author 武继明 [wzslw@163.com]
 * @since 16-11-30 下午1:06
 */
@Entity
class Song extends AbstractPersistable<Long>{
    private String name;
    private String singer;
    private String url;

    public Song(String name, String singer, String url) {
        this.name = name;
        this.singer = singer;
        this.url = url;
    }

    public Song() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

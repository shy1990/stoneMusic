package com.wujiming.music;

import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 武继明 [wzslw@163.com]
 * @since 16-11-30 上午9:57
 */
@RestController
class MusicController {
    Pattern pattern= Pattern.compile("(ws\\.stream\\.qqmusic\\.qq\\.com.*m4a)");
    @Autowired
    private SongRepository songRepository;

    @GetMapping(value = "/search",produces = "application/json")
    ResponseEntity search(String name) throws IOException {
        StringBuffer urlSb=new StringBuffer("https://c.y.qq.com/soso/fcgi-bin/search_cp?")
                .append("jsonpCallback=searchCallbacksong&")
                .append("w=").append(name);
            String s = Request.Get(urlSb.toString())
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36")
                    .execute()
                    .returnContent().asString();
           s= s.substring("searchCallbacksong(".length(),s.length()-1);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/add")
    Song add(String name,String singer,String url){
        Song song=new Song(name,singer,url);
        return songRepository.save(song);
    }
    @GetMapping("/songs")
    ResponseEntity list(){
        return ResponseEntity.ok(songRepository.findAll());
    }
    @GetMapping("/next")
    String next(){
        final Song song = songRepository.findTopByOrderById();
    if (song==null){
        return null;
    }
        return getSongStreamUrl(song.getUrl());
    }

    @GetMapping("/pop")
    void pop(){
        final Song song = songRepository.findTopByOrderById();
        songRepository.delete(song);

    }
    private String getSongStreamUrl(String songUrl) {

        String url=null;
        String id=songUrl.substring(songUrl.lastIndexOf("=")+1,songUrl.length());

        try {
            String streamUrl="https://y.qq.com/portal/song/"+id+"_num.html";
            System.out.println(streamUrl);
            final String s = Request.Get(streamUrl).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36")
                    .execute().returnContent().asString();
            Matcher matcher=pattern.matcher(s);
            if (matcher.find()) url=matcher.group();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }



}

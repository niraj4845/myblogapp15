package com.blogapp15.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDto {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private long id;
    @NotEmpty
    private  String description;
    private  String content;
    @NotEmpty
    @Size(min = 3,message = "Title should be atleast 3 chracters")
    private  String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

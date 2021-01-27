package com.revature.chronicle.models;

import java.util.Date;
import java.util.List;

public abstract class Media {

    public abstract int getId();
    public abstract void setId(int id);

    public abstract String getUrl();
    public abstract void setUrl(String url);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    public abstract String getTitle();
    public abstract void setTitle(String title);

    public abstract Date getDate();
    public abstract void setDate(Date date);

    public abstract String getUser();
    public abstract void setUser(String user);

    public abstract List<Tag> getTags();
    public abstract void setTags(List<Tag> tags);
}

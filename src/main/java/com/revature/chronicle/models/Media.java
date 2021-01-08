package com.revature.chronicle.models;

import java.util.Date;
import java.util.Set;

public abstract class Media {

    public abstract int getId();
    public abstract void setId(int id);

    public abstract String getUrl();
    public abstract void setUrl(String url);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    public abstract Date getDate();
    public abstract void setDate(Date date);

    public abstract User getUser();
    public abstract void setUser(User user);

    public abstract Set<Tag> getTags();
    public abstract void setTags(Set<Tag> tags);
}

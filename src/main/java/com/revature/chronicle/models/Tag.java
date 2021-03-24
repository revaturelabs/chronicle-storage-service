package com.revature.chronicle.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * A model representing a tag on a video or note
 */
@Entity
@Table(name="tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int tagID;

    @Column(name="type")
    private String type;

    @Column(name = "value")
    private String value;


    public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Tag(String type, String value) {
        this.type = type;
        this.value = value;
    }


	public int getTagID() {
		return tagID;
	}


	public void setTagID(int tagID) {
		this.tagID = tagID;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tagID;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (tagID != other.tagID)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Tag [tagID=" + tagID + ", type=" + type + ", value=" + value + "]";
	}
    
}

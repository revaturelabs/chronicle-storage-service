package com.revature.chronicle.util;

import java.util.Comparator;

import com.revature.chronicle.models.Video;

public class VideoComparator implements Comparator<Video>{

	@Override
	public int compare(Video o1, Video o2) {
		
		return o1.getId() - o2.getId();
	}
	
	
	

}

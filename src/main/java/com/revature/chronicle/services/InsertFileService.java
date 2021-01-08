package com.revature.chronicle.services;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class InsertFileService {
    public Object insertFile(String url, String processedForm) {
        return null;
    }

    public boolean addSuccess() {
        return false;
    }
}

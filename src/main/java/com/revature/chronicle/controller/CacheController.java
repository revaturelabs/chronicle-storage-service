package com.revature.chronicle.controller;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
public class CacheController implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

	@Override
	public void customize(ConcurrentMapCacheManager cacheManager) {
//		cacheManager.setCacheNames(asList("users","ids"));
	}

}

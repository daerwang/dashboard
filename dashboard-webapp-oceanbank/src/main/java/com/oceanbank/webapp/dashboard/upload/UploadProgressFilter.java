package com.oceanbank.webapp.dashboard.upload;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class UploadProgressFilter extends OncePerRequestFilter{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private final String PROGRESS_TAIL = ".progress"; 
    
	@Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        String originalUrl = requestUri.substring(0, requestUri.length() - PROGRESS_TAIL.length() -1);
        String attributeName = ProgressCapableMultipartResolver.PROGRESS_PREFIX + originalUrl;
        
        //LOGGER.info("attributeName - " + attributeName);
        
        Object progress = request.getSession().getAttribute(attributeName);
        if (progress != null) {
            ProgressDescriptor descriptor = (ProgressDescriptor)progress; 
            response.getOutputStream().write(descriptor.toString().getBytes());
        }else{
        	Long currentCount = (Long)request.getSession().getAttribute("currentCount");
        	Long cifListCount = (Long)request.getSession().getAttribute("cifListCount");
        	ProgressDescriptor descriptor = new ProgressDescriptor(currentCount, cifListCount);
        	response.getOutputStream().write(descriptor.toString().getBytes());
        }
    }

}

package com.oceanbank.webapp.dashboard.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class ProgressCapableMultipartResolver extends CommonsMultipartResolver{
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public final static String PROGRESS_PREFIX = "ProgressCapableMultipartResolver:";
	 
    private void initProgressProcessor(HttpServletRequest request) {
    	String key = PROGRESS_PREFIX + request.getRequestURI();
    	LOGGER.info("key - " + key);
        request.getSession().setAttribute(PROGRESS_PREFIX + request.getRequestURI(), new ProgressDescriptor());
    }
     
    private void clearProgressProcessor(HttpServletRequest request) { 

        request.getSession().removeAttribute(PROGRESS_PREFIX + request.getRequestURI());
    }
     
    private void processProgress(long bytesRead, long bytesTotal, HttpServletRequest request) {
         
        request.getSession().setAttribute(PROGRESS_PREFIX + request.getRequestURI(), new ProgressDescriptor(bytesRead, bytesTotal));
    }
     
    protected MultipartParsingResult parseRequest(final HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        ServletFileUpload fileUpload = (ServletFileUpload)prepareFileUpload(encoding);
 
        initProgressProcessor(request); 
        fileUpload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long pBytesRead, long pContentLength, int pItems) {
                processProgress(pBytesRead, pContentLength, request);
            }
        });
         
        MultipartParsingResult result = null; 
        try {
            result = parseFileItems( fileUpload.parseRequest(request), encoding);
            
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
             
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse " +"multipart servlet request", ex);
        }
         
        clearProgressProcessor(request); 
        return result; 
    }
}

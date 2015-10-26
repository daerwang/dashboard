package com.oceanbank.webapp.dashboard.upload;

public class ProgressDescriptor {
	public long bytesRead; 
    public long bytesTotal;
     
    public ProgressDescriptor() {
        bytesRead = bytesTotal = 0; 
    }
     
    public ProgressDescriptor(long bytesRead,
            long bytesTotal) 
    { 
        this.bytesRead = bytesRead; 
        this.bytesTotal = bytesTotal; 
    }
     
    public long getBytesRead() { 
        return bytesRead; 
    }
     
    public long getBytesTotal() { 
        return bytesTotal;
    }
     
    @Override
    public String toString() {
        return bytesRead + "/" + bytesTotal; 
    }
}

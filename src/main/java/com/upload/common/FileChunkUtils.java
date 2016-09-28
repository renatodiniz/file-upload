package com.upload.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.TreeMap;

import org.springframework.util.FileCopyUtils;

public class FileChunkUtils {
	
	public static void saveChunk(File dir, String fileName, long chunkFrom, byte[] bytes) throws IOException {
	    File target = new File(dir, fileName + "." + chunkFrom + ".chunk");
	    FileCopyUtils.copy(bytes, target);
	}
    
	public static TreeMap<Long, Long> getChunkStartsToLengths(File dir, 
	        String fileName) throws IOException {
	    TreeMap<Long, Long> chunkStartsToLengths = new TreeMap<Long, Long>();
	    for (File f : dir.listFiles()) {
	        String chunkFileName = f.getName();
	        if (chunkFileName.startsWith(fileName + ".") && 
	                chunkFileName.endsWith(".chunk")) {
	            chunkStartsToLengths.put(Long.parseLong(chunkFileName.substring(
	                    fileName.length() + 1, chunkFileName.length() - 6)), f.length());
	        }
	    }
	    return chunkStartsToLengths;
	}

	public static long getCommonLength(TreeMap<Long, Long> chunkStartsToLengths) {
	    long ret = 0;
	    for (long len : chunkStartsToLengths.values())
	        ret += len;
	    return ret;
	}
	
	public static File assembleAndDeleteChunks(File dir, String fileName, List<Long> chunkStarts) throws IOException {
	    File assembledFile = new File(dir, fileName);
	    if (assembledFile.exists()) // In case chunks come in concurrent way
	        return assembledFile;
	    OutputStream assembledOs = new FileOutputStream(assembledFile);
	    byte[] buf = new byte[100000];
	    try {
	        for (long chunkFrom : chunkStarts) {
	            File chunkFile = new File(dir, fileName + "." + chunkFrom + ".chunk");
	            InputStream is = new FileInputStream(chunkFile);
	            try {
	                while (true) {
	                    int r = is.read(buf);
	                    if (r == -1)
	                        break;
	                    if (r > 0)
	                        assembledOs.write(buf, 0, r);
	                }
	            } finally {
	                is.close();
	            }
	            chunkFile.delete();
	        }
	    } finally {
	        assembledOs.close();
	    }
	    return assembledFile;
	}
}

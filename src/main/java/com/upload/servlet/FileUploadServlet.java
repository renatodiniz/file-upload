package com.upload.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUploadServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String UPLOAD_DIR = "upload_dir_servlet";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String range = request.getHeader("Content-Range");
	    
	    // obtem a posicao inicial e final do chunk e tamanho total do arquivo através das informações do header
	    long fileFullLength = -1;
	    long chunkFrom = -1;
	    long chunkTo = -1;
	    if (range != null) {
	        if (!range.startsWith("bytes "))
	            throw new ServletException("Unexpected range format: " + range);
	        String[] fromToAndLength = range.substring(6).split(Pattern.quote("/"));
	        fileFullLength = Long.parseLong(fromToAndLength[1]);
	        String[] fromAndTo = fromToAndLength[0].split(Pattern.quote("-"));
	        chunkFrom = Long.parseLong(fromAndTo[0]);
	        chunkTo = Long.parseLong(fromAndTo[1]);
	    }
	    
	    // cria diretorio e instancia classes de controle de diretorio
	    new File(UPLOAD_DIR).mkdir();
	    File tempDir = new File(UPLOAD_DIR);  // Configure according 
	    File storageDir = tempDir;                                      // project server environment.
	    String uploadId = null;
	    FileItemFactory factory = new DiskFileItemFactory(10000000, tempDir);
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    
	    try {
	    	
	    	// recupera lista de parametros para parsear os itens do request
	        List<?> items = upload.parseRequest(request);
	        Iterator<?> it = items.iterator();
	        List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
	        
	        while (it.hasNext()) {
	        	// recupera um item de request
	            FileItem item = (FileItem) it.next();
	            
	            // testa se o item de request é um form field
	            if (item.isFormField()) {
	                if (item.getFieldName().equals("uploadId"))
	                        uploadId = item.getString();
	                
	            // se não for o form field é porque é um bloco do arquivo
	            } else { 
	                Map<String, Object> fileInfo = new LinkedHashMap<String, Object>();
	                File assembledFile = null;
	                fileInfo.put("name", item.getName());
	                fileInfo.put("type", item.getContentType());
	                
	                // instancia File para recuperar o diretorio do upload
	                File dir = new File(storageDir, uploadId);
	                
	                // testa se diretorio existe e caso contrário cria
	                if (!dir.exists())
	                    dir.mkdir();
	                
	                // tamanho do arquivo < 0 significa que o arquivo não foi quebrado em bloco
	                if (fileFullLength < 0) {
	                    fileInfo.put("size", item.getSize());
	                    assembledFile = new File(dir, item.getName());
	                    item.write(assembledFile);
	                    
	                // caso contrário, o arquivo é um bloco
	                } else {
	                    byte[] bytes = item.get();
	                    // testa se tamanho do arquivo condiz com o tamanho esperado do bloco
	                    if (chunkFrom + bytes.length != chunkTo + 1)
	                        throw new ServletException("Unexpected length of chunk: " + bytes.length + 
	                                " != " + (chunkTo + 1) + " - " + chunkFrom);
	                    
	                    // salva o bloco do arquivo
	                    saveChunk(dir, item.getName(), chunkFrom, bytes, fileFullLength);
	                    
	                    // faz o cálculo da soma em bytes dos blocos do arquivo que já foram salvos no disco
	                    TreeMap<Long, Long> chunkStartsToLengths = getChunkStartsToLengths(dir, item.getName());
	                    long lengthSoFar = getCommonLength(chunkStartsToLengths);
	                    fileInfo.put("size", lengthSoFar);	                    
	                    
	                    // se o arquivo inteiro já foi salvo no disco
	                    if (lengthSoFar == fileFullLength) {
	                    	// realiza o parse dos blocos de arquivo que se encontram no disco
	                        assembledFile = assembleAndDeleteChunks(dir, item.getName(), 
	                                new ArrayList<Long>(chunkStartsToLengths.keySet()));
	                    }
	                }
	                
	                // parse dos blocos de arquivo terminou
	                if (assembledFile != null) {
	                    fileInfo.put("complete", true);
	                    fileInfo.put("serverPath", assembledFile.getAbsolutePath());
	                }
	                ret.add(fileInfo);
	            }
	        }
	        
	        // envia o map de informações na resposta
	        Map<String, Object> filesInfo = new LinkedHashMap<String, Object>();
	        filesInfo.put("files", ret);
	        response.setContentType("application/json");
	        response.getWriter().write(new ObjectMapper().writeValueAsString(filesInfo));
	        response.getWriter().close();
	    } catch (ServletException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        throw new ServletException(ex);
	    }
	}

	private static void saveChunk(File dir, String fileName, 
	        long from, byte[] bytes, long fileFullLength) throws IOException {
	    File target = new File(dir, fileName + "." + from + ".chunk");
	    OutputStream os = new FileOutputStream(target);
	    try {
	        os.write(bytes);
	    } finally {
	        os.close();
	    }
	}

	private static TreeMap<Long, Long> getChunkStartsToLengths(File dir, 
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

	private static long getCommonLength(TreeMap<Long, Long> chunkStartsToLengths) {
	    long ret = 0;
	    for (long len : chunkStartsToLengths.values())
	        ret += len;
	    return ret;
	}

	private static File assembleAndDeleteChunks(File dir, String fileName, 
	        List<Long> chunkStarts) throws IOException {
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

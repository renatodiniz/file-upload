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

/**
 * The Class FileChunkUtils.
 * Classe utilitária para lidar com blocos de arquivos.
 */
public class FileChunkUtils {
	
	/**
	 * Salva um bloco de arquivo em disco.
	 *
	 * @param dir
	 *            diretório para salvar arquivo
	 * @param fileName
	 *            nome do arquivo
	 * @param chunkFrom
	 *            the chunk from
	 * @param bytes
	 *            bytes do bloco de arquivo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void saveChunk(File dir, String fileName, long chunkFrom, byte[] bytes) throws IOException {
	    File target = new File(dir, fileName + "." + chunkFrom + ".chunk");
	    FileCopyUtils.copy(bytes, target);
	}
    
	/**
	 * Retorna um map com as posições iniciais dos 
	 * blocos de arquivo e tamanho dos blocos no diretorio.
	 *
	 * @param dir
	 *            diretório dos blocos de arquivo
	 * @param fileName
	 *            nome do arquivo
	 * @return map de posições iniciais e tamanho dos blocos
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Retorna a quantidade de bytes do arquivo que já foi salvo 
	 * no disco juntando todos os blocos de arquivo.
	 *
	 * @param chunkStartsToLengths
	 *            map de posições iniciais e tamanho dos blocos
	 * @return quantidade de bytes do arquivo
	 */
	public static long getCommonLength(TreeMap<Long, Long> chunkStartsToLengths) {
	    long ret = 0;
	    for (long len : chunkStartsToLengths.values())
	        ret += len;
	    return ret;
	}
	
	/**
	 * Realiza o parse dos blocos de arquivo.
	 *
	 * @param dir
	 *            diretório dos blocos de arquivo
	 * @param fileName
	 *            nome do arquivo
	 * @param chunkStarts
	 *            lista das posições de início de cada bloco
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void assembleAndDeleteChunks(File dir, String fileName, List<Long> chunkStarts) throws IOException {
	    File assembledFile = new File(dir, fileName);
	    if (assembledFile.exists()) // Caso os blocos do mesmo arquivo venham de forma concorrente
	        return;
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
	}
}

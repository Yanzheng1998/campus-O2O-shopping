package com.yanzheng.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 
 * @author yanzheng
 *
 */
public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cfile) {
		File newFile = new File(cfile.getOriginalFilename());
		try {
			cfile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	/**
	 *  process image, return the path of generated image
	 * @param thumbnail
	 * @param targetAddr
	 * @return relative path of img
	 */
	public static String generateThumbnail(File thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		File dest =   new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(200, 200).
			watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark,jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * randomly generate file name. time + 5 digit random number
	 * 
	 * @return
	 */
	private static String getRandomFileName() {
		//get random num
		int ranNum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr+ranNum;
	}
	
	/**
	 * get input file`s extension
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(File cFile) {
		String originalFileName = cFile.getName();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	/**
	 * build dirs
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
 	}
	
	
	public static void main(String[] args) throws IOException {
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Thumbnails.of(new File("")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark,jpg")), 0.25f)
				.outputQuality(0.8f).toFile("");
	}
}

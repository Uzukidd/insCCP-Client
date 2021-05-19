package org.committee.insCCP.fileManager;

import java.io.File;

/**
 * Windows文件系统的相关数据
 */

import javax.swing.filechooser.FileSystemView;

public class FileWindows {
	
	public final static FileSystemView fsv = FileSystemView.getFileSystemView();
	
	public static String getDefaultDirectory() {
		
		return fsv.getDefaultDirectory().getPath() + "\\insCCP";	
		
	}
	
	static {
		init();
	}
	
	public static void init() {
		
		File file = new File(getDefaultDirectory());
		
		if (file.exists() && file.isFile()) file.delete();
		
		file.mkdirs();
		
	}

}

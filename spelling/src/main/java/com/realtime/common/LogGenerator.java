package com.realtime.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/*
 * Created by mesa@1205
*/
public class LogGenerator {

	public final static java.util.logging.Logger createLogs(Class classname,String filename,boolean append)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = simpleDateFormat.format(new Date());
        filename = filename + "." + date;
        String path = Constants.usr_dir + Constants.file_separator + "Logs";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        
        java.util.logging.Logger logger = null;
        try{
	            logger = java.util.logging.Logger.getLogger(classname.getName());
	
	            logger.setLevel(Level.ALL);
	            FileHandler handler = new FileHandler(path + Constants.file_separator + filename
	                    + ".txt", append);
	
	            handler.setFormatter(new SimpleFormatter());
	            logger.addHandler(handler);
	            logger.setUseParentHandlers(false);
        	}
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        return logger;
	}
	
	
}

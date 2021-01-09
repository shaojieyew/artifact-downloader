package ysj.util;

import org.apache.log4j.Logger;

public class Util {

    public static Logger initLogger(Class classType){
        org.apache.log4j.BasicConfigurator.configure();
        return Logger.getLogger(classType.getName());
    }
}

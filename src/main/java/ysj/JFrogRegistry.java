package ysj;

import org.apache.log4j.Logger;
import ysj.util.Util;
import ysj.entities.Package;
import java.io.File;
import java.util.List;

public class JFrogRegistry extends Registry {
    final static Logger logger = Util.initLogger(JFrogRegistry.class);

    private String host;
    public JFrogRegistry(String host) {
        this.host = host;
    }

    @Override
    public List<Package> getPackages() {
        return null;
    }

    @Override
    public List<Package> getPackages(String group) {
        return null;
    }

    @Override
    public List<Package> getPackages(String group, String artifact) {
        return null;
    }

    @Override
    public File download(Package pkg) {
        return null;
    }

    // to be removed
    public static void main(String[] args){
    }
}

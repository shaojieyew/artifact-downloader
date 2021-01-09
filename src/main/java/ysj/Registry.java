package ysj;

import ysj.entities.Package;
import java.io.File;
import java.util.List;

public abstract class Registry {
    String localRepo = "target/repository";

    /**
     * @return list of packages in registry
     */
    abstract public List<Package> getPackages();

    /**
     * @param group of artifacts
     * @return list of packages in registry filtered by group
     */
    abstract public List<Package> getPackages(String group);

    /**
     * @param group of artifacts
     * @param artifact
     * @return list of packages in registry filtered by group and artifact
     */
    abstract public List<Package> getPackages(String group, String artifact);

    /**
     * download package
     * @param pkg
     * @return returns the downloaded pkg as File
     */
    abstract public File download(Package pkg);
}

package ysj.entities;

import java.io.File;

public class Package {
    private String id;
    private String group;
    private String artifact;
    private String version;
    private PackageType package_type;
    private File jar;

    public enum PackageType{
        MAVEN
    }
    public Package(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PackageType getPackage_type() {
        return package_type;
    }

    public void setPackage_type(PackageType package_type) {
        this.package_type = package_type;
    }

    public File getJar() {
        return jar;
    }

    public void setJar(File jar) {
        this.jar = jar;
    }
}

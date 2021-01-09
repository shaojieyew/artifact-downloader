package ysj.entities;

import java.io.File;

public class Package {
    private String id;
    private String groupId;
    private String artifactId;
    private String version;
    private PackageType package_type;
    private File jar;

    public enum PackageType{
        MAVEN
    }
    public Package(String groupId, String artifactId, String version){
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public Package(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
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

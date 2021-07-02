# ArtifactDownloader
Simple wrapper around org.eclipse.aether that list and download artifacts

##Download Artifact
```
MavenRegistrySvc reg = new MavenRegistrySvc("https://gitlab.com/api/v4/projects/25819110/packages/maven","B8UxzhjZiBDJK51ZVH123","./repository");
Package pkg = new Package("org.example","app","0.0.1-SNAPSHOT");
File file = reg.download(pkg);
```

##List Artifacts 
List Artifacts only implemented for GitlabRegistrySvc
```
GitlabRegistrySvc reg = new GitlabRegistrySvc("https://gitlab.com/api/v4/projects/25819110/packages/maven","B8UxzhjZiBDJK51ZVH123","./repository" );
List<Package> packages = reg.getPackages(); // list all artifacts 
List<Package> packages = reg.getPackages("org.example"); // list all artifacts by groupId
List<Package> packages = reg.getPackages("org.example","app"); // list all artifacts by groupId.artifactId
```
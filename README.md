# ArtifactDownloader
Simple wrapper around org.eclipse.aether that list and download artifacts

## List Artifacts
```
GitlabRegistry reg = new GitlabRegistry("https://gitlab.com","R1dRsTrc6S4eXyWm1ti0","22122171");
List<Package> packages = reg.getPackages(); // list all artifacts 
List<Package> packages = reg.getPackages("org.example"); // list all artifacts by groupId
List<Package> packages = reg.getPackages("org.example","app"); // list all artifacts by groupId.artifactId
```

## Download Artifact
```
GitlabRegistry reg = new GitlabRegistry("https://gitlab.com","R1dRsTrc6S4eXyWm1ti0","22122171");
Package pkg = new Package("org.example","app","0.0.1-SNAPSHOT");
File file = reg.download(pkg);
```


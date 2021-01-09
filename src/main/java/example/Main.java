package example;

import ysj.GitlabRegistry;
import ysj.entities.Package;

import java.util.List;

public class Main {
    // to be removed
    public static void main(String[] args){
        GitlabRegistry reg = new GitlabRegistry("https://gitlab.com","R1dRsTrc6S4eXyWm1ti0","22122171");

        // get list of artifacts
        List<Package> packages = reg.getPackages();
        for(Package p : packages){
            System.out.println(p.getGroupId()+":"+p.getArtifactId()+":"+p.getVersion());
        }

        // download artifact
        System.out.println(reg.download(packages.get(0)).getAbsolutePath());
    }
}

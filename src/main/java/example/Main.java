package example;

import org.eclipse.aether.resolution.ArtifactResolutionException;
import ysj.entities.Package;
import ysj.GitlabRegistrySvc;

import java.util.List;

public class Main {
    // to be removed
    public static void main(String[] args) throws ArtifactResolutionException {
        GitlabRegistrySvc reg = new GitlabRegistrySvc("https://gitlab.com/api/v4/projects/25819110/packages/maven","B8UxzhjZiBDJK51ZVHxY","" );

        // get list of artifacts
        List<Package> packages = reg.getPackages();
        for(Package p : packages){
            System.out.println(p.getGroup()+":"+p.getArtifact()+":"+p.getVersion());
        }

        // download artifact
        System.out.println(reg.download(packages.get(0)).getAbsolutePath());
    }
}

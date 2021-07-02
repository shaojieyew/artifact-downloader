package ysj;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ysj.entities.Package;

public class MavenRegistrySvc extends AbstractRegistrySvc {

    public MavenRegistrySvc(String url, String username, String password,  String localRepository) {
        super(url, username,  password,  localRepository);
    }
    public MavenRegistrySvc(String url, String privateToken, String localRepository) {
        super(url,  privateToken, localRepository);
    }

    @Override
    public List<Package> getPackages() {
        return new ArrayList<>();
    }

    @Override
    public List<Package> getPackages(String group) {
        return new ArrayList<>();
    }

    @Override
    public List<Package> getPackages(String group, String artifact) {
        return new ArrayList<>();
    }

    @Override
    public Optional<Package> getPackage(String group, String artifact, String version) {
        return Optional.empty();
    }

}

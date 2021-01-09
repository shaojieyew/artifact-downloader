package ysj;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ysj.util.HttpService;
import ysj.util.Util;
import ysj.entities.Package;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GitlabRegistry extends Registry {
    final static Logger logger = Util.initLogger(GitlabRegistry.class);

    private String host;
    private String privateToken;
    private String projectId;

    public GitlabRegistry(String host, String privateToken, String projectId) {
        this.host = host;
        this.privateToken = privateToken;
        this.projectId = projectId;
    }

    @Override
    public List<Package> getPackages() {
        List<Package> packages = new ArrayList<>();
        String url = host+"/api/v4/projects/"+projectId+"/packages";
        String strResponse = "";
        HashMap<String,String> requestMap = new HashMap<String,String>();
        requestMap.put("content-type","application/json");
        requestMap.put("PRIVATE-TOKEN",privateToken);
        try {
            HttpURLConnection con = HttpService.getConnection( HttpService.HttpMethod.GET, url, requestMap, null);
            int statusCode = con.getResponseCode();
            strResponse = HttpService.inputStreamToString(con.getInputStream());
            if(statusCode != 200){
                throw new Exception(strResponse);
            }

            JSONParser parser = new JSONParser();
            JSONArray jsonPackages = (JSONArray)parser.parse(strResponse);

            for (Object obj: jsonPackages){
                JSONObject jsonPackage = (JSONObject)obj;
                Package artifactPackage = new Package();
                String name = jsonPackage.get("name").toString();
                artifactPackage.setGroupId(name.substring(0,name.lastIndexOf("/")));
                artifactPackage.setArtifactId(name.substring(name.lastIndexOf("/")+1));
                artifactPackage.setVersion(jsonPackage.get("version").toString());
                artifactPackage.setId(jsonPackage.get("id").toString());
                if(jsonPackage.get("package_type").toString().equals("maven")){
                    artifactPackage.setPackage_type(Package.PackageType.MAVEN);
                    packages.add(artifactPackage);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return packages;
    }

    @Override
    public List<Package> getPackages(String group) {
        return getPackages().stream().filter(pkg->pkg.getGroupId().equalsIgnoreCase(group)).collect(Collectors.toList());
    }


    @Override
    public List<Package> getPackages(String group, String artifact) {
        return getPackages().stream()
                .filter(pkg->(
                        pkg.getGroupId().equalsIgnoreCase(group) && pkg.getArtifactId().equalsIgnoreCase(artifact)
                )).collect(Collectors.toList());
    }
    @Override
    public File download(Package pkg) {
        ArtifactDownloader downloader = new ArtifactDownloader();
        downloader.setRemoteRepoUrl(host+"/api/v4/projects/"+projectId+"/packages/maven");
        Map<String, String> headers = new HashMap();
        headers.put("PRIVATE-TOKEN",privateToken);
        downloader.setHeaders(headers);
        downloader.setLocalRepoPath(localRepo);
        return downloader.download(pkg.getGroupId(), pkg.getArtifactId(),pkg.getVersion(),"","jar");
    }
}

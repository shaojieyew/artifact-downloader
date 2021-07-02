package ysj;

import common.http.HttpCallerClient;
import ysj.entities.Package;
import common.http.HttpCaller;
import common.http.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GitlabRegistrySvc extends AbstractRegistrySvc {

    public final static String type = "gitlab";
    private String host;
    private String projectId;

    public GitlabRegistrySvc(String url, String username, String password, String localRepository) {
        super(url, username,  password, localRepository);
        setupRepo(url);
    }

    public GitlabRegistrySvc(String url, String privateToken, String localRepository) {
        super(url, privateToken, localRepository);
        setupRepo(url);
    }

    public void setupRepo(String url){
        Matcher hostMatcher = Pattern.compile("^(http[s]?:\\/\\/)?[a-zA-Z0-9\\.:]+").matcher(url);
        Matcher projectIdMatcher = Pattern.compile("\\/projects\\/[0-9]+\\/").matcher(url);
        hostMatcher.find();
        projectIdMatcher.find();
        this.host =  hostMatcher.group(0);
        this.projectId = projectIdMatcher.group(0).replaceAll("\\D+","");
    }

    @Override
    public List<Package> getPackages() {
        List<Package> packages = new ArrayList<>();
        String url = host+"/api/v4/projects/"+projectId+"/packages";
        String strResponse = "";
        HashMap<String,String> requestMap = new HashMap();
        requestMap.put("content-type", MediaType.APPLICATION_JSON);
        if(getPrivateToken()!=null){
            requestMap.put("PRIVATE-TOKEN",getPrivateToken());
        }
        try {
            HttpCaller httpCaller = new HttpCallerClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("content-type",MediaType.APPLICATION_JSON);
            if(getPrivateToken()!=null) {
                httpGet.addHeader("PRIVATE-TOKEN", getPrivateToken());
            }
            HttpResponse response = httpCaller.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            strResponse = HttpUtil.httpEntityToString(response.getEntity());
            if(statusCode != 200){
                throw new Exception(strResponse);
            }

            JSONParser parser = new JSONParser();
            JSONArray jsonPackages = (JSONArray)parser.parse(strResponse);
            for(int i = 0 ;i < jsonPackages.size(); i ++){
                JSONObject jsonPackage = (JSONObject)jsonPackages.get(i);
                Package artifactPackage = new Package();
                String name = jsonPackage.get("name").toString();
                artifactPackage.setGroup(name.substring(0,name.lastIndexOf("/")));
                artifactPackage.setArtifact(name.substring(name.lastIndexOf("/")+1,name.length()));
                artifactPackage.setVersion(jsonPackage.get("version").toString());
                artifactPackage.setId(jsonPackage.get("id").toString());
                if(jsonPackage.get("package_type").toString().equals("maven")){
                    artifactPackage.setPackage_type(Package.PackageType.MAVEN);
                    packages.add(artifactPackage);
                }
            }
        } catch (Exception e) {
        }
        return packages;
    }

    @Override
    public List<Package> getPackages(String group) {
        return getPackages().stream().filter(pkg->pkg.getGroup().equalsIgnoreCase(group)).collect(Collectors.toList());
    }


    @Override
    public List<Package> getPackages(String group, String artifact) {
        return getPackages().stream()
                .filter(pkg->(
                        pkg.getGroup().equalsIgnoreCase(group) && pkg.getArtifact().equalsIgnoreCase(artifact)
                )).collect(Collectors.toList());
    }

    @Override
    public Optional<Package> getPackage(String group, String artifact, String version) {
        return getPackages().stream()
                .filter(pkg->(
                        pkg.getGroup().equalsIgnoreCase(group) && pkg.getArtifact().equalsIgnoreCase(artifact) && pkg.getVersion().equalsIgnoreCase(version)
                )).findFirst();
    }


}

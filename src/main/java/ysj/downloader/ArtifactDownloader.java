package ysj.downloader;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.ConfigurationProperties;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.repository.AuthenticationBuilder;
import org.eclipse.aether.util.repository.ConservativeAuthenticationSelector;
import org.eclipse.aether.util.repository.DefaultAuthenticationSelector;

import java.io.File;
import java.util.*;

public class ArtifactDownloader {
    private String serverId = null;

    private Map<String, String> headers = null;
    private String remoteRepoUrl = null;
    private String localRepoPath = null;

    private String username = null;
    private String password = null;
    private String privateKeyPath = null;
    private String passPhase = null;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRemoteRepoUrl() {
        return remoteRepoUrl;
    }

    public void setRemoteRepoUrl(String remoteRepoUrl) {
        this.remoteRepoUrl = remoteRepoUrl;
    }

    public String getLocalRepoPath() {
        return localRepoPath;
    }

    public void setLocalRepoPath(String localRepoPath) {
        this.localRepoPath = localRepoPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPassPhase() {
        return passPhase;
    }

    public void setPassPhase(String passPhase) {
        this.passPhase = passPhase;
    }

    public ArtifactDownloader(){
        serverId = Integer.toString(this.hashCode());
    }

    /**
     * download pkg specified by the params
     * @param groupId
     * @param artifactId
     * @param version
     * @param classifier
     * @param packaging
     * @return File of downloaded pkg
     */
    public File download(String groupId, String artifactId,
                         String version, String classifier, String packaging) throws ArtifactResolutionException {
        Authentication authentication = null;
        AuthenticationBuilder auth = new AuthenticationBuilder();
        if(username!=null)
            auth.addUsername(username);
        if(password!=null)
            auth.addPassword(password);
        if(privateKeyPath!=null)
            auth.addPrivateKey( privateKeyPath, Optional.of(passPhase).orElse(""));
        if(username!=null || password!=null || privateKeyPath!=null){
            authentication=auth.build();
        }

        RepositorySystem repositorySystem = newRepositorySystem();
        RepositorySystemSession session = newSession(repositorySystem,
                new File(localRepoPath), authentication);
        Artifact artifact = new DefaultArtifact(groupId, artifactId, classifier,
                packaging, version);
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(artifact);
        List<RemoteRepository> repositories = new ArrayList<>();
        RemoteRepository remoteRepository = new RemoteRepository.Builder(serverId,
                "default", remoteRepoUrl).setAuthentication(authentication).build();
        repositories.add(remoteRepository);
        artifactRequest.setRepositories(repositories);
        File result;
        ArtifactResult artifactResult = repositorySystem.resolveArtifact(session,
                artifactRequest);
        artifact = artifactResult.getArtifact();
        if (artifact != null)
        {
            result = artifact.getFile();
        }
        else
        {
            result = null;
        }
        return result;
    }

    private static RepositorySystem newRepositorySystem()
    {
        DefaultServiceLocator locator = MavenRepositorySystemUtils
                .newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class,
                BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
        return locator.getService(RepositorySystem.class);
    }

    private RepositorySystemSession newSession(RepositorySystem system,
                                               File localRepository, Authentication authentication)
    {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils
                .newSession();

        Map<Object, Object> configProps = new LinkedHashMap<>();
        if(headers!=null){
            configProps.put( ConfigurationProperties.HTTP_HEADERS + "."+serverId , Collections.unmodifiableMap(headers));
            session.setConfigProperties(configProps);
        }


        if(username!=null ||password!=null || privateKeyPath!=null){
            DefaultAuthenticationSelector selector = new DefaultAuthenticationSelector();
            selector.add(serverId,authentication);
            session.setAuthenticationSelector(new ConservativeAuthenticationSelector( selector ));
        }

        LocalRepository localRepo = new LocalRepository(localRepository.toString());
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session,
                localRepo));

        return session;
    }
}
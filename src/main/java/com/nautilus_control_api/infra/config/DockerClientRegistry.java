package com.nautilus_control_api.infra.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.nautilus_control_api.domain.model.entity.DockerHost;
import com.nautilus_control_api.domain.repository.DockerHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class DockerClientRegistry {
    private final DockerHostRepository dockerHostRepository;

    private final Map<UUID, DockerClient> clients = new ConcurrentHashMap<>();

    public DockerClient getClient(UUID hostId) {
        return clients.computeIfAbsent(hostId, this::buildClientForHost);
    }

    public void invalidate(UUID hostId) {
        DockerClient removed = clients.remove(hostId);
        closeQuietly(removed);
        cleanupCertDir(hostId);
    }

    public boolean testConnection(String dockerHost, boolean tlsEnabled, String caCert, String clientCert, String clientKey) {
        UUID tempId = UUID.randomUUID();
        DockerClient client = null;
        try {
            client = buildClient(dockerHost, tlsEnabled, caCert, clientCert, clientKey, tempId);
            client.pingCmd().exec();
            return true;
        } catch (RuntimeException e) {
            return false;
        } finally {
            closeQuietly(client);
            cleanupCertDir(tempId);
        }
    }

    private DockerClient buildClientForHost(UUID hostId) {
        DockerHost host = dockerHostRepository.findById(hostId)
                .filter(DockerHost::isActive)
                .orElseThrow(() -> new IllegalArgumentException("Host Docker nao encontrado ou inativo: " + hostId));

        return buildClient(host.getDockerHost(), host.isTlsEnabled(), host.getCaCert(), host.getClientCert(), host.getClientKey(), hostId);
    }

    private DockerClient buildClient(String dockerHost, boolean tlsEnabled, String caCert, String clientCert, String clientKey, UUID certDirId) {
        DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig
                .createDefaultConfigBuilder()
                .withDockerHost(dockerHost);

        if (tlsEnabled) {
            Path certDir = writeCertFiles(certDirId, caCert, clientCert, clientKey);
            configBuilder.withDockerTlsVerify(true)
                    .withDockerCertPath(certDir.toString());
        } else {
            configBuilder.withDockerTlsVerify(false);
        }

        DefaultDockerClientConfig dockerClientConfig = configBuilder.build();

        ApacheDockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .build();

        return DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerHttpClient(httpClient)
                .build();
    }

    private Path writeCertFiles(UUID id, String caCert, String clientCert, String clientKey) {
        try {
            Path dir = certDir(id);
            Files.createDirectories(dir);
            writeSecureFile(dir.resolve("ca.pem"), caCert);
            writeSecureFile(dir.resolve("cert.pem"), clientCert);
            writeSecureFile(dir.resolve("key.pem"), clientKey);
            return dir;
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao preparar certificados TLS do host " + id, e);
        }
    }

    private void writeSecureFile(Path path, String content) throws IOException {
        Files.writeString(path, content == null ? "" : content);
        try {
            Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rw-------"));
        } catch (UnsupportedOperationException ignored) {
            // sistema sem suporte a permissoes POSIX (ex.: Windows) - segue sem restringir
        }
    }

    private Path certDir(UUID id) {
        return Paths.get(System.getProperty("java.io.tmpdir"), "nautilus-certs", id.toString());
    }

    private void cleanupCertDir(UUID id) {
        Path dir = certDir(id);
        if (!Files.exists(dir)) {
            return;
        }
        try (var stream = Files.walk(dir)) {
            stream.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException ignored) {
                    // melhor esforco de limpeza
                }
            });
        } catch (IOException ignored) {
            // melhor esforco de limpeza
        }
    }

    private void closeQuietly(DockerClient client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (IOException ignored) {
            // melhor esforco ao fechar
        }
    }
}

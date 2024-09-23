package com.nautilus_control_api.infra.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {

    @Value("${docker.socket.path}")
    private String dockerSocketPath;

    @Bean
    public DockerClient buildDockerClient() {
        DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig
                .createDefaultConfigBuilder();

        if(this.dockerSocketPath != null && this.dockerSocketPath.startsWith("unix://")) {
            configBuilder.withDockerHost(this.dockerSocketPath)
                    .withDockerTlsVerify(false);
        }

        DefaultDockerClientConfig dockerClientconfig = configBuilder.build();

        ApacheDockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientconfig.getDockerHost())
                .sslConfig(dockerClientconfig.getSSLConfig())
                .build();

        return DockerClientBuilder.getInstance(dockerClientconfig)
                .withDockerHttpClient(httpClient)
                .build();
    }
}

package com.nautilus_control_api.infra.config;

import com.nautilus_control_api.domain.model.entity.DockerHost;
import com.nautilus_control_api.domain.repository.DockerHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DockerHostSeeder implements ApplicationRunner {
    private final DockerHostRepository dockerHostRepository;

    @Value("${docker.socket.path}")
    private String dockerSocketPath;

    @Override
    public void run(ApplicationArguments args) {
        if (dockerHostRepository.count() > 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DockerHost localHost = DockerHost.builder()
                .name("Local")
                .description("Docker Engine da maquina onde o Nautilus esta rodando")
                .dockerHost(dockerSocketPath)
                .tlsEnabled(false)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        dockerHostRepository.save(localHost);
    }
}

package com.nautilus_control_api.domain.service.impl;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.nautilus_control_api.domain.service.DockerService;
import com.nautilus_control_api.infra.config.DockerClientRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DockerServiceImpl implements DockerService {
    private final DockerClientRegistry dockerClientRegistry;

    @Override
    public List<Container> listContainers(UUID hostId, boolean showAll) {
        return dockerClientRegistry.getClient(hostId).listContainersCmd().withShowAll(showAll).exec();
    }

    @Override
    public List<Image> listImages(UUID hostId) {
        return dockerClientRegistry.getClient(hostId).listImagesCmd().exec();
    }

    @Override
    public List<Image> filterImages(UUID hostId, String imageName) {
        return dockerClientRegistry.getClient(hostId).listImagesCmd().withImageNameFilter(imageName).exec();
    }

    @Override
    public void createContainer(UUID hostId, String imageName) {
        dockerClientRegistry.getClient(hostId).createContainerCmd(imageName).exec();
    }

    @Override
    public void startContainer(UUID hostId, String containerId) {
        dockerClientRegistry.getClient(hostId).startContainerCmd(containerId).exec();
    }

    @Override
    public void stopContainer(UUID hostId, String containerId) {
        dockerClientRegistry.getClient(hostId).stopContainerCmd(containerId).exec();
    }

    @Override
    public void removeContainer(UUID hostId, String containerId) {
        dockerClientRegistry.getClient(hostId).removeContainerCmd(containerId).exec();
    }

    @Override
    public void restartContainer(UUID hostId, String containerId) {
        dockerClientRegistry.getClient(hostId).restartContainerCmd(containerId).exec();
    }

    @Override
    public void killContainer(UUID hostId, String containerId) {
        dockerClientRegistry.getClient(hostId).killContainerCmd(containerId).exec();
    }
}

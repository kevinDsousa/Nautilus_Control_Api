package com.nautilus_control_api.domain.service.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.nautilus_control_api.domain.service.DockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DockerServiceImpl implements DockerService {
    private final DockerClient dockerClient;

    @Override
    public List<Container> listContainers(boolean showAll) {
        return dockerClient.listContainersCmd().withShowAll(showAll).exec();
    }

    @Override
    public List<Image> listImages() {
        return dockerClient.listImagesCmd().exec();
    }

    @Override
    public List<Image> filterImages(String imageName) {
        return dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();
    }

    @Override
    public void createContainer(String imageName) {
        dockerClient.createContainerCmd(imageName).exec();
    }

    @Override
    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }

    @Override
    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    @Override
    public void removeContainer(String containerId) {
        dockerClient.removeContainerCmd(containerId).exec();
    }

    @Override
    public void restartContainer(String containerId) {
        dockerClient.restartContainerCmd(containerId).exec();
    }

    @Override
    public void killContainer(String containerId) {
        dockerClient.killContainerCmd(containerId).exec();
    }
}

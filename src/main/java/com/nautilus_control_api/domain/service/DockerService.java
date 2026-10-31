package com.nautilus_control_api.domain.service;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;

import java.util.List;
import java.util.UUID;

public interface DockerService {
    List<Container> listContainers(UUID hostId, boolean showAll);
    List<Image> listImages(UUID hostId);
    List<Image> filterImages(UUID hostId, String imageName);
    void createContainer(UUID hostId, String imageName);
    void startContainer(UUID hostId, String containerId);
    void stopContainer(UUID hostId, String containerId);
    void removeContainer(UUID hostId, String containerId);
    void restartContainer(UUID hostId, String containerId);
    void killContainer(UUID hostId, String containerId);
}

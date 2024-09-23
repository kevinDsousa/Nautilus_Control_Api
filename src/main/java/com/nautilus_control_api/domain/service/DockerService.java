package com.nautilus_control_api.domain.service;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;

import java.util.List;

public interface DockerService {
    List<Container> listContainers(boolean showAll);
    List<Image> listImages();
    List<Image> filterImages(String imageName);
    void createContainer(String imageName);
    void startContainer(String containerId);
    void stopContainer(String containerId);
    void removeContainer(String containerId);
    void restartContainer(String containerId);
    void killContainer(String containerId);
}

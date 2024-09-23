package com.nautilus_control_api.domain.controller;


import com.github.dockerjava.api.model.Container;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DockerContainersController {
    List<Container> listContainers(boolean showAll);
    void startContainer(@PathVariable String id);
    void restartContainer(@PathVariable String id);
    void stopContainer(@PathVariable String id);
    void killContainer(@PathVariable String id);
    void removeContainer(@PathVariable String id);
    void createContainer(@RequestParam String imageName);
}

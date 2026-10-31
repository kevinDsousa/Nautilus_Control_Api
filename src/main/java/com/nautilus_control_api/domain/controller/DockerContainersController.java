package com.nautilus_control_api.domain.controller;


import com.github.dockerjava.api.model.Container;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface DockerContainersController {
    List<Container> listContainers(@PathVariable UUID hostId, boolean showAll);
    void startContainer(@PathVariable UUID hostId, @PathVariable String id);
    void restartContainer(@PathVariable UUID hostId, @PathVariable String id);
    void stopContainer(@PathVariable UUID hostId, @PathVariable String id);
    void killContainer(@PathVariable UUID hostId, @PathVariable String id);
    void removeContainer(@PathVariable UUID hostId, @PathVariable String id);
    void createContainer(@PathVariable UUID hostId, @RequestParam String imageName);
}

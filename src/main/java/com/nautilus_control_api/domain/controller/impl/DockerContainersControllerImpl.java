package com.nautilus_control_api.domain.controller.impl;

import com.github.dockerjava.api.model.Container;
import com.nautilus_control_api.domain.controller.DockerContainersController;
import com.nautilus_control_api.domain.service.DockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker/hosts/{hostId}/containers")
public class DockerContainersControllerImpl implements DockerContainersController {
    private final DockerService dockerService;

    @Override
    @GetMapping("/list")
    public List<Container> listContainers(@PathVariable UUID hostId, @RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return dockerService.listContainers(hostId, showAll);
    }

    @Override
    @PostMapping("/{id}/start")
    public void startContainer(@PathVariable UUID hostId, @PathVariable String id) {
        dockerService.startContainer(hostId, id);
    }

    @Override
    @PutMapping("/{id}/stop")
    public void stopContainer(@PathVariable UUID hostId, @PathVariable String id) {
        dockerService.stopContainer(hostId, id);
    }

    @Override
    @PutMapping("/{id}/restart")
    public void restartContainer(@PathVariable UUID hostId, @PathVariable String id) {
        dockerService.restartContainer(hostId, id);
    }

    @Override
    @PutMapping("/{id}/kill")
    public void killContainer(@PathVariable UUID hostId, @PathVariable String id) {
        dockerService.killContainer(hostId, id);
    }

    @Override
    @DeleteMapping("/{id}/remove")
    public void removeContainer(@PathVariable UUID hostId, @PathVariable String id) {
        dockerService.removeContainer(hostId, id);
    }

    @Override
    @PostMapping("/create")
    public void createContainer(@PathVariable UUID hostId, @RequestParam String imageName) {
        dockerService.createContainer(hostId, imageName);
    }
}

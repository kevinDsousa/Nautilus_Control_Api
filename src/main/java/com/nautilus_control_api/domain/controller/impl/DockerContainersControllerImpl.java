package com.nautilus_control_api.domain.controller.impl;

import com.github.dockerjava.api.model.Container;
import com.nautilus_control_api.domain.controller.DockerContainersController;
import com.nautilus_control_api.domain.service.DockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker/containers")
public class DockerContainersControllerImpl implements DockerContainersController {
    private final DockerService dockerService;

    @Override
    @GetMapping("/list")
    public List<Container> listContainers(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return dockerService.listContainers(showAll);
    }

    @Override
    @PostMapping("/{id}/start")
    public void startContainer(@PathVariable String id) {
        dockerService.startContainer(id);
    }

    @Override
    @PutMapping("/{id}/stop")
    public void stopContainer(@PathVariable String id) {
        dockerService.stopContainer(id);
    }

    @Override
    @PutMapping("/{id}/restart")
    public void restartContainer(@PathVariable String id) {
        dockerService.restartContainer(id);
    }

    @Override
    @PutMapping("/{id}/kill")
    public void killContainer(@PathVariable String id) {
        dockerService.killContainer(id);
    }

    @Override
    @DeleteMapping("/{id}/remove")
    public void removeContainer(@PathVariable String id) {
        dockerService.removeContainer(id);
    }

    @Override
    @PostMapping("/create")
    public void createContainer(@RequestParam String imageName) {
        dockerService.createContainer(imageName);
    }
}

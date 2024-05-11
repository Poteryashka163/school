package ru.hogwarts.school.controlle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getPort() {
        return serverPort;
    }
    @GetMapping("/sum")
    public int getSum() {
        int n = 1_000_000;
        return (n * (n + 1)) / 2;
    }

}

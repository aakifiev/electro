package electro.controllers;

import electro.model.Man;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ManController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/getMan")
    public Man getMan(@RequestParam(value="name", defaultValue = "name") String name){
        return new Man(counter.incrementAndGet() + "", name);
    }
}

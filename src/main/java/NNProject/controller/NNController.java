package NNProject.controller;

import NNProject.service.RabbitNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/nnproject")
public class NNController {
    @Autowired
    RabbitNameService rabbitNameService;

    @RequestMapping("/")
    public ArrayList<String> loadPets(@RequestParam(value = "location", defaultValue = "virginia") String location) {

        ArrayList<String> rl = rabbitNameService.makeList(location, "100");


        return rl;
    }

}

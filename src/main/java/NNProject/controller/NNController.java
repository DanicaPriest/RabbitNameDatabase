package NNProject.controller;

import NNProject.model.Rabbit;
import NNProject.model.User;
import NNProject.service.RabbitNameService;
import NNProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/nnproject")
public class NNController {
    @Autowired
    RabbitNameService rabbitNameService;
    @Autowired
    UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByName(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUsername());
        modelAndView.addObject("adminMessage","You are awesome!!");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


    @RequestMapping("/load")
    public ArrayList<String> loadPets(@RequestParam(value = "location", defaultValue = "virginia") String location) {

        ArrayList<String> rl = rabbitNameService.makeList(location, "100");


        return rl;
    }

    @RequestMapping("/clean")
    public ArrayList<Rabbit> cleanDB(){
        return rabbitNameService.cleanDB();
    }

}
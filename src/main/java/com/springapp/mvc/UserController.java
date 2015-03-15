package com.springapp.mvc;

import com.springapp.mvc.models.User;
import com.springapp.mvc.repositories.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userRepository.findAll());
        return "user/users";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult result) {

        userRepository.save(user);

        return "redirect:/";
    }

    @RequestMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {

        userRepository.delete(userRepository.findOne(userId));

        return "redirect:/";
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public  @ResponseBody String listUsersJson(ModelMap model) throws JSONException {
        JSONArray userArray = new JSONArray();
        for(User user : userRepository.findAll()) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", user.getId());
            userJson.put("firstName", user.getFirstName());
            userJson.put("email", user.getEmail());
            userJson.put("lastName", user.getLastName());
            userArray.put(userJson);
        }
        return userArray.toString();
    }
}
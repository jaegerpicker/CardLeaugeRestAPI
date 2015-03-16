package com.springapp.mvc.controllers;

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

    @RequestMapping(value = "/api/user/", method = RequestMethod.POST)
    public @ResponseBody String addUserJson(@ModelAttribute("user") User user, BindingResult result) throws JSONException {
        userRepository.save(user);
        JSONObject userJson = new JSONObject();
        userJson.put("id", user.getId());
        userJson.put("firstName", user.getFirstName());
        userJson.put("lastName", user.getLastName());
        userJson.put("email", user.getEmail());
        return userJson.toString();
    }

    @RequestMapping(value = "/api/user/", method = RequestMethod.PUT)
    public @ResponseBody String updateUserJson(@ModelAttribute("user") User user, BindingResult result) throws JSONException {
        User updateUser = userRepository.findOne(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        userRepository.save(updateUser);
        JSONObject userJson = new JSONObject(updateUser.toString());
        return userJson.toString();
    }

    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.DELETE)
    public String deleteUserJson(@PathVariable("userId") Long userId) throws JSONException {
        userRepository.delete(userRepository.findOne(userId));
        return "redirect:/api/users/";
    }

    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.GET)
    public @ResponseBody String getUser(@PathVariable("userId") Long userId, BindingResult result) throws JSONException {
        User user = userRepository.findOne(userId);
        JSONObject userJson = new JSONObject(user.toString());
        return userJson.toString();
    }
}
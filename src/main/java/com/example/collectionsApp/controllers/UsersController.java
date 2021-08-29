package com.example.collectionsApp.controllers;

import com.example.collectionsApp.models.CollectionItem;
import com.example.collectionsApp.models.User;
import com.example.collectionsApp.service.cloudinary.CloudinaryService;
import com.example.collectionsApp.service.CollectionService;
import com.example.collectionsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CloudinaryService cloudinaryService;

    private String nameUserPage;

    private String keySearch = "";

    @GetMapping("/{nameUser}")
    public String userPage(@PathVariable("nameUser") String name, @ModelAttribute("collection") CollectionItem collection, Model model) {
        nameUserPage = name;
        if(name.equals(userService.getAuthenticationName()) || userService.isAdmin()){
            model.addAttribute("isAuthentication", userService.isAuthentication());
            model.addAttribute("isAdmin", userService.isAdmin());
            model.addAttribute("authenticationName", userService.getAuthenticationName());
            model.addAttribute("user", userService.findByName(name));

            if(keySearch.replaceAll("\\s+","").length()!=0) {
                model.addAttribute("collections", collectionService.searchAllCollections(keySearch));
                model.addAttribute("showAll", true);
            }
            else {
                model.addAttribute("collections", collectionService.findAllSortByUserId(userService.findByName(name).getId()));
                model.addAttribute("showAll", false);
            }

            return "usersPage";
        }
       return "redirect:/main";
    }

    @PostMapping("/addCollection")
    public String addCollection(@ModelAttribute("collection") @Valid CollectionItem collection,
                                BindingResult bindingResult,
                                @RequestParam("file") MultipartFile file,
                                Model model){

        if(bindingResult.hasErrors()){
            return "redirect:/users/"+nameUserPage;
        }

        String public_id = cloudinaryService.upload(file, nameUserPage, collection.getName(), "null");
        if(public_id != null)
            collection.setUrl(public_id);
        else
            collection.setUrl(null);

        collectionService.add(collection, nameUserPage);
        return "redirect:/users/"+nameUserPage;
    }


    @PostMapping(value = "/changeCollection", params = "action=deleteCollection")
    public String deleteCollection(@RequestParam("id") Long id){

        collectionService.deleteById(id);

        return "redirect:/users/"+nameUserPage;
    }


    @PostMapping(value = "/changeCollection", params = "action=changeCollection")
    public String changeCollection(@RequestParam("id") Long id,
                                   @RequestParam("name") String name,
                                   @RequestParam("description") String description,
                                   @RequestParam("file") MultipartFile file){

        CollectionItem collection = collectionService.findById(id);
        collection.setName(name);
        collection.setDescription(description);

        String public_id = cloudinaryService.upload(file, nameUserPage, collection.getName(), "null");
        if(public_id != null)
            collection.setUrl(public_id);

        collectionService.changeCollection(collection);

        return "redirect:/users/"+nameUserPage;
    }

    @PostMapping(value = "/editUser", params = "action=editProfile")
    public String editProfile(@RequestParam("id")Long id,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("email") String email){

        User user = userService.findByName(nameUserPage);

        String public_id = cloudinaryService.upload(file, nameUserPage, "null", "null");
        if(public_id != null){
            user.setUrl(public_id);
        }
        user.setEmail(email);
        userService.changeUser(user);
        return "redirect:/users/"+nameUserPage;
    }

    @PostMapping(value = "/search", params = "action=search")
    public String deleteItem(@RequestParam("key") String key){
        keySearch = key;
        return "redirect:/users/"+nameUserPage;
    }


}



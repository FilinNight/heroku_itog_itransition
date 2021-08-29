package com.example.collectionsApp.controllers;

import com.example.collectionsApp.models.Item;
import com.example.collectionsApp.service.CollectionService;
import com.example.collectionsApp.service.ItemService;
import com.example.collectionsApp.service.comment.CommentItemService;
import com.example.collectionsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class MainPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CommentItemService commentItemService;

    private boolean flagShowCollectionsAll = true;

    private String keySearch = "";


    @GetMapping("/main")
    public String main(@ModelAttribute("item") Item item, Model model){
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("authenticationName", userService.getAuthenticationName());
        model.addAttribute("isAdmin", userService.isAdmin());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("collectionsTop", collectionService.findTop());
        model.addAttribute("flagShowCollectionsAll", flagShowCollectionsAll);

        if(keySearch.replaceAll("\\s+","").length()!=0){
            model.addAttribute("allCollections", collectionService.searchAllCollections(keySearch));
            model.addAttribute("items", itemService.searchAllItems(keySearch));
            model.addAttribute("showAll", true);
        }
        else {
            model.addAttribute("items", itemService.findAllSortByDate());
            model.addAttribute("allCollections", collectionService.findAllSort());
            model.addAttribute("showAll", false);
        }

        return "main";
    }



    @PostMapping(value = "/main", params = "action=changeCollectionsAll")
    public String changeFlagCollectionsAll(){
        if(flagShowCollectionsAll)
            flagShowCollectionsAll = false;
        else
            flagShowCollectionsAll = true;
        return "redirect:/main";
    }

    @PostMapping(value = "/main", params = "action=search")
    public String search(@RequestParam("key") String key){
        keySearch = key;
        return "redirect:/main";
    }

    @PostMapping("/main/addCommentItem")
    public String addCommentItem(@RequestParam("message")String message,
                                 @RequestParam("id_item")Long id_item){
        commentItemService.add(message, id_item);
        return "redirect:/main";
    }


}

package com.example.collectionsApp.controllers;

import com.example.collectionsApp.models.Item;

import com.example.collectionsApp.service.cloudinary.CloudinaryService;
import com.example.collectionsApp.service.CollectionService;
import com.example.collectionsApp.service.ItemService;
import com.example.collectionsApp.service.comment.CommentCollectionService;
import com.example.collectionsApp.service.comment.CommentItemService;
import com.example.collectionsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/collections")
public class CollectionsController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private CommentItemService commentItemService;
    @Autowired
    private CommentCollectionService commentCollectionService;

    private Long id_collection;

    private String keySearch = "";

    @GetMapping("/{id}")
    public String showPageCollection(@PathVariable("id") Long id, @ModelAttribute("item") @Valid Item item, BindingResult bindingResult,
                                     Model model){
        id_collection = id;
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("isAdmin", userService.isAdmin());
        model.addAttribute("authenticationName", userService.getAuthenticationName());
        model.addAttribute("nameUserCollection", collectionService.findById(id).getUser().getUsername());
        model.addAttribute("collection", collectionService.findById(id));

        if(keySearch.replaceAll("\\s+","").length()!=0) {
            model.addAttribute("items", itemService.searchAllByIdCollection(keySearch, id_collection));
            model.addAttribute("showAll", true);
        }
        else {
            model.addAttribute("items", itemService.findAllSortDataByCollectionId(id));
            model.addAttribute("showAll", false);
        }
        return "collectionPage";
    }

    @PostMapping(value = "/{id}", params = "action=add")
    public String addItem(@PathVariable("id") Long id,
                          @ModelAttribute("item") @Valid Item item,
                          BindingResult bindingResult,
                          @RequestParam("file")MultipartFile file){

        if(bindingResult.hasErrors()){
            return "redirect:/collections/"+id;
        }

        String public_id = cloudinaryService.upload(file, collectionService.findById(id).getUser().getUsername(), collectionService.findById(id).getName(), item.getName());
        if(public_id != null)
            item.setUrl(public_id);

        itemService.add(item, collectionService.findById(id));
        return "redirect:/collections/"+id;
    }

    @PostMapping(value = "/changeItem", params = "action=deleteItem")
    public String deleteItem(@RequestParam("item_id") Long item_id){

        itemService.deleteById(item_id);
        return "redirect:/collections/"+id_collection;
    }


    @PostMapping(value = "/changeItem", params = "action=changeItem")
    public String changeItem(@RequestParam("item_id") Long item_id,
                             @RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("file") MultipartFile file){

        Item item = itemService.findById(item_id);
        item.setName(name);
        item.setDescription(description);

        String public_id = cloudinaryService.upload(file, item.getCollection().getUser().getUsername(), item.getCollection().getName(), item.getName());

        if(public_id != null)
            item.setUrl(public_id);

        itemService.changeItem(item);

        return "redirect:/collections/"+id_collection;
    }

    @PostMapping("/addCommentItem")
    public String addCommentItem(@RequestParam("message")String message,
                                 @RequestParam("id_item")Long id_item){

        commentItemService.add(message, id_item);
        return "redirect:/collections/"+id_collection;
    }

    @PostMapping("/addCommentCollection")
    public String addCommentCollection(@RequestParam("message")String message){

        commentCollectionService.add(message, id_collection);
        return "redirect:/collections/"+id_collection;
    }

    @PostMapping(value = "/search", params = "action=search")
    public String deleteItem(@RequestParam("key") String key){
        keySearch = key;
        return "redirect:/collections/"+id_collection;
    }

}

package me.matrix89.markpages.controller;

import me.matrix89.markpages.data.model.TagModel;
import me.matrix89.markpages.data.repository.PageRepository;
import me.matrix89.markpages.data.repository.TagRepository;
import me.matrix89.markpages.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    //It's formatted in such a way because i use it only in editor.html to create autocomplete chips
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> allTags() {
        Iterable<TagModel> a = tagRepository.findAll();
        Map<String, String> list = new HashMap<>();
        a.forEach(tagModel -> list.put(tagModel.getName(), null));
        return list;
    }//TODO https://github.com/showdownjs/showdown/wiki/Showdown's-Markdown-syntax#ordered-lists <- To repair

    @RequestMapping(value = "/p/{stringId}", method = RequestMethod.GET)
    //TODO HashMap not gonna work with 2 or more keys "key"
    public @ResponseBody
    Map<String, String> pageTags(@PathVariable("stringId") String stringId) {
        Iterable<TagModel> a = pageRepository.findAllByStringId(stringId).getTags();
        Map<String, String> list = new HashMap<>();
        a.forEach(tagModel -> list.put("tag", tagModel.getName()));
        return list;
    }

}

package me.matrix89.markpages.controller;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Index {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    Logger logger;

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("pages", pageRepository.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("pages", pageRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        logger.warn("Using mapping from Index controller!!!");//TODO Żeby działało w AdminController
        return "admin";
    }

    @GetMapping("/editor")
    public String editor() {
        return "editor";
    }

    @GetMapping("/edit/{a}")
    public String edit(@PathVariable String a, Model model) {
        PageModel p = pageRepository.getByName(a);
        if(a == null){
            return "redirect;/editor";
        }
        model.addAttribute("name", p.getName());
        model.addAttribute("content", p.getContent());
        return "editor";

    }

    @RequestMapping("/add")
    public String add(@RequestParam String name, @RequestParam String mdPage) {
        PageModel p = new PageModel();
        p.setName(name);
        p.setContent(mdPage);
        System.out.println(mdPage);
        pageRepository.save(p);
        return String.format("redirect:%s", name);
    }

    @RequestMapping("/update")
    public String update(@RequestParam String name, @RequestParam String mdPage){
        PageModel m = pageRepository.getByName(name);
        m.setContent(mdPage);
        pageRepository.save(m);
        return String.format("redirect:%s", name);
    }

    @GetMapping("/{a}")
    public String mdPage(@PathVariable String a, Model model) {
        PageModel p = pageRepository.getByName(a);
        if (p == null){
            return "redirect:/";
        }
        model.addAttribute("page_title", p.getName());
        model.addAttribute("content", p.getContent());
        return "mdPage";
    }

}

package me.matrix89.markpages.controller;

import me.matrix89.markpages.Util;
import me.matrix89.markpages.data.model.PageMaintainerModel;
import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.TagModel;
import me.matrix89.markpages.data.model.UserModel;
import me.matrix89.markpages.data.repository.PageMaintainerRepository;
import me.matrix89.markpages.data.repository.PageRepository;
import me.matrix89.markpages.data.repository.TagRepository;
import me.matrix89.markpages.data.repository.UserRepository;
import me.matrix89.markpages.service.EditService;
import me.matrix89.markpages.service.PermissionService;
import me.matrix89.markpages.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class EditorController {

    private PageRepository pageRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private PageMaintainerRepository pageMaintainerRepository;
    private EditService editService;
    private TagService tagService;
    private PermissionService permissionService;

    public EditorController(PageRepository pageRepository,
                            UserRepository userRepository,
                            TagRepository tagRepository,
                            PageMaintainerRepository pageMaintainerRepository,
                            EditService editService,
                            TagService tagService,
                            PermissionService permissionService) {
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.pageMaintainerRepository = pageMaintainerRepository;
        this.editService = editService;
        this.tagService = tagService;
        this.permissionService = permissionService;
    }

    @GetMapping("/edit")
    public String editor(@RequestParam(defaultValue = "ace") String e, Model model) {
        if (e.equals("cke")) {
            return "editor-cke";
        } else if (e.equals("ace")) {
            model.addAttribute("tags", tagRepository.findAll());
            return "editor";
        }

        return "editor";
    }

    @GetMapping("/edit/{stringId}")
    public String edit(@PathVariable String stringId, Model model, Principal principal) {
        PageModel page = pageRepository.findAllByStringId(stringId);
        UserModel user = userRepository.getByUsername(principal.getName());
        Set<TagModel> tags = page.getTags();
        tags.forEach(t -> t.setPages(null));
        if (user == null || page == null)
            return "redirect:/editor";
        if (permissionService.canEdit(user, page)) {
            model.addAttribute("visibility", page.getVisibility());
            model.addAttribute("page", page);
            model.addAttribute("pageTags", tags);
            return "editor";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/add")
    public String add(@RequestParam String pageName, @RequestParam String pageContent,
                      @RequestParam PageModel.Visibility visibility,
                      @RequestParam List<String> tags,
                      Principal principal) {
        PageModel page = new PageModel();
        PageMaintainerModel pm = new PageMaintainerModel();
        page.setName(pageName);
        page.setContent(pageContent);
        page.setCreationDate(new Date());
        page.setLastEdited(new Date());
        page.setVisibility(visibility);
        page.setStringId(Util.randomString(8));
        pm.setPage(page);
        pm.setUser(userRepository.getByUsername(principal.getName()));
        pm.setRole(PageMaintainerModel.Role.OWNER);
        tagService.addTags(tags, page);
        pageRepository.save(page);
        pageMaintainerRepository.save(pm);
        return String.format("redirect:/p/%s", page.getStringId());
    }

    @RequestMapping("/update")
    public String update(@RequestParam String stringId, @RequestParam String pageContent,
                         @RequestParam PageModel.Visibility visibility, @RequestParam String pageName,
                         @RequestParam List<String> tags,
                         Principal principal) {
        PageModel page = pageRepository.findAllByStringId(stringId);
        UserModel user = userRepository.getByUsername(principal.getName());
        if (principal != null && page != null && user != null &&
                permissionService.canEdit(user, page)) {
            page.setName(pageName);
            page.setContent(pageContent);
            page.setVisibility(visibility);
            page.setLastEdited(new Date());
            tagService.addTags(tags, page);
            pageRepository.save(page);
        }
        return String.format("redirect:/p/%s", stringId);
    }

}

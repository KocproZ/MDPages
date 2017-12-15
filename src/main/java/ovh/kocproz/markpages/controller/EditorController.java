package ovh.kocproz.markpages.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.TagModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.markpages.data.repository.PageRepository;
import ovh.kocproz.markpages.data.repository.TagRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.service.EditService;
import ovh.kocproz.markpages.service.PermissionService;
import ovh.kocproz.markpages.service.TagService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/edit")
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

    @GetMapping("")
    public String editor() {
        return "editor";
    }

    @GetMapping("/{stringId}")
    public String edit(@PathVariable String stringId, Model model, Authentication auth) {
        PageModel page = pageRepository.findOneByStringId(stringId);
        UserModel user = userRepository.getByUsername(auth.getName());
        Set<TagModel> tags = null;
        if (user == null || page == null) return "redirect:/edit";
        if (page != null) {
            tags = page.getTags();
            tags.forEach(t -> t.setPages(null));
        }
        if (permissionService.canEdit(user, page)) {
            model.addAttribute("visibility", page.getVisibility());
            model.addAttribute("page", page);
            model.addAttribute("pageTags", tags);
            return "editor";
        } else {
            return String.format("redirect:/p/%s", stringId);
        }
    }

    @PostMapping("/add")
    public String add(@RequestParam(name = "name") String pageName,
                      @RequestParam(name = "pageContent") String pageContent,
                      @RequestParam(name = "visibility") Visibility visibility,
                      @RequestParam(name = "tags") List<String> tags,
                      @RequestParam(name = "users") List<String> users,
                      Principal principal) {
        PageModel page = editService.addPage(pageName, pageContent, visibility, tags);

        editService.setOwner(page, principal.getName());
        editService.setMaintainers(users, page);

        return String.format("redirect:/p/%s", page.getStringId());
    }

    @PostMapping("/update")
    public String update(@RequestParam(name = "stringId") String code,
                         @RequestParam(name = "name") String pageName,
                         @RequestParam(name = "pageContent") String pageContent,
                         @RequestParam(name = "visibility") Visibility visibility,
                         @RequestParam(name = "tags") List<String> tags,
                         @RequestParam(name = "users", required = false) List<String> users,
                         Principal principal) {
        UserModel user = userRepository.getByUsername(principal.getName());
        PageModel page = pageRepository.findOneByStringId(code);

        if (pageRepository.exists(code) && user != null &&
                permissionService.canEdit(user, pageRepository.findOneByStringId(code))) {
            editService.updatePage(pageName, pageContent, visibility, tags, code);
            if (permissionService.getRole(code, user.getUsername()) == PageMaintainerModel.Role.OWNER && users != null)
                editService.setMaintainers(users, page);
        }

        return String.format("redirect:/p/%s", code);
    }


}

package ovh.kocproz.markpages.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ovh.kocproz.markpages.data.dto.PageFormDTO;
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

import javax.validation.Valid;
import java.security.Principal;
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
    public String editor(PageFormDTO pageFormDTO) {
        return "editor";
    }

    @GetMapping("/{code}")
    public String edit(@Valid PageFormDTO formData,
                       BindingResult bindingResult,
                       @PathVariable String code,
                       Model model,
                       Authentication auth) {
        PageModel page = pageRepository.findOneByCode(code);
        UserModel user = userRepository.getByUsername(auth.getName());
        Set<TagModel> tags = null;
        if (user == null || page == null) return "redirect:/edit";
        if (page != null) {
            tags = page.getTags();
            tags.forEach(t -> t.setPages(null));
        }
        if (permissionService.canEdit(user, page)) {
            formData.setTitle(page.getName());
            formData.setCode(page.getCode());
            model.addAttribute("visibility", page.getVisibility());
            model.addAttribute("page", page);
            model.addAttribute("pageTags", tags);
            return "editor";
        } else {
            return String.format("redirect:/p/%s", code);
        }
    }

    @PostMapping("/add")
    public String add(@Valid PageFormDTO formData,
                      BindingResult bindingResult,
                      Principal principal) {
        if (bindingResult.hasErrors())
            return "redirect:/edit";

        PageModel page = editService.addPage(formData.getTitle(),
                formData.getContent(), formData.getVisibility(), formData.getTags());

        editService.setOwner(page, principal.getName());
        editService.setMaintainers(formData.getUsers(), page);

        return String.format("redirect:/p/%s", page.getCode());
    }

    @PostMapping("/update")
    public String update(@Valid PageFormDTO formData,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors())
            return "redirect:/edit";
        UserModel user = userRepository.getByUsername(principal.getName());
        PageModel page = pageRepository.findOneByCode(formData.getCode());

        if (pageRepository.exists(formData.getCode()) && user != null &&
                permissionService.canEdit(user, pageRepository.findOneByCode(formData.getCode()))) {
            editService.updatePage(formData.getTitle(),
                    formData.getContent(), formData.getVisibility(), formData.getTags(), formData.getCode());
            if (permissionService.getRole(formData.getCode(), user.getUsername()) == PageMaintainerModel.Role.OWNER)
                editService.setMaintainers(formData.getUsers(), page);
        }

        return String.format("redirect:/p/%s", formData.getCode());
    }


}

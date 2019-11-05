package ovh.kocproz.mdpages.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.dto.PageFormDTO;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.TagModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.mdpages.page.repository.PageRepository;
import ovh.kocproz.mdpages.data.repository.TagRepository;
import ovh.kocproz.mdpages.data.repository.UserRepository;
import ovh.kocproz.mdpages.exception.NoPermissionException;
import ovh.kocproz.mdpages.service.EditService;
import ovh.kocproz.mdpages.service.PermissionService;
import ovh.kocproz.mdpages.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Set;

//@Controller
@RequestMapping("/edit")
public class EditController {

    private PageRepository pageRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private PageMaintainerRepository pageMaintainerRepository;
    private EditService editService;
    private TagService tagService;
    private PermissionService permissionService;

    public EditController(PageRepository pageRepository,
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
    public String editor(Model model, PageFormDTO pageFormDTO) {
        model.addAttribute("visibility", false);
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

        tags = page.getTags();
        tags.forEach(t -> t.setPages(null));

        if (permissionService.canEdit(user, page)) {
            formData.setTitle(page.getTitle());
            formData.setCode(page.getCode());
            model.addAttribute("visibility", page.getVisibility() == Visibility.AUTHORIZED);
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
            if (permissionService.hasFullEditPermissions(formData.getCode(), user))
                editService.setMaintainers(formData.getUsers(), page);
        }

        return String.format("redirect:/p/%s", formData.getCode());
    }

    @PostMapping("/delete")
    public String delete(@Valid @NotNull @RequestParam String code, Principal principal) {
        if (principal == null || !permissionService.hasFullEditPermissions(code, principal.getName()))
            throw new NoPermissionException();
        editService.deletePage(code);
        return "index";
        //TODO some success message
    }

}

package me.matrix89.markpages.controller;

import me.matrix89.markpages.data.model.PageMaintainerModel;
import me.matrix89.markpages.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

/**
 * @author Hubertus
 * Created 27.10.2017
 */
@Controller
public class PageManagerController {

    private PermissionService permissionService;

    public PageManagerController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/manage/{stringId}")
    public String manage(@PathVariable String stringId, Model model, Principal principal) {
        PageMaintainerModel maintainerModel = permissionService.getRole(stringId, principal.getName());
        if (maintainerModel == null || maintainerModel.getPage() == null || maintainerModel.getUser() == null)
            return "redirect:/";
        model.addAttribute("user", maintainerModel.getUser());
        model.addAttribute("page", maintainerModel.getPage());
        model.addAttribute("role", maintainerModel.getRole());
        return "pageManager";
    }

}

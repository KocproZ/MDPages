package ovh.kocproz.mdpages.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ovh.kocproz.mdpages.MultipartFileSender;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.dto.FileUploadDTO;
import ovh.kocproz.mdpages.data.model.FileModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.UserRepository;
import ovh.kocproz.mdpages.exception.FileTooLargeException;
import ovh.kocproz.mdpages.exception.NotFoundException;
import ovh.kocproz.mdpages.service.FileService;
import ovh.kocproz.mdpages.service.PermissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
//@Controller
@RequestMapping("/files")
public class FilesController {

    private final PermissionService permissionService;
    private final UserRepository userRepository;
    private final FileService fileService;

    public FilesController(PermissionService permissionService,
                           UserRepository userRepository,
                           FileService fileService) {
        this.permissionService = permissionService;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    @GetMapping("")
    public String files(Model m, Authentication auth) {
        if (auth != null)
            m.addAttribute("files", fileService.getFiles());
        else
            m.addAttribute("files", fileService.getFiles(Visibility.PUBLIC));
        return "files/list";
    }

    @GetMapping("/upload")
    public String upload(FileUploadDTO fileUploadDTO) {
        return "files/upload";
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public String postUpload(@Valid FileUploadDTO formData,
                             BindingResult bindingResult,
                             Authentication auth) {
        if (bindingResult.hasErrors())
            return "files/upload";
        UserModel user = userRepository.getByUsername(auth.getName());
        if (permissionService.canUpload(user)) {
            try {
                String fileCode = fileService.saveFile(formData.getFile(),
                        formData.getName(), user, formData.getVisibility()).getCode();
                return "redirect:/files/" + fileCode;
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/files/upload?error";
            } catch (FileTooLargeException e) {
                return "redirect:/files/upload?fileTooLarge";
            }
        }
        return "redirect:/files/upload?noPermission";
    }

    @GetMapping("/update")
    public String update(FileUploadDTO fileUploadDTO,
                         @RequestParam(name = "code", defaultValue = "") String code,
                         Model m) {
        if (code.isEmpty()) return "redirect:/files/upload";
        FileModel fileModel = fileService.getFileModel(code);
        if (fileModel == null) return "redirect:/files/upload";
        m.addAttribute("file", fileModel);
        return "files/update";
    }

    @PostMapping(path = "/update", headers = "content-type=multipart/*")
    public String postUpdate(@Valid FileUploadDTO formData,
                             BindingResult bindingResult,
                             Authentication auth) {
        if (bindingResult.hasErrors())
            return "files/upload";
        UserModel user = userRepository.getByUsername(auth.getName());
        FileModel file = fileService.getFileModel(formData.getCode());
        if (permissionService.canUpload(user) && permissionService.canUpdateFile(user, file)) {
            try {
                String fileCode = fileService.updateFile(formData.getFile(),
                        formData.getCode(), formData.getName(), formData.getVisibility()).getCode();
                return "redirect:/files/" + fileCode;
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/files/update?error";
            } catch (FileTooLargeException e) {
                return "redirect:/files/update?fileTooLarge";
            }
        }
        return "redirect:/files/upload?noPermission";
    }

    @RequestMapping("/{filecode}")
    public void serveFile(@PathVariable String filecode, HttpServletRequest request,
                          HttpServletResponse response, Authentication auth)
            throws ServletException, IOException {
        try {
            FileModel fileModel = fileService.getFileModel(filecode);
            if (fileModel.getVisibility() == Visibility.PUBLIC)
                MultipartFileSender.data(fileModel).with(request).with(response).serveResource();
            else if (auth != null)
                MultipartFileSender.data(fileModel).with(request).with(response).serveResource();
        } catch (NotFoundException e) {
            response.sendRedirect("/");
        } catch (IOException e) {
            response.sendRedirect("/");
        }
    }
}

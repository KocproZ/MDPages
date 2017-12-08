package ovh.kocproz.markpages.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.markpages.MultipartFileSender;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.FileModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.exception.IllegalMimeTypeException;
import ovh.kocproz.markpages.exception.NotFoundException;
import ovh.kocproz.markpages.service.FileService;
import ovh.kocproz.markpages.service.PermissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
@Controller
@RequestMapping("/files")
public class UploadController {

    private final PermissionService permissionService;
    private final UserRepository userRepository;
    private final FileService fileService;

    public UploadController(PermissionService permissionService,
                            UserRepository userRepository,
                            FileService fileService) {
        this.permissionService = permissionService;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    @GetMapping("")
    public String files() {
        return "redirect:/files/upload"; //TODO Some kind of files list
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public String postUpload(@RequestParam(name = "name") String name,
                             @RequestParam(name = "file") MultipartFile file,
                             @RequestParam(name = "visibility") Visibility visibility,
                             Authentication auth) {
        UserModel user = userRepository.getByUsername(auth.getName());
        if (permissionService.canUpload(user)) {
            try {
                String fileCode = fileService.save(file, name, user, visibility);
                return "redirect:/files/" + fileCode;
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/files/upload?error";
            } catch (IllegalMimeTypeException e) {
                return "redirect:/files/upload?illegalMimeType";
            }
        }
        return "redirect:/files/upload?noPermission";
    }

    @GetMapping("/{filecode}")
    public void showImage(@PathVariable String filecode, HttpServletRequest request,
                          HttpServletResponse response, Authentication auth)
            throws ServletException, IOException {
        try {
            FileModel fileModel = fileService.getFileModel(filecode);
            if (auth != null)
                MultipartFileSender.data(fileModel).with(request).with(response).serveResource();
            else
                response.sendRedirect("/");
        } catch (NotFoundException e) {
            response.sendRedirect("/");
        } catch (IOException e) {
            response.sendRedirect("/");
        }
    }
}

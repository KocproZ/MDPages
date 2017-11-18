package ovh.kocproz.markpages.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.exception.FilenameTakenException;
import ovh.kocproz.markpages.service.FileService;
import ovh.kocproz.markpages.service.PermissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
@Controller
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


    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public String postUpload(@RequestParam(name = "name") String name,
                             @RequestParam(name = "file") MultipartFile file,
                             Principal principal) {
        UserModel user = userRepository.getByUsername(principal.getName());
        if (permissionService.canUpload(user)) {
            try {
                name = fileService.save(file, name, user);
                return "redirect:/edit/uploadComplete?name=" + name;
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/edit/upload?error";
            } catch (FilenameTakenException e) {
                return "redirect:/edit/upload?filenameTaken";
            }
        }
        return "redirect:/edit/upload?noPermission";

    }

    @GetMapping("/file/{filename}")
    public void showImage(@PathVariable String filename, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(fileService.getData(true, filename));

        response.getOutputStream().close();
    }
}

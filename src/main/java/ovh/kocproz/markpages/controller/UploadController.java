package ovh.kocproz.markpages.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.FileModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.exception.FilenameTakenException;
import ovh.kocproz.markpages.exception.IllegalMimeTypeException;
import ovh.kocproz.markpages.service.FileService;
import ovh.kocproz.markpages.service.PermissionService;

import javax.servlet.ServletException;
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
                             @RequestParam(name = "visibility") Visibility visibility,
                             Authentication auth) {
        UserModel user = userRepository.getByUsername(auth.getName());
        if (permissionService.canUpload(user)) {
            try {
                name = fileService.save(file, name, user, visibility);
                return "redirect:/uploadComplete?name=" + name;
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/upload?error";
            } catch (FilenameTakenException e) {
                return "redirect:/upload?filenameTaken";
            }catch (IllegalMimeTypeException e){
                return "redirect:/upload?illegalMimeType";
            }
        }
        return "redirect:/edit/upload?noPermission";

    }

    @GetMapping("/file/{filename}")
    public void showImage(@PathVariable String filename, HttpServletResponse response, Principal principal)
            throws ServletException, IOException {
        FileModel fileModel = fileService.getData(principal != null, filename);
        response.setContentType(fileModel.getMimeType());
        response.getOutputStream().write(fileModel.getData());

        response.getOutputStream().close();
    }
}

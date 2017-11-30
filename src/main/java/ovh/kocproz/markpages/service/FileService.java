package ovh.kocproz.markpages.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.markpages.Util;
import ovh.kocproz.markpages.Visibility;
import ovh.kocproz.markpages.data.model.FileModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.FileRepository;
import ovh.kocproz.markpages.exception.IllegalMimeTypeException;
import ovh.kocproz.markpages.exception.NotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
@Service
public class FileService {

    private static List<String> allowedMimeTypes = Arrays.asList("image/gif", "image/jpeg", "image/png", "image/tiff", "audio/mpeg", "audio/x-wav");

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    public String save(MultipartFile file, String name, UserModel creator, Visibility visibility) throws IOException {
        byte[] data = file.getBytes();
        InputStream is = new ByteArrayInputStream(data);
        String mimeType = URLConnection.guessContentTypeFromStream(is);

        if (!allowedMimeTypes.contains(mimeType)) throw new IllegalMimeTypeException();

        if (name == null || name.length() < 3) {
            name = "Name not given";
        }

        FileModel fileModel = new FileModel();
        fileModel.setData(data);
        fileModel.setName(name);
        fileModel.setCode(Util.randomString(8));
        fileModel.setCreator(creator);
        fileModel.setVisibility(visibility);
        fileModel.setMimeType(mimeType);
        fileRepository.save(fileModel);
        return fileModel.getCode();
    }

    public FileModel getData(boolean loggedIn, String code) {
        FileModel fileModel = fileRepository.findFirstByCode(code);
        if (fileModel == null || (fileModel.getVisibility() == Visibility.AUTHORIZED && !loggedIn))
            throw new NotFoundException();
        return fileModel;
    }

}

package ovh.kocproz.markpages.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.markpages.data.model.FileModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.FileRepository;
import ovh.kocproz.markpages.exception.FilenameTakenException;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
@Service
public class FileService {

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    public String save(MultipartFile file, String name, UserModel creator) throws IOException, FilenameTakenException {

        if (name == null || name.length() < 3) {
            name = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        }
        FileModel check = fileRepository.findFirstByName(name);
        if (check != null) {
            throw new FilenameTakenException();
        }
        FileModel fileModel = new FileModel();
        fileModel.setData(file.getBytes());
        fileModel.setName(name);
        fileModel.setCreator(creator);
        fileRepository.save(fileModel);
        return name;
    }

    public byte[] getData(boolean loggedIn, String name) {
        FileModel fileModel = fileRepository.findFirstByName(name);
        return fileModel.getData();
    }

}

package ovh.kocproz.mdpages.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ovh.kocproz.mdpages.Util;
import ovh.kocproz.mdpages.Visibility;
import ovh.kocproz.mdpages.data.model.FileModel;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.data.repository.FileRepository;
import ovh.kocproz.mdpages.exception.FileTooLargeException;
import ovh.kocproz.mdpages.exception.NotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

/**
 * @author Hubertus
 * Created 18.11.2017
 */
@Service
public class FileService {

    private FileRepository fileRepository;
    @Value("${maxFileSize:8388608}")
    private long MAX_FILE_SIZE;

    public FileService(FileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    public FileModel saveFile(MultipartFile file, String name, UserModel creator, Visibility visibility) throws IOException {
        if (name == null || name.length() < 3) {
            name = "Name not given";
        }

        String code = Util.randomString(8);

        FileModel fileModel = updateFile(file, code, name, visibility);
        fileModel.setCreator(creator);
        fileRepository.save(fileModel);
        return fileModel;
    }

    public FileModel updateFile(MultipartFile file, String code, String name, Visibility visibility) throws IOException {
        if (file.getSize() > MAX_FILE_SIZE) throw new FileTooLargeException();
        byte[] data = file.getBytes();
        InputStream is = new ByteArrayInputStream(data);
        String mimeType = URLConnection.guessContentTypeFromStream(is);

        FileModel fileModel = fileRepository.findFirstByCode(code);
        boolean exists = fileModel != null;
        if (!exists)
            fileModel = new FileModel();

        fileModel.setName(name);
        fileModel.setData(data);
        fileModel.setLastEdited(new Date());
        fileModel.setVisibility(visibility);
        fileModel.setMimeType(mimeType);
        fileModel.setCode(code);
        if (exists)
            fileRepository.save(fileModel);

        return fileModel;
    }

    public FileModel getFileModel(String code) throws NotFoundException {
        FileModel fileModel = fileRepository.findFirstByCode(code);
        if (fileModel == null)
            throw new NotFoundException();

        return fileModel;
    }

    public List<FileModel> getFiles() {
        return fileRepository.findAll();
    }

    public List<FileModel> getFiles(Visibility visibility) {
        return fileRepository.findAllByVisibility(visibility);
    }

}

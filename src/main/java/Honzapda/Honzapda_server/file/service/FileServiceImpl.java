package Honzapda.Honzapda_server.file.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements FileService{

    @Autowired
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;

    public FileServiceImpl(Storage storage) {
        this.storage = storage;
    }
    @Override
    public List<String> uploadObject(List<MultipartFile> multipartFiles) throws IOException{

        List<String> uuids = new ArrayList<>();
        for(MultipartFile image : multipartFiles){

            String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
            String ext = image.getContentType();

            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext)
                            .build(),
                    image.getInputStream()
            );

            uuids.add("https://storage.googleapis.com/"+bucketName+"/"+uuid);
        }

        return uuids;

    }

    @Override
    public void deleteObject(String objectName) {

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Blob blob = storage.get(bucketName, objectName);
        if (blob == null) {
            System.out.println("The object " + objectName + " wasn't found in " + bucketName);
            return;
        }

        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        storage.delete(bucketName, objectName,precondition);

        System.out.println("Object " + objectName + " was deleted from " + bucketName);
    }


}

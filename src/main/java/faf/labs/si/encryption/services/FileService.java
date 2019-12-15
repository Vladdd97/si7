package faf.labs.si.encryption.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private Path hostPath = Paths.get("C:\\Windows\\System32\\drivers\\etc\\hosts");
    private File userFile = new File("C:\\Users\\Vlad\\Desktop\\si_lab_7\\check.txt");

    public void unblockSite(String text) throws IOException {
        List<String> list = Files.readAllLines(hostPath);
        String line = list.stream().filter(x -> x.contains(text)).limit(1).collect(Collectors.joining(""));
        list.removeAll(Collections.singletonList(line));
        Files.write(hostPath, list);
    }

    public void blockSite(String text) throws IOException {
        List<String> list = Files.readAllLines(hostPath);
        list.add("0.0.0.0 " + text);
        Files.write(hostPath, list);
    }

    public void saveFile(MultipartFile multipartFile) throws IOException {
        multipartFile.transferTo(userFile);
    }

    public List<String> readFileContent() throws IOException {
        return Files.readAllLines(userFile.toPath());
    }

}

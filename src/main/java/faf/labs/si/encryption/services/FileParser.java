package faf.labs.si.encryption.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileParser {

    Path hostPath = Paths.get("C:\\Windows\\System32\\drivers\\etc\\hosts");

    public void unblockSite(String text) throws IOException {
        List<String> list = Files.readAllLines(hostPath);
        String line = list.stream().filter(x -> x.contains(text)).limit(1).collect(Collectors.joining(""));
        list.remove(line);
        Files.write(hostPath, list);
    }



    public void blockSite(String text) throws IOException {
        List<String> list = Files.readAllLines(hostPath);
        String line = list.stream().filter(x -> x.contains(text)).limit(1).collect(Collectors.joining(""));
        list.add(text);
        Files.write(hostPath, list);
    }

}

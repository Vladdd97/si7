package faf.labs.si.encryption.domain.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConnectionTypeChecker implements Runnable {

    private Path hostPath = Paths.get(PathManager.HOST_PATH);
    private Path wifiHostPath = Paths.get(PathManager.WIFI_HOST_PATH);
    private Path lanHostPath = Paths.get(PathManager.LAN_HOST_PATH);


    @Override
    public void run() {
        ConnectionType connectionType = InternetUtils.checkConnectionType();
        if (connectionType.equals(ConnectionType.LAN)){
            System.out.println("Is lan connection");
            try {
                copyFileContent(lanHostPath, hostPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            System.out.println("Is WIFI connection");
            try {
                copyFileContent(wifiHostPath, hostPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFileContent(Path fromFile, Path toFile) throws IOException {
        List<String> list = Files.readAllLines(fromFile);
        Files.write(toFile, list);
    }
}

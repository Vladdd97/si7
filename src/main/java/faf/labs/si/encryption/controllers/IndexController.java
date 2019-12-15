package faf.labs.si.encryption.controllers;

import faf.labs.si.encryption.domain.User;
import faf.labs.si.encryption.services.Encoder;
import faf.labs.si.encryption.services.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/index")
public class IndexController {

    @Autowired
    private Encoder encoder;
    @Autowired
    private FileParser fileParser;

    private static final int KEY = 3;

    @GetMapping("/credentialsForm")
    public String credentialsForm(Model model){
        model.addAttribute("user",new User());
        return "form";
    }

    @PostMapping("/getCredentials")
    public String getCredentials(@ModelAttribute User user,  Model model) throws IOException {

        String encryptedUsername = (encoder.encryption(user.getUsername(), KEY));
        String encryptedPassword = (encoder.encryption(user.getPassword(), KEY));
        Path path = Paths.get("C:\\Users\\Vlad\\Desktop\\si_lab_7\\testFile.txt");
        Files.write(path, Arrays.asList(encryptedUsername, encryptedPassword));
        model.addAttribute("filePath", path.toString());

        return "success";
    }

    private void displayCredentials(User user){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        System.out.println("\n\n\t Encrypted username password");
        String encryptedUsername = (encoder.encryption(user.getUsername(), KEY));
        String encryptedPassword = (encoder.encryption(user.getPassword(), KEY));
        System.out.println(encryptedUsername);
        System.out.println(encryptedPassword);

        System.out.println("\n\n\t Decrypted username password");
        System.out.println(encoder.decryption(encryptedUsername, KEY));
        System.out.println(encoder.decryption(encryptedPassword, KEY));
    }



}

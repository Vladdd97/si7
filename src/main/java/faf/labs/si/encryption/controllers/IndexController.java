package faf.labs.si.encryption.controllers;

import faf.labs.si.encryption.domain.AccessManagement;
import faf.labs.si.encryption.domain.Credentials;
import faf.labs.si.encryption.domain.User;
import faf.labs.si.encryption.domain.util.ActionType;
import faf.labs.si.encryption.domain.util.PathManager;
import faf.labs.si.encryption.services.Encoder;
import faf.labs.si.encryption.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.WebParam;
import javax.validation.Valid;
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
    private FileService fileService;

    private static final int KEY = 3;

    @GetMapping("/credentialsForm")
    public String credentialsForm(Model model) {
        model.addAttribute("user", new User());
        return "createCredentialsForm";
    }

    @PostMapping("/getCredentials")
    public String getCredentials(@ModelAttribute User user, Model model) throws IOException {

        String encryptedUsername = (encoder.encryption(user.getUsername(), KEY));
        String encryptedPassword = (encoder.encryption(user.getPassword(), KEY));
        Path path = Paths.get(PathManager.SAVE_CREDENTIALS_PATH);
        Files.write(path, Arrays.asList(encryptedUsername, encryptedPassword));
        String message = "File with your credentials was generated in: \n" + path.toString();
        model.addAttribute("message", message);

        return "infoMessage";
    }

    private void displayCredentials(User user) {
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


    @GetMapping("/signInForm")
    public String signInForm(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "signInForm";
    }


    @PostMapping("/checkCredentials")
    public String checkCredentials(@ModelAttribute Credentials credentials, Model model) throws IOException {
        fileService.saveFile(credentials.getFile());
        List<String> list = fileService.readFileContent();
        String username = credentials.getUser().getUsername();
        String password = credentials.getUser().getPassword();
        String decodedUsername = encoder.decryption(list.get(0), KEY);
        String decodedPassword = encoder.decryption(list.get(1), KEY);

        System.out.println("\n\n\t Given username and password: ");
        System.out.println(username);
        System.out.println(password);

        System.out.println("\n\n\t Decoded username and password: ");
        System.out.println(decodedUsername);
        System.out.println(decodedPassword);

        if (username.equals(decodedUsername) && password.equals(decodedPassword)) {
            return "redirect:/accessManagement";
        }
        model.addAttribute("message", "Incorrect credentials");
        return "infoMessage";
    }

    @GetMapping("/accessManagement")
    public String accessManagement(Model model) {
        model.addAttribute("accessManagement", new AccessManagement());
        return "sitesAccessManagement";
    }

    @PostMapping("/changeAccess")
    public String changeAccess(@Valid AccessManagement accessManagement, BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message",
                    bindingResult
                            .getFieldErrors()
                            .stream()
                            .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))

            );
            return "infoMessage";
        }

        if (accessManagement.getActionType() == ActionType.BLOCK_SITE) {
            fileService.blockSite(accessManagement.getSiteName());
        } else {
            fileService.unblockSite(accessManagement.getSiteName());
        }


        model.addAttribute("message", "Chosen site: " + accessManagement.getSiteName() +
                "\nChosen action: " + accessManagement.getActionType().toString());
        return "infoMessage";
    }


}

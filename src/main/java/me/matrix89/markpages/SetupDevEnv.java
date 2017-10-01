package me.matrix89.markpages;

import me.matrix89.markpages.model.PageModel;
import me.matrix89.markpages.model.UserModel;
import me.matrix89.markpages.repository.PageRepository;
import me.matrix89.markpages.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.UUID;

@Component
public class SetupDevEnv {

    @Value("${dev}")
    private Boolean dev = false;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dllAuto;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;

    @PostConstruct
    public void postInit() {
        if (dev && dllAuto.equals("create")) {
            createUsers();
            createPages();
        }
    }

    private void createPages() {
        for (int i = 0; i < 32; i++) {
            PageModel page = new PageModel();
            page.setVisibility("everyone");
            page.setMaintainer(userRepository.findOne(1));
            page.setLastEdited(new Date());
            page.setCreationDate(new Date());
            String randomMarkdown = randomMarkdown();
            page.setContent(randomMarkdown);
            page.setName(UUID.randomUUID().toString());
            pageRepository.save(page);
        }
    }

    private String randomMarkdown() {
        StringBuilder out = new StringBuilder();
        try {
            URL url = new URL("https://jaspervdj.be/lorem-markdownum/markdown.txt");
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                out.append(inputLine).append("\n");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    private void createUsers() {
        UserModel admin = new UserModel();
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole("ROLE_ADMIN");
        admin.setUsername("admin");
        userRepository.save(admin);
    }
}

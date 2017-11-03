package ovh.kocproz.markpages;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.TagModel;
import ovh.kocproz.markpages.data.model.UserModel;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.markpages.data.repository.PageRepository;
import ovh.kocproz.markpages.data.repository.TagRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

@Component
public class SetupDevEnv {

    @Value("${dev}")
    private Boolean dev = false;

    @Value("${dev.generator.pages}")
    private int pagesToGenerate = 64;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dllAuto;


    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;

    @Autowired
    private Logger logger;

    @Autowired
    private PageMaintainerRepository maintainerRepository;

    @PostConstruct
    public void postInit() {
        if (dev && dllAuto.equals("create")) {
            logger.info("Launching in dev mode. Populating database with random data...");
            createUsers();
            createTags();
            createPages();
        }
    }

    private void createPages() {
        Lorem lorem = LoremIpsum.getInstance();
        for (int i = 0; i < pagesToGenerate; i++) {
            PageModel page = new PageModel();
            page.setVisibility(i % 4 == 0 ? PageModel.Visibility.AUTHORIZED : PageModel.Visibility.PUBLIC);
//            page.setMaintainer(i % 6 == 0 ? userRepository.findOne(2l) : userRepository.findOne(1l));
            PageMaintainerModel maintainerModel = new PageMaintainerModel();
            maintainerModel.setPage(page);
            maintainerModel.setRole(i % 3 == 0 ? PageMaintainerModel.Role.OWNER : PageMaintainerModel.Role.MAINTAINER);
            maintainerModel.setUser(i % 6 == 0 ? userRepository.findOne(2l) : userRepository.findOne(1l));
            page.setLastEdited(new Date());
            page.setCreationDate(new Date());
            String randomMarkdown = randomMarkdown();
            page.setContent(randomMarkdown);
//            page.setName(UUID.randomUUID().toString());
            page.setName(lorem.getTitle(3, 10));
            page.setStringId(Util.randomString(8));
            page.addTag(tagRepository.findOne(1l));
            if (i % 2 == 0)
                page.addTag(tagRepository.findOne(2l));
            pageRepository.save(page);
            maintainerRepository.save(maintainerModel);
        }
    }

    private void createTags() {
        TagModel basicTag = new TagModel();
        basicTag.setName("Basic");
        tagRepository.save(basicTag);
        TagModel emptyTag = new TagModel();
        emptyTag.setName("Empty");
        tagRepository.save(emptyTag);
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

        UserModel user = new UserModel();
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole("ROLE_USER");
        user.setUsername("user");
        userRepository.save(user);
    }
}

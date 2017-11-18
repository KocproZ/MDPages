package ovh.kocproz.markpages;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;
import ovh.kocproz.markpages.data.model.PageMaintainerModel;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.PermissionModel;
import ovh.kocproz.markpages.data.model.TagModel;
import ovh.kocproz.markpages.data.repository.PageMaintainerRepository;
import ovh.kocproz.markpages.data.repository.PageRepository;
import ovh.kocproz.markpages.data.repository.TagRepository;
import ovh.kocproz.markpages.data.repository.UserRepository;
import ovh.kocproz.markpages.service.UserService;

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

    private PageRepository pageRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private UserService userService;
    private Pbkdf2PasswordEncoder passwordEncoder;
    private Logger logger;
    private PageMaintainerRepository maintainerRepository;

    public SetupDevEnv(PageRepository pageRepository,
                       UserRepository userRepository,
                       TagRepository tagRepository,
                       UserService userService,
                       Pbkdf2PasswordEncoder passwordEncoder,
                       Logger logger,
                       PageMaintainerRepository maintainerRepository) {
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
        this.maintainerRepository = maintainerRepository;
    }
    
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
            page.setLastEdited(new Date());
            page.setCreationDate(new Date());
            String randomMarkdown = randomMarkdown();
            page.setContent(randomMarkdown);
            page.setName(lorem.getTitle(3, 6));
            page.setStringId(Util.randomString(8));
            page.addTag(tagRepository.findOne(1l));
            if (i % 2 == 0)
                page.addTag(tagRepository.findOne(2l));
            pageRepository.save(page);
            PageMaintainerModel pm = new PageMaintainerModel();
            pm.setPage(page);
            pm.setRole(PageMaintainerModel.Role.OWNER);
            pm.setUser(i % 6 == 0 ? userRepository.findOne(2l) : userRepository.findOne(1l));
            maintainerRepository.save(pm);
            pm = new PageMaintainerModel();
            pm.setPage(page);
            pm.setRole(PageMaintainerModel.Role.MAINTAINER);
            pm.setUser(i % 6 == 0 ? userRepository.findOne(1l) : userRepository.findOne(2l));
            maintainerRepository.save(pm);
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
        userService.createUser("admin", "admin",
                new PermissionModel.Permission[]{PermissionModel.Permission.CREATE,
                        PermissionModel.Permission.UPLOAD,
                        PermissionModel.Permission.ADMIN,
                        PermissionModel.Permission.MODERATE,
                        PermissionModel.Permission.REGISTER});

        userService.createUser("user", "user",
                new PermissionModel.Permission[]{PermissionModel.Permission.CREATE,
                        PermissionModel.Permission.UPLOAD});
    }
}

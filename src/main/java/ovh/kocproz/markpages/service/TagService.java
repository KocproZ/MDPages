package ovh.kocproz.markpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ovh.kocproz.markpages.data.model.PageModel;
import ovh.kocproz.markpages.data.model.TagModel;
import ovh.kocproz.markpages.data.repository.PageRepository;
import ovh.kocproz.markpages.data.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 25.10.2017
 */
@Service
public class TagService {

    private TagRepository tagRepository;
    private PageRepository pageRepository;

    public TagService(TagRepository tagRepository, PageRepository pageRepository) {
        this.tagRepository = tagRepository;
        this.pageRepository = pageRepository;
    }

    public void setTags(List<String> tags, PageModel page) {
        page.clearTags();
        tags.forEach(tag -> {
            if (!tag.isEmpty()) {
                TagModel foundTag = tagRepository.findFirstByName(tag);
                if (foundTag == null) {
                    foundTag = new TagModel();
                    foundTag.setName(tag);
                    tagRepository.save(foundTag);
                }
                page.addTag(foundTag);
            }
        });
    }

    public List<String> getAllTags() {
        Iterable<TagModel> tags = tagRepository.findAll();
        return convert(tags);
    }

    public List<String> getTagsContaining(String fragment) {
        Iterable<TagModel> tags = tagRepository.findAllByNameContaining(
                fragment,
                new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "name")));
        return convert(tags);
    }

    public String getPageTagsString(String pageId) {
        PageModel page = pageRepository.findAllByStringId(pageId);
        StringBuilder builder = new StringBuilder();
        for (TagModel tag : page.getTags()) {
            builder.append(tag.getName());
            builder.append(",");
        }
        builder.delete(builder.length() - 1, builder.length() );
        return builder.toString();
    }

    public List<String> getPageTags(String pageId) {
        PageModel page = pageRepository.findAllByStringId(pageId);
        if (page == null) return new ArrayList<>();
        return convert(page.getTags());
    }

    private List<String> convert(Iterable<TagModel> tags) {
        List<String> tagNames = new ArrayList<>();
        for (TagModel tag : tags) {
            tagNames.add(tag.getName());
        }
        return tagNames;

    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void cleanupTags() {
        Iterable<TagModel> tags = tagRepository.findAll();

        for (TagModel tag : tags) {
            PageModel page = pageRepository.findFirstByTags(tag);
            if (page == null) tagRepository.delete(tag.getId());
        }
    }
}

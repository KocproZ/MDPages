package ovh.kocproz.mdpages.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.kocproz.mdpages.data.model.PageModel;
import ovh.kocproz.mdpages.data.model.TagModel;
import ovh.kocproz.mdpages.data.repository.PageRepository;
import ovh.kocproz.mdpages.data.repository.TagRepository;

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

    /**
     * Sets tags for given page
     *
     * @param tags tags to set
     * @param page page to set tags to
     */
    public void setTags(List<String> tags, PageModel page) {
        page.clearTags();
        tags.forEach(tag -> {
            if (!tag.isEmpty() && tag.length() <= 32) {
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

    /**
     * Returns {@link #convert(Iterable) converted} list of first 10 tags
     * containing given fragment
     *
     * @param fragment string to search for
     * @return list of tags
     */
    public List<String> getTagsContaining(String fragment) {
        Iterable<TagModel> tags = tagRepository.findAllByNameContaining(
                fragment,
                new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "name")));
        return convert(tags);
    }

    public List<String> getAllTagsContaining(String fragment) {
        Iterable<TagModel> tags = tagRepository.findAllByNameContaining(fragment);
        return convert(tags);
    }

    public String getPageTagsString(String pageId) {
        PageModel page = pageRepository.findOneByCode(pageId);
        StringBuilder builder = new StringBuilder();
        for (TagModel tag : page.getTags()) {
            builder.append(tag.getName());
            builder.append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    /**
     * Returns {@link #convert(Iterable) converted} list of page's tags.
     * If page does not exist, returns empty List.
     *
     * @param pageId 8-characters long id of page
     * @return list of tags
     */
    public List<String> getPageTags(String pageId) {
        PageModel page = pageRepository.findOneByCode(pageId);
        if (page == null) return new ArrayList<>();
        return convert(page.getTags());
    }

    /**
     * Converts Iterable of TagModel to List of Strings
     *
     * @param tags Iterable to convert
     * @return list of tags
     */
    private List<String> convert(Iterable<TagModel> tags) {
        List<String> tagNames = new ArrayList<>();
        for (TagModel tag : tags) {
            tagNames.add(tag.getName());
        }
        return tagNames;

    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    @Transactional
    void deleteOrphanedTags() {
        Iterable<TagModel> tags = tagRepository.findAll();

        for (TagModel tag : tags) {
            PageModel page = pageRepository.findFirstByTags(tag);
            if (page == null) tagRepository.delete(tag.getId());
        }
    }
}

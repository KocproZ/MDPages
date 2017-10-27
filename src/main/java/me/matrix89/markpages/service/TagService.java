package me.matrix89.markpages.service;

import me.matrix89.markpages.data.model.PageModel;
import me.matrix89.markpages.data.model.TagModel;
import me.matrix89.markpages.data.repository.PageRepository;
import me.matrix89.markpages.data.repository.TagRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    public List<String> getAllTags() {
        Iterable<TagModel> tags = tagRepository.findAll();
        return convert(tags);
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

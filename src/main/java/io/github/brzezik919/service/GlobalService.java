package io.github.brzezik919.service;

import com.google.common.io.Files;
import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GlobalService {

    public Page<Card> findPaginatedCard(Pageable pageable, List importList){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Card> list;

        if(importList.size() < startItem){
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, importList.size());
            list = importList.subList(startItem, toIndex);
        }

        Page<Card> cardPage
                = new PageImpl<Card>(list, PageRequest.of(currentPage, pageSize), importList.size());

        return cardPage;
    }

    public String getExtensionFile(String filename) {
        return Files.getFileExtension(filename);
    }
}

package org.jbtc.aniapp.model;

import java.util.List;

import lombok.Data;

@Data
public class PaginationAnimes extends Pagination{
    private List<Anime> documents;


    public List<Anime> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Anime> documents) {
        this.documents = documents;
    }
}

package org.jbtc.aniapp.model;

import java.util.List;

import lombok.Data;

@Data
public class PaginationEpisodes extends  Pagination{
    private List<Episode> documents;
}

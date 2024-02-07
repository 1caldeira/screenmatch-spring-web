package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SeriesDTO;
import br.com.alura.screenmatch.model.Series;
import br.com.alura.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository repository;

    public List<SeriesDTO> getAllSeries(){
        return dataConversion(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return dataConversion(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getReleases() {
        return dataConversion(repository.findTop5ByOrderByEpisodesReleaseDateDesc());
    }

    private List<SeriesDTO> dataConversion(List<Series> series){
        return series.stream()
                .map(s -> new SeriesDTO(s.getId(),s.getTitle(),s.getSeasons(),s.getRating(),s.getGenre(),s.getActors(),s.getSynopsis(),s.getPoster()))
                .collect(Collectors.toList());
    }

    public SeriesDTO getById(Long id) {
        Optional<Series> series = repository.findById(id);
        if(series.isPresent()){
            Series s = series.get();
            return new SeriesDTO(s.getId(),s.getTitle(),s.getSeasons(),s.getRating(),s.getGenre(),s.getActors(),s.getSynopsis(),s.getPoster());
        }
        return null;
    }
}

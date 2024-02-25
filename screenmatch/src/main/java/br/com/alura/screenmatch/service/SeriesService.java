package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodeDTO;
import br.com.alura.screenmatch.dto.SeriesDTO;
import br.com.alura.screenmatch.model.Category;
import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.Series;
import br.com.alura.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository repository;

    public List<SeriesDTO> getAllSeries() {
        return dataConversion(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return dataConversion(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getReleases() {
        return dataConversion(repository.recentReleases());
    }

    private List<SeriesDTO> dataConversion(List<Series> series) {
        return series.stream()
                .map(s -> new SeriesDTO(s.getId(), s.getTitle(), s.getSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getSynopsis(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public SeriesDTO getById(Long id) {
        Optional<Series> series = repository.findById(id);
        if (series.isPresent()) {
            Series s = series.get();
            return new SeriesDTO(s.getId(), s.getTitle(), s.getSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getSynopsis(), s.getPoster());
        }
        return null;
    }

    public List<EpisodeDTO> getAllSeasons(Long id) {
        Optional<Series> series = repository.findById(id);
        if (series.isPresent()) {
            Series s = series.get();
            return s.getEpisodes().stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodeDTO> getSeason(Long id, Integer season) {
        return repository.getEpisodesBySeason(id, season).stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> getSeriesByCategory(String category) {
        Category genre = Category.fromString(category);
        return dataConversion(repository.findByGenre(genre));
    }

    public List<EpisodeDTO> getTopEpisodes(Long id) {
        return repository.top5Episodes(id).stream()
                .map(e -> new EpisodeDTO(e.getSeason(),e.getEpisodeNumber(),e.getTitle()))
                .collect(Collectors.toList());
    }
}

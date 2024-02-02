package br.com.alura.screenmatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SeriesController {

    @GetMapping("/series")
    public String getSeries(){
        return "All the series will be listed here.";
    }
}

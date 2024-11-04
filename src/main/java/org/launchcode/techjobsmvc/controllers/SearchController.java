package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("search")
public class SearchController {

    private static final Map<String, String> columnChoices = new HashMap<>();

    static {
        columnChoices.put("all", "All");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("employer", "Employer");
        columnChoices.put("coreCompetency", "Skill");
        columnChoices.put("location", "Location");
    }

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Search");
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model,
                                       @RequestParam String searchType,
                                       @RequestParam String searchTerm) {
        model.addAttribute("columns", columnChoices);

        String searchTypeLabel = columnChoices.getOrDefault(searchType, "All");
        model.addAttribute("title", "Jobs With " + searchTypeLabel + ": " + searchTerm);

        Iterable jobs;
        if ("all".equals(searchType)) {
            jobs = JobData.findByValue(searchTerm);
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        model.addAttribute("jobs", jobs);
        return "search";
    }
}

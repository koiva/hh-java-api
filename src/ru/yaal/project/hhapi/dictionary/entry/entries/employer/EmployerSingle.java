package ru.yaal.project.hhapi.dictionary.entry.entries.employer;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


import java.net.URL;

public class EmployerSingle extends EmployerInVacancy {
    public static final EmployerSingle NULL_EMPLOYER = new EmployerSingle();
    @Setter
    private String type;
    @Getter
    @Setter
    @SerializedName("site_url")
    @SuppressWarnings("PMD.UnusedPrivateField")
    private URL siteUrl;
    @Getter
    @Setter
    @SuppressWarnings("PMD.UnusedPrivateField")
    private String description;
    @Getter
    @Setter
    @SerializedName("vacancies_url")
    @SuppressWarnings("PMD.UnusedPrivateField")
    private URL vacanciesUrl;

    public EmployerType getType() {
        return EmployerType.EMPLOYER_TYPES.getById(type);
    }
}
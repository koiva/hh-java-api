package ru.yaal.project.hhapi.vacancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yaal.project.hhapi.search.ISearch;
import ru.yaal.project.hhapi.search.SearchException;
import ru.yaal.project.hhapi.search.SearchParamNames;
import ru.yaal.project.hhapi.search.SearchParameterBox;

import java.util.Set;

public class IterableVacancySearch extends AbstractVacancySearch<IterableVacancyList> {
    private static final Logger LOG = LoggerFactory.getLogger(IterableVacancySearch.class);
    private Integer vacancyLimit;

    public IterableVacancySearch() throws SearchException {
        this(DEFAULT_VACANCIES_LIMIT);
    }

    public IterableVacancySearch(int vacancyLimit) throws SearchException {
        if (vacancyLimit < MIN_VACANCIES_LIMIT || vacancyLimit > MAX_VACANCIES_LIMIT) {
            throw new SearchException(
                    "���������� �������� ����� ���� � �������� �� %d �� %d. ��������: %d.",
                    MIN_VACANCIES_LIMIT, MAX_VACANCIES_LIMIT, vacancyLimit);
        }
        this.vacancyLimit = vacancyLimit;
    }

    @Override
    public IterableVacancyList search() throws SearchException {
        verifySearchParameters(getParameterBox());
        if (vacancyLimit <= PerPage.MAX_PER_PAGE) {
            addParameter(new PerPage(vacancyLimit));
        } else {
            addParameter(new PerPage(PerPage.MAX_PER_PAGE));
        }
        try {
            ISearch<PageableVacancyList> search = new PageableVacancySearch(vacancyLimit);
            search.addParameter(getParameterBox());
            PageableVacancyList pageableVacancyList = search.search();
            return new IterableVacancyList(pageableVacancyList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SearchException(e);
        }
    }

    private void verifySearchParameters(SearchParameterBox parameterBox) throws SearchException {
        Set<SearchParamNames> keys = parameterBox.getParameterMap().keySet();
        for (SearchParamNames name : keys) {
            if (name == SearchParamNames.PAGE) {
                throw new SearchException("� IterableVacancySearch ������ �������� ����� ��������.");
            }
            if (name == SearchParamNames.PER_PAGE) {
                throw new SearchException("� IterableVacancySearch ������ �������� ���������� �������� �� ��������.");
            }
        }

    }
}
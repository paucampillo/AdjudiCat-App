package com.adjudicat.migration;

import com.adjudicat.exception.AdjudicatBaseException;

public interface MigrationService {
    void fetchFromApi() throws AdjudicatBaseException;
}

package com.adjudicat.migration;

import com.adjudicat.controller.impl.BaseController;
import com.adjudicat.exception.AdjudicatBaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/migration")
public class MigrationController extends BaseController {

    private final MigrationService migrationService;

    @GetMapping("/execute")
    public void fetchFromApi() throws AdjudicatBaseException {
        migrationService.fetchFromApi();
    }

}

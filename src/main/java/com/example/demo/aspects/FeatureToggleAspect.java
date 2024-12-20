package com.example.demo.aspects;

import com.example.demo.exceptions.FeatureNotAvailableException;
import com.example.demo.service.FeatureToggleService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Before("@annotation(com.example.demo.annotation.FeatureToggle) && execution(* com.example.demo.service.*.*(..))")
    public void checkFeatureAvailability() throws FeatureNotAvailableException {
        if (!featureToggleService.isCosmoCatsEnabled()) {
            throw new FeatureNotAvailableException("CosmoCats feature is not available.");
        }
    }
}

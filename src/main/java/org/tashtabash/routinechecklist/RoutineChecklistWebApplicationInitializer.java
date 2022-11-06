package org.tashtabash.routinechecklist;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class RoutineChecklistWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { RoutineChecklistConfiguration.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}

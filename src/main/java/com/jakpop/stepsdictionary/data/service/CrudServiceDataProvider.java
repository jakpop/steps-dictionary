package com.jakpop.stepsdictionary.data.service;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

public class CrudServiceDataProvider<T, F> extends FilterablePageableDataProvider<T, F> {
    private final CrudService<T, String> service;
    private List<QuerySortOrder> defaultSortOrders;

    public CrudServiceDataProvider(CrudService<T, String> service, QuerySortOrder... defaultSortOrders) {
        this.service = service;
        this.defaultSortOrders = Arrays.asList(defaultSortOrders);
    }

    protected Page<T> fetchFromBackEnd(Query<T, F> query, Pageable pageable) {
        return this.service.list(pageable);
    }

    protected List<QuerySortOrder> getDefaultSortOrders() {
        return this.defaultSortOrders;
    }

    protected int sizeInBackEnd(Query<T, F> query) {
        return this.service.count();
    }
}


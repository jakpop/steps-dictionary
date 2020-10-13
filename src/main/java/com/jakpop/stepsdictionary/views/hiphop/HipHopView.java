package com.jakpop.stepsdictionary.views.hiphop;

import java.util.Optional;

import com.jakpop.stepsdictionary.data.entity.HipHopStep;
import com.jakpop.stepsdictionary.data.entity.Person;
import com.jakpop.stepsdictionary.data.service.HipHopStepService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.jakpop.stepsdictionary.views.main.MainView;

@Route(value = "steps/hiphop", layout = MainView.class)
@PageTitle("Hip Hop")
@CssImport("./styles/views/hiphop/hip-hop-view.css")
public class HipHopView extends Div {

    private Grid<HipHopStep> grid;

    private TextField name = new TextField();
    private TextField creator = new TextField();
    private TextField period = new TextField();
    private TextField description = new TextField();
    private TextField videoUrl = new TextField();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<HipHopStep> binder;

    private HipHopStep step = new HipHopStep();

    private HipHopStepService hipHopStepService;

    public HipHopView(@Autowired HipHopStepService hipHopStepService) {
        setId("hip-hop-view");
        this.hipHopStepService = hipHopStepService;
        // Configure Grid
        grid = new Grid<>(HipHopStep.class);
        grid.setColumns("name", "creator", "period", "description", "videoUrl");
        grid.setDataProvider(new CrudServiceDataProvider<HipHopStep, Void>(hipHopStepService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<HipHopStep> stepFromBackend= hipHopStepService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (stepFromBackend.isPresent()) {
                    populateForm(stepFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new Binder<>(HipHopStep.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.step == null) {
                    this.step = new HipHopStep();
                }
                binder.writeBean(this.step);
                hipHopStepService.update(this.step);
                clearForm();
                refreshGrid();
                Notification.show("Step details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the step details.");
            }
        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, name, "Name");
        addFormItem(editorDiv, formLayout, creator, "Creator");
        addFormItem(editorDiv, formLayout, period, "Period");
        addFormItem(editorDiv, formLayout, description, "Description");
        addFormItem(editorDiv, formLayout, videoUrl, "Video Url");
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(HipHopStep value) {
        this.step = value;
        binder.readBean(this.step);
    }
}

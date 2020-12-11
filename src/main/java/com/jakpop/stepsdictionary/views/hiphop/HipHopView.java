package com.jakpop.stepsdictionary.views.hiphop;

import java.util.List;
import java.util.Optional;

import com.jakpop.stepsdictionary.data.entity.steps.HipHopStep;
import com.jakpop.stepsdictionary.data.entity.enums.Period;
import com.jakpop.stepsdictionary.data.entity.users.User;
import com.jakpop.stepsdictionary.data.service.CrudServiceDataProvider;
import com.jakpop.stepsdictionary.data.service.HipHopStepService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.jakpop.stepsdictionary.views.main.MainView;

@Route(value = "steps/hiphop", layout = MainView.class)
@PageTitle("Hip Hop Steps")
@CssImport("./styles/views/hiphop/hip-hop-view.css")
public class HipHopView extends Div {

    private Grid<HipHopStep> grid;

    private TextField name = new TextField();
    private TextField creator = new TextField();
    private ComboBox<String> period = new ComboBox<>();
    private TextField description = new TextField();
    private TextField videoUrl = new TextField();

    private Button save = new Button("Save");
    private Button search = new Button("Search");
    private Button delete = new Button("Delete");

    private Binder<HipHopStep> binder;

    private HipHopStep step = new HipHopStep();

    private HipHopStepService hipHopStepService;

    public HipHopView(@Autowired HipHopStepService hipHopStepService) {
        setId("hip-hop-view");
        this.hipHopStepService = hipHopStepService;
        // Configure Grid
        grid = new Grid<>(HipHopStep.class);
        grid.setColumns("name", "period");
        grid.addComponentColumn(step -> {
            Anchor anchor = new Anchor();
            anchor.setHref(step.getVideoUrl());
            anchor.setText("(click)");
            anchor.setTarget("_blank");
            return anchor;
        }).setHeader("Video Url");
        grid.setDataProvider(new CrudServiceDataProvider<HipHopStep, Void>(hipHopStepService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // Configure ComboBox
        period.setItems(Period.OLD_SCHOOL.getDates(), Period.MIDDLE_SCHOOL.getDates(), Period.NEW_SCHOOL.getDates(), Period.OTHER.getDates());

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

        search.addClickListener(e -> {
            List<HipHopStep> steps = hipHopStepService.findByParams(name.getValue(), period.getValue());
            clearForm();
            refreshGrid(steps);
        });

        delete.addClickListener(e -> {
            deleteStep(this.step);
            refreshGrid();
        });

        User user = VaadinSession.getCurrent().getAttribute(User.class);

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        if (user != null) {
            createEditorLayout(splitLayout);
        }

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
        addFormItem(editorDiv, formLayout, period, "Creation period");
        addFormItem(editorDiv, formLayout, videoUrl, "Video Url");
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        search.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(save, search, delete);
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
        grid.setItems(hipHopStepService.findByParams(null, null));
    }

    private void refreshGrid(List<HipHopStep> steps) {
        grid.setItems(steps);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(HipHopStep value) {
        this.step = value;
        binder.readBean(this.step);
    }

    private void deleteStep(HipHopStep step) {
        hipHopStepService.delete(step.getId());
    }
}

package com.jakpop.stepsdictionary.views.dancehall;

import java.util.List;
import java.util.Optional;

import com.jakpop.stepsdictionary.data.entity.enums.Role;
import com.jakpop.stepsdictionary.data.entity.steps.DancehallStep;
import com.jakpop.stepsdictionary.data.entity.enums.Period;
import com.jakpop.stepsdictionary.data.entity.enums.Type;
import com.jakpop.stepsdictionary.data.entity.users.User;
import com.jakpop.stepsdictionary.data.service.CrudServiceDataProvider;
import com.jakpop.stepsdictionary.data.service.DancehallStepsService;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.jakpop.stepsdictionary.views.main.MainView;

@Route(value = "steps/dancehall", layout = MainView.class)
@PageTitle("Dancehall Steps")
@CssImport("./styles/views/dancehall/dancehall-view.css")
public class DancehallView extends Div {

    private Grid<DancehallStep> grid;

    private TextField name = new TextField();
    private TextField creator = new TextField();
    private ComboBox<String> period = new ComboBox<>();
    private ComboBox<String> type = new ComboBox<>();
    private TextField videoUrl = new TextField();

    private Button save = new Button("Save");
    private Button search = new Button("Search");
    private Button refresh = new Button("Refresh");
    private Button delete = new Button("Delete");

    private Binder<DancehallStep> binder;

    private DancehallStep step;

    private User user;

    private DancehallStepsService dancehallStepService;


    public DancehallView(@Autowired DancehallStepsService dancehallStepService) {
        setId("dancehall-view");
        this.dancehallStepService = dancehallStepService;
        user = VaadinSession.getCurrent().getAttribute(User.class);
        // Configure Grid
        grid = new Grid<>(DancehallStep.class);
        grid.setColumns("name", "creator", "period", "type");
        grid.addComponentColumn(step -> {
            Anchor anchor = new Anchor();
            anchor.setHref(step.getVideoUrl());
            if (StringUtils.isNotBlank(anchor.getHref())) {
                anchor.setText("(click)");
                anchor.setTarget("_blank");
            }
            return anchor;
        }).setHeader("Video Url");
        grid.setDataProvider(new CrudServiceDataProvider<DancehallStep, Void>(dancehallStepService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // Configure ComboBox
        period.setItems(Period.OLD_SCHOOL.getName(), Period.MIDDLE_SCHOOL.getName(), Period.NEW_SCHOOL.getName(), Period.OTHER.getName());
        type.setItems(Type.SMOOTH.getName(), Type.BADMAN.getName(), Type.FEMALE.getName(), Type.OTHER.getName());

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<DancehallStep> stepFromBackend = dancehallStepService.get(event.getValue().getId());
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
        binder = new Binder<>(DancehallStep.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        save.addClickListener(e -> {
            try {
                if (this.step == null) {
                    DancehallStep dancehallStep = new DancehallStep();
                    dancehallStep.init(user);
                    binder.writeBean(dancehallStep);
                    dancehallStepService.add(dancehallStep);
                }
                else {
                    binder.writeBean(this.step);
                    dancehallStepService.update(this.step);
                }
                clearForm();
                refreshGrid();
                Notification.show("Step details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the step details.");
            }
        });

        search.addClickListener(e -> {
            List<DancehallStep> steps = dancehallStepService.findByParams(name.getValue(), creator.getValue(), period.getValue(), type.getValue());
            clearForm();
            refreshGrid(steps);
        });

        refresh.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addClickListener(e -> {
            deleteStep(this.step);
            refreshGrid();
        });

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
        addFormItem(editorDiv, formLayout, creator, "Creator");
        addFormItem(editorDiv, formLayout, period, "Creation period");
        addFormItem(editorDiv, formLayout, type, "Type");
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
        if (user.getRole().equals(Role.ADMIN)) {
            delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            buttonLayout.add(save, search, delete);
        } else if (user.getRole().equals(Role.USER)) {
            refresh.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            buttonLayout.add(save, search, refresh);
        }

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
//        grid.select(null);
//        grid.getDataProvider().refreshAll();
        grid.setItems(dancehallStepService.getAll());
    }

    private void refreshGrid(List<DancehallStep> steps) {
        grid.setItems(steps);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(DancehallStep value) {
        this.step = value;
        binder.readBean(this.step);
    }

    private void deleteStep(DancehallStep step) {
        dancehallStepService.delete(step.getId());
    }
}

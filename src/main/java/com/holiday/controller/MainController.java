package com.holiday.controller;

import com.holiday.api.HttpClient;
import com.holiday.dto.Country;
import com.holiday.dto.Holiday;
import com.holiday.dto.Month;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.HOLDING;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private ChoiceBox<Country> countryId;

    @FXML
    private TableView<Holiday> tableId;

    @FXML
    private TableColumn<Holiday, String> dateId;

    @FXML
    private TableColumn<Holiday, String> localNameId;

    @FXML
    private TableColumn<Holiday, String> nameId;

    @FXML
    private TableColumn<Holiday, Boolean> fixedId;

    @FXML
    private TableColumn<Holiday, Boolean> globalId;

    @FXML
    private TableColumn<Holiday, Integer> launchYearId;

    @FXML
    private TableColumn<Holiday, String> typeId;

    List<Country> countryList;

    List<Holiday> holidays;

    private HttpClient client;

    public void initialize(URL location, ResourceBundle resources) {
        client = new HttpClient();
        countryList = Country.addNotSelected(client.getAllCountries());
        countryId.setItems(FXCollections.observableArrayList(countryList));
        dateId.setCellValueFactory(new PropertyValueFactory<Holiday, String>("date"));
        localNameId.setCellValueFactory(new PropertyValueFactory<Holiday, String>("localName"));
        nameId.setCellValueFactory(new PropertyValueFactory<Holiday, String>("name"));
        fixedId.setCellValueFactory(new PropertyValueFactory<Holiday, Boolean>("fixed"));
        globalId.setCellValueFactory(new PropertyValueFactory<Holiday, Boolean>("global"));
        launchYearId.setCellValueFactory(new PropertyValueFactory<Holiday, Integer>("launchYear"));
        typeId.setCellValueFactory(new PropertyValueFactory<Holiday, String>("type"));
        countryId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Country country = countryList.get((Integer) newValue);
                if (country != null)
                    if (country.isNotNaN()) {
                        holidays = client.getHolidaysOfCountry(country.getKey(), LocalDate.now().getYear());
                        tableId.setItems(FXCollections.observableArrayList(holidays));
                    } else tableId.getItems().clear();
            }

        });
    }

    public void showDiagram() {
        Country country = countryId.getValue();
        if (holidays != null && country.isNotNaN()) {
            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setTitle(country.getValue());
            stage.setWidth(500);
            stage.setHeight(500);

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(countHolidaysInMonth());
            final PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Diagram of holidays");

            ((Group) scene.getRoot()).getChildren().add(chart);
            stage.setScene(scene);
            stage.show();
        }

    }

    private List<PieChart.Data> countHolidaysInMonth() {
        List<PieChart.Data> data = new ArrayList<>();
        List<Holiday>[] gruppedHoliday = new List[12];

        for (Holiday holiday : holidays) {
            int month = Integer.valueOf(holiday.getDate().split("-")[1]) - 1;
            if (gruppedHoliday[month] == null)
                gruppedHoliday[month] = new ArrayList<Holiday>();
            gruppedHoliday[month].add(holiday);
        }

        for (int i = 0; i < gruppedHoliday.length; i++) {
            if (gruppedHoliday[i] != null) {
                double percent = (double) gruppedHoliday[i].size() / (double) holidays.size() * 100.0;
                data.add(new PieChart.Data(Month.getMonthByNumber(i + 1), (int) percent));
            }
        }
        return data;
    }
}
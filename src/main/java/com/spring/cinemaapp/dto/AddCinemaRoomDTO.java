package com.spring.cinemaapp.dto;

import java.util.List;

public class AddCinemaRoomDTO {

    private Integer numberOfRows;
    private Integer numberOfCols;
    private List<ExtraPriceDTO> extraPrices;

    public AddCinemaRoomDTO(Integer numberOfRows, Integer numberOfCols, List<ExtraPriceDTO> extraPrices) {
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
        this.extraPrices = extraPrices;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(Integer numberOfCols) {
        this.numberOfCols = numberOfCols;
    }

    public List<ExtraPriceDTO> getExtraPrices() {
        return extraPrices;
    }

    public void setExtraPrices(List<ExtraPriceDTO> extraPrices) {
        this.extraPrices = extraPrices;
    }
}

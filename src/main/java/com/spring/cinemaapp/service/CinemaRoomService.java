package com.spring.cinemaapp.service;

import com.spring.cinemaapp.dto.AddCinemaRoomDTO;
import com.spring.cinemaapp.dto.ExtraPriceDTO;
import com.spring.cinemaapp.model.CinemaRoom;
import com.spring.cinemaapp.model.Seat;
import com.spring.cinemaapp.repository.CinemaRoomRepository;
import com.spring.cinemaapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaRoomService {
    private CinemaRoomRepository cinemaRoomRepository;
    private MovieRepository movieRepository;

    @Autowired
    public CinemaRoomService(CinemaRoomRepository cinemaRoomRepository, MovieRepository movieRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.movieRepository = movieRepository;
    }

    public CinemaRoom addCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setNumberOfRows(addCinemaRoomDTO.getNumberOfRows());
        cinemaRoom.setNumberOfCols(addCinemaRoomDTO.getNumberOfCols());

        generateSeatsForCinemaRoom(addCinemaRoomDTO, cinemaRoom);
        generateExtraPricesForCinemaRoom(addCinemaRoomDTO, cinemaRoom);
        return cinemaRoomRepository.save(cinemaRoom);// salvare in db cinema +seats ->avem cascadare
    }

    //Locurile de pe randurile 6 si 7 vor avea valoarea atributului extraPrice egala cu 3
    //Locurile de pe randurile 5 si 6 vor avea valoarea atributului extraPrice egala cu 2
    private void generateExtraPricesForCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, CinemaRoom cinemaRoom) {
        //1. parcurgem lista de extraPrices
        //2. parcurgem randurile de la startingRow la endingRow
        //3. la fiecare loc de pe fiecare rand setam extraPrice curent

        for (ExtraPriceDTO extraPriceDTO : addCinemaRoomDTO.getExtraPrices()) {
            for (int i = extraPriceDTO.getStartingRow(); i <= extraPriceDTO.getEndingRow(); i++) {
                for (int j = 0; j < addCinemaRoomDTO.getNumberOfCols(); j++) {
                    Seat seat = getSeatByRowAndCol(cinemaRoom, i, j + 1).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the seat was not found"));
                    seat.setExtraPrice(extraPriceDTO.getExtraPrice());
                }
            }
        }
    }

    private void generateSeatsForCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, CinemaRoom cinemaRoom) {
        for (int i = 0; i < addCinemaRoomDTO.getNumberOfRows(); i++) {
            for (int j = 0; j < addCinemaRoomDTO.getNumberOfCols(); j++) {
                Seat seat = new Seat();
                seat.setSeatRow(i + 1);
                seat.setSeatCol(j + 1);
                seat.setExtraPrice(0);
                cinemaRoom.getSeatList().add(seat);
                seat.setCinemaRoom(cinemaRoom);
            }
        }
    }

    public Optional<Seat> getSeatByRowAndCol(CinemaRoom cinemaRoom, Integer row, Integer col) {
        /*for (Seat seat : cinemaRoom.getSeatList()) {
            if (row == seat.getSeatRow() && col == seat.getSeatCol()) {
                return  seat;
            }
        }
        return null;*/

        return cinemaRoom.getSeatList().stream()
                .filter((seat -> seat.getSeatRow() == row && seat.getSeatCol() == col))
                .findFirst();
    }
}

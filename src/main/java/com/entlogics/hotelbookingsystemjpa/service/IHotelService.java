package com.entlogics.hotelbookingsystemjpa.service;

import java.util.List;

import com.entlogics.hotelbookingsystemjpa.dto.BookingDTO;
import com.entlogics.hotelbookingsystemjpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystemjpa.dto.EmployeeDTO;
import com.entlogics.hotelbookingsystemjpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystemjpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystemjpa.dto.RoomDTO;
import com.entlogics.hotelbookingsystemjpa.dto.ServiceDTO;
import com.entlogics.hotelbookingsystemjpa.entity.Hotel;

public interface IHotelService {

	List<HotelDTO> getListOfHotels(String hotel_name);

	HotelInformationDTO getHotelInformation(int hotel_id);

	List<CustomerDTO> getAllCustomersOfHotel(int hotel_id);

	List<BookingDTO> getAllBookingsOfHotel(int hotel_id);

	List<ServiceDTO> getAllServicesOfHotel(int hotel_id);

	List<EmployeeDTO> getAllEmployeesOfHotel(int hotel_id);

	List<RoomDTO> getAllRoomsOfHotel(int hotel_id);

	String addNewHotel(Hotel hotel);

	boolean editHotel(int hotel_id, Hotel hotel);

	boolean deleteHotel(int hotel_id);

}

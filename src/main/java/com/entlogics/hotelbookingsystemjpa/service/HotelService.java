package com.entlogics.hotelbookingsystemjpa.service;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entlogics.hotelbookingsystemjpa.dao.IHotelDAO;
import com.entlogics.hotelbookingsystemjpa.dto.BookingDTO;
import com.entlogics.hotelbookingsystemjpa.dto.CustomerDTO;
import com.entlogics.hotelbookingsystemjpa.dto.EmployeeDTO;
import com.entlogics.hotelbookingsystemjpa.dto.HotelDTO;
import com.entlogics.hotelbookingsystemjpa.dto.HotelInformationDTO;
import com.entlogics.hotelbookingsystemjpa.dto.RoomDTO;
import com.entlogics.hotelbookingsystemjpa.dto.ServiceDTO;
import com.entlogics.hotelbookingsystemjpa.entity.BookingStatus;
import com.entlogics.hotelbookingsystemjpa.entity.Hotel;

// This class 
@Service
public class HotelService implements IHotelService {
	
	private static Logger logger = LogManager.getLogger(HotelService.class);

	// Inject hotel dao interface for calling its methods
	@Autowired
	private IHotelDAO hotelDAO;
	
	// This method returns the list of hotels
	@Override
	@Transactional(readOnly=true)
	public List<HotelDTO> getListOfHotels(String hotel_name) {
		
		// If value of hotel_name is null, make it empty string 
		hotel_name = hotel_name.equals(null) ? "" : hotel_name; 
		
		// Get the list of hotel object array by calling getListOfHotels method of hotelDAO
		List<Object[]> nativeQuery = hotelDAO.getListOfHotels(hotel_name);
		
		// Create List object of HotelDTO to return from Native query
		List<HotelDTO> hotelDTOListNative = new ArrayList<HotelDTO>();

		
		// Loop through all the hotel object, type cast into hotel dto fields and add the hotel dto object to list of hotels
				for (Object[] obj : nativeQuery) {
					HotelDTO hotelDTO = new HotelDTO();
					hotelDTO.setHotel_id((int) obj[0]);
					hotelDTO.setHotel_name((String) obj[1]);
					hotelDTO.setHotel_location((String) obj[2]);
					hotelDTO.setHotel_phone((String) obj[3]);
					hotelDTO.setHotel_email((String) obj[4]);
					hotelDTO.setHotel_rating((int) obj[5]);
					hotelDTO.setPet_friendly((boolean) obj[6]);

					hotelDTOListNative.add(hotelDTO);
				}
		
		// Delegate the call to hotel dao
		return hotelDTOListNative;
	}

	// This method return hotel information
	@Override
	@Transactional(readOnly=true)
	public HotelInformationDTO getHotelInformation(int hotel_id) {
		
		// Create hotel information dto object to return
		HotelInformationDTO hotelDTO = new HotelInformationDTO();
		
		// Initialize Integer ArrayList for setting list properties of hotel dto
		List<Integer> customerIdList = new ArrayList<>();
		List<Integer> bookingIdList = new ArrayList<>();
		List<Integer> serviceIdList = new ArrayList<>();
		List<Integer> employeeIdList = new ArrayList<>();
		List<Integer> roomIdList = new ArrayList<>();
		
		// Call the dao method to get list of hotel information object
		List<Object[]> query = hotelDAO.getHotelInformation(hotel_id);
		
		logger.info("Testing the query "+query.get(0));
		
		// Get the first array of the list
		Object[] hotelArr = query.get(0);
		
		// Set the general hotel information 
		hotelDTO.setHotel_id((int) hotelArr[0]);
		hotelDTO.setHotel_name((String) hotelArr[1]);
		hotelDTO.setHotel_location((String) hotelArr[2]);
		hotelDTO.setHotel_phone((String) hotelArr[3]);
		hotelDTO.setHotel_email((String) hotelArr[4]);
		hotelDTO.setHotel_rating((int) hotelArr[5]);
		hotelDTO.setPet_friendly((boolean) hotelArr[6]);
		
		// Loop through all the hotel objects, if id list doesn't contain that id then add the id to the list
		for (Object[] obj : query ) {				
			
			if (!customerIdList.contains((int)obj[11])) {
				logger.info("Called customer if statement");
				customerIdList.add((int) obj[11]);
			}
			
			if (!serviceIdList.contains((int) obj[10])) {
				logger.info("Called service if statement");
				serviceIdList.add((int) obj[10]);
			}
			
			if (!employeeIdList.contains((int) obj[9])) {
				employeeIdList.add((int) obj[9]);
			}
			
			if (!roomIdList.contains((int) obj[8])) {
				roomIdList.add((int) obj[8]);
			}

			if (!bookingIdList.contains((int) obj[7])) {
				bookingIdList.add((int) obj[7]);
			}

		}
		
		// Set the list properties of the Hotel DTO
		hotelDTO.setHotel_booking(bookingIdList);
		hotelDTO.setHotel_customer(customerIdList);
		hotelDTO.setHotel_employee(employeeIdList);
		hotelDTO.setHotel_room(roomIdList);
		hotelDTO.setHotel_service(serviceIdList);
		
		// Return the hotel dao
		return hotelDTO;
	}

	// This method returns list of customers of hotel 
	@Override
	@Transactional(readOnly=true)
	public List<CustomerDTO> getAllCustomersOfHotel(int hotel_id) {
		
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		
		// Call the hotel dao method to get all customers of hotel list of object
		List<Object[]> queryResult =  hotelDAO.getAllCustomersOfHotel(hotel_id);
		
		// Loop through all the object of queryResult
		for(Object[] obj : queryResult) {
			CustomerDTO customerDTO = new CustomerDTO();
			
			customerDTO.setCustomer_id((int) obj[0]);
			customerDTO.setCustomer_name((String) obj[1]);
			customerDTO.setCustomer_address((String) obj[2]);
			customerDTO.setCustomer_phone((String) obj[3]);
			customerDTO.setCustomer_email((String) obj[4]);
			customerDTO.setCustomer_preferences((String) obj[5]);
			customerDTO.setCustomer_special_needs((String) obj[6]);
			
			customerDTOList.add(customerDTO);
		}
		
		return customerDTOList;
	}

	// This method returns list of all bookings of a hotel
	@Override
	@Transactional(readOnly=true)
	public List<BookingDTO> getAllBookingsOfHotel(int hotel_id) {
		
		List<BookingDTO> bookingDTOList = new ArrayList<>();
			
		// Call the dao method to get all the bookings object of hotel
		List<Object[]> bookingListObject =  hotelDAO.getAllBookingsOfHotel(hotel_id);
		
		for (Object[] booking : bookingListObject) {
			BookingDTO bookingDTO = new BookingDTO();
			
			bookingDTO.setBooking_id((int) booking[0]);
			bookingDTO.setBooking_date_time((Date) booking[1]);
			bookingDTO.setBooking_amount((double) booking[2]);
			bookingDTO.setCheck_in_date_time((Date) booking[3]);
			bookingDTO.setCheck_out_date_Time((Date) booking[4]);
			bookingDTO.setNo_of_adults((Integer) booking[5]);
			bookingDTO.setNo_of_child((Integer) booking[6]);
			bookingDTO.setBooking_status((BookingStatus) booking[7]);
						
			bookingDTOList.add(bookingDTO);
		}
		
		return bookingDTOList;
	}

	// This service method returns the list of all services of a hotel
	@Override
	@Transactional(readOnly=true)
	public List<ServiceDTO> getAllServicesOfHotel(int hotel_id) {
		
		// Delegate the call to hotel dao
		return hotelDAO.getAllServicesOfHotel(hotel_id);
	}

	// This service method return list of employees of a hotel
	@Override
	@Transactional(readOnly=true)
	public List<EmployeeDTO> getAllEmployeesOfHotel(int hotel_id) {
		
		// Create employee dto list 
		List<EmployeeDTO> employeeDTOList = new ArrayList<>();
		
		// Get the list of employee object
		List<Object[]> employeeObjList = hotelDAO.getAllEmployeesOfHotel(hotel_id);
		
		// Loop through list of employee object, set the properties of employeeDTO and add it to the list of employee dto
		for (Object[] employee: employeeObjList) {
			EmployeeDTO employeeDTO = new EmployeeDTO();
			
			employeeDTO.setEmployee_id((int) employee[0]);
			employeeDTO.setEmployee_name((String) employee[1]);
			employeeDTO.setEmployee_salary((float) employee[2]);
			employeeDTO.setEmployee_phone((long) employee[3]);
			employeeDTO.setEmployee_address((String) employee[4]);
			employeeDTO.setEmployee_email((String) employee[5]);
			employeeDTO.setEmployee_joining_date((Date) employee[6]);
			
			employeeDTOList.add(employeeDTO);
		}
		
		// Return list of employee
		return employeeDTOList;
	}

	// This service method return list of all the rooms of a hotel
	@Override
	@Transactional(readOnly=true)
	public List<RoomDTO> getAllRoomsOfHotel(int hotel_id) {
		
		// Create array list of room dto
		List<RoomDTO> roomDTOList = new ArrayList<>();
		
		// Get all rooms of hotel
		List<Object[]> roomObjList = hotelDAO.getAllRoomsOfHotel(hotel_id);
		
		// Loop through list of room object array, create room dto object and set its properties, and add it to room dto list
		for (Object[] room : roomObjList) {
			RoomDTO roomDTO = new RoomDTO();
			
			roomDTO.setRoom_id((int) room[0]);
			roomDTO.setRoom_type((String) room[1]);
			roomDTO.setRoom_price((float) room[2]);
			roomDTO.setRoom_no((int) room[3]);
			roomDTO.setFloor_no((int) room[4]);
			roomDTO.setRoom_area((String) room[5]);
			roomDTO.setRoom_occupancy_details((String) room[6]);
			
			roomDTOList.add(roomDTO);
		}
		
		// Return list of rooms
		return roomDTOList;
	}

	// This service method calls dao method to add a new hotel and return boolean value
	@Override
	@Transactional
	public String addNewHotel(Hotel hotel) {
		
		// Delegate the call to hotel dao
		boolean addedHotel = hotelDAO.addNewHotel(hotel);
		
		return addedHotel == true ? "Hotel Added Successfully" : "Operation Unsuccessful";
	}

	// This service method calls the dao method to edit a hotel and return a booelan value
	@Override
	@Transactional
	public boolean editHotel(int hotel_id, Hotel hotel) {
		
		// Delegate the call to hotel dao
		return hotelDAO.editHotel(hotel_id, hotel);
	}

	// This service method calls the dao method to delete a hotel and return a boolean value
	@Override
	@Transactional
	public boolean deleteHotel(int hotel_id) {
		
		// Delegate the call to hotel dao
		return hotelDAO.deleteHotel(hotel_id);
	}

}

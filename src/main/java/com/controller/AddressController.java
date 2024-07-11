package com.controller;

import com.model.Address;
import com.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/addAddress")
    public String showAddAddressForm(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        return "addAddress";
    }

    @PostMapping("/saveAddress")
    public String saveAddress(HttpSession session, @RequestParam("address") String address,
                              @RequestParam("city") String city, @RequestParam("state") String state,
                              @RequestParam("country") String country) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Address newAddress = new Address();
        newAddress.setUserId(userId);
        newAddress.setAddress(address);
        newAddress.setCity(city);
        newAddress.setState(state);
        newAddress.setCountry(country);

        addressService.saveAddress(newAddress);
        return "redirect:/myAddress";
    }

    @GetMapping("/myAddress")
    public String viewAddresses(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        model.addAttribute("addresses", addresses);
        return "myAddress";
    }

    @GetMapping("/editAddress")
    public String editAddress(@RequestParam("id") Long id, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        Address address = addressService.getAddressById(id);
        model.addAttribute("address", address);
        return "editAddress";
    }

    @PostMapping("/updateAddress")
    public String updateAddress(Address address, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        address.setUserId(userId);
        addressService.saveAddress(address);
        return "redirect:/myAddress";
    }

    @PostMapping("/deleteAddress")
    public String deleteAddress(@RequestParam("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/myAddress";
    }
}

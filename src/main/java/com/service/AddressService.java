package com.service;

import com.model.Address;
import com.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}

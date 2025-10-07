package com.example.service;

import com.example.model.postgres.Bank;
import com.example.repo.potgres.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final BankRepository repository;

    @Autowired
    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    public Bank addBank(Bank bank){
        return repository.save(bank);
    }

    public List<Bank> getAllBanks() {
        return repository.findAll();
    }
}

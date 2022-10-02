package com.lstech.crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lstech.crud.dto.ClientDTO;
import com.lstech.crud.entities.Client;
import com.lstech.crud.repositories.ClientRepository;
import com.lstech.crud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(client -> new ClientDTO(client));
	}
	
	@Transactional
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client client = obj.orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado"));
		return new ClientDTO(client);
	}

	public ClientDTO insert(ClientDTO dto) {
		Client client = new Client();
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
		client = repository.save(client);
		return new ClientDTO(client);
	}
}

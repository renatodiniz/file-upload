package com.upload.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class UploadController {
	
//	@Autowired
//	private ShipwreckRepository shipwreckRepository;
//
//	@RequestMapping(value = "shipwrecks", method = RequestMethod.GET)
//	public List<Shipwreck> list() {
//		return shipwreckRepository.findAll();
//	}
//
//	@RequestMapping(value = "shipwrecks", method = RequestMethod.POST)
//	public Shipwreck create(@RequestBody Shipwreck shipwreck) {
//		return shipwreckRepository.saveAndFlush(shipwreck);
//	}
//
//	@RequestMapping(value = "shipwrecks/{id}", method = RequestMethod.GET)
//	public Shipwreck get(@PathVariable Long id) {
//		return shipwreckRepository.findOne(id);
//	}
//
//	@RequestMapping(value = "shipwrecks/{id}", method = RequestMethod.PUT)
//	public Shipwreck update(@PathVariable Long id, @RequestBody Shipwreck shipwreck) {
//		Shipwreck existingShipwreck = shipwreckRepository.findOne(id);
//		BeanUtils.copyProperties(shipwreck, existingShipwreck);
//		return shipwreckRepository.saveAndFlush(existingShipwreck);
//	}
//
//	@RequestMapping(value = "shipwrecks/{id}", method = RequestMethod.DELETE)
//	public Shipwreck delete(@PathVariable Long id) {
//		Shipwreck existingShipwreck = shipwreckRepository.findOne(id);
//		shipwreckRepository.delete(existingShipwreck);
//		return existingShipwreck;
//	}
	
}

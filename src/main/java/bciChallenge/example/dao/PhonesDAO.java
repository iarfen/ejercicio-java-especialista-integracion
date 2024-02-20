package bciChallenge.example.dao;

import bciChallenge.example.model.Phone;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.CrudRepository;

@Repository
@Configurable
public interface PhonesDAO extends CrudRepository<Phone, Long>{}

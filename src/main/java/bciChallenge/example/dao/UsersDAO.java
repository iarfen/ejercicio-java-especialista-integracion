package bciChallenge.example.dao;

import bciChallenge.example.model.User;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.CrudRepository;

@Repository
@Configurable
public interface UsersDAO extends CrudRepository<User, Long>{}


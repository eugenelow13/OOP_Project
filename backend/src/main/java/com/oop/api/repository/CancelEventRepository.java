package com.oop.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.oop.api.model.CancelEvent;
@Repository
public interface CancelEventRepository extends CrudRepository<CancelEvent, Integer> {
       public Optional<CancelEvent> findById(Integer id);
}


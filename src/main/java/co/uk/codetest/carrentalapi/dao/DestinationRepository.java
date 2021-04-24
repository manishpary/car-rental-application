package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
  Destination findByCityIgnoreCase(String city);
}

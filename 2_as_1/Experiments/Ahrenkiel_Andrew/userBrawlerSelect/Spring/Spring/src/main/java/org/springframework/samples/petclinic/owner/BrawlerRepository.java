package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface BrawlerRepository extends JpaRepository<Brawlers, Integer> {

}
package klasha.store.KlashaCourier.repository;


import klasha.store.KlashaCourier.models.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@RepositoryRestResource(exported = false)
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {

}

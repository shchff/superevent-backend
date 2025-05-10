package ru.shchff.superevent_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shchff.superevent_backend.entities.Venue;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long>
{
    List<Venue> findAllByOwnerId(Long ownerId);
    @Query("SELECT v FROM Venue v " +
            "WHERE (:search IS NULL OR LOWER(v.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:city IS NULL OR v.city = :city) " +
            "AND (:category IS NULL OR v.category.name = :category) " +
            "AND (:price IS NULL OR " +
            "      (:price = 'low' AND v.price <= 10000) OR " +
            "      (:price = 'medium' AND v.price > 10000 AND v.price <= 50000) OR " +
            "      (:price = 'high' AND v.price > 50000))")
    List<Venue> findByFilters(@Param("search") String search,
                              @Param("city") String city,
                              @Param("price") String price,
                              @Param("category") String category);
}

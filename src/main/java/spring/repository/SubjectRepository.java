package spring.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.entity.Subject;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Query("select s from Subject s join fetch s.professors where s.id = :id")
    Optional<Subject> findByIdWithProfessor(@Param("id") Integer id);
    boolean existsByName(String name);
    boolean existsById(@NotNull Integer id);
}

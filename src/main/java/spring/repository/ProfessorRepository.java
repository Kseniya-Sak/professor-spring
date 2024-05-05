package spring.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.entity.Professor;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    @Query("select p from Professor p join fetch p.subjects where p.id = :id")
    Optional<Professor> findByIdWithSubject(@Param("id") Integer id);
    boolean existsById(@NotNull Integer id);
}

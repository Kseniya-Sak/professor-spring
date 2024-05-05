package spring.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.entity.Department;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select d from Department d join fetch d.professors where d.id = :id")
    Optional<Department> findByIdWithProfessor(@Param("id") Integer id);
    boolean existsByName(String name);
    boolean existsById(@NotNull Integer id);
}

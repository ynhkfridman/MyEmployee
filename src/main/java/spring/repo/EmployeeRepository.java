package spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.JPAEntities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}

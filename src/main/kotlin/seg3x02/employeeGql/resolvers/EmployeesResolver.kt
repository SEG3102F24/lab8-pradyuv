package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeesRepository.findById(id).orElse(null)
    }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = createEmployeeInput.name,
            dateOfBirth = createEmployeeInput.dateOfBirth,
            city = createEmployeeInput.city,
            salary = createEmployeeInput.salary,
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        employeesRepository.save(employee)
        return employee
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument createEmployeeInput: CreateEmployeeInput): Employee? {
        val employee = employeesRepository.findById(id).orElse(null)
        return if (employee != null) {
            val updatedEmployee = employee.copy(
                name = createEmployeeInput.name,
                dateOfBirth = createEmployeeInput.dateOfBirth,
                city = createEmployeeInput.city,
                salary = createEmployeeInput.salary,
                gender = createEmployeeInput.gender,
                email = createEmployeeInput.email
            )
            employeesRepository.save(updatedEmployee)
            updatedEmployee
        } else {
            null
        }
    }
}

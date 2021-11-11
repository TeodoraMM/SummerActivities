package repository;
import model.*;
import java.util.List;

public interface RegistrationRepository extends Repository<Integer, Registration> {
    List<Registration> filterByName(String name);
    List<Registration> filterByDate(String dateValue);
    List<Registration> filterBySummerActivityObj(SummerActivity obj);
}

package repository;

import model.SummerActivity;
import model.Registration;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrationInMemoryRepository extends AbstractRepository<Integer, Registration> implements RegistrationRepository{
    @Override
    public List<Registration> filterByName(String name) {
        return getAll().stream().filter(x->x.getName().toLowerCase().equals(name.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Registration> filterByDate(String dateValue) {
        return getAll().stream().filter(x->x.getDate().toLowerCase().equals(dateValue.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Registration> filterBySummerActivityObj(SummerActivity obj) {
        return getAll().stream().filter(x->x.getRequest()==obj).collect(Collectors.toList());
    }
}

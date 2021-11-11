package repository;

import model.SummerActivity;
import java.util.List;
import java.util.stream.Collectors;

public class SummerActivityInMemoryRepository extends AbstractRepository<Integer, SummerActivity> implements SummerActivityRepository {
    @Override
    public List<SummerActivity> filterByName(String name) {
        return getAll().stream().filter(x->x.getName().toLowerCase().equals(name.toLowerCase())).collect(Collectors.toList());
    }
    @Override
    public List<SummerActivity> filterByDate(String dateValue) {
        return getAll().stream().filter(x->x.getDate().equals(dateValue)).collect(Collectors.toList());
    }
}

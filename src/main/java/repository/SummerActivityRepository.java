package repository;
import model.SummerActivity;
import java.util.List;

public interface SummerActivityRepository extends Repository<Integer, SummerActivity> {
    List<SummerActivity> filterByName(String name);
    List<SummerActivity> filterByDate(String dateValue);
}


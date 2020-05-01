import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Person {

    private final String firstname, lastname;
    private final int birthyear, birthmonth, birthday;
    private int age;

    private final int hash;

    private Map<Integer, String> m = new HashMap<>();

    public Person(String firstname, String lastname, int birthyear, int birthmonth, int birthday, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthyear = birthyear;
        this.birthmonth = birthmonth;
        this.birthday = birthday;
        this.age = age;
        this.hash = hashCode();
    }


    @Override
    public int hashCode() {
        final int hash = Objects.hash(firstname, lastname, birthyear, birthmonth, birthday, age);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return birthyear == person.birthyear && birthmonth == person.birthmonth &&
               birthday == person.birthday   &&
               age == person.age             &&
               hash == person.hash           &&
               Objects.equals(firstname, person.firstname) &&
               Objects.equals(lastname, person.lastname);
    }
}

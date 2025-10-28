package main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name = "Ella";
    private final Parrot PARROT;

    @Autowired
    public Person(Parrot PARROT) {
        this.PARROT = PARROT;
    }

    public String getName() {
        return name;
    }

    public Parrot getPARROT() {
        return PARROT;
    }

    public void setName(String name) {
        this.name = name;
    }



}

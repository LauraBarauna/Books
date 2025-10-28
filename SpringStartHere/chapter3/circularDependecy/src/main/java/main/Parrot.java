package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Parrot {
    private String name  = "Koko";
    private final Person PERSON;

    @Autowired
    public Parrot(Person person) {
        this.PERSON = person;
    }

    public String getName() {
        return name;
    }

    public Person getPerson() {
        return PERSON;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parrot{" +
                "name='" + name + '\'' +
                '}';
    }
}

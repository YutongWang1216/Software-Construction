package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;
	private List<Person> friends;
	
	public Person(String name) {
		this.name = name;
		friends = new ArrayList<>();
	}
	
	public void addFriend(Person p) {
		if (friends.contains(p))
			System.out.println("This Edge already exists!");
		else
			friends.add(p);
	}
	
	public List<Person> getFriends() {
		return friends;
	}
	
	public String getName() {
		return name;
	}
}

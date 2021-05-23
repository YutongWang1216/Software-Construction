package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph {
	private List<Person> people;
	private Set<String> nameDic;

	public FriendshipGraph() {
		people = new ArrayList<Person>();
		nameDic = new HashSet<String>();
	}

	public int addVertex(Person p) {
		if (nameDic.contains(p.getName())) {
			System.out.println("The name already exists!");
			return -1;
		}
		people.add(p);
		nameDic.add(p.getName());
		return 0;
	}

	public int addEdge(Person src, Person dst) {
		if (!(nameDic.contains(src.getName()) && nameDic.contains(dst.getName()))) {
			System.out.println("The name does not exist!");
			return -1;
		}
		dst.addFriend(src);
		return 0;
	}

	public int getDistance(Person src, Person dst) {
		if (src == dst)
			return 0;

		Queue<Person> myqueue = new LinkedList<>();
		Map<Person, Integer> distance = new HashMap<>();
		myqueue.offer(src);
		distance.put(src, 0);
		while (!myqueue.isEmpty()) {
			Person cur = myqueue.poll();
			for (Person p : cur.getFriends()) {
				if (!distance.containsKey(p)) {
					distance.put(p, distance.get(cur) + 1);
					myqueue.offer(p);
					if (p == dst)
						return distance.get(p);
				}
			}
		}
		return -1;
	}
	
	public List<Person> getPeople() {
		return people;
	}

	public static void main(String args[]) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
	}
}

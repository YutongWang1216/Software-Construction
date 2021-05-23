package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {
	FriendshipGraph graph = new FriendshipGraph();
	
	/**
	 * Tests addVertex.
	 */
	@Test
	public void addVertextest() {
		Person zhao = new Person("Zhao");
		Person qian = new Person("Qian");
		Person sun = new Person("Sun");
		Person li = new Person("Li");
		graph.addVertex(zhao);
		graph.addVertex(qian);
		graph.addVertex(sun);
		graph.addVertex(li);
		assertTrue(graph.getPeople().contains(zhao));
		assertTrue(graph.getPeople().contains(qian));
		assertTrue(graph.getPeople().contains(sun));
		assertTrue(graph.getPeople().contains(li));
		
		graph.addVertex(zhao);
		assertEquals(4, graph.getPeople().size());
	}
	
	/**
	 * Tests addEdge.
	 */
	@Test
	public void addEdgetest() {
		Person zhou = new Person("Zhou");
		Person wu = new Person("Wu");
		Person zheng = new Person("Zheng");
		Person wang = new Person("Wang");
		Person nobody = new Person("Nobody"); //Not in the graph.
		
		graph.addVertex(zhou);
		graph.addVertex(wu);
		graph.addVertex(zheng);
		graph.addVertex(wang);
		graph.addEdge(zhou, wu);
		graph.addEdge(wu, zhou);
		graph.addEdge(zhou, zheng);
		graph.addEdge(zheng, zhou);
		graph.addEdge(zheng, wang);
		graph.addEdge(wang, zheng);
		assertTrue(zhou.getFriends().contains(wu));
		assertTrue(wu.getFriends().contains(zhou));
		assertTrue(zhou.getFriends().contains(zheng));
		assertTrue(zheng.getFriends().contains(zhou));
		assertTrue(zheng.getFriends().contains(wang));
		assertTrue(wang.getFriends().contains(zheng));

		graph.addEdge(zhou, nobody);
		assertTrue(!zhou.getFriends().contains(nobody)); //Cannot find Nobody's name in nameDic.

		graph.addEdge(zhou, wu);
		assertEquals(2, zhou.getFriends().size()); //No use adding an edge twice.
	}
	
	/**
	 * Tests getDistance.
	 */
	@Test
	public void getDistancetest() {
		Person feng = new Person("Feng");
		Person chen = new Person("Chen");
		Person chu = new Person("chu");
		Person wei = new Person("Wei");
		Person jiang = new Person("Jiang");
		Person shen = new Person("Shen");
		Person han = new Person("Han");
		Person yang = new Person("Yang");
		
		graph.addVertex(feng);
		graph.addVertex(chen);
		graph.addVertex(chu);
		graph.addVertex(wei);
		graph.addVertex(jiang);
		graph.addVertex(shen);
		graph.addVertex(han);
		graph.addVertex(yang);
		graph.addEdge(feng, chen);
		graph.addEdge(chen, feng);
		graph.addEdge(chen, jiang);
		graph.addEdge(jiang, chen);
		graph.addEdge(feng, wei);
		graph.addEdge(wei, feng);
		graph.addEdge(feng, chu);
		graph.addEdge(chu, feng);
		graph.addEdge(chu, wei);
		graph.addEdge(wei, chu);
		graph.addEdge(wei, jiang);
		graph.addEdge(jiang, wei);
		graph.addEdge(wei, han);
		graph.addEdge(han, wei);
		graph.addEdge(han, chu);
		graph.addEdge(chu, han);

		graph.addEdge(han, shen);
		graph.addEdge(shen, han);
		graph.addEdge(jiang, shen);
		graph.addEdge(shen, jiang);
		assertEquals(1, graph.getDistance(feng, chen));
		assertEquals(2, graph.getDistance(feng, jiang));
		assertEquals(0, graph.getDistance(feng, feng));
		assertEquals(3, graph.getDistance(chen, han));
		assertEquals(2, graph.getDistance(chu, shen));
		assertEquals(-1, graph.getDistance(feng, yang));
		assertEquals(-1, graph.getDistance(yang, wei));
	}
}
